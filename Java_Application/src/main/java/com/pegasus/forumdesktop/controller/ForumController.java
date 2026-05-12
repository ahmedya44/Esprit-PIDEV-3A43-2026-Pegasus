package com.pegasus.forumdesktop.controller;

import com.pegasus.controllers.SceneNavigator;
import com.pegasus.forumdesktop.dao.PostDao;
import com.pegasus.forumdesktop.model.Comment;
import com.pegasus.forumdesktop.model.GifItem;
import com.pegasus.forumdesktop.model.Post;
import com.pegasus.forumdesktop.model.PostStatus;
import com.pegasus.forumdesktop.model.TranslationValue;
import com.pegasus.forumdesktop.model.User;
import com.pegasus.forumdesktop.service.ForumService;
import com.pegasus.forumdesktop.view.ForumView;
import javafx.collections.FXCollections;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ForumController {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final ForumView view;
    private final ForumService forumService;
    private User currentUser;
    private Post selectedPost;
    private Comment selectedComment;
    private boolean backOffice;

    public ForumController(ForumView view, ForumService forumService) {
        this.view = view;
        this.forumService = forumService;
        bind();
    }

    public void bootstrapWithForumUser(User forumUser) {
        if (forumUser == null) {
            showError("Please sign in from Pegasus.");
            return;
        }
        currentUser = forumUser;
        backOffice = forumUser.isAdmin() || "admin".equalsIgnoreCase(forumUser.getDtype());

        view.activeUserLabel.setText((backOffice ? "Back Office: " : "Front Office: ") + forumUser.getDisplayName());
        view.showForum(backOffice);
        refreshPosts();
        if (backOffice) {
            refreshAdminComments();
            refreshStats();
        }
    }

    private void bind() {
        view.logoutButton.setOnAction(event -> logout());
        view.searchField.textProperty().addListener((obs, oldValue, newValue) -> refreshPosts());
        view.statusFilter.setOnAction(event -> refreshPosts());
        view.myPostsOnly.setOnAction(event -> refreshPosts());
        view.localeBox.setOnAction(event -> renderSelectedPost());
        view.postsList.getSelectionModel().selectedItemProperty().addListener((obs, oldPost, newPost) -> {
            selectedPost = newPost;
            selectedComment = null;
            renderSelectedPost();
        });
        view.commentsList.getSelectionModel().selectedItemProperty().addListener((obs, oldComment, newComment) -> {
            selectedComment = newComment;
            renderSelectedComment();
        });
        view.adminCommentsList.getSelectionModel().selectedItemProperty().addListener((obs, oldComment, newComment) -> {
            selectedComment = newComment;
            renderSelectedComment();
        });

        view.newPostButton.setOnAction(event -> clearPostForm());
        view.savePostButton.setOnAction(event -> savePost());
        view.deletePostButton.setOnAction(event -> deletePost());
        view.choosePostImageButton.setOnAction(event -> choosePostImage());
        view.clearPostImageButton.setOnAction(event -> clearPostImage());
        view.rateButton.setOnAction(event -> ratePost());
        view.addCommentButton.setOnAction(event -> addComment());
        view.updateCommentButton.setOnAction(event -> updateComment());
        view.deleteCommentButton.setOnAction(event -> deleteComment());
        view.savePostTranslationButton.setOnAction(event -> savePostTranslation());
        view.saveCommentTranslationButton.setOnAction(event -> saveCommentTranslation());
        view.suggestTitleButton.setOnAction(event -> suggestTitle());
        view.suggestContentButton.setOnAction(event -> suggestContent());
        view.autoTranslatePostButton.setOnAction(event -> autoTranslatePost());
        view.autoTranslateCommentButton.setOnAction(event -> autoTranslateComment());
        view.gifSearchButton.setOnAction(event -> pickGif());
        view.refreshAdminCommentsButton.setOnAction(event -> refreshAdminComments());
        view.adminCommentSearch.textProperty().addListener((obs, oldValue, newValue) -> refreshAdminComments());
        view.statsButton.setOnAction(event -> refreshStats());
    }

    private void logout() {
        SceneNavigator.logoutToFrontHome();
        if (view.getRoot().getScene() != null && view.getRoot().getScene().getWindow() instanceof Stage forumStage) {
            forumStage.close();
        }
    }

    private void refreshPosts() {
        if (currentUser == null) {
            return;
        }
        try {
            Integer selectedId = selectedPost == null ? null : selectedPost.getId();
            List<Post> posts = forumService.visiblePosts(currentUser, view.searchField.getText(), selectedStatusFilter());
            if (view.myPostsOnly.isSelected()) {
                posts = posts.stream()
                    .filter(post -> post.getOwnerId() != null && post.getOwnerId() == currentUser.getId())
                    .toList();
            }
            view.postsList.setItems(FXCollections.observableArrayList(posts));
            selectedPost = posts.stream().filter(post -> selectedId != null && post.getId() == selectedId).findFirst().orElse(posts.isEmpty() ? null : posts.get(0));
            if (selectedPost != null) {
                view.postsList.getSelectionModel().select(selectedPost);
            }
            renderSelectedPost();
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void renderSelectedPost() {
        if (selectedPost == null) {
            clearPostForm();
            view.commentsList.setItems(FXCollections.emptyObservableList());
            view.ratingLabel.setText("No post selected");
            return;
        }
        try {
            TranslationValue translated = forumService.translatedPost(selectedPost, view.localeBox.getValue());
            view.postTitle.setText(translated.title());
            view.postContent.setText(translated.content());
            view.postStatus.setValue(selectedPost.getStatus());
            view.postImageName.setText(nullToBlank(selectedPost.getImageName()));
            renderPostImage(selectedPost.getImageName());
            view.allowedViewerIds.setText(selectedPost.getBlacklistedViewerIds().stream().map(String::valueOf).collect(Collectors.joining(", ")));
            view.postMetaLabel.setText("#" + selectedPost.getId()
                + " | owner: " + selectedPost.getOwnerName()
                + " | author email: " + selectedPost.getAuthorEmail()
                + " | created: " + (selectedPost.getCreatedAt() == null ? "-" : DATE_FORMAT.format(selectedPost.getCreatedAt())));
            view.ratingLabel.setText(forumService.ratingSummary(selectedPost.getId()).label());
            view.commentsList.setItems(FXCollections.observableArrayList(forumService.commentsForPost(selectedPost.getId())));
            boolean canManagePost = forumService.canManagePost(currentUser, selectedPost);
            view.savePostButton.setDisable(!canManagePost);
            view.deletePostButton.setDisable(!canManagePost);
            view.savePostTranslationButton.setDisable(!backOffice);
            view.autoTranslatePostButton.setDisable(!backOffice);
            view.addCommentButton.setDisable(!selectedPost.isOpen());
            setPostFormEditable(canManagePost);
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void renderSelectedComment() {
        if (selectedComment == null) {
            view.commentContent.clear();
            view.commentGifUrl.clear();
            return;
        }
        view.commentContent.setText(forumService.translatedComment(selectedComment, view.localeBox.getValue()));
        view.commentGifUrl.setText(nullToBlank(selectedComment.getGifUrl()));
        boolean canManage = forumService.canManageComment(currentUser, selectedComment);
        view.updateCommentButton.setDisable(!canManage);
        view.deleteCommentButton.setDisable(!canManage);
        view.saveCommentTranslationButton.setDisable(!backOffice);
        view.autoTranslateCommentButton.setDisable(!backOffice);
    }

    private void clearPostForm() {
        selectedPost = null;
        view.postsList.getSelectionModel().clearSelection();
        view.postMetaLabel.setText("New post");
        view.postTitle.clear();
        view.postContent.clear();
        view.postStatus.setValue(PostStatus.OPEN);
        view.postImageName.clear();
        renderPostImage(null);
        view.allowedViewerIds.clear();
        view.savePostButton.setDisable(false);
        view.deletePostButton.setDisable(true);
        setPostFormEditable(true);
    }

    private void savePost() {
        try {
            Set<Integer> allowedIds = forumService.parseIds(view.allowedViewerIds.getText());
            if (selectedPost == null) {
                forumService.createPost(currentUser, view.postTitle.getText(), view.postContent.getText(), view.postStatus.getValue(), view.postImageName.getText(), allowedIds);
                showInfo("Post created.");
            } else {
                forumService.updatePost(currentUser, selectedPost, view.postTitle.getText(), view.postContent.getText(), view.postStatus.getValue(), view.postImageName.getText(), allowedIds);
                showInfo("Post updated.");
            }
            refreshPosts();
            if (backOffice) {
                refreshStats();
            }
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void deletePost() {
        if (selectedPost == null || !confirm("Delete selected post?")) {
            return;
        }
        try {
            forumService.deletePost(currentUser, selectedPost);
            showInfo("Post deleted.");
            selectedPost = null;
            refreshPosts();
            if (backOffice) {
                refreshStats();
            }
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void addComment() {
        if (selectedPost == null) {
            showError("Select a post first.");
            return;
        }
        try {
            forumService.addComment(currentUser, selectedPost, view.commentContent.getText(), view.commentGifUrl.getText());
            view.commentContent.clear();
            view.commentGifUrl.clear();
            renderSelectedPost();
            if (backOffice) {
                refreshAdminComments();
                refreshStats();
            }
            showInfo("Comment added.");
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void updateComment() {
        if (selectedComment == null) {
            showError("Select a comment first.");
            return;
        }
        try {
            forumService.updateComment(currentUser, selectedComment, view.commentContent.getText(), view.commentGifUrl.getText());
            renderSelectedPost();
            if (backOffice) {
                refreshAdminComments();
            }
            showInfo("Comment updated.");
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void deleteComment() {
        if (selectedComment == null || !confirm("Delete selected comment?")) {
            return;
        }
        try {
            forumService.deleteComment(currentUser, selectedComment);
            selectedComment = null;
            renderSelectedPost();
            if (backOffice) {
                refreshAdminComments();
                refreshStats();
            }
            showInfo("Comment deleted.");
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void ratePost() {
        if (selectedPost == null) {
            showError("Select a post first.");
            return;
        }
        try {
            forumService.ratePost(currentUser, selectedPost, view.ratingSpinner.getValue());
            view.ratingLabel.setText(forumService.ratingSummary(selectedPost.getId()).label());
            showInfo("Rating saved.");
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void savePostTranslation() {
        if (selectedPost == null) {
            showError("Select a post first.");
            return;
        }
        if (!backOffice) {
            showError("Translations are managed from the back office.");
            return;
        }
        try {
            forumService.savePostTranslation(selectedPost, view.localeBox.getValue(), view.postTitle.getText(), view.postContent.getText());
            showInfo("Post translation saved in " + view.localeBox.getValue() + ".");
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void saveCommentTranslation() {
        if (selectedComment == null) {
            showError("Select a comment first.");
            return;
        }
        if (!backOffice) {
            showError("Translations are managed from the back office.");
            return;
        }
        try {
            forumService.saveCommentTranslation(selectedComment, view.localeBox.getValue(), view.commentContent.getText());
            showInfo("Comment translation saved in " + view.localeBox.getValue() + ".");
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void suggestTitle() {
        try {
            chooseString("Title Suggestions", forumService.suggest("post_title", view.postTitle.getText(), view.postContent.getText(), view.localeBox.getValue(), 4))
                .ifPresent(view.postTitle::setText);
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void suggestContent() {
        try {
            chooseString("Content Suggestions", forumService.suggest("post_content", view.postContent.getText(), view.postTitle.getText(), view.localeBox.getValue(), 4))
                .ifPresent(view.postContent::setText);
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void autoTranslatePost() {
        if (selectedPost == null) {
            showError("Select a post first.");
            return;
        }
        if (!backOffice) {
            showError("Translations are managed from the back office.");
            return;
        }
        try {
            TranslationValue translated = forumService.autoTranslatePost(selectedPost, view.localeBox.getValue());
            view.postTitle.setText(translated.title());
            view.postContent.setText(translated.content());
            showInfo("Translation generated. Click Save Translation to store it.");
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void autoTranslateComment() {
        if (selectedComment == null) {
            showError("Select a comment first.");
            return;
        }
        if (!backOffice) {
            showError("Translations are managed from the back office.");
            return;
        }
        try {
            view.commentContent.setText(forumService.autoTranslateComment(selectedComment, view.localeBox.getValue()));
            showInfo("Comment translation generated. Click Save Comment Translation to store it.");
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void pickGif() {
        try {
            List<GifItem> gifs = forumService.searchGifs(view.gifSearchField.getText(), 12);
            if (gifs.isEmpty()) {
                showError("No GIFs found. Check API keys in the environment.");
                return;
            }
            ListView<GifItem> list = new ListView<>(FXCollections.observableArrayList(gifs));
            list.setPrefSize(760, 430);
            list.setCellFactory(itemList -> new ListCell<>() {
                @Override
                protected void updateItem(GifItem item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                        return;
                    }

                    ImageView preview = gifImage(item.preview().isBlank() ? item.url() : item.preview(), 240, 150);
                    javafx.scene.control.Label title = new javafx.scene.control.Label(item.title());
                    title.setWrapText(true);
                    javafx.scene.control.Label url = new javafx.scene.control.Label(item.url());
                    url.setWrapText(true);
                    url.getStyleClass().add("muted");
                    setText(null);
                    setGraphic(new VBox(6, preview, title, url));
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                }
            });
            list.getSelectionModel().selectFirst();
            Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
            dialog.setTitle("Choose GIF");
            dialog.setHeaderText("Select a GIF");
            dialog.getDialogPane().setContent(list);
            ButtonType use = new ButtonType("Use Selected", ButtonBar.ButtonData.OK_DONE);
            dialog.getButtonTypes().setAll(use, ButtonType.CANCEL);
            dialog.showAndWait()
                .filter(button -> button == use)
                .map(button -> list.getSelectionModel().getSelectedItem())
                .ifPresent(item -> view.commentGifUrl.setText(item.url()));
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private ImageView gifImage(String url, double width, double height) {
        Image image = new Image(url, width, height, true, true, true);
        ImageView view = new ImageView(image);
        view.setFitWidth(width);
        view.setFitHeight(height);
        view.setPreserveRatio(true);
        view.getStyleClass().add("gif-preview");
        return view;
    }

    private void refreshAdminComments() {
        if (currentUser == null || !backOffice) {
            return;
        }
        try {
            view.adminCommentsList.setItems(FXCollections.observableArrayList(forumService.recentComments(view.adminCommentSearch.getText())));
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void refreshStats() {
        if (currentUser == null || !backOffice) {
            return;
        }
        try {
            ForumService.ForumStats stats = forumService.stats();
            StringBuilder builder = new StringBuilder();
            builder.append("Posts by status\n");
            for (PostDao.StatusCount count : stats.countsByStatus()) {
                builder.append(count.status()).append(": ").append(count.count()).append('\n');
            }
            builder.append("\nTotal comments: ").append(stats.totalComments()).append("\n\nTop commented posts\n");
            for (PostDao.TopPost top : stats.topCommentedPosts()) {
                builder.append("#").append(top.post().getId())
                    .append(" ").append(top.post().getTitle())
                    .append(" - ").append(top.commentsCount()).append(" comment(s)\n");
            }
            view.statsArea.setText(builder.toString());
            renderCharts(stats);
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void renderCharts(ForumService.ForumStats stats) {
        view.statusPieChart.setData(FXCollections.observableArrayList(
            stats.countsByStatus().stream()
                .map(count -> new PieChart.Data(count.status().name(), count.count()))
                .toList()
        ));

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (PostDao.TopPost top : stats.topCommentedPosts()) {
            String label = "#" + top.post().getId();
            series.getData().add(new XYChart.Data<>(label, top.commentsCount()));
        }
        view.topPostsChart.getData().setAll(series);
    }

    private PostStatus selectedStatusFilter() {
        String value = view.statusFilter.getValue();
        return value == null || value.equals("ALL") ? null : PostStatus.valueOf(value);
    }

    private boolean confirm(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(message);
        return alert.showAndWait().filter(button -> button.getButtonData().isDefaultButton()).isPresent();
    }

    private java.util.Optional<String> chooseString(String title, List<String> choices) {
        if (choices == null || choices.isEmpty()) {
            showError("No suggestions available. Check GEMINI_API_KEY.");
            return java.util.Optional.empty();
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle(title);
        dialog.setHeaderText(title);
        return dialog.showAndWait();
    }

    private void showInfo(String message) {
        view.feedbackLabel.setText(message);
        view.feedbackLabel.getStyleClass().removeAll("error");
    }

    private void showError(String message) {
        view.feedbackLabel.setText(message);
        if (!view.feedbackLabel.getStyleClass().contains("error")) {
            view.feedbackLabel.getStyleClass().add("error");
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Forum Error");
        alert.setHeaderText("Action failed");
        alert.setContentText(message == null || message.isBlank() ? "Unknown error." : message);
        alert.showAndWait();
    }

    private String nullToBlank(String value) {
        return value == null ? "" : value;
    }

    private void setPostFormEditable(boolean editable) {
        view.postTitle.setDisable(!editable);
        view.postContent.setDisable(!editable);
        view.postStatus.setDisable(!editable);
        view.postImageName.setDisable(!editable);
        view.choosePostImageButton.setDisable(!editable);
        view.clearPostImageButton.setDisable(!editable);
        view.allowedViewerIds.setDisable(!editable);
    }

    private void choosePostImage() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose post image");
        chooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png", "*.webp", "*.gif"),
            new FileChooser.ExtensionFilter("All files", "*.*")
        );

        File selected = chooser.showOpenDialog(view.getRoot().getScene().getWindow());
        if (selected == null) {
            return;
        }

        try {
            String storedName = copyImageToUploads(selected.toPath());
            view.postImageName.setText(storedName);
            renderPostImage(storedName);
            showInfo("Image selected.");
        } catch (IOException ex) {
            showError("Could not copy image: " + ex.getMessage());
        }
    }

    private void clearPostImage() {
        view.postImageName.clear();
        renderPostImage(null);
        showInfo("Image cleared. Save the post to apply.");
    }

    private String copyImageToUploads(Path source) throws IOException {
        String originalName = source.getFileName().toString();
        String extension = "";
        int dot = originalName.lastIndexOf('.');
        if (dot >= 0 && dot < originalName.length() - 1) {
            extension = originalName.substring(dot).toLowerCase();
        }
        String safeBase = dot > 0 ? originalName.substring(0, dot) : originalName;
        safeBase = safeBase.replaceAll("[^A-Za-z0-9_-]+", "-").replaceAll("^-+|-+$", "");
        if (safeBase.isBlank()) {
            safeBase = "forum-image";
        }

        Path uploadDir = forumUploadsDir();
        Files.createDirectories(uploadDir);
        String storedName = safeBase + "-" + UUID.randomUUID().toString().substring(0, 12) + extension;
        Files.copy(source, uploadDir.resolve(storedName), StandardCopyOption.REPLACE_EXISTING);
        return storedName;
    }

    private void renderPostImage(String imageName) {
        if (imageName == null || imageName.isBlank()) {
            view.postImagePreview.setImage(null);
            view.postImageMessage.setText("No image attached.");
            return;
        }

        String source = imageName.trim();
        if (!source.startsWith("http://") && !source.startsWith("https://") && !source.startsWith("file:/")) {
            String uploadsDir = System.getenv().getOrDefault(
                "PEGASUS_FORUM_UPLOADS_DIR",
                forumUploadsDir().toString()
            );
            source = new File(uploadsDir, source).toURI().toString();
        }

        Image image = new Image(source, 320, 190, true, true, true);
        view.postImagePreview.setImage(image);
        view.postImageMessage.setText(imageName);
    }

    private Path forumUploadsDir() {
        String configured = System.getenv("PEGASUS_FORUM_UPLOADS_DIR");
        if (configured != null && !configured.isBlank()) {
            return Path.of(configured);
        }
        return Path.of(System.getProperty("user.home"), "pegasus", "uploads", "forum");
    }
}
