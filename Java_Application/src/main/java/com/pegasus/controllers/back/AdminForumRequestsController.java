package com.pegasus.controllers.back;

import com.pegasus.controllers.front.ForumModuleLauncher;
import com.pegasus.forumdesktop.dao.UserDao;
import com.pegasus.forumdesktop.model.Post;
import com.pegasus.forumdesktop.model.PostStatus;
import com.pegasus.forumdesktop.service.ForumService;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.File;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdminForumRequestsController {
    @FXML private Label statusLabel;
    @FXML private VBox requestsContainer;
    @FXML private TextField searchField;
    @FXML private ComboBox<String> requestTypeFilterBox;
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
            refreshRequests();
        } catch (Exception e) {
            statusLabel.setText("Failed to load requests: " + e.getMessage());
        }
    }

    @FXML
    public void refreshRequests() {
        if (forumService == null || requestsContainer == null) {
            return;
        }
        requestsContainer.getChildren().clear();
        String query = searchField == null ? "" : safe(searchField.getText(), "").toLowerCase();
        String typeFilter = requestTypeFilterBox == null ? "All" : safe(requestTypeFilterBox.getValue(), "All");
        String sortBy = sortBox == null ? "Newest" : safe(sortBox.getValue(), "Newest");
        List<Post> requests = forumService.visiblePosts(adminForumUser, "", PostStatus.IN_PROGRESS).stream()
                .filter(post -> typeFilter.equalsIgnoreCase("All")
                        || safe(post.getRequestType(), "CREATE").equalsIgnoreCase(typeFilter))
                .filter(post -> query.isBlank()
                        || safe(post.getTitle(), "").toLowerCase().contains(query)
                        || safe(post.getContent(), "").toLowerCase().contains(query)
                        || safe(post.getOwnerName(), safe(post.getAuthorName(), "")).toLowerCase().contains(query)
                        || safe(post.getAuthorEmail(), "").toLowerCase().contains(query))
                .collect(Collectors.toCollection(ArrayList::new));
        requests.sort(resolveSort(sortBy));
        if (requests.isEmpty()) {
            Label none = new Label("No pending forum post requests.");
            none.getStyleClass().add("admin-panel-copy");
            requestsContainer.getChildren().add(none);
            statusLabel.setText("0 pending requests.");
            return;
        }
        for (Post post : requests) {
            requestsContainer.getChildren().add(buildRequestCard(post));
        }
        statusLabel.setText(requests.size() + " pending requests.");
    }

    private void configureQueryControls() {
        if (requestTypeFilterBox != null) {
            requestTypeFilterBox.getItems().setAll("All", "CREATE", "EDIT");
            requestTypeFilterBox.setValue("All");
        }
        if (sortBox != null) {
            sortBox.getItems().setAll("Newest", "Oldest", "Title A-Z", "Title Z-A");
            sortBox.setValue("Newest");
        }
    }

    private Comparator<Post> resolveSort(String sortBy) {
        String sort = safe(sortBy, "Newest");
        return switch (sort) {
            case "Oldest" -> Comparator.comparing(Post::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder()));
            case "Title A-Z" -> Comparator.comparing(post -> safe(post.getTitle(), "").toLowerCase());
            case "Title Z-A" -> Comparator.comparing((Post post) -> safe(post.getTitle(), "").toLowerCase()).reversed();
            default -> Comparator.comparing(Post::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder()));
        };
    }

    private VBox buildRequestCard(Post post) {
        Label title = new Label(post.getTitle() == null || post.getTitle().isBlank() ? "Untitled post" : post.getTitle());
        title.getStyleClass().add("admin-panel-title");

        String ownerName = post.getOwnerName() == null || post.getOwnerName().isBlank() ? post.getAuthorName() : post.getOwnerName();
        Label owner = new Label("By " + (ownerName == null || ownerName.isBlank() ? "Unknown" : ownerName));
        owner.getStyleClass().add("admin-panel-copy");

        Label status = new Label("Status: " + (post.getStatus() == null ? "-" : post.getStatus().name())
                + " | Admin Ban: " + (post.isBannedByAdmin() ? "YES" : "NO"));
        status.getStyleClass().add("admin-page-subtitle");

        Label requestType = new Label("Request type: " + safe(post.getRequestType(), "CREATE"));
        requestType.getStyleClass().add("admin-page-subtitle");

        Label authorEmail = new Label("Author email: " + safe(post.getAuthorEmail(), "-"));
        authorEmail.getStyleClass().add("admin-page-subtitle");

        Label created = new Label("Created: " + (post.getCreatedAt() == null
                ? "-"
                : post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        created.getStyleClass().add("admin-page-subtitle");

        Label updated = new Label("Updated: " + (post.getUpdatedAt() == null
                ? "-"
                : post.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        updated.getStyleClass().add("admin-page-subtitle");

        Label image = new Label("Image: " + safe(post.getImageName(), "(none)"));
        image.getStyleClass().add("admin-page-subtitle");
        ImageView imagePreview = buildRequestImagePreview(post.getImageName());

        Label blacklist = new Label("Blacklist: " + blacklistEmails(post));
        blacklist.setWrapText(true);
        blacklist.getStyleClass().add("admin-panel-copy");

        Label content = new Label(post.getContent() == null ? "" : post.getContent());
        content.setWrapText(true);
        content.getStyleClass().add("admin-panel-copy");

        Button accept = new Button("Accept");
        accept.getStyleClass().add("admin-primary-button");
        accept.setOnAction(event -> {
            try {
                forumService.acceptPostRequest(adminForumUser, post);
                refreshRequests();
            } catch (Exception e) {
                statusLabel.setText("Accept failed: " + e.getMessage());
            }
        });

        Button deny = new Button("Deny");
        deny.getStyleClass().add("admin-secondary-button");
        deny.setOnAction(event -> {
            try {
                forumService.denyPostRequest(adminForumUser, post);
                refreshRequests();
            } catch (Exception e) {
                statusLabel.setText("Deny failed: " + e.getMessage());
            }
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox actions = new HBox(8, spacer, accept, deny);
        actions.setAlignment(Pos.CENTER_LEFT);

        VBox card = imagePreview == null
                ? new VBox(10, title, owner, status, requestType, authorEmail, created, updated, image, blacklist, content, actions)
                : new VBox(10, title, owner, status, requestType, authorEmail, created, updated, image, imagePreview, blacklist, content, actions);
        card.getStyleClass().add("admin-panel");
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

    private String safe(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value.trim();
    }

    private ImageView buildRequestImagePreview(String imageName) {
        if (imageName == null || imageName.isBlank()) {
            return null;
        }
        String source = imageName.trim();
        if (!source.startsWith("http://") && !source.startsWith("https://") && !source.startsWith("file:/")) {
            source = new File(forumUploadsDir().toFile(), source).toURI().toString();
        }
        Image image = new Image(source, 420, 240, true, true, true);
        if (image.isError()) {
            return null;
        }
        ImageView view = new ImageView(image);
        view.setFitWidth(420);
        view.setFitHeight(240);
        view.setPreserveRatio(true);
        view.getStyleClass().add("forum-card-image");
        return view;
    }

    private Path forumUploadsDir() {
        String configured = System.getenv("PEGASUS_FORUM_UPLOADS_DIR");
        if (configured != null && !configured.isBlank()) {
            return Path.of(configured);
        }
        return Path.of(System.getProperty("user.home"), "pegasus", "uploads", "forum");
    }
}
