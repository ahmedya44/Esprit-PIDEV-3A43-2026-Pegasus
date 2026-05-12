package com.pegasus.controllers.front;

import com.pegasus.controllers.SceneNavigator;
import com.pegasus.forumdesktop.dao.UserDao;
import com.pegasus.forumdesktop.model.Comment;
import com.pegasus.forumdesktop.model.GifItem;
import com.pegasus.forumdesktop.model.Post;
import com.pegasus.forumdesktop.model.PostStatus;
import com.pegasus.forumdesktop.model.RatingSummary;
import com.pegasus.forumdesktop.service.ForumService;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.io.File;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Comparator;
import javafx.scene.layout.FlowPane;

public class ForumPageController extends FrontNavController {
    @FXML
    private StackPane forumHost;
    @FXML
    private VBox forumLandingPane;
    @FXML
    private VBox forumCardsPane;
    @FXML
    private Label forumStatsLabel;
    @FXML
    private Button forumsTabButton;
    @FXML
    private Button myForumsTabButton;
    @FXML
    private Button createPostTabButton;
    @FXML
    private TextField forumSearchField;
    @FXML
    private ComboBox<String> forumSortBox;
    @FXML
    private StackPane createPostPane;
    @FXML
    private TextField createPostTitleField;
    @FXML
    private TextArea createPostContentField;
    @FXML
    private ComboBox<PostStatus> createPostStatusBox;
    @FXML
    private TextField createPostImageField;
    @FXML
    private TextField createBlacklistEmailsField;
    @FXML
    private FlowPane createBlacklistChipsPane;
    @FXML
    private StackPane editPostPane;
    @FXML
    private TextField editPostTitleField;
    @FXML
    private TextArea editPostContentField;
    @FXML
    private ComboBox<PostStatus> editPostStatusBox;
    @FXML
    private TextField editPostImageField;
    @FXML
    private FlowPane editBlacklistChipsPane;
    @FXML
    private TextField editBlacklistInputField;
    @FXML
    private StackPane commentsOverlay;
    @FXML
    private Label commentsPopupTitle;
    @FXML
    private VBox commentsPopupList;
    @FXML
    private TextArea commentsPopupInput;
    @FXML
    private TextField commentsGifSearchField;
    @FXML
    private HBox commentsGifResultsBox;
    @FXML
    private HBox commentsSelectedGifBox;
    @FXML
    private ImageView commentsSelectedGifPreview;
    @FXML
    private VBox commentsPopupCard;
    @FXML
    private Button commentsSubmitButton;
    @FXML
    private Button commentsCancelUpdateButton;

    private final List<Post> loadedPosts = new ArrayList<>();
    private final Map<Integer, Integer> commentCountByPostId = new HashMap<>();
    private Integer currentForumUserId;
    private ForumService forumService;
    private com.pegasus.forumdesktop.model.User forumUser;
    private boolean currentMyForumsMode = false;
    private Post editingPost;
    private Post activeCommentsPost;
    private Comment editingComment;
    private String selectedGifUrl;
    private final HttpClient imageHttpClient = HttpClient.newHttpClient();
    private final Set<String> createBlacklistEmailsDraft = new LinkedHashSet<>();
    private final Set<String> editBlacklistEmailsDraft = new LinkedHashSet<>();

    @FXML
    public void initialize() {
        refreshFrontNavbarState();
        if (SceneNavigator.getCurrentUser() == null) {
            try {
                SceneNavigator.goTo("/views/front/signin-view.fxml");
            } catch (Exception ignored) {
                // Nothing else to do here.
            }
            return;
        }
        loadForumLanding();
        configureForumQueryControls();
        Platform.runLater(this::bindCommentsPopupSize);
    }

    @FXML
    public void openForumWorkspace() {
        try {
            Parent forumContent = ForumModuleLauncher.buildForumContentForFrontPage();
            forumHost.getChildren().setAll(forumContent);
            forumHost.setVisible(true);
            forumHost.setManaged(true);
            forumLandingPane.setVisible(false);
            forumLandingPane.setManaged(false);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Forum Error");
            alert.setHeaderText("Could not load the forum page.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void backToForumLanding() {
        forumHost.getChildren().clear();
        forumHost.setVisible(false);
        forumHost.setManaged(false);
        forumLandingPane.setVisible(true);
        forumLandingPane.setManaged(true);
        loadForumLanding();
    }

    @FXML
    public void showForumsTab() {
        showFeedPane();
        currentMyForumsMode = false;
        renderForums(false);
        setActiveTab(forumsTabButton);
    }

    @FXML
    public void showMyForumsTab() {
        showFeedPane();
        currentMyForumsMode = true;
        renderForums(true);
        setActiveTab(myForumsTabButton);
    }

    @FXML
    public void showCreatePostTab() {
        forumCardsPane.setVisible(false);
        forumCardsPane.setManaged(false);
        createPostPane.setVisible(true);
        createPostPane.setManaged(true);
        if (editPostPane != null) {
            editPostPane.setVisible(false);
            editPostPane.setManaged(false);
        }
        forumStatsLabel.setText("Create a new forum post");
        editingPost = null;
        createPostTitleField.clear();
        createPostContentField.clear();
        createPostStatusBox.setValue(PostStatus.IN_PROGRESS);
        createPostImageField.clear();
        createBlacklistEmailsField.clear();
        createBlacklistEmailsDraft.clear();
        renderCreateBlacklistChips();
        setActiveTab(createPostTabButton);
    }

    @FXML
    public void createForumPost() {
        if (forumService == null || forumUser == null) {
            return;
        }
        try {
            if (editingPost == null) {
                forumService.createPost(
                        forumUser,
                        createPostTitleField.getText(),
                        createPostContentField.getText(),
                        createPostStatusBox.getValue() == null ? PostStatus.OPEN : createPostStatusBox.getValue(),
                        createPostImageField.getText(),
                        resolveBlacklistedViewerIdsFromEmails()
                );
            } else {
                forumService.updatePost(
                        forumUser,
                        editingPost,
                        createPostTitleField.getText(),
                        createPostContentField.getText(),
                        createPostStatusBox.getValue() == null ? PostStatus.OPEN : createPostStatusBox.getValue(),
                        createPostImageField.getText(),
                        resolveBlacklistedViewerIdsFromEmails()
                );
            }
            createPostTitleField.clear();
            createPostContentField.clear();
            createPostStatusBox.setValue(PostStatus.IN_PROGRESS);
            createPostImageField.clear();
            createBlacklistEmailsField.clear();
            createBlacklistEmailsDraft.clear();
            renderCreateBlacklistChips();
            editingPost = null;
            loadForumLanding();
            if (currentMyForumsMode) {
                showMyForumsTab();
            } else {
                showForumsTab();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Create Post Error");
            alert.setHeaderText("Could not create forum post.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void cancelCreatePost() {
        showForumsTab();
    }

    @FXML
    public void chooseCreatePostImage() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose Forum Image");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "Images", "*.png", "*.jpg", "*.jpeg", "*.webp", "*.gif"
        ));
        Window owner = forumLandingPane != null && forumLandingPane.getScene() != null ? forumLandingPane.getScene().getWindow() : null;
        java.io.File selected = chooser.showOpenDialog(owner);
        if (selected == null) {
            return;
        }
        try {
            String storedName = copyForumImage(selected.toPath());
            createPostImageField.setText(storedName);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Image Upload Error");
            alert.setHeaderText("Could not upload image.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void clearCreatePostImage() {
        createPostImageField.clear();
    }

    @FXML
    public void suggestCreatePostTitle() {
        if (forumService == null) {
            return;
        }
        List<String> suggestions = forumService.suggest(
                "post_title",
                safeText(createPostTitleField.getText(), ""),
                safeText(createPostContentField.getText(), ""),
                "orig",
                4
        );
        chooseSuggestion("Title Suggestions", suggestions, createPostTitleField);
    }

    @FXML
    public void suggestCreatePostContent() {
        if (forumService == null) {
            return;
        }
        List<String> suggestions = forumService.suggest(
                "post_content",
                safeText(createPostContentField.getText(), ""),
                safeText(createPostTitleField.getText(), ""),
                "orig",
                4
        );
        chooseSuggestion("Content Suggestions", suggestions, createPostContentField);
    }

    private void loadForumLanding() {
        forumHost.setVisible(false);
        forumHost.setManaged(false);
        forumLandingPane.setVisible(true);
        forumLandingPane.setManaged(true);
        forumCardsPane.getChildren().clear();
        createPostPane.setVisible(false);
        createPostPane.setManaged(false);
        if (editPostPane != null) {
            editPostPane.setVisible(false);
            editPostPane.setManaged(false);
        }
        forumCardsPane.setVisible(true);
        forumCardsPane.setManaged(true);
        createPostStatusBox.setValue(PostStatus.IN_PROGRESS);
        createPostStatusBox.getItems().setAll(PostStatus.values());
        if (editPostStatusBox != null) {
            editPostStatusBox.setItems(createPostStatusBox.getItems());
        }

        try {
            UserDao userDao = new UserDao();
            forumService = ForumModuleLauncher.createForumService(userDao);
            forumUser = ForumModuleLauncher.resolveForumUserForCurrentSession(userDao);
            currentForumUserId = forumUser.getId();
            List<Post> posts = forumService.visiblePosts(forumUser, "", null);
            loadedPosts.clear();
            loadedPosts.addAll(posts);
            commentCountByPostId.clear();
            for (Post post : posts) {
                commentCountByPostId.put(post.getId(), forumService.commentsForPost(forumUser, post.getId()).size());
            }
            setActiveTab(forumsTabButton);
            renderForums(false);
        } catch (Exception e) {
            forumStatsLabel.setText("Could not load forums");
            Label failed = new Label("Forum feed is temporarily unavailable.");
            failed.getStyleClass().add("forum-empty-state");
            forumCardsPane.getChildren().add(failed);
        }
    }

    private void renderForums(boolean onlyMine) {
        forumCardsPane.getChildren().clear();
        String query = forumSearchField == null ? "" : safeText(forumSearchField.getText(), "").toLowerCase();
        String sortBy = forumSortBox == null ? "Newest" : safeText(forumSortBox.getValue(), "Newest");
        List<Post> posts = loadedPosts.stream()
                .filter(post -> !onlyMine || (currentForumUserId != null && post.getOwnerId() != null && post.getOwnerId().equals(currentForumUserId)))
                .filter(post -> onlyMine || post.getStatus() == PostStatus.OPEN)
                .filter(post -> query.isBlank()
                        || safeText(post.getTitle(), "").toLowerCase().contains(query)
                        || safeText(post.getContent(), "").toLowerCase().contains(query)
                        || safeText(post.getOwnerName(), safeText(post.getAuthorName(), "")).toLowerCase().contains(query))
                .toList();
        List<Post> mutablePosts = new ArrayList<>(posts);
        mutablePosts.sort(resolveForumSort(sortBy));

        forumStatsLabel.setText(onlyMine
                ? mutablePosts.size() + " forums by you"
                : mutablePosts.size() + " open forums available");

        for (Post post : mutablePosts) {
            forumCardsPane.getChildren().add(buildForumCard(post, onlyMine));
        }
        if (mutablePosts.isEmpty()) {
            Label empty = new Label(onlyMine
                    ? "You have not created any forum posts yet."
                    : "No forum posts yet. Be the first to start a conversation.");
            empty.getStyleClass().add("forum-empty-state");
            forumCardsPane.getChildren().add(empty);
        }
    }
    private VBox buildForumCard(Post post, boolean showStatus) {
        RatingSummary ratingSummary = forumService.ratingSummary(post.getId());
        OptionalDouble ownRating = forumService.userRatingForPost(forumUser, post.getId());
        int ownStars = ownRating.isPresent() ? (int) Math.round(ownRating.getAsDouble()) : 0;

        Label upVote = new Label("\u25B2");
        upVote.getStyleClass().add("forum-vote-arrow");
        Label voteScore = new Label(String.format("%.1f", ratingSummary.average()));
        voteScore.getStyleClass().add("forum-vote-score");
        Label downVote = new Label("\u25BC");
        downVote.getStyleClass().add("forum-vote-arrow");
        VBox voteRail = new VBox(4, upVote, voteScore, downVote);
        voteRail.getStyleClass().add("forum-vote-rail");
        voteRail.setAlignment(Pos.TOP_CENTER);

        String statusToken = safeText(post.getStatus() == null ? "OPEN" : post.getStatus().name(), "OPEN").toLowerCase();
        String statusClass = "forum-status-" + statusToken;

        Label community = new Label("r/PegasusForum");
        community.getStyleClass().add("forum-card-community");
        Label dot = new Label("\u2022");
        dot.getStyleClass().add("forum-card-dot");
        Label meta = new Label("Posted by " + safeText(post.getOwnerName(), safeText(post.getAuthorName(), "Unknown")));
        meta.getStyleClass().add("forum-card-meta");
        HBox metaRow;
        if (showStatus) {
            Label status = new Label(post.getStatus().name());
            status.getStyleClass().addAll("forum-card-status", statusClass);
            metaRow = new HBox(6, community, dot, meta, new Region(), status);
            HBox.setHgrow(metaRow.getChildren().get(3), javafx.scene.layout.Priority.ALWAYS);
        } else {
            metaRow = new HBox(6, community, dot, meta);
        }
        metaRow.setAlignment(Pos.CENTER_LEFT);

        boolean postBannedForOwnerView = post.isBannedByAdmin()
                && post.getOwnerId() != null
                && currentForumUserId != null
                && post.getOwnerId().equals(currentForumUserId);
        Label title = new Label(postBannedForOwnerView
                ? "[BANNED BY ADMIN] " + safeText(post.getTitle(), "Untitled forum")
                : safeText(post.getTitle(), "Untitled forum"));
        title.getStyleClass().add("forum-card-title");
        title.setWrapText(true);
        Label snippet = new Label(postBannedForOwnerView
                ? "This post is banned by the admins. Only you can still see it."
                : truncate(safeText(post.getContent(), ""), 160));
        snippet.getStyleClass().add("forum-card-snippet");
        snippet.setWrapText(true);
        ImageView postImage = buildPostImagePreview(post.getImageName());

        Label comments = new Label(commentCountByPostId.getOrDefault(post.getId(), 0) + " comments");
        comments.getStyleClass().add("forum-card-action");
        comments.setOnMouseClicked(event -> {
            event.consume();
            openCommentsDialog(post);
        });

        HBox starsBox = buildRatingStars(post, ownStars);
        Label ratingInfo = new Label(String.format("%.1f/5 (%d)", ratingSummary.average(), ratingSummary.count()));
        ratingInfo.getStyleClass().add("forum-card-rating-info");

        Label open = new Label("Open");
        open.getStyleClass().add("forum-card-action");
        HBox actionsRow = new HBox(12, comments, starsBox, ratingInfo, open);
        if (currentMyForumsMode && post.getOwnerId() != null && currentForumUserId != null && post.getOwnerId().equals(currentForumUserId)) {
            Region spacer = new Region();
            HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
            Button editButton = new Button("\u270E");
            editButton.getStyleClass().addAll("forum-icon-button", "forum-edit-button");
            editButton.setOnAction(event -> {
                event.consume();
                openEditPost(post);
            });
            Button deleteButton = new Button("\u2716");
            deleteButton.getStyleClass().addAll("forum-icon-button", "forum-delete-button");
            deleteButton.setOnAction(event -> {
                event.consume();
                deletePost(post);
            });
            actionsRow.getChildren().addAll(spacer, editButton, deleteButton);
        }
        actionsRow.setAlignment(Pos.CENTER_LEFT);

        VBox content = postImage == null
                ? new VBox(8, metaRow, title, snippet, actionsRow)
                : new VBox(8, metaRow, title, postImage, snippet, actionsRow);
        HBox shell = new HBox(12, voteRail, content);
        HBox.setHgrow(content, javafx.scene.layout.Priority.ALWAYS);
        shell.setAlignment(Pos.TOP_LEFT);

        VBox card = new VBox(shell);
        card.getStyleClass().add("forum-card");
        if (showStatus) {
            card.getStyleClass().add("forum-card-" + statusToken);
        }
        return card;
    }

    @FXML
    public void onForumQueryChanged() {
        renderForums(currentMyForumsMode);
    }

    private void configureForumQueryControls() {
        if (forumSortBox != null) {
            forumSortBox.getItems().setAll("Newest", "Oldest", "Title A-Z", "Title Z-A", "Most Comments");
            forumSortBox.setValue("Newest");
        }
    }

    private Comparator<Post> resolveForumSort(String sortBy) {
        String sort = safeText(sortBy, "Newest");
        return switch (sort) {
            case "Oldest" -> Comparator.comparing(Post::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder()));
            case "Title A-Z" -> Comparator.comparing(post -> safeText(post.getTitle(), "").toLowerCase());
            case "Title Z-A" -> Comparator.comparing((Post post) -> safeText(post.getTitle(), "").toLowerCase()).reversed();
            case "Most Comments" -> Comparator.comparingInt((Post post) -> commentCountByPostId.getOrDefault(post.getId(), 0)).reversed();
            default -> Comparator.comparing(Post::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder()));
        };
    }

    private HBox buildRatingStars(Post post, int ownStars) {
        HBox starsBox = new HBox(2);
        starsBox.getStyleClass().add("forum-rating-stars");
        for (int star = 0; star <= 5; star++) {
            Button starButton = new Button(star == 0 ? "0" : "\u2605");
            starButton.getStyleClass().add("forum-star-button");
            if (star <= ownStars && star > 0) {
                starButton.getStyleClass().add("forum-star-button-active");
            }
            if (star == 0 && ownStars == 0) {
                starButton.getStyleClass().add("forum-star-zero-active");
            }
            final int selectedValue = star;
            starButton.setOnAction(event -> {
                event.consume();
                ratePostFromCard(post, selectedValue);
            });
            starsBox.getChildren().add(starButton);
        }
        return starsBox;
    }

    private void ratePostFromCard(Post post, int stars) {
        if (forumService == null || forumUser == null || post == null) {
            return;
        }
        try {
            forumService.ratePost(forumUser, post, stars);
            renderForums(currentMyForumsMode);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Rating Error");
            alert.setHeaderText("Could not save rating.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    private String safeText(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value.trim();
    }

    private String truncate(String value, int max) {
        if (value == null || value.length() <= max) {
            return value;
        }
        return value.substring(0, max - 3) + "...";
    }

    private void setActiveTab(Button active) {
        if (forumsTabButton != null) {
            forumsTabButton.getStyleClass().remove("forum-subnav-button-active");
        }
        if (myForumsTabButton != null) {
            myForumsTabButton.getStyleClass().remove("forum-subnav-button-active");
        }
        if (createPostTabButton != null) {
            createPostTabButton.getStyleClass().remove("forum-subnav-button-active");
        }
        if (active != null && !active.getStyleClass().contains("forum-subnav-button-active")) {
            active.getStyleClass().add("forum-subnav-button-active");
        }
    }

    private int scoreForPost(Post post) {
        int comments = commentCountByPostId.getOrDefault(post.getId(), 0);
        int contentWeight = post.getContent() == null ? 0 : Math.min(20, post.getContent().length() / 40);
        return Math.max(1, comments * 2 + contentWeight);
    }

    private void showFeedPane() {
        createPostPane.setVisible(false);
        createPostPane.setManaged(false);
        if (editPostPane != null) {
            editPostPane.setVisible(false);
            editPostPane.setManaged(false);
        }
        forumCardsPane.setVisible(true);
        forumCardsPane.setManaged(true);
    }

    private void openEditPost(Post post) {
        editingPost = post;
        editPostTitleField.setText(safeText(post.getTitle(), ""));
        editPostContentField.setText(safeText(post.getContent(), ""));
        editPostStatusBox.setValue(post.getStatus() == null ? PostStatus.OPEN : post.getStatus());
        editPostImageField.setText(safeText(post.getImageName(), ""));
        editBlacklistEmailsDraft.clear();
        Map<Integer, String> emailById = forumService.users().stream()
                .collect(Collectors.toMap(com.pegasus.forumdesktop.model.User::getId, u -> safeText(u.getEmail(), ""), (a, b) -> a));
        for (Integer id : post.getBlacklistedViewerIds()) {
            String email = emailById.get(id);
            if (email != null && !email.isBlank()) {
                editBlacklistEmailsDraft.add(email.toLowerCase());
            }
        }
        renderEditBlacklistChips();
        editBlacklistInputField.clear();
        forumCardsPane.setVisible(false);
        forumCardsPane.setManaged(false);
        createPostPane.setVisible(false);
        createPostPane.setManaged(false);
        editPostPane.setVisible(true);
        editPostPane.setManaged(true);
        forumStatsLabel.setText("Edit your forum post");
    }

    @FXML
    public void onAddEditBlacklistEmail() {
        String raw = editBlacklistInputField == null ? "" : safeText(editBlacklistInputField.getText(), "");
        if (raw.isBlank()) {
            return;
        }
        for (String token : raw.split("[,;\\s]+")) {
            String email = token.trim().toLowerCase();
            if (email.contains("@") && !email.startsWith("@") && !email.endsWith("@")) {
                editBlacklistEmailsDraft.add(email);
            }
        }
        editBlacklistInputField.clear();
        renderEditBlacklistChips();
    }

    @FXML
    public void chooseEditPostImage() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose Forum Image");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "Images", "*.png", "*.jpg", "*.jpeg", "*.webp", "*.gif"
        ));
        Window owner = forumLandingPane != null && forumLandingPane.getScene() != null ? forumLandingPane.getScene().getWindow() : null;
        java.io.File selected = chooser.showOpenDialog(owner);
        if (selected == null) {
            return;
        }
        try {
            String storedName = copyForumImage(selected.toPath());
            editPostImageField.setText(storedName);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Image Upload Error");
            alert.setHeaderText("Could not upload image.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void clearEditPostImage() {
        editPostImageField.clear();
    }

    @FXML
    public void saveEditedPost() {
        if (forumService == null || forumUser == null || editingPost == null) {
            return;
        }
        try {
            forumService.updatePost(
                    forumUser,
                    editingPost,
                    editPostTitleField.getText(),
                    editPostContentField.getText(),
                    editPostStatusBox.getValue() == null ? PostStatus.OPEN : editPostStatusBox.getValue(),
                    editPostImageField.getText(),
                    resolveBlacklistedViewerIdsFromEmailSet(editBlacklistEmailsDraft)
            );
            editingPost = null;
            editBlacklistEmailsDraft.clear();
            loadForumLanding();
            showMyForumsTab();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Edit Post Error");
            alert.setHeaderText("Could not update forum post.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void cancelEditPost() {
        editingPost = null;
        editBlacklistEmailsDraft.clear();
        showMyForumsTab();
    }

    private void deletePost(Post post) {
        if (forumService == null || forumUser == null) {
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Post");
        confirm.setHeaderText("Delete this post?");
        confirm.setContentText(post.getTitle());
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }
        try {
            forumService.deletePost(forumUser, post);
            loadForumLanding();
            showMyForumsTab();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Error");
            alert.setHeaderText("Could not delete post.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private Set<Integer> resolveBlacklistedViewerIdsFromEmails() {
        if (forumService == null || forumUser == null) {
            return Set.of();
        }

        Set<String> blacklistedEmails = new LinkedHashSet<>(createBlacklistEmailsDraft);
        String raw = createBlacklistEmailsField == null ? null : createBlacklistEmailsField.getText();
        blacklistedEmails.addAll(parseEmailTokens(raw));
        if (blacklistedEmails.isEmpty()) {
            return Set.of();
        }
        Set<Integer> blacklisted = new HashSet<>();
        Set<String> matchedEmails = new HashSet<>();
        List<com.pegasus.forumdesktop.model.User> users = forumService.users();
        users.forEach(user -> {
            if (user.getId() == forumUser.getId()) {
                return;
            }
            String email = user.getEmail() == null ? "" : user.getEmail().trim().toLowerCase();
            if (blacklistedEmails.contains(email)) {
                blacklisted.add(user.getId());
                matchedEmails.add(email);
            }
        });
        Set<String> unknown = new LinkedHashSet<>(blacklistedEmails);
        unknown.removeAll(matchedEmails);
        if (!unknown.isEmpty()) {
            throw new IllegalArgumentException("These emails were not found: " + String.join(", ", unknown));
        }
        return blacklisted;
    }

    private Set<String> parseEmailTokens(String raw) {
        if (raw == null || raw.isBlank()) {
            return Set.of();
        }
        Set<String> emails = new LinkedHashSet<>();
        for (String token : raw.split("[,;\\s]+")) {
            String email = token == null ? "" : token.trim().toLowerCase();
            if (email.isBlank()) {
                continue;
            }
            if (email.contains("@") && !email.startsWith("@") && !email.endsWith("@")) {
                emails.add(email);
            }
        }
        return emails;
    }

    @FXML
    public void onAddCreateBlacklistEmail() {
        String raw = createBlacklistEmailsField == null ? "" : safeText(createBlacklistEmailsField.getText(), "");
        if (raw.isBlank()) {
            return;
        }
        createBlacklistEmailsDraft.addAll(parseEmailTokens(raw));
        createBlacklistEmailsField.clear();
        renderCreateBlacklistChips();
    }

    private void renderCreateBlacklistChips() {
        if (createBlacklistChipsPane == null) {
            return;
        }
        createBlacklistChipsPane.getChildren().clear();
        for (String email : createBlacklistEmailsDraft) {
            Label emailLabel = new Label(email);
            emailLabel.getStyleClass().add("forum-email-chip-text");
            Button removeButton = new Button("\u00D7");
            removeButton.getStyleClass().add("forum-email-chip-remove");
            removeButton.setOnAction(event -> {
                createBlacklistEmailsDraft.remove(email);
                renderCreateBlacklistChips();
            });
            HBox chip = new HBox(8, emailLabel, removeButton);
            chip.getStyleClass().add("forum-email-chip");
            chip.setAlignment(Pos.CENTER_LEFT);
            createBlacklistChipsPane.getChildren().add(chip);
        }
    }

    private Set<Integer> resolveBlacklistedViewerIdsFromEmailSet(Set<String> emails) {
        if (forumService == null || forumUser == null || emails == null || emails.isEmpty()) {
            return Set.of();
        }
        Set<String> target = emails.stream().map(String::toLowerCase).collect(Collectors.toSet());
        Set<Integer> blacklisted = new HashSet<>();
        forumService.users().forEach(user -> {
            if (user.getId() == forumUser.getId()) {
                return;
            }
            String email = user.getEmail() == null ? "" : user.getEmail().trim().toLowerCase();
            if (target.contains(email)) {
                blacklisted.add(user.getId());
            }
        });
        return blacklisted;
    }

    private void renderEditBlacklistChips() {
        if (editBlacklistChipsPane == null) {
            return;
        }
        editBlacklistChipsPane.getChildren().clear();
        for (String email : editBlacklistEmailsDraft) {
            Label emailLabel = new Label(email);
            emailLabel.getStyleClass().add("forum-email-chip-text");
            Button removeButton = new Button("\u00D7");
            removeButton.getStyleClass().add("forum-email-chip-remove");
            removeButton.setOnAction(event -> {
                editBlacklistEmailsDraft.remove(email);
                renderEditBlacklistChips();
            });
            HBox chip = new HBox(8, emailLabel, removeButton);
            chip.getStyleClass().add("forum-email-chip");
            chip.setAlignment(Pos.CENTER_LEFT);
            editBlacklistChipsPane.getChildren().add(chip);
        }
    }

    private ImageView buildPostImagePreview(String imageName) {
        if (imageName == null || imageName.isBlank()) {
            return null;
        }
        String source = imageName.trim();
        if (!source.startsWith("http://") && !source.startsWith("https://") && !source.startsWith("file:/")) {
            source = new File(forumUploadsDir().toFile(), source).toURI().toString();
        }
        Image image = new Image(source, 760, 320, true, true, true);
        if (image.isError()) {
            return null;
        }
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(760);
        imageView.setFitHeight(320);
        imageView.getStyleClass().add("forum-card-image");
        return imageView;
    }

    private Path forumUploadsDir() {
        String configured = System.getenv("PEGASUS_FORUM_UPLOADS_DIR");
        if (configured != null && !configured.isBlank()) {
            return Path.of(configured);
        }
        return Path.of(System.getProperty("user.home"), "pegasus", "uploads", "forum");
    }

    private String copyForumImage(Path sourcePath) throws Exception {
        String fileName = sourcePath.getFileName().toString();
        String extension = "";
        int dot = fileName.lastIndexOf('.');
        if (dot >= 0) {
            extension = fileName.substring(dot);
        }
        String storedName = "forum-" + System.currentTimeMillis() + extension;
        Path uploadDir = Path.of(System.getProperty("user.home"), "pegasus", "uploads", "forum");
        Files.createDirectories(uploadDir);
        Path target = uploadDir.resolve(storedName);
        Files.copy(sourcePath, target, StandardCopyOption.REPLACE_EXISTING);
        return storedName;
    }

    private void chooseSuggestion(String title, List<String> suggestions, TextField field) {
        if (suggestions == null || suggestions.isEmpty()) {
            return;
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>(suggestions.get(0), suggestions);
        dialog.setTitle(title);
        dialog.setHeaderText("Choose a suggestion");
        dialog.setContentText("Suggestion:");
        dialog.showAndWait().ifPresent(field::setText);
    }

    private void chooseSuggestion(String title, List<String> suggestions, TextArea area) {
        if (suggestions == null || suggestions.isEmpty()) {
            return;
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>(suggestions.get(0), suggestions);
        dialog.setTitle(title);
        dialog.setHeaderText("Choose a suggestion");
        dialog.setContentText("Suggestion:");
        dialog.showAndWait().ifPresent(area::setText);
    }

    private void openCommentsDialog(Post post) {
        if (forumService == null || forumUser == null || post == null) {
            return;
        }
        activeCommentsPost = post;
        commentsPopupTitle.setText("Comments - " + safeText(post.getTitle(), "Forum Post"));
        commentsPopupInput.clear();
        editingComment = null;
        selectedGifUrl = null;
        refreshSelectedGifPreview();
        updateCommentComposerMode(false);
        refreshCommentsPopup();
        commentsOverlay.setManaged(true);
        commentsOverlay.setVisible(true);
    }

    @FXML
    public void closeCommentsPopup() {
        commentsOverlay.setVisible(false);
        commentsOverlay.setManaged(false);
        activeCommentsPost = null;
        editingComment = null;
        selectedGifUrl = null;
        refreshSelectedGifPreview();
        updateCommentComposerMode(false);
        if (commentsGifResultsBox != null) {
            commentsGifResultsBox.getChildren().clear();
        }
        if (commentsGifSearchField != null) {
            commentsGifSearchField.clear();
        }
    }

    @FXML
    public void addCommentFromPopup() {
        if (forumService == null || forumUser == null || activeCommentsPost == null) {
            return;
        }
        try {
            if (editingComment == null) {
                forumService.addComment(forumUser, activeCommentsPost, commentsPopupInput.getText(), selectedGifUrl);
            } else {
                forumService.updateComment(forumUser, editingComment, commentsPopupInput.getText(), selectedGifUrl);
            }
            commentsPopupInput.clear();
            editingComment = null;
            selectedGifUrl = null;
            refreshSelectedGifPreview();
            updateCommentComposerMode(false);
            refreshCommentsPopup();
            renderForums(currentMyForumsMode);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Comment Error");
            alert.setHeaderText("Could not add comment.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void searchCommentsGifs() {
        if (forumService == null || commentsGifResultsBox == null) {
            return;
        }
        commentsGifResultsBox.getChildren().clear();
        try {
            String query = commentsGifSearchField == null ? "" : safeText(commentsGifSearchField.getText(), "");
            List<GifItem> gifs = forumService.searchGifs(query, 10);
            for (GifItem gif : gifs) {
                String previewUrl = (gif.preview() == null || gif.preview().isBlank()) ? gif.url() : gif.preview();
                if (previewUrl == null || previewUrl.isBlank()) {
                    continue;
                }
                Image image = loadRemoteImage(previewUrl, 120, 90);
                if (image.isError() && gif.url() != null && !gif.url().isBlank()) {
                    image = loadRemoteImage(gif.url(), 120, 90);
                }
                if (image.isError()) {
                    continue;
                }
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(120);
                imageView.setFitHeight(90);
                imageView.setPreserveRatio(true);
                imageView.getStyleClass().add("forum-gif-result-image");

                StackPane tile = new StackPane(imageView);
                tile.getStyleClass().add("forum-gif-result-tile");
                tile.setOnMouseClicked(event -> {
                    selectedGifUrl = gif.url();
                    refreshSelectedGifPreview();
                });
                commentsGifResultsBox.getChildren().add(tile);
            }
            if (commentsGifResultsBox.getChildren().isEmpty()) {
                Label none = new Label("No GIFs rendered. API key works, likely thumbnail loading issue.");
                none.getStyleClass().add("forum-comment-gif-link");
                commentsGifResultsBox.getChildren().add(none);
            }
        } catch (Exception e) {
            Label fail = new Label("GIF search failed: " + safeText(e.getMessage(), "unknown error"));
            fail.getStyleClass().add("forum-comment-gif-link");
            commentsGifResultsBox.getChildren().add(fail);
        }
    }

    private void refreshCommentsPopup() {
        if (activeCommentsPost == null || commentsPopupList == null) {
            return;
        }
        commentsPopupList.getChildren().clear();
        List<Comment> comments = forumService.commentsForPost(forumUser, activeCommentsPost.getId());
        for (Comment comment : comments) {
            Label author = new Label(safeText(comment.getOwnerName(), "User"));
            author.getStyleClass().add("forum-comment-author");
            boolean commentBannedForOwnerView = comment.isBannedByAdmin()
                    && forumUser != null
                    && comment.getOwnerId() != null
                    && comment.getOwnerId() == forumUser.getId();
            String text = commentBannedForOwnerView
                    ? "This comment is banned by the admins. Only you can still see it."
                    : safeText(comment.getContent(), "").trim();
            if (text.isEmpty() && !commentBannedForOwnerView) {
                text = "GIF attached";
            }
            Label body = new Label(text);
            body.setWrapText(true);
            body.getStyleClass().add("forum-comment-body");
            VBox item = new VBox(6, author, body);
            if (!commentBannedForOwnerView && comment.getGifUrl() != null && !comment.getGifUrl().isBlank()) {
                Image gifImage = loadRemoteImage(comment.getGifUrl(), 360, 240);
                if (!gifImage.isError()) {
                    ImageView gifView = new ImageView(gifImage);
                    gifView.setPreserveRatio(true);
                    gifView.setFitWidth(360);
                    gifView.setFitHeight(240);
                    gifView.getStyleClass().add("forum-comment-gif");
                    item.getChildren().add(gifView);
                } else {
                    Label gifLink = new Label("GIF preview unavailable");
                    gifLink.getStyleClass().add("forum-comment-gif-link");
                    gifLink.setOnMouseClicked(event -> {
                        selectedGifUrl = comment.getGifUrl();
                        refreshSelectedGifPreview();
                    });
                    item.getChildren().add(gifLink);
                }
            }
            if (forumService.canManageComment(forumUser, comment)) {
                Region spacer = new Region();
                HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
                Button editCommentButton = new Button("Edit");
                editCommentButton.getStyleClass().add("forum-card-action");
                editCommentButton.setOnAction(event -> editOwnComment(comment));
                Button deleteCommentButton = new Button("Delete");
                deleteCommentButton.getStyleClass().addAll("forum-icon-button", "forum-delete-button");
                deleteCommentButton.setOnAction(event -> deleteOwnComment(comment));
                HBox row = new HBox(8, spacer, editCommentButton, deleteCommentButton);
                row.setAlignment(Pos.CENTER_LEFT);
                item.getChildren().add(row);
            }
            item.getStyleClass().add("forum-comment-item");
            commentsPopupList.getChildren().add(item);
        }
        commentCountByPostId.put(activeCommentsPost.getId(), comments.size());
    }

    private void editOwnComment(Comment comment) {
        if (forumService == null || forumUser == null || comment == null) {
            return;
        }
        editingComment = comment;
        commentsPopupInput.setText(safeText(comment.getContent(), ""));
        selectedGifUrl = safeText(comment.getGifUrl(), "");
        if (selectedGifUrl.isBlank()) {
            selectedGifUrl = null;
        }
        refreshSelectedGifPreview();
        if (commentsSubmitButton != null) {
            commentsSubmitButton.setText("Update Comment");
        }
        updateCommentComposerMode(true);
        commentsPopupInput.requestFocus();
        commentsPopupInput.positionCaret(commentsPopupInput.getText().length());
    }

    private void deleteOwnComment(Comment comment) {
        if (forumService == null || forumUser == null || comment == null) {
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Comment");
        confirm.setHeaderText("Delete this comment?");
        confirm.setContentText(safeText(comment.getContent(), "GIF comment"));
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }
        try {
            forumService.deleteComment(forumUser, comment);
            if (editingComment != null && editingComment.getId() == comment.getId()) {
                editingComment = null;
                commentsPopupInput.clear();
                selectedGifUrl = null;
                refreshSelectedGifPreview();
                updateCommentComposerMode(false);
            }
            refreshCommentsPopup();
            renderForums(currentMyForumsMode);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Comment Error");
            alert.setHeaderText("Could not delete comment.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void clearSelectedGifFromComposer() {
        selectedGifUrl = null;
        refreshSelectedGifPreview();
    }

    @FXML
    public void cancelCommentUpdate() {
        editingComment = null;
        commentsPopupInput.clear();
        selectedGifUrl = null;
        refreshSelectedGifPreview();
        updateCommentComposerMode(false);
    }

    private void refreshSelectedGifPreview() {
        if (commentsSelectedGifBox == null || commentsSelectedGifPreview == null) {
            return;
        }
        if (selectedGifUrl == null || selectedGifUrl.isBlank()) {
            commentsSelectedGifPreview.setImage(null);
            commentsSelectedGifBox.setVisible(false);
            commentsSelectedGifBox.setManaged(false);
            return;
        }
        Image preview = loadRemoteImage(selectedGifUrl, 120, 90);
        if (preview.isError()) {
            commentsSelectedGifPreview.setImage(null);
            commentsSelectedGifBox.setVisible(false);
            commentsSelectedGifBox.setManaged(false);
            return;
        }
        commentsSelectedGifPreview.setImage(preview);
        commentsSelectedGifBox.setVisible(true);
        commentsSelectedGifBox.setManaged(true);
    }

    private void updateCommentComposerMode(boolean editing) {
        if (commentsSubmitButton != null) {
            commentsSubmitButton.setText(editing ? "Update Comment" : "Add Comment");
        }
        if (commentsCancelUpdateButton != null) {
            commentsCancelUpdateButton.setVisible(editing);
            commentsCancelUpdateButton.setManaged(editing);
        }
    }

    private void bindCommentsPopupSize() {
        if (commentsOverlay == null || commentsPopupCard == null || commentsOverlay.getScene() == null) {
            return;
        }
        var scene = commentsOverlay.getScene();
        commentsPopupCard.prefWidthProperty().bind(scene.widthProperty().multiply(0.62));
        commentsPopupCard.prefHeightProperty().bind(scene.heightProperty().multiply(0.78));
        commentsPopupCard.minWidthProperty().bind(scene.widthProperty().multiply(0.52));
        commentsPopupCard.minHeightProperty().bind(scene.heightProperty().multiply(0.62));
        commentsPopupCard.maxWidthProperty().bind(scene.widthProperty().multiply(0.84));
        commentsPopupCard.maxHeightProperty().bind(scene.heightProperty().multiply(0.88));
    }

    private Image loadRemoteImage(String url, double width, double height) {
        try {
            if (url == null || url.isBlank()) {
                return new Image(new ByteArrayInputStream(new byte[0]));
            }
            HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                    .header("User-Agent", "PegasusForumJavaFX/1.0")
                    .GET()
                    .build();
            HttpResponse<byte[]> response = imageHttpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
            if (response.statusCode() < 200 || response.statusCode() >= 300 || response.body() == null || response.body().length == 0) {
                return new Image(new ByteArrayInputStream(new byte[0]));
            }
            return new Image(new ByteArrayInputStream(response.body()), width, height, true, true);
        } catch (Exception e) {
            return new Image(new ByteArrayInputStream(new byte[0]));
        }
    }
}



