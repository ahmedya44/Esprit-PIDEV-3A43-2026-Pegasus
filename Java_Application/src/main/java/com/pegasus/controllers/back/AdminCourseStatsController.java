package com.pegasus.controllers.back;

import com.pegasus.services.LearningProgressService;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.List;

public class AdminCourseStatsController {
    @FXML private VBox statsRoot;
    @FXML private Label totalCoursesLabel;
    @FXML private Label publishedCoursesLabel;
    @FXML private Label startedCoursesLabel;
    @FXML private Label completedCoursesLabel;
    @FXML private Label quizAttemptsLabel;
    @FXML private Label averageScoreLabel;
    @FXML private Label passRateLabel;

    @FXML private VBox courseEngagementContainer;
    @FXML private VBox quizScoreContainer;
    @FXML private PieChart quizPassPieChart;

    @FXML private TableView<LearningProgressService.CourseStats> courseStatsTable;
    @FXML private TableColumn<LearningProgressService.CourseStats, String> courseNameColumn;
    @FXML private TableColumn<LearningProgressService.CourseStats, String> courseStatusColumn;
    @FXML private TableColumn<LearningProgressService.CourseStats, Integer> courseStartedColumn;
    @FXML private TableColumn<LearningProgressService.CourseStats, Integer> courseCompletedColumn;
    @FXML private TableColumn<LearningProgressService.CourseStats, String> courseLessonsColumn;
    @FXML private TableColumn<LearningProgressService.CourseStats, Integer> courseQuizAttemptsColumn;
    @FXML private TableColumn<LearningProgressService.CourseStats, String> courseAvgScoreColumn;
    @FXML private TableColumn<LearningProgressService.CourseStats, String> coursePassRateColumn;

    @FXML private TableView<LearningProgressService.QuizStats> quizStatsTable;
    @FXML private TableColumn<LearningProgressService.QuizStats, String> quizNameColumn;
    @FXML private TableColumn<LearningProgressService.QuizStats, String> quizCourseColumn;
    @FXML private TableColumn<LearningProgressService.QuizStats, Integer> quizAttemptsColumn;
    @FXML private TableColumn<LearningProgressService.QuizStats, Integer> quizPassedColumn;
    @FXML private TableColumn<LearningProgressService.QuizStats, Integer> quizFailedColumn;
    @FXML private TableColumn<LearningProgressService.QuizStats, String> quizAvgScoreColumn;
    @FXML private TableColumn<LearningProgressService.QuizStats, String> quizScoreRangeColumn;
    @FXML private TableColumn<LearningProgressService.QuizStats, String> quizAvgTimeColumn;

    private final LearningProgressService learningProgressService = new LearningProgressService();
    private final ObservableList<LearningProgressService.CourseStats> courseRows = FXCollections.observableArrayList();
    private final ObservableList<LearningProgressService.QuizStats> quizRows = FXCollections.observableArrayList();
    private boolean entrancePlayed;

    @FXML
    public void initialize() {
        setupCourseTable();
        setupQuizTable();
        refreshStats();
        playEntranceAnimation();
    }

    @FXML
    public void refreshStats() {
        LearningProgressService.OverallLearningStats overview = learningProgressService.getOverallStats();
        List<LearningProgressService.CourseStats> courseStats = learningProgressService.getCourseStats();
        List<LearningProgressService.QuizStats> quizStats = learningProgressService.getQuizStats();

        courseRows.setAll(courseStats);
        quizRows.setAll(quizStats);
        updateSummary(overview);
        renderCourseEngagement(courseStats);
        renderQuizScoreMeters(quizStats);
        renderQuizPassChart(quizStats);
    }

    private void setupCourseTable() {
        courseStatsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        courseStatsTable.setFixedCellSize(58);
        courseStatsTable.setItems(courseRows);
        courseStatsTable.setPlaceholder(tablePlaceholder("No saved course activity yet."));

        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        courseStartedColumn.setCellValueFactory(new PropertyValueFactory<>("startedUsers"));
        courseCompletedColumn.setCellValueFactory(new PropertyValueFactory<>("completedUsers"));
        courseLessonsColumn.setCellValueFactory(new PropertyValueFactory<>("lessonProgressText"));
        courseQuizAttemptsColumn.setCellValueFactory(new PropertyValueFactory<>("quizAttempts"));
        courseAvgScoreColumn.setCellValueFactory(new PropertyValueFactory<>("averageScoreText"));
        coursePassRateColumn.setCellValueFactory(new PropertyValueFactory<>("passRateText"));
        courseStatusColumn.setCellFactory(column -> statusCell());
        styleTextColumn(courseNameColumn);
    }

    private void setupQuizTable() {
        quizStatsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        quizStatsTable.setFixedCellSize(58);
        quizStatsTable.setItems(quizRows);
        quizStatsTable.setPlaceholder(tablePlaceholder("No saved quiz attempts yet."));

        quizNameColumn.setCellValueFactory(new PropertyValueFactory<>("quizName"));
        quizCourseColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        quizAttemptsColumn.setCellValueFactory(new PropertyValueFactory<>("attempts"));
        quizPassedColumn.setCellValueFactory(new PropertyValueFactory<>("passedAttempts"));
        quizFailedColumn.setCellValueFactory(new PropertyValueFactory<>("failedAttempts"));
        quizAvgScoreColumn.setCellValueFactory(new PropertyValueFactory<>("averageScoreText"));
        quizScoreRangeColumn.setCellValueFactory(new PropertyValueFactory<>("scoreRangeText"));
        quizAvgTimeColumn.setCellValueFactory(new PropertyValueFactory<>("averageTimeText"));
        styleTextColumn(quizNameColumn);
        styleTextColumn(quizCourseColumn);
    }

    private TableCell<LearningProgressService.CourseStats, String> statusCell() {
        return new TableCell<>() {
            private final Label badge = new Label();

            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setGraphic(null);
                    return;
                }

                badge.setText(status);
                badge.getStyleClass().setAll("admin-status-badge", statusClass(status));
                setGraphic(badge);
            }
        };
    }

    private <T> void styleTextColumn(TableColumn<T, String> column) {
        column.setCellFactory(col -> new TableCell<>() {
            private final Label label = new Label();

            {
                label.getStyleClass().add("admin-table-main-text");
                label.setWrapText(true);
            }

            @Override
            protected void updateItem(String value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null) {
                    setGraphic(null);
                    return;
                }
                label.setText(value);
                setGraphic(label);
            }
        });
    }

    private void updateSummary(LearningProgressService.OverallLearningStats overview) {
        animateIntegerLabel(totalCoursesLabel, overview.getTotalCourses());
        animateIntegerLabel(publishedCoursesLabel, overview.getPublishedCourses());
        animateIntegerLabel(startedCoursesLabel, overview.getStartedCourses());
        animateIntegerLabel(completedCoursesLabel, overview.getCompletedCourses());
        animateIntegerLabel(quizAttemptsLabel, overview.getQuizAttempts());
        animatePercentLabel(averageScoreLabel, overview.getAverageScore());
        animatePercentLabel(passRateLabel, overview.getPassRate());
    }

    private void renderCourseEngagement(List<LearningProgressService.CourseStats> stats) {
        courseEngagementContainer.getChildren().clear();
        List<LearningProgressService.CourseStats> activeRows = stats.stream()
                .filter(row -> row.getStartedUsers() > 0 || row.getCompletedUsers() > 0 || row.getCompletedLessons() > 0)
                .limit(6)
                .toList();

        if (activeRows.isEmpty()) {
            courseEngagementContainer.getChildren().add(emptyState("No learners have started a course yet."));
            return;
        }

        int maxStarted = Math.max(1, activeRows.stream()
                .mapToInt(LearningProgressService.CourseStats::getStartedUsers)
                .max()
                .orElse(1));
        for (LearningProgressService.CourseStats row : activeRows) {
            double startedProgress = clamp(row.getStartedUsers() / (double) maxStarted);
            double completionProgress = row.getStartedUsers() == 0
                    ? 0
                    : clamp(row.getCompletedUsers() / (double) row.getStartedUsers());
            VBox card = createMeterCard(
                    row.getCourseName(),
                    safeStatus(row.getStatus()) + " | " + row.getLessonProgressText() + " lessons done",
                    "Started users",
                    String.valueOf(row.getStartedUsers()),
                    startedProgress,
                    "Completed users",
                    row.getCompletedUsers() + " (" + Math.round(completionProgress * 100) + "%)",
                    completionProgress,
                    false
            );
            courseEngagementContainer.getChildren().add(card);
        }
    }

    private void renderQuizScoreMeters(List<LearningProgressService.QuizStats> stats) {
        quizScoreContainer.getChildren().clear();
        List<LearningProgressService.QuizStats> activeRows = stats.stream()
                .filter(row -> row.getAttempts() > 0)
                .limit(6)
                .toList();

        if (activeRows.isEmpty()) {
            quizScoreContainer.getChildren().add(emptyState("No users have taken a quiz yet."));
            return;
        }

        for (LearningProgressService.QuizStats row : activeRows) {
            double averageProgress = clamp(row.getAverageScore() / 100.0);
            VBox card = createMeterCard(
                    row.getQuizName(),
                    row.getCourseName() + " | " + row.getAttempts() + " take(s)",
                    "Average score",
                    row.getAverageScoreText(),
                    averageProgress,
                    "Passed / failed",
                    row.getPassedAttempts() + " / " + row.getFailedAttempts(),
                    row.getAttempts() == 0 ? 0 : clamp(row.getPassedAttempts() / (double) row.getAttempts()),
                    true
            );
            quizScoreContainer.getChildren().add(card);
        }
    }

    private VBox createMeterCard(String title, String subtitle,
                                 String firstLabel, String firstValue, double firstProgress,
                                 String secondLabel, String secondValue, double secondProgress,
                                 boolean scoreStyle) {
        Label titleLabel = new Label(shorten(title, 54));
        titleLabel.getStyleClass().add("admin-stat-meter-title");
        titleLabel.setWrapText(true);

        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.getStyleClass().add("admin-stat-meter-subtitle");
        subtitleLabel.setWrapText(true);

        VBox copy = new VBox(2, titleLabel, subtitleLabel);
        HBox header = new HBox(copy);
        header.setAlignment(Pos.CENTER_LEFT);

        ProgressBar firstBar = new ProgressBar(0);
        firstBar.getStyleClass().addAll("admin-stat-progress", scoreStyle ? "score" : "started");
        ProgressBar secondBar = new ProgressBar(0);
        secondBar.getStyleClass().addAll("admin-stat-progress", scoreStyle ? "passed" : "completed");

        VBox card = new VBox(
                10,
                header,
                meterLine(firstLabel, firstValue, firstBar),
                meterLine(secondLabel, secondValue, secondBar)
        );
        card.getStyleClass().add("admin-stat-meter-card");
        animateProgress(firstBar, firstProgress);
        animateProgress(secondBar, secondProgress);
        return card;
    }

    private HBox meterLine(String labelText, String valueText, ProgressBar progressBar) {
        Label label = new Label(labelText);
        label.getStyleClass().add("admin-stat-meter-label");
        label.setMinWidth(118);

        Label value = new Label(valueText);
        value.getStyleClass().add("admin-stat-meter-value");
        value.setMinWidth(86);
        value.setAlignment(Pos.CENTER_RIGHT);

        progressBar.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(progressBar, Priority.ALWAYS);

        HBox row = new HBox(12, label, progressBar, value);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private void renderQuizPassChart(List<LearningProgressService.QuizStats> stats) {
        int passed = stats.stream().mapToInt(LearningProgressService.QuizStats::getPassedAttempts).sum();
        int failed = stats.stream().mapToInt(LearningProgressService.QuizStats::getFailedAttempts).sum();

        if (passed + failed == 0) {
            quizPassPieChart.setData(FXCollections.observableArrayList(new PieChart.Data("No attempts", 1)));
            quizPassPieChart.setTitle("Waiting for attempts");
            return;
        }

        quizPassPieChart.setTitle("");
        quizPassPieChart.setData(FXCollections.observableArrayList(
                new PieChart.Data("Passed", passed),
                new PieChart.Data("Failed", failed)
        ));
        Platform.runLater(() -> quizPassPieChart.getData().forEach(data -> animatePieSlice(data.getNode())));
    }

    private void animateIntegerLabel(Label label, int target) {
        SimpleIntegerProperty value = new SimpleIntegerProperty(0);
        value.addListener((obs, oldValue, newValue) -> label.setText(String.valueOf(newValue.intValue())));
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(value, 0)),
                new KeyFrame(Duration.millis(700), new KeyValue(value, target, Interpolator.EASE_OUT))
        );
        timeline.play();
    }

    private void animatePercentLabel(Label label, double target) {
        SimpleDoubleProperty value = new SimpleDoubleProperty(0);
        value.addListener((obs, oldValue, newValue) -> label.setText(Math.round(newValue.doubleValue()) + "%"));
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(value, 0)),
                new KeyFrame(Duration.millis(700), new KeyValue(value, Math.max(0, target), Interpolator.EASE_OUT))
        );
        timeline.play();
    }

    private void animateProgress(ProgressBar progressBar, double target) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), 0)),
                new KeyFrame(Duration.millis(760), new KeyValue(progressBar.progressProperty(), clamp(target), Interpolator.EASE_OUT))
        );
        timeline.play();
    }

    private void animatePieSlice(Node slice) {
        if (slice == null) {
            return;
        }
        slice.setScaleX(0.92);
        slice.setScaleY(0.92);
        FadeTransition fade = new FadeTransition(Duration.millis(420), slice);
        fade.setFromValue(0.35);
        fade.setToValue(1);
        javafx.animation.ScaleTransition scale = new javafx.animation.ScaleTransition(Duration.millis(420), slice);
        scale.setToX(1);
        scale.setToY(1);
        new ParallelTransition(fade, scale).play();
    }

    private void playEntranceAnimation() {
        if (entrancePlayed || statsRoot == null) {
            return;
        }
        entrancePlayed = true;
        int index = 0;
        for (Node child : statsRoot.getChildren()) {
            child.setOpacity(0);
            child.setTranslateY(18);
            FadeTransition fade = new FadeTransition(Duration.millis(360), child);
            fade.setToValue(1);
            TranslateTransition slide = new TranslateTransition(Duration.millis(360), child);
            slide.setToY(0);
            ParallelTransition transition = new ParallelTransition(fade, slide);
            transition.setDelay(Duration.millis(index * 70L));
            transition.play();
            index++;
        }
    }

    private Label emptyState(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("admin-stat-empty");
        label.setMaxWidth(Double.MAX_VALUE);
        return label;
    }

    private Label tablePlaceholder(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("admin-table-placeholder");
        return label;
    }

    private double clamp(double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            return 0;
        }
        return Math.max(0, Math.min(1, value));
    }

    private String safeStatus(String status) {
        return status == null || status.isBlank() ? "COURSE" : status;
    }

    private String shorten(String value, int maxLength) {
        if (value == null || value.isBlank()) {
            return "Untitled";
        }
        return value.length() <= maxLength ? value : value.substring(0, maxLength - 1) + ".";
    }

    private String statusClass(String status) {
        if ("PUBLISHED".equalsIgnoreCase(status)) {
            return "admin-status-published";
        }
        if ("HIDDEN".equalsIgnoreCase(status)) {
            return "admin-status-hidden";
        }
        return "admin-status-draft";
    }
}
