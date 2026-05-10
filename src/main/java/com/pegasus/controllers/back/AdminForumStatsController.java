package com.pegasus.controllers.back;

import com.pegasus.controllers.front.ForumModuleLauncher;
import com.pegasus.forumdesktop.dao.UserDao;
import com.pegasus.forumdesktop.model.Post;
import com.pegasus.forumdesktop.model.PostStatus;
import com.pegasus.forumdesktop.model.RatingSummary;
import com.pegasus.forumdesktop.service.ForumService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class AdminForumStatsController {
    @FXML private Label statusLabel;
    @FXML private Label totalPostsLabel;
    @FXML private Label totalCommentsLabel;
    @FXML private Label totalRatingsLabel;
    @FXML private Label avgRatingLabel;
    @FXML private PieChart statusPieChart;
    @FXML private PieChart moderationPieChart;
    @FXML private BarChart<Number, String> topCommentsChart;
    @FXML private BarChart<Number, String> topRatingsChart;
    @FXML private VBox topCommentsDetailsBox;
    @FXML private VBox topRatingsDetailsBox;

    private ForumService forumService;
    private com.pegasus.forumdesktop.model.User adminForumUser;

    @FXML
    public void initialize() {
        try {
            UserDao userDao = new UserDao();
            forumService = ForumModuleLauncher.createForumService(userDao);
            adminForumUser = ForumModuleLauncher.resolveForumUserForCurrentSession(userDao);
            refreshStats();
        } catch (Exception e) {
            if (statusLabel != null) {
                statusLabel.setText("Failed to load forum stats: " + e.getMessage());
            }
        }
    }

    @FXML
    public void refreshStats() {
        if (forumService == null) {
            return;
        }

        List<Post> posts = new ArrayList<>(forumService.visiblePosts(adminForumUser, "", null));
        int totalPosts = posts.size();
        if (totalPosts == 0) {
            totalPostsLabel.setText("0");
            totalCommentsLabel.setText("0");
            totalRatingsLabel.setText("0");
            avgRatingLabel.setText("0.00 / 5");
            statusPieChart.setData(FXCollections.observableArrayList());
            moderationPieChart.setData(FXCollections.observableArrayList());
            topCommentsChart.getData().clear();
            topRatingsChart.getData().clear();
            topCommentsDetailsBox.getChildren().clear();
            topRatingsDetailsBox.getChildren().clear();
            statusLabel.setText("No forum data yet.");
            return;
        }

        Map<PostStatus, Integer> byStatus = new EnumMap<>(PostStatus.class);
        int totalComments = 0;
        int totalRatingsCount = 0;
        double ratingsAverageAccumulated = 0.0;
        int bannedPosts = 0;
        int bannedComments = 0;
        int createRequests = 0;
        int editRequests = 0;

        List<PostCommentsRow> topCommentRows = new ArrayList<>();
        List<PostRatingRow> topRatedRows = new ArrayList<>();

        for (Post post : posts) {
            PostStatus status = post.getStatus() == null ? PostStatus.OPEN : post.getStatus();
            byStatus.merge(status, 1, Integer::sum);
            if (post.isBannedByAdmin()) {
                bannedPosts++;
            }
            if ("EDIT".equalsIgnoreCase(post.getRequestType())) {
                editRequests++;
            } else {
                createRequests++;
            }

            var comments = forumService.commentsForPost(post.getId());
            totalComments += comments.size();
            for (var comment : comments) {
                if (comment.isBannedByAdmin()) {
                    bannedComments++;
                }
            }
            topCommentRows.add(new PostCommentsRow(post, comments.size()));

            RatingSummary ratingSummary = forumService.ratingSummary(post.getId());
            totalRatingsCount += ratingSummary.count();
            ratingsAverageAccumulated += ratingSummary.average();
            topRatedRows.add(new PostRatingRow(post, ratingSummary));
        }

        double avgRatingAcrossPosts = ratingsAverageAccumulated / totalPosts;

        totalPostsLabel.setText(String.valueOf(totalPosts));
        totalCommentsLabel.setText(String.valueOf(totalComments));
        totalRatingsLabel.setText(String.valueOf(totalRatingsCount));
        avgRatingLabel.setText(String.format("%.2f / 5", avgRatingAcrossPosts));

        statusPieChart.setData(FXCollections.observableArrayList(
                new PieChart.Data("OPEN", byStatus.getOrDefault(PostStatus.OPEN, 0)),
                new PieChart.Data("IN_PROGRESS", byStatus.getOrDefault(PostStatus.IN_PROGRESS, 0)),
                new PieChart.Data("DENIED", byStatus.getOrDefault(PostStatus.DENIED, 0)),
                new PieChart.Data("CLOSED", byStatus.getOrDefault(PostStatus.CLOSED, 0))
        ));
        installPieTooltips(statusPieChart);

        moderationPieChart.setData(FXCollections.observableArrayList(
                new PieChart.Data("Banned posts", bannedPosts),
                new PieChart.Data("Banned comments", bannedComments),
                new PieChart.Data("Create requests", createRequests),
                new PieChart.Data("Edit requests", editRequests)
        ));
        installPieTooltips(moderationPieChart);

        topCommentRows.sort(Comparator.comparingInt(PostCommentsRow::comments).reversed());
        topRatedRows.sort(Comparator
                .comparingDouble((PostRatingRow row) -> row.summary().average())
                .thenComparingInt(row -> row.summary().count())
                .reversed());

        topCommentsChart.getData().clear();
        XYChart.Series<Number, String> commentsSeries = new XYChart.Series<>();
        int commentLimit = Math.min(5, topCommentRows.size());
        for (int i = 0; i < commentLimit; i++) {
            PostCommentsRow row = topCommentRows.get(i);
            commentsSeries.getData().add(new XYChart.Data<>(row.comments(), shortTitle(row.post().getTitle())));
        }
        topCommentsChart.getData().add(commentsSeries);
        installBarTooltips(commentsSeries, topCommentRows.stream()
                .limit(commentLimit)
                .map(row -> safeTitle(row.post()) + " -> " + row.comments() + " comments")
                .toList());
        fillTopCommentsDetails(topCommentRows, commentLimit);

        topRatingsChart.getData().clear();
        XYChart.Series<Number, String> ratingsSeries = new XYChart.Series<>();
        int ratingLimit = Math.min(5, topRatedRows.size());
        for (int i = 0; i < ratingLimit; i++) {
            PostRatingRow row = topRatedRows.get(i);
            ratingsSeries.getData().add(new XYChart.Data<>(row.summary().average(), shortTitle(row.post().getTitle())));
        }
        topRatingsChart.getData().add(ratingsSeries);
        installBarTooltips(ratingsSeries, topRatedRows.stream()
                .limit(ratingLimit)
                .map(row -> safeTitle(row.post()) + " -> " + String.format("%.2f/5 (%d votes)", row.summary().average(), row.summary().count()))
                .toList());
        fillTopRatingsDetails(topRatedRows, ratingLimit);

        statusLabel.setText("Forum stats refreshed for " + totalPosts + " posts.");
    }

    private void installPieTooltips(PieChart chart) {
        for (PieChart.Data data : chart.getData()) {
            String text = data.getName() + ": " + (int) data.getPieValue();
            if (data.getNode() != null) {
                Tooltip.install(data.getNode(), new Tooltip(text));
            } else {
                data.nodeProperty().addListener((obs, oldNode, newNode) -> {
                    if (newNode != null) {
                        Tooltip.install(newNode, new Tooltip(text));
                    }
                });
            }
        }
    }

    private void installBarTooltips(XYChart.Series<Number, String> series, List<String> details) {
        for (int i = 0; i < series.getData().size() && i < details.size(); i++) {
            XYChart.Data<Number, String> data = series.getData().get(i);
            String text = details.get(i);
            if (data.getNode() != null) {
                Tooltip.install(data.getNode(), new Tooltip(text));
            } else {
                data.nodeProperty().addListener((obs, oldNode, newNode) -> {
                    if (newNode != null) {
                        Tooltip.install(newNode, new Tooltip(text));
                    }
                });
            }
        }
    }

    private void fillTopCommentsDetails(List<PostCommentsRow> rows, int limit) {
        topCommentsDetailsBox.getChildren().clear();
        for (int i = 0; i < limit; i++) {
            PostCommentsRow row = rows.get(i);
            Label detail = new Label((i + 1) + ". " + safeTitle(row.post()) + " - " + row.comments() + " comments");
            detail.getStyleClass().add("admin-panel-copy");
            detail.setWrapText(true);
            topCommentsDetailsBox.getChildren().add(detail);
        }
    }

    private void fillTopRatingsDetails(List<PostRatingRow> rows, int limit) {
        topRatingsDetailsBox.getChildren().clear();
        for (int i = 0; i < limit; i++) {
            PostRatingRow row = rows.get(i);
            Label detail = new Label((i + 1) + ". " + safeTitle(row.post()) + " - "
                    + String.format("%.2f/5 (%d votes)", row.summary().average(), row.summary().count()));
            detail.getStyleClass().add("admin-panel-copy");
            detail.setWrapText(true);
            topRatingsDetailsBox.getChildren().add(detail);
        }
    }

    private String shortTitle(String value) {
        String clean = value == null || value.isBlank() ? "Untitled post" : value.trim();
        return clean.length() > 24 ? clean.substring(0, 21) + "..." : clean;
    }

    private String safeTitle(Post post) {
        return post == null || post.getTitle() == null || post.getTitle().isBlank()
                ? "Untitled post"
                : post.getTitle().trim();
    }

    private record PostCommentsRow(Post post, int comments) {}
    private record PostRatingRow(Post post, RatingSummary summary) {}
}
