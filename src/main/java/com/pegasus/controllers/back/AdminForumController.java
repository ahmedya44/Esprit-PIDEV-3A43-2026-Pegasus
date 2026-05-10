package com.pegasus.controllers.back;

import com.pegasus.controllers.front.ForumModuleLauncher;
import com.pegasus.forumdesktop.dao.UserDao;
import com.pegasus.forumdesktop.model.Comment;
import com.pegasus.forumdesktop.model.Post;
import com.pegasus.forumdesktop.model.PostStatus;
import com.pegasus.forumdesktop.service.ForumService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class AdminForumController {
    @FXML private Label statusLabel;
    @FXML private VBox postsContainer;
    @FXML private TextField searchField;
    @FXML private ComboBox<String> statusFilterBox;
    @FXML private ComboBox<String> sortBox;

    private ForumService forumService;
    private com.pegasus.forumdesktop.model.User adminForumUser;

    @FXML
    public void initialize() {
        try {
            UserDao userDao = new UserDao();
            forumService = ForumModuleLauncher.createForumService(userDao);
            adminForumUser = ForumModuleLauncher.resolveForumUserForCurrentSession(userDao);
            configureQueryControls();
            refreshForumDashboard();
        } catch (Exception e) {
            if (statusLabel != null) {
                statusLabel.setText("Failed to load forum dashboard: " + e.getMessage());
            }
        }
    }

    @FXML
    public void openForumModeration() {
        ForumModuleLauncher.openForumWindow();
        if (statusLabel != null) {
            statusLabel.setText("Forum workspace opened in its dedicated window.");
        }
    }

    @FXML
    public void refreshForumDashboard() {
        if (forumService == null || postsContainer == null) {
            return;
        }

        postsContainer.getChildren().clear();
        String query = searchField == null ? "" : safe(searchField.getText(), "").toLowerCase();
        String statusFilter = statusFilterBox == null ? "All" : safe(statusFilterBox.getValue(), "All");
        String sortBy = sortBox == null ? "Newest" : safe(sortBox.getValue(), "Newest");

        List<Post> posts = forumService.visiblePosts(adminForumUser, "", null).stream()
                .filter(post -> statusFilter.equalsIgnoreCase("All")
                        || safe(post.getStatus() == null ? "OPEN" : post.getStatus().name(), "OPEN").equalsIgnoreCase(statusFilter))
                .filter(post -> query.isBlank()
                        || safe(post.getTitle(), "").toLowerCase().contains(query)
                        || safe(post.getContent(), "").toLowerCase().contains(query)
                        || safe(post.getOwnerName(), safe(post.getAuthorName(), "")).toLowerCase().contains(query))
                .collect(Collectors.toCollection(ArrayList::new));
        posts.sort(resolveSort(sortBy));
        if (posts.isEmpty()) {
            Label empty = new Label("No forum posts found.");
            empty.getStyleClass().add("admin-panel-copy");
            postsContainer.getChildren().add(empty);
            statusLabel.setText("0 posts loaded.");
            return;
        }

        for (Post post : posts) {
            postsContainer.getChildren().add(buildPostCard(post));
        }
        statusLabel.setText(posts.size() + " posts loaded.");
    }

    private void configureQueryControls() {
        if (statusFilterBox != null) {
            statusFilterBox.getItems().setAll("All", "OPEN", "IN_PROGRESS", "DENIED", "CLOSED");
            statusFilterBox.setValue("All");
        }
        if (sortBox != null) {
            sortBox.getItems().setAll("Newest", "Oldest", "Title A-Z", "Title Z-A", "Most Comments");
            sortBox.setValue("Newest");
        }
    }

    private Comparator<Post> resolveSort(String sortBy) {
        String sort = safe(sortBy, "Newest");
        return switch (sort) {
            case "Oldest" -> Comparator.comparing(Post::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder()));
            case "Title A-Z" -> Comparator.comparing(post -> safe(post.getTitle(), "").toLowerCase());
            case "Title Z-A" -> Comparator.comparing((Post post) -> safe(post.getTitle(), "").toLowerCase()).reversed();
            case "Most Comments" -> Comparator.comparingInt((Post post) -> forumService.commentsForPost(post.getId()).size()).reversed();
            default -> Comparator.comparing(Post::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder()));
        };
    }

    private VBox buildPostCard(Post post) {
        Label title = new Label(post.getTitle() == null || post.getTitle().isBlank() ? "Untitled post" : post.getTitle());
        title.getStyleClass().add("admin-panel-title");

        Label owner = new Label("By " + safe(post.getOwnerName(), safe(post.getAuthorName(), "Unknown")));
        owner.getStyleClass().add("admin-panel-copy");

        Label status = new Label("Status: " + (post.getStatus() == null ? "OPEN" : post.getStatus().name()));
        status.getStyleClass().add("admin-page-subtitle");

        ComboBox<PostStatus> statusBox = new ComboBox<>(FXCollections.observableArrayList(PostStatus.values()));
        statusBox.setValue(post.getStatus() == null ? PostStatus.OPEN : post.getStatus());
        Button applyStatusButton = new Button("Update Status");
        applyStatusButton.getStyleClass().add("admin-secondary-button");
        applyStatusButton.setOnAction(event -> {
            try {
                forumService.changePostStatus(adminForumUser, post, statusBox.getValue());
                refreshForumDashboard();
            } catch (Exception e) {
                statusLabel.setText("Status update failed: " + e.getMessage());
            }
        });
        HBox statusActions = new HBox(8, statusBox, applyStatusButton);
        statusActions.setAlignment(Pos.CENTER_LEFT);

        Label created = new Label("Created: " + (post.getCreatedAt() == null
                ? "-"
                : post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        created.getStyleClass().add("admin-page-subtitle");

        HBox meta = new HBox(14, owner, status, created);
        meta.setAlignment(Pos.CENTER_LEFT);

        Label content = new Label(safe(post.getContent(), ""));
        content.setWrapText(true);
        content.getStyleClass().add("admin-panel-copy");

        Label blacklist = new Label("Blacklist: " + blacklistEmails(post));
        blacklist.setWrapText(true);
        blacklist.getStyleClass().add("admin-panel-copy");

        Button togglePostBanButton = new Button(post.isBannedByAdmin() ? "Unban Post" : "Ban Post");
        togglePostBanButton.getStyleClass().add(post.isBannedByAdmin() ? "admin-secondary-button" : "admin-primary-button");
        togglePostBanButton.setOnAction(event -> togglePostBan(post));

        List<Comment> comments = forumService.commentsForPost(post.getId());
        Label commentsLabel = new Label("Comments (" + comments.size() + ")");
        commentsLabel.getStyleClass().add("admin-page-subtitle");

        VBox commentsBox = new VBox(6);
        for (Comment comment : comments) {
            commentsBox.getChildren().add(buildCommentRow(comment));
        }
        if (comments.isEmpty()) {
            Label none = new Label("No comments");
            none.getStyleClass().add("admin-panel-copy");
            commentsBox.getChildren().add(none);
        }

        VBox card = new VBox(10, title, meta, content, status, statusActions, blacklist, togglePostBanButton, commentsLabel, commentsBox);
        card.getStyleClass().add("admin-panel");
        card.setFillWidth(true);
        return card;
    }

    private String blacklistEmails(Post post) {
        if (post.getBlacklistedViewerIds() == null || post.getBlacklistedViewerIds().isEmpty()) {
            return "(none)";
        }
        Map<Integer, String> emailById = forumService.users().stream()
                .collect(Collectors.toMap(com.pegasus.forumdesktop.model.User::getId, u -> safe(u.getEmail(), ""), (a, b) -> a));
        String joined = post.getBlacklistedViewerIds().stream()
                .map(id -> {
                    String email = emailById.get(id);
                    return email == null || email.isBlank() ? ("#" + id) : email;
                })
                .collect(Collectors.joining(", "));
        return joined.isBlank() ? "(none)" : joined;
    }

    private HBox buildCommentRow(Comment comment) {
        Label author = new Label(safe(comment.getOwnerName(), safe(comment.getAuthorName(), "User")) + ":");
        author.getStyleClass().add("admin-page-subtitle");

        String bodyValue = safe(comment.getContent(), "");
        if (bodyValue.isBlank()) {
            bodyValue = comment.getGifUrl() == null || comment.getGifUrl().isBlank()
                    ? "(empty comment)"
                    : "(GIF only)";
        }
        Label body = new Label(bodyValue);
        body.setWrapText(true);
        body.getStyleClass().add("admin-panel-copy");
        HBox.setHgrow(body, Priority.ALWAYS);

        Label gif = new Label((comment.getGifUrl() == null || comment.getGifUrl().isBlank()) ? "" : "GIF");
        gif.getStyleClass().add("admin-page-subtitle");

        Button toggleCommentBanButton = new Button(comment.isBannedByAdmin() ? "Unban Comment" : "Ban Comment");
        toggleCommentBanButton.getStyleClass().add(comment.isBannedByAdmin() ? "admin-secondary-button" : "admin-primary-button");
        toggleCommentBanButton.setOnAction(event -> toggleCommentBan(comment));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox row = new HBox(8, author, body, spacer, gif, toggleCommentBanButton);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private void togglePostBan(Post post) {
        try {
            if (post.isBannedByAdmin()) {
                forumService.unbanPost(adminForumUser, post);
            } else {
                forumService.banPost(adminForumUser, post);
            }
            refreshForumDashboard();
        } catch (Exception e) {
            statusLabel.setText("Post moderation failed: " + e.getMessage());
        }
    }

    private void toggleCommentBan(Comment comment) {
        try {
            if (comment.isBannedByAdmin()) {
                forumService.unbanComment(adminForumUser, comment);
            } else {
                forumService.banComment(adminForumUser, comment);
            }
            refreshForumDashboard();
        } catch (Exception e) {
            statusLabel.setText("Comment moderation failed: " + e.getMessage());
        }
    }

    private String safe(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value.trim();
    }
}
