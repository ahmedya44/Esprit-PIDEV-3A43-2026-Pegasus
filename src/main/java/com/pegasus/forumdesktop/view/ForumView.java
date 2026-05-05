package com.pegasus.forumdesktop.view;

import com.pegasus.forumdesktop.model.Comment;
import com.pegasus.forumdesktop.model.Post;
import com.pegasus.forumdesktop.model.PostStatus;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ForumView {
    private final StackPane root = new StackPane();

    public final Label activeUserLabel = new Label();
    public final Button logoutButton = new Button("Logout");
    public final ComboBox<String> localeBox = new ComboBox<>(FXCollections.observableArrayList("orig", "en", "fr", "es", "de", "it", "ar"));
    public final TextField searchField = new TextField();
    public final ComboBox<String> statusFilter = new ComboBox<>(FXCollections.observableArrayList("ALL", "OPEN", "CLOSED", "HIDDEN"));
    public final CheckBox myPostsOnly = new CheckBox("My posts only");
    public final ListView<Post> postsList = new ListView<>();

    public final TextField postTitle = new TextField();
    public final TextArea postContent = new TextArea();
    public final ComboBox<PostStatus> postStatus = new ComboBox<>(FXCollections.observableArrayList(PostStatus.values()));
    public final TextField postImageName = new TextField();
    public final Button choosePostImageButton = new Button("Choose Image");
    public final Button clearPostImageButton = new Button("Clear Image");
    public final TextField allowedViewerIds = new TextField();
    public final Button newPostButton = new Button("New");
    public final Button savePostButton = new Button("Save");
    public final Button deletePostButton = new Button("Delete");
    public final Button savePostTranslationButton = new Button("Save Translation");
    public final Button suggestTitleButton = new Button("Suggest Title");
    public final Button suggestContentButton = new Button("Suggest Content");
    public final Button autoTranslatePostButton = new Button("Auto Translate");

    public final Label postMetaLabel = new Label("Select a post");
    public final ImageView postImagePreview = new ImageView();
    public final Label postImageMessage = new Label();
    public final Label ratingLabel = new Label("No ratings yet");
    public final Spinner<Double> ratingSpinner = new Spinner<>();
    public final Button rateButton = new Button("Rate");

    public final ListView<Comment> commentsList = new ListView<>();
    public final TextArea commentContent = new TextArea();
    public final TextField commentGifUrl = new TextField();
    public final Button addCommentButton = new Button("Add Comment");
    public final Button updateCommentButton = new Button("Update Comment");
    public final Button deleteCommentButton = new Button("Delete Comment");
    public final Button saveCommentTranslationButton = new Button("Save Comment Translation");
    public final Button autoTranslateCommentButton = new Button("Auto Translate Comment");
    public final TextField gifSearchField = new TextField();
    public final Button gifSearchButton = new Button("Find GIF");

    public final TextField adminCommentSearch = new TextField();
    public final ListView<Comment> adminCommentsList = new ListView<>();
    public final Button refreshAdminCommentsButton = new Button("Refresh Comments");
    public final Button statsButton = new Button("Refresh Stats");
    public final TextArea statsArea = new TextArea();
    public final PieChart statusPieChart = new PieChart();
    public final BarChart<String, Number> topPostsChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
    public final Label feedbackLabel = new Label();

    public ForumView() {
        configureControls();
        showForum();
    }

    public Parent getRoot() {
        return root;
    }

    public void showForum() {
        showForum(false);
    }

    public void showForum(boolean admin) {
        root.getChildren().setAll(buildForumPane(admin));
    }

    private Parent buildForumPane(boolean admin) {
        BorderPane shell = new BorderPane();
        shell.getStyleClass().add("app-root");
        shell.setTop(buildTopBar());
        shell.setLeft(buildPostsPane(admin));
        shell.setCenter(buildTabs(admin));
        shell.setBottom(feedbackLabel);
        BorderPane.setMargin(feedbackLabel, new Insets(8));
        return shell;
    }

    private Parent buildTopBar() {
        Label brand = new Label("Pegasus");
        brand.getStyleClass().add("brand-label");
        Label forum = new Label("FORUM");
        forum.getStyleClass().addAll("nav-link", "active-link");
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        logoutButton.getStyleClass().add("home-button");
        HBox bar = new HBox(18, brand, forum, activeUserLabel, new Label("Language"), localeBox, spacer, logoutButton);
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.getStyleClass().add("top-nav");
        return bar;
    }

    private Parent buildPostsPane(boolean admin) {
        searchField.setPromptText("Search title, content, author");
        statusFilter.setValue("ALL");
        postsList.setPrefWidth(360);
        Label title = new Label(admin ? "Back Office Posts" : "Forum Posts");
        title.getStyleClass().add("section-title");
        VBox pane = new VBox(8, title, searchField, statusFilter, myPostsOnly, postsList);
        pane.setPadding(new Insets(10));
        pane.setPrefWidth(380);
        pane.getStyleClass().add("side-pane");
        return pane;
    }

    private Parent buildTabs(boolean admin) {
        TabPane tabs = new TabPane();
        Tab postTab = new Tab(admin ? "Manage Posts" : "Forum", buildPostPane(admin));
        Tab commentsTab = new Tab(admin ? "Manage Comments" : "Comments", buildCommentsPane(admin));
        postTab.setClosable(false);
        commentsTab.setClosable(false);
        if (admin) {
            Tab adminTab = new Tab("Dashboard", buildAdminPane());
            adminTab.setClosable(false);
            tabs.getTabs().setAll(postTab, commentsTab, adminTab);
        } else {
            tabs.getTabs().setAll(postTab, commentsTab);
        }
        return tabs;
    }

    private Parent buildPostPane(boolean admin) {
        postContent.setWrapText(true);
        postContent.setPrefRowCount(10);
        allowedViewerIds.setPromptText("Example: 2, 8, 15. Used only for HIDDEN posts.");
        postImageName.setPromptText("Image filename");
        postImageName.setEditable(false);
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.addRow(0, new Label("Title"), postTitle);
        form.addRow(1, new Label("Content"), postContent);
        form.addRow(2, new Label("Status"), postStatus);
        HBox imageControls = new HBox(8, postImageName, choosePostImageButton, clearPostImageButton);
        HBox.setHgrow(postImageName, Priority.ALWAYS);
        form.addRow(3, new Label("Image"), imageControls);
        form.addRow(4, new Label("Allowed viewers"), allowedViewerIds);
        GridPane.setHgrow(postTitle, Priority.ALWAYS);
        GridPane.setHgrow(postContent, Priority.ALWAYS);

        savePostTranslationButton.setManaged(admin);
        savePostTranslationButton.setVisible(admin);
        autoTranslatePostButton.setManaged(admin);
        autoTranslatePostButton.setVisible(admin);
        HBox apiActions = new HBox(8, suggestTitleButton, suggestContentButton, autoTranslatePostButton);
        apiActions.setAlignment(Pos.CENTER_LEFT);
        HBox actions = new HBox(8, newPostButton, savePostButton, deletePostButton, savePostTranslationButton);
        actions.setAlignment(Pos.CENTER_LEFT);
        savePostButton.getStyleClass().add("primary");
        deletePostButton.getStyleClass().add("danger");

        HBox rating = new HBox(8, ratingLabel, ratingSpinner, rateButton);
        rating.setAlignment(Pos.CENTER_LEFT);
        rateButton.getStyleClass().add("primary");

        postImagePreview.setFitWidth(320);
        postImagePreview.setFitHeight(190);
        postImagePreview.setPreserveRatio(true);
        postImagePreview.getStyleClass().add("post-image-preview");
        postImageMessage.getStyleClass().add("muted");
        VBox imageBox = new VBox(6, postImagePreview, postImageMessage);
        imageBox.getStyleClass().add("image-box");

        VBox pane = new VBox(10, postMetaLabel, form, imageBox, apiActions, actions, new Separator(), rating);
        pane.setPadding(new Insets(12));
        return pane;
    }

    private Parent buildCommentsPane(boolean admin) {
        commentContent.setPromptText("Comment text");
        commentContent.setWrapText(true);
        commentContent.setPrefRowCount(4);
        commentGifUrl.setPromptText("Optional GIF URL");
        gifSearchField.setPromptText("Search GIFs");
        HBox gifTools = new HBox(8, gifSearchField, gifSearchButton);
        HBox.setHgrow(gifSearchField, Priority.ALWAYS);
        saveCommentTranslationButton.setManaged(admin);
        saveCommentTranslationButton.setVisible(admin);
        autoTranslateCommentButton.setManaged(admin);
        autoTranslateCommentButton.setVisible(admin);
        HBox actions = new HBox(8, addCommentButton, updateCommentButton, deleteCommentButton, saveCommentTranslationButton);
        actions.setAlignment(Pos.CENTER_LEFT);
        HBox translationActions = new HBox(8, autoTranslateCommentButton);
        translationActions.setAlignment(Pos.CENTER_LEFT);
        addCommentButton.getStyleClass().add("primary");
        deleteCommentButton.getStyleClass().add("danger");
        VBox pane = new VBox(10, commentsList, commentContent, commentGifUrl, gifTools, translationActions, actions);
        pane.setPadding(new Insets(12));
        return pane;
    }

    private Parent buildAdminPane() {
        adminCommentSearch.setPromptText("Search comments");
        statsArea.setEditable(false);
        statsArea.setPrefRowCount(12);
        HBox commentTools = new HBox(8, adminCommentSearch, refreshAdminCommentsButton, statsButton);
        HBox.setHgrow(adminCommentSearch, Priority.ALWAYS);
        statusPieChart.setTitle("Posts by status");
        statusPieChart.setLegendVisible(true);
        statusPieChart.setPrefSize(360, 260);
        topPostsChart.setTitle("Top commented posts");
        topPostsChart.setLegendVisible(false);
        topPostsChart.setPrefSize(520, 260);
        HBox charts = new HBox(12, statusPieChart, topPostsChart);
        charts.setAlignment(Pos.CENTER_LEFT);
        VBox pane = new VBox(10, commentTools, charts, adminCommentsList, new Label("Stats"), statsArea);
        pane.setPadding(new Insets(12));
        return pane;
    }

    private void configureControls() {
        localeBox.setValue("orig");
        postStatus.setValue(PostStatus.OPEN);
        ratingSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.5, 5.0, 4.0, 0.5));
        ratingSpinner.setEditable(true);
        postsList.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Post post, boolean empty) {
                super.updateItem(post, empty);
                setText(empty || post == null ? null : "#" + post.getId() + " [" + post.getStatus() + "] " + post.getTitle());
            }
        });
        commentsList.setCellFactory(list -> new CommentCell());
        adminCommentsList.setCellFactory(list -> new CommentCell());
    }

    private static class CommentCell extends ListCell<Comment> {
        @Override
        protected void updateItem(Comment comment, boolean empty) {
            super.updateItem(comment, empty);
            if (empty || comment == null) {
                setText(null);
                setGraphic(null);
                return;
            }
            String content = comment.getContent() == null || comment.getContent().isBlank() ? "(GIF only)" : comment.getContent();
            Label author = new Label("#" + comment.getId() + " by " + comment.getOwnerName());
            author.getStyleClass().add("comment-author");
            Label body = new Label(content);
            body.setWrapText(true);
            VBox box = new VBox(6, author, body);

            if (comment.getGifUrl() != null && !comment.getGifUrl().isBlank()) {
                ImageView preview = gifImage(comment.getGifUrl(), 220, 150);
                box.getChildren().add(preview);
            }
            setText(null);
            setGraphic(box);
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
    }
}
