package com.pegasus.controllers.front;

import com.pegasus.controllers.FrontContentAware;
import com.pegasus.controllers.SceneNavigator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import com.pegasus.entities.Course;
import com.pegasus.entities.CourseSection;
import com.pegasus.entities.CourseVideo;
import com.pegasus.entities.Quiz;
import com.pegasus.entities.QuizChoice;
import com.pegasus.entities.QuizQuestion;
import com.pegasus.entities.User;
import com.pegasus.services.CertificatePdfService;
import com.pegasus.services.CourseSectionService;
import com.pegasus.services.CourseService;
import com.pegasus.services.CourseVideoService;
import com.pegasus.services.LearningInsightsService;
import com.pegasus.services.LearningProgressService;
import com.pegasus.services.QuizChoiceService;
import com.pegasus.services.QuizQuestionService;
import com.pegasus.services.QuizService;
import com.pegasus.services.YouTubeVideoSummaryService;

import java.awt.Desktop;
import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class CoursePlayerController implements FrontContentAware {

    @FXML
    private Label lblCourseEyebrow;

    @FXML
    private Label lblHeaderTitle;

    @FXML
    private StackPane heroThumbnailBox;

    @FXML
    private Label lblSummaryTitle;

    @FXML
    private Label lblSummaryDescription;

    @FXML
    private Label lblSummaryMeta;

    @FXML
    private Label lblSummaryStatus;

    @FXML
    private Button btnStartCourse;

    @FXML
    private VBox playerArea;

    @FXML
    private VBox sectionsListContainer;

    @FXML
    private Label lblProgress;

    @FXML
    private Label lblPlayerSectionTitle;

    @FXML
    private Label lblPlayerSectionMeta;

    @FXML
    private Label lblPlayerLessonTitle;

    @FXML
    private Label lblPlayerLessonMeta;

    @FXML
    private Label lblLessonDescription;

    @FXML
    private StackPane lessonVideoBox;

    @FXML
    private VBox lessonContentCard;

    @FXML
    private Label lblLessonVideoTitle;

    @FXML
    private Label lblLessonStatus;

    @FXML
    private Button btnOpenVideo;

    @FXML
    private Button btnCompleteLesson;

    @FXML
    private ScrollPane playerScrollPane;

    @FXML
    private VBox quizArea;

    @FXML
    private Label lblQuizTitle;

    @FXML
    private Label lblQuizMeta;

    @FXML
    private Label lblQuizAttemptStatus;

    @FXML
    private VBox quizQuestionsContainer;

    @FXML
    private Button btnSubmitQuiz;

    @FXML
    private Label lblQuizResult;

    @FXML
    private VBox careerInsightsCard;

    @FXML
    private Label lblCareerSummary;

    @FXML
    private FlowPane skillProfileContainer;

    @FXML
    private VBox recommendationsContainer;

    @FXML
    private TextField txtLearnerName;

    @FXML
    private Button btnGenerateCertificate;

    @FXML
    private Label lblCertificateStatus;

    @FXML
    private Label lblQuizScoreDetail;

    @FXML
    private Label lblQuizTimeDetail;

    @FXML
    private Label lblQuizRemainingDetail;

    private final CourseService courseService = new CourseService();
    private final CourseSectionService courseSectionService = new CourseSectionService();
    private final CourseVideoService courseVideoService = new CourseVideoService();
    private final QuizService quizService = new QuizService();
    private final QuizQuestionService quizQuestionService = new QuizQuestionService();
    private final QuizChoiceService quizChoiceService = new QuizChoiceService();
    private final LearningInsightsService learningInsightsService = new LearningInsightsService();
    private final LearningProgressService learningProgressService = new LearningProgressService();
    private final CertificatePdfService certificatePdfService = new CertificatePdfService();
    private final YouTubeVideoSummaryService youTubeVideoSummaryService = new YouTubeVideoSummaryService();
    private final List<CourseSection> sections = new ArrayList<>();
    private final Map<Integer, List<CourseVideo>> videosBySectionId = new HashMap<>();
    private final Set<Integer> openedVideoIds = new HashSet<>();
    private final Set<Integer> completedVideoIds = new HashSet<>();
    private final Set<Integer> completedSectionIds = new HashSet<>();
    private final Map<Integer, ToggleGroup> answerGroupsByQuestionId = new HashMap<>();

    private FrontLayoutController frontLayoutController;
    private Course course;
    private CourseSection selectedSection;
    private CourseVideo selectedVideo;
    private Quiz courseQuiz;
    private List<QuizQuestion> quizQuestions = List.of();
    private boolean courseStarted;
    private boolean quizSubmitted;
    private boolean quizSelected;
    private int lastQuizScore = -1;
    private long quizStartedAtMillis = -1;
    private long lastQuizTimeSpentSeconds = -1;
    private long lastQuizTimeRemainingSeconds = -1;
    private Integer currentUserId;

    @FXML
    public void initialize() {
        playerArea.setManaged(false);
        playerArea.setVisible(false);
        quizArea.setManaged(false);
        quizArea.setVisible(false);
        updatePlayerState();
    }

    @Override
    public void setFrontLayoutController(FrontLayoutController frontLayoutController) {
        this.frontLayoutController = frontLayoutController;
    }

    public void setCourse(Course course) {
        this.course = course;
        lblCourseEyebrow.setText("Course player");
        lblHeaderTitle.setText(course.getTitle());
        lblSummaryTitle.setText(course.getTitle());
        lblSummaryDescription.setText(course.getDescription() == null || course.getDescription().isBlank()
                ? "This course is ready to guide the learner through structured video sections."
                : course.getDescription());
        refreshCourseData();
        renderHeroThumbnail();
        updateProgressLabel();
        updatePlayerState();
        refreshCareerInsights();
    }

    @FXML
    private void goBackToCourses() {
        if (frontLayoutController != null) {
            frontLayoutController.showCourses(null);
        }
    }

    @FXML
    private void startCourse() {
        courseStarted = true;
        persistCourseStarted();
        btnStartCourse.setText("Course in progress");
        btnStartCourse.setDisable(true);
        playerArea.setManaged(true);
        playerArea.setVisible(true);

        if (!sections.isEmpty()) {
            if (selectedSection == null || !isSectionUnlocked(selectedSection)) {
                selectSection(findFirstUnlockedSection());
            } else {
                selectSection(selectedSection);
            }
        }

        playerScrollPane.setVvalue(1.0);
        refreshSectionsList();
        updatePlayerState();
    }

    @FXML
    private void openSelectedVideo() {
        if (selectedVideo == null) {
            return;
        }

        String url = selectedVideo.getVideoUrl();
        if (url == null || url.isBlank()) {
            showAlert(Alert.AlertType.WARNING, "Missing Video URL", "This lesson does not have a playable video link yet.");
            return;
        }

        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
                openedVideoIds.add(selectedVideo.getId());
                persistVideoOpened(selectedVideo);
                updatePlayerState();
                refreshSectionsList();
                return;
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Open Video Error", "The lesson video could not be opened.");
            return;
        }

        showAlert(Alert.AlertType.WARNING, "Unsupported Action", "Opening links is not supported on this device.");
    }

    @FXML
    private void completeSelectedLesson() {
        if (selectedVideo == null) {
            return;
        }
        if (!openedVideoIds.contains(selectedVideo.getId())) {
            showAlert(Alert.AlertType.INFORMATION, "Watch The Lesson First", "Open the lesson video before marking it as completed.");
            return;
        }

        completedVideoIds.add(selectedVideo.getId());
        persistVideoCompleted(selectedVideo);
        if (selectedSection != null && isSectionCompleted(selectedSection)) {
            completedSectionIds.add(selectedSection.getId());
            persistSectionCompleted(selectedSection);
        }
        persistCourseCompletedIfFinished();

        CourseVideo nextVideo = findNextUnlockedVideoInSection(selectedSection);
        if (nextVideo != null) {
            selectedVideo = nextVideo;
        } else if (selectedSection != null && isSectionCompleted(selectedSection)) {
            CourseSection nextSection = findNextSection(selectedSection);
            if (nextSection != null && isSectionUnlocked(nextSection)) {
                selectSection(nextSection);
            }
        }

        refreshSectionsList();
        updateProgressLabel();
        updatePlayerState();
        refreshCareerInsights();
    }

    private void refreshCourseData() {
        sections.clear();
        videosBySectionId.clear();
        openedVideoIds.clear();
        completedVideoIds.clear();
        completedSectionIds.clear();
        answerGroupsByQuestionId.clear();
        selectedSection = null;
        selectedVideo = null;
        courseQuiz = null;
        quizQuestions = List.of();
        courseStarted = false;
        quizSubmitted = false;
        quizSelected = false;
        lastQuizScore = -1;
        quizStartedAtMillis = -1;
        lastQuizTimeSpentSeconds = -1;
        lastQuizTimeRemainingSeconds = -1;
        currentUserId = resolveCurrentUserId();
        btnStartCourse.setText("Start Course");
        btnStartCourse.setDisable(false);
        playerArea.setManaged(false);
        playerArea.setVisible(false);
        lessonContentCard.setManaged(true);
        lessonContentCard.setVisible(true);
        quizArea.setManaged(false);
        quizArea.setVisible(false);
        quizQuestionsContainer.getChildren().clear();
        lblQuizResult.setText("");

        if (course == null) {
            return;
        }

        sections.addAll(courseSectionService.getByCourseId(course.getId()));
        sections.sort(Comparator.comparingInt(CourseSection::getOrderIndex).thenComparingInt(CourseSection::getId));

        int totalVideos = 0;
        for (CourseSection section : sections) {
            List<CourseVideo> videos = new ArrayList<>(courseVideoService.getBySectionId(section.getId()));
            videos.sort(Comparator.comparingInt(CourseVideo::getOrderIndex).thenComparingInt(CourseVideo::getId));
            videosBySectionId.put(section.getId(), videos);
            totalVideos += videos.size();
        }

        lblSummaryMeta.setText(sections.size() + " section(s) | " + totalVideos + " lesson video(s)");
        lblSummaryStatus.setText(course.getStatus() == null ? "Course" : course.getStatus().toUpperCase());
        courseQuiz = quizService.getByCourseId(course.getId());
        if (courseQuiz != null) {
            quizQuestions = quizQuestionService.getByQuizId(courseQuiz.getId());
        }
        restorePersistedProgress();
        refreshSectionsList();
        refreshQuizArea();
        refreshCareerInsights();
    }

    private void restorePersistedProgress() {
        if (!hasPersistentLearner()) {
            return;
        }

        Integer quizId = courseQuiz == null ? null : courseQuiz.getId();
        LearningProgressService.CourseProgressSnapshot progress =
                learningProgressService.loadCourseProgress(currentUserId, course.getId(), quizId);

        openedVideoIds.addAll(progress.getOpenedVideoIds());
        completedVideoIds.addAll(progress.getCompletedVideoIds());
        completedSectionIds.addAll(progress.getCompletedSectionIds());
        courseStarted = progress.isStarted();

        if (courseStarted) {
            syncCompletedSectionsFromVideos();
            playerArea.setManaged(true);
            playerArea.setVisible(true);
            btnStartCourse.setText(isCourseCompleted() ? "Course completed" : "Course in progress");
            btnStartCourse.setDisable(true);
            if (!sections.isEmpty()) {
                selectedSection = findFirstUnlockedSection();
                if (selectedSection != null) {
                    selectedVideo = findFirstUnlockedVideo(selectedSection);
                }
            }
        }

        if (progress.getQuizAttempt() != null) {
            applyQuizAttempt(progress.getQuizAttempt());
        }
    }

    private void syncCompletedSectionsFromVideos() {
        for (CourseSection section : sections) {
            if (isSectionCompleted(section) && completedSectionIds.add(section.getId())) {
                persistSectionCompleted(section);
            }
        }
        persistCourseCompletedIfFinished();
    }

    private void refreshSectionsList() {
        sectionsListContainer.getChildren().clear();

        if (sections.isEmpty()) {
            Label emptyLabel = new Label("No sections have been added to this course yet.");
            emptyLabel.getStyleClass().add("course-player-empty-copy");
            sectionsListContainer.getChildren().add(emptyLabel);
            return;
        }

        for (CourseSection section : sections) {
            sectionsListContainer.getChildren().add(buildSectionCard(section));
        }

        if (courseQuiz != null) {
            sectionsListContainer.getChildren().add(buildQuizPathCard());
        }
    }

    private VBox buildQuizPathCard() {
        boolean courseCompleted = isCourseCompleted();
        boolean active = quizSelected;

        Label titleLabel = new Label("Course Quiz - " + courseQuiz.getTitle());
        titleLabel.getStyleClass().add("course-player-section-title");
        titleLabel.setWrapText(true);

        Label metaLabel = new Label(quizQuestions.size() + " question(s)");
        metaLabel.getStyleClass().add("course-player-section-meta");

        Label stateLabel = new Label(quizSubmitted ? "Submitted" : courseCompleted ? "Unlocked" : "Locked");
        stateLabel.getStyleClass().add("course-player-state-pill");
        if (quizSubmitted) {
            stateLabel.getStyleClass().add("completed");
        } else if (!courseCompleted) {
            stateLabel.getStyleClass().add("locked");
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox header = new HBox(10, new VBox(4, titleLabel, metaLabel), spacer, stateLabel);
        header.setAlignment(Pos.TOP_LEFT);

        VBox card = new VBox(12, header);
        card.getStyleClass().add("course-player-section-card");
        if (active) {
            card.getStyleClass().add("active");
        }
        if (!courseCompleted) {
            card.getStyleClass().add("locked");
        }
        if (quizSubmitted) {
            card.getStyleClass().add("completed");
        }
        card.setCursor(courseCompleted ? Cursor.HAND : Cursor.DEFAULT);

        Label hintLabel = new Label(courseCompleted
                ? "Final step in this course path."
                : "Complete all lessons to unlock.");
        hintLabel.getStyleClass().add("course-player-section-meta");
        card.getChildren().add(hintLabel);

        if (courseCompleted) {
            card.setOnMouseClicked(event -> selectQuiz());
        }

        return card;
    }

    private VBox buildSectionCard(CourseSection section) {
        boolean unlocked = courseStarted && isSectionUnlocked(section);
        boolean active = selectedSection != null && selectedSection.getId() == section.getId();
        boolean completed = completedSectionIds.contains(section.getId());
        List<CourseVideo> videos = videosBySectionId.getOrDefault(section.getId(), List.of());

        Label titleLabel = new Label("Section " + section.getOrderIndex() + " - " + section.getTitle());
        titleLabel.getStyleClass().add("course-player-section-title");
        titleLabel.setWrapText(true);

        Label metaLabel = new Label(videos.size() + " lesson(s)");
        metaLabel.getStyleClass().add("course-player-section-meta");

        Label stateLabel = new Label(completed ? "Completed" : unlocked ? "Unlocked" : courseStarted ? "Locked" : "Starts after you begin");
        stateLabel.getStyleClass().add("course-player-state-pill");
        if (completed) {
            stateLabel.getStyleClass().add("completed");
        } else if (!unlocked) {
            stateLabel.getStyleClass().add("locked");
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(10, new VBox(4, titleLabel, metaLabel), spacer, stateLabel);
        header.setAlignment(Pos.TOP_LEFT);

        VBox card = new VBox(12);
        card.getStyleClass().add("course-player-section-card");
        if (active) {
            card.getStyleClass().add("active");
        }
        if (!unlocked) {
            card.getStyleClass().add("locked");
        }
        if (completed) {
            card.getStyleClass().add("completed");
        }
        card.getChildren().add(header);
        card.setCursor(unlocked ? Cursor.HAND : Cursor.DEFAULT);

        if (unlocked) {
            card.setOnMouseClicked(event -> {
                quizSelected = false;
                selectSection(section);
            });
        }

        VBox lessonsBox = new VBox(8);
        for (int i = 0; i < videos.size(); i++) {
            CourseVideo video = videos.get(i);
            Button lessonButton = new Button((i + 1) + ". " + video.getTitle());
            lessonButton.getStyleClass().add("course-player-lesson-button");
            lessonButton.setMaxWidth(Double.MAX_VALUE);

            boolean lessonUnlocked = unlocked && isVideoUnlocked(section, video);
            boolean lessonCompleted = completedVideoIds.contains(video.getId());
            boolean lessonSelected = selectedVideo != null && selectedVideo.getId() == video.getId();

            if (!lessonUnlocked) {
                lessonButton.getStyleClass().add("locked");
                lessonButton.setText((i + 1) + ". " + video.getTitle() + "  | Locked");
            } else if (lessonCompleted) {
                lessonButton.getStyleClass().add("completed");
                lessonButton.setText((i + 1) + ". " + video.getTitle() + "  | Done");
            }

            if (lessonSelected) {
                lessonButton.getStyleClass().add("active");
            }

            lessonButton.setDisable(!lessonUnlocked);
            lessonButton.setOnAction(event -> {
                quizSelected = false;
                selectedSection = section;
                selectedVideo = video;
                updatePlayerState();
                refreshSectionsList();
            });

            Button resumeButton = new Button("Résumé");
            resumeButton.getStyleClass().add("course-player-video-summary-button");
            resumeButton.setMaxWidth(Double.MAX_VALUE);
            resumeButton.setDisable(!lessonUnlocked);
            resumeButton.setOnAction(event -> showVideoResume(video));

            VBox lessonItem = new VBox(6, lessonButton, resumeButton);
            lessonsBox.getChildren().add(lessonItem);
        }

        if (!videos.isEmpty()) {
            card.getChildren().add(lessonsBox);
        } else {
            Label emptyLessons = new Label("No lesson video yet for this section.");
            emptyLessons.getStyleClass().add("course-player-section-meta");
            card.getChildren().add(emptyLessons);
        }

        return card;
    }

    private void selectSection(CourseSection section) {
        if (section == null || !isSectionUnlocked(section)) {
            return;
        }

        selectedSection = section;
        List<CourseVideo> videos = videosBySectionId.getOrDefault(section.getId(), List.of());
        if (videos.isEmpty()) {
            completedSectionIds.add(section.getId());
            persistSectionCompleted(section);
            persistCourseCompletedIfFinished();
            selectedVideo = null;
            CourseSection nextSection = findNextSection(section);
            if (nextSection != null && isSectionUnlocked(nextSection)) {
                selectedSection = nextSection;
                videos = videosBySectionId.getOrDefault(nextSection.getId(), List.of());
                if (!videos.isEmpty()) {
                    selectedVideo = findFirstUnlockedVideo(nextSection);
                }
            } else {
                selectedSection = null;
            }
        } else if (selectedVideo == null || selectedVideo.getSectionId() != section.getId() || !isVideoUnlocked(section, selectedVideo)) {
            selectedVideo = findFirstUnlockedVideo(section);
        }

        refreshSectionsList();
        updatePlayerState();
    }

    private void updatePlayerState() {
        updateProgressLabel();
        updateContentMode();
        refreshCareerInsights();

        if (course == null) {
            lblPlayerSectionTitle.setText("Lesson");
            lblPlayerSectionMeta.setText("Choose a course to begin.");
            lblPlayerLessonTitle.setText("No lesson selected");
            lblPlayerLessonMeta.setText("");
            lblLessonDescription.setText("The current lesson will appear here.");
            lblLessonVideoTitle.setText("No video loaded");
            lblLessonStatus.setText("");
            btnOpenVideo.setDisable(true);
            btnCompleteLesson.setDisable(true);
            return;
        }

        boolean hasSections = !sections.isEmpty();
        btnStartCourse.setDisable(!hasSections || courseStarted);

        if (!courseStarted) {
            lblPlayerSectionTitle.setText("Course overview");
            lblPlayerSectionMeta.setText(hasSections
                    ? "Start the course to unlock the first section and begin the lesson flow."
                    : "This course still needs sections before learners can start.");
            lblPlayerLessonTitle.setText("Lesson area locked");
            lblPlayerLessonMeta.setText(hasSections ? "The first lesson will appear after you click Start Course." : "No sections found.");
            lblLessonDescription.setText("Sections unlock one by one. A learner must open and complete each lesson before the next section becomes available.");
            lblLessonVideoTitle.setText("Lesson video preview");
            lblLessonStatus.setText(hasSections ? "Waiting for course start" : "No content available yet");
            btnOpenVideo.setDisable(true);
            btnCompleteLesson.setDisable(true);
            return;
        }

        if (quizSelected) {
            refreshQuizArea();
            refreshSectionsList();
            return;
        }

        if (selectedSection == null) {
            selectedSection = findFirstUnlockedSection();
        }

        if (selectedSection == null) {
            lblPlayerSectionTitle.setText("Course completed");
            lblPlayerSectionMeta.setText("Every section in this course has been finished.");
            lblPlayerLessonTitle.setText("All sections completed");
            lblPlayerLessonMeta.setText(completedSectionIds.size() + " / " + sections.size() + " section(s) completed");
            lblLessonDescription.setText("Nice work. The full course path has been completed in order.");
            lblLessonVideoTitle.setText("No remaining lessons");
            lblLessonStatus.setText("All done");
            btnOpenVideo.setDisable(true);
            btnCompleteLesson.setDisable(true);
            refreshQuizArea();
            return;
        }

        List<CourseVideo> videos = videosBySectionId.getOrDefault(selectedSection.getId(), List.of());
        if (selectedVideo == null && !videos.isEmpty()) {
            selectedVideo = findFirstUnlockedVideo(selectedSection);
        }

        lblPlayerSectionTitle.setText(selectedSection.getTitle());
        lblPlayerSectionMeta.setText("Section " + selectedSection.getOrderIndex() + " | " + videos.size() + " lesson(s)");

        if (selectedVideo == null) {
            lblPlayerLessonTitle.setText("No lesson in this section yet");
            lblPlayerLessonMeta.setText("Add a lesson video to continue this part of the course.");
            lblLessonDescription.setText("This section is unlocked but still has no lesson video attached.");
            lblLessonVideoTitle.setText("No lesson video");
            lblLessonStatus.setText(isSectionCompleted(selectedSection) ? "Section completed" : "Waiting for lesson content");
            btnOpenVideo.setDisable(true);
            btnCompleteLesson.setDisable(true);
            return;
        }

        lblPlayerLessonTitle.setText(selectedVideo.getTitle());
        lblPlayerLessonMeta.setText("Lesson " + selectedVideo.getOrderIndex() + " | " + formatDuration(selectedVideo.getDurationSec()));
        lblLessonDescription.setText("Open the lesson video, then mark it as completed to unlock the next step in the course path.");
        lblLessonVideoTitle.setText(selectedVideo.getTitle());

        boolean videoCompleted = completedVideoIds.contains(selectedVideo.getId());
        boolean videoOpened = openedVideoIds.contains(selectedVideo.getId());
        btnOpenVideo.setDisable(false);
        btnCompleteLesson.setDisable(videoCompleted || !videoOpened);
        if (videoCompleted) {
            lblLessonStatus.setText("Lesson completed");
        } else if (videoOpened) {
            lblLessonStatus.setText("Video opened. You can now complete this lesson.");
        } else {
            lblLessonStatus.setText("Open the lesson video to continue.");
        }
        refreshQuizArea();
    }

    private void updateProgressLabel() {
        int totalSections = sections.size();
        int totalVideos = videosBySectionId.values().stream().mapToInt(List::size).sum();
        lblProgress.setText(completedSectionIds.size() + "/" + totalSections + " sections completed | "
                + completedVideoIds.size() + "/" + totalVideos + " lessons completed");
    }

    private void selectQuiz() {
        if (courseQuiz == null || !isCourseCompleted()) {
            return;
        }

        if (quizStartedAtMillis < 0) {
            quizStartedAtMillis = System.currentTimeMillis();
        }
        quizSelected = true;
        refreshQuizArea();
        refreshSectionsList();
        updateContentMode();
    }

    private void updateContentMode() {
        boolean showQuiz = quizSelected && courseQuiz != null && isCourseCompleted();
        if (lessonContentCard != null) {
            lessonContentCard.setManaged(!showQuiz);
            lessonContentCard.setVisible(!showQuiz);
        }
        if (quizArea != null) {
            quizArea.setManaged(showQuiz);
            quizArea.setVisible(showQuiz);
        }
    }

    private boolean isCourseCompleted() {
        return !sections.isEmpty() && completedSectionIds.size() >= sections.size();
    }

    private void renderHeroThumbnail() {
        heroThumbnailBox.getChildren().clear();
        heroThumbnailBox.getStyleClass().setAll("course-player-hero");

        String thumbnailUrl = course == null ? null : course.getThumbnailUrl();
        if (thumbnailUrl != null && !thumbnailUrl.isBlank()) {
            try {
                ImageView imageView = new ImageView(new Image(thumbnailUrl, true));
                imageView.setPreserveRatio(false);
                imageView.setFitHeight(280);
                imageView.fitWidthProperty().bind(heroThumbnailBox.widthProperty());
                imageView.getStyleClass().add("course-player-hero-image");
                heroThumbnailBox.getChildren().add(imageView);
                return;
            } catch (Exception ignored) {
            }
        }

        Label fallback = new Label(course == null ? "Course" : course.getTitle());
        fallback.getStyleClass().add("course-player-hero-fallback");
        heroThumbnailBox.getChildren().add(fallback);
    }

    private CourseSection findFirstUnlockedSection() {
        for (CourseSection section : sections) {
            if (isSectionUnlocked(section)) {
                return section;
            }
        }
        return null;
    }

    private CourseSection findNextSection(CourseSection currentSection) {
        int index = sections.indexOf(currentSection);
        if (index >= 0 && index + 1 < sections.size()) {
            return sections.get(index + 1);
        }
        return null;
    }

    private CourseVideo findFirstUnlockedVideo(CourseSection section) {
        List<CourseVideo> videos = videosBySectionId.getOrDefault(section.getId(), List.of());
        for (CourseVideo video : videos) {
            if (isVideoUnlocked(section, video)) {
                return video;
            }
        }
        return videos.isEmpty() ? null : videos.get(0);
    }

    private CourseVideo findNextUnlockedVideoInSection(CourseSection section) {
        List<CourseVideo> videos = videosBySectionId.getOrDefault(section.getId(), List.of());
        for (CourseVideo video : videos) {
            if (isVideoUnlocked(section, video) && !completedVideoIds.contains(video.getId())) {
                return video;
            }
        }
        return null;
    }

    private boolean isSectionUnlocked(CourseSection section) {
        int index = sections.indexOf(section);
        if (index <= 0) {
            return true;
        }
        CourseSection previousSection = sections.get(index - 1);
        return completedSectionIds.contains(previousSection.getId());
    }

    private boolean isVideoUnlocked(CourseSection section, CourseVideo video) {
        List<CourseVideo> videos = videosBySectionId.getOrDefault(section.getId(), List.of());
        int index = videos.indexOf(video);
        if (index <= 0) {
            return true;
        }
        CourseVideo previousVideo = videos.get(index - 1);
        return completedVideoIds.contains(previousVideo.getId());
    }

    private boolean isSectionCompleted(CourseSection section) {
        List<CourseVideo> videos = videosBySectionId.getOrDefault(section.getId(), List.of());
        if (videos.isEmpty()) {
            return true;
        }
        for (CourseVideo video : videos) {
            if (!completedVideoIds.contains(video.getId())) {
                return false;
            }
        }
        return true;
    }

    private String formatDuration(int durationSec) {
        int minutes = durationSec / 60;
        int seconds = durationSec % 60;
        if (minutes == 0) {
            return seconds + " sec";
        }
        return String.format("%d:%02d", minutes, seconds);
    }

    private long calculateQuizTimeSpentSeconds() {
        if (quizStartedAtMillis < 0) {
            return 0;
        }
        return Math.max(0, (System.currentTimeMillis() - quizStartedAtMillis) / 1000);
    }

    private long calculateQuizTimeRemainingSeconds(long timeSpentSeconds) {
        if (courseQuiz == null || courseQuiz.getTimeLimitMin() == null) {
            return -1;
        }
        long totalSeconds = courseQuiz.getTimeLimitMin() * 60L;
        return Math.max(0, totalSeconds - timeSpentSeconds);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showVideoResume(CourseVideo video) {
        if (video == null) {
            return;
        }

        try {
            YouTubeVideoSummaryService.VideoSummary summary = youTubeVideoSummaryService.summarize(video.getVideoUrl());
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Video Résumé");
            dialog.setHeaderText(summary.title());

            DialogPane pane = dialog.getDialogPane();
            pane.getStyleClass().add("app-dialog-pane");
            pane.getButtonTypes().add(javafx.scene.control.ButtonType.CLOSE);

            VBox content = new VBox(12);
            content.setPrefWidth(620);

            Label metaLabel = new Label("Channel: " + summary.channel()
                    + " | Duration: " + summary.duration()
                    + " | Views: " + summary.views()
                    + " | Published: " + summary.publishedAt());
            metaLabel.setWrapText(true);
            metaLabel.getStyleClass().add("course-player-panel-subtitle");

            TextArea resumeArea = new TextArea(summary.resume());
            resumeArea.setWrapText(true);
            resumeArea.setEditable(false);
            resumeArea.setPrefRowCount(9);

            content.getChildren().addAll(metaLabel, resumeArea);
            pane.setContent(content);
            dialog.showAndWait();
        } catch (Exception exception) {
            showAlert(Alert.AlertType.ERROR, "YouTube Résumé Error", exception.getMessage());
        }
    }

    private void applyQuizAttempt(LearningProgressService.QuizAttemptSnapshot attempt) {
        if (attempt == null) {
            return;
        }

        quizSubmitted = true;
        lastQuizScore = attempt.getScorePercent();
        lastQuizTimeSpentSeconds = attempt.getTimeSpentSeconds();
        lastQuizTimeRemainingSeconds = attempt.getTimeRemainingSeconds();
        if (btnSubmitQuiz != null) {
            btnSubmitQuiz.setDisable(true);
        }
        disableQuizInputs();
        updateQuizResultBadge(attempt.getScorePercent(), attempt.isPassed());
        if (lblQuizAttemptStatus != null) {
            lblQuizAttemptStatus.setText("Quiz already submitted. Your saved attempt is loaded for this user.");
        }
    }

    private void updateQuizResultBadge(int percentage, boolean passed) {
        if (passed) {
            lblQuizResult.setText("Passed | Score: " + percentage + "%");
            lblQuizResult.getStyleClass().setAll("course-player-quiz-result", "passed");
        } else {
            lblQuizResult.setText("Failed | Score: " + percentage + "%");
            lblQuizResult.getStyleClass().setAll("course-player-quiz-result", "failed");
        }
    }

    private void persistCourseStarted() {
        if (hasPersistentLearner()) {
            learningProgressService.markCourseStarted(currentUserId, course.getId());
        }
    }

    private void persistVideoOpened(CourseVideo video) {
        if (hasPersistentLearner() && video != null) {
            learningProgressService.markVideoOpened(currentUserId, course.getId(), video.getSectionId(), video.getId());
        }
    }

    private void persistVideoCompleted(CourseVideo video) {
        if (hasPersistentLearner() && video != null) {
            learningProgressService.markVideoCompleted(currentUserId, course.getId(), video.getSectionId(), video.getId());
        }
    }

    private void persistSectionCompleted(CourseSection section) {
        if (hasPersistentLearner() && section != null) {
            learningProgressService.markSectionCompleted(currentUserId, course.getId(), section.getId());
        }
    }

    private void persistCourseCompletedIfFinished() {
        if (hasPersistentLearner() && isCourseCompleted()) {
            learningProgressService.markCourseCompleted(currentUserId, course.getId());
            if (btnStartCourse != null) {
                btnStartCourse.setText("Course completed");
            }
        }
    }

    private boolean hasPersistentLearner() {
        return currentUserId != null && currentUserId > 0 && course != null;
    }

    private Integer resolveCurrentUserId() {
        User currentUser = SceneNavigator.getCurrentUser();
        if (currentUser == null || currentUser.getId() == null || currentUser.getId() <= 0) {
            return null;
        }
        return currentUser.getId();
    }

    @FXML
    private void submitQuiz() {
        if (courseQuiz == null || quizSubmitted) {
            return;
        }

        if (quizQuestions.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "No Quiz Questions", "This course quiz has not been filled with questions yet.");
            return;
        }

        int totalPoints = 0;
        int earnedPoints = 0;

        for (QuizQuestion question : quizQuestions) {
            totalPoints += question.getPoints();
            ToggleGroup group = answerGroupsByQuestionId.get(question.getId());
            if (group == null || !(group.getSelectedToggle() instanceof RadioButton selectedButton)) {
                showAlert(Alert.AlertType.WARNING, "Incomplete Quiz", "Please answer every quiz question before submitting.");
                return;
            }

            QuizChoice selectedChoice = (QuizChoice) selectedButton.getUserData();
            if (selectedChoice != null && selectedChoice.isCorrect()) {
                earnedPoints += question.getPoints();
            }
        }

        int percentage = totalPoints == 0 ? 0 : (int) Math.round((earnedPoints * 100.0) / totalPoints);
        boolean passed = percentage >= courseQuiz.getPassingScore();
        long timeSpentSeconds = calculateQuizTimeSpentSeconds();
        long timeRemainingSeconds = calculateQuizTimeRemainingSeconds(timeSpentSeconds);

        if (hasPersistentLearner()) {
            boolean saved = learningProgressService.recordQuizAttempt(
                    currentUserId,
                    course.getId(),
                    courseQuiz.getId(),
                    percentage,
                    earnedPoints,
                    totalPoints,
                    passed,
                    timeSpentSeconds,
                    timeRemainingSeconds
            );
            if (!saved) {
                LearningProgressService.QuizAttemptSnapshot existingAttempt =
                        learningProgressService.getQuizAttempt(currentUserId, courseQuiz.getId());
                if (existingAttempt != null) {
                    applyQuizAttempt(existingAttempt);
                    refreshSectionsList();
                    refreshCareerInsights();
                    showAlert(Alert.AlertType.INFORMATION, "Quiz Already Taken", "Your first quiz attempt is already saved for this course.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Quiz Save Error", "The quiz attempt could not be saved. Please try again.");
                }
                return;
            }
        }

        quizSubmitted = true;
        lastQuizScore = percentage;
        lastQuizTimeSpentSeconds = timeSpentSeconds;
        lastQuizTimeRemainingSeconds = timeRemainingSeconds;
        btnSubmitQuiz.setDisable(true);
        disableQuizInputs();

        updateQuizResultBadge(percentage, passed);

        lblQuizAttemptStatus.setText("Quiz submitted. This learner flow allows only one attempt.");
        refreshSectionsList();
        refreshCareerInsights();
    }

    @FXML
    private void generateCertificate() {
        if (course == null || !quizSubmitted) {
            showAlert(Alert.AlertType.INFORMATION, "Certificate Locked", "Submit the quiz before downloading the certificate PDF.");
            return;
        }

        String learnerName = txtLearnerName == null || txtLearnerName.getText() == null || txtLearnerName.getText().isBlank()
                ? "Pegasus Learner"
                : txtLearnerName.getText().trim();

        btnGenerateCertificate.setDisable(true);
        lblCertificateStatus.setText("Generating certificate PDF...");

        CompletableFuture
                .supplyAsync(() -> generateCertificateFile(learnerName))
                .thenAccept(path -> Platform.runLater(() -> {
                    if (path == null) {
                        return;
                    }
                    btnGenerateCertificate.setDisable(false);
                    lblCertificateStatus.setText("PDF downloaded: " + path.toAbsolutePath());
                }));
    }

    private Path generateCertificateFile(String learnerName) {
        try {
            return certificatePdfService.generateCertificate(
                    course,
                    learnerName,
                    lastQuizScore,
                    courseQuiz == null ? 0 : courseQuiz.getPassingScore(),
                    lastQuizTimeSpentSeconds,
                    lastQuizTimeRemainingSeconds,
                    learningInsightsService.inferSkills(course, lastQuizScore),
                    learningInsightsService.recommendCourses(course, courseService.getPublishedCourses(), lastQuizScore)
            );
        } catch (Exception exception) {
            Platform.runLater(() -> {
                btnGenerateCertificate.setDisable(false);
                lblCertificateStatus.setText("Could not generate PDF: " + exception.getMessage());
            });
        }
        return null;
    }

    private void refreshCareerInsights() {
        if (careerInsightsCard == null || course == null) {
            return;
        }

        boolean completed = isCourseCompleted();
        boolean reportUnlocked = quizSubmitted;
        List<String> skills = learningInsightsService.inferSkills(course, lastQuizScore);
        List<String> recommendations = learningInsightsService.recommendCourses(course, courseService.getPublishedCourses(), lastQuizScore);

        careerInsightsCard.setManaged(completed);
        careerInsightsCard.setVisible(completed);

        lblCareerSummary.setText(reportUnlocked
                ? "Quiz submitted. Your report, skills, recommendations, and PDF certificate are ready."
                : "Submit the quiz to unlock your report and certificate PDF.");

        lblQuizScoreDetail.setText(lastQuizScore >= 0 ? "Score: " + lastQuizScore + "%" : "Score: --");
        lblQuizTimeDetail.setText(lastQuizTimeSpentSeconds >= 0 ? "Time: " + formatDuration((int) lastQuizTimeSpentSeconds) : "Time: --");
        lblQuizRemainingDetail.setText(lastQuizTimeRemainingSeconds >= 0 ? "Remaining: " + formatDuration((int) lastQuizTimeRemainingSeconds) : "Remaining: --");

        skillProfileContainer.getChildren().clear();
        for (String skill : skills) {
            Label pill = new Label(skill);
            pill.getStyleClass().add("course-player-skill-pill");
            skillProfileContainer.getChildren().add(pill);
        }

        recommendationsContainer.getChildren().clear();
        for (String recommendation : recommendations) {
            Label item = new Label(recommendation);
            item.setWrapText(true);
            item.getStyleClass().add("course-player-recommendation-item");
            recommendationsContainer.getChildren().add(item);
        }

        btnGenerateCertificate.setDisable(!reportUnlocked);
        if (lblCertificateStatus.getText() == null || !lblCertificateStatus.getText().startsWith("PDF downloaded:")) {
            lblCertificateStatus.setText(reportUnlocked
                    ? "Enter the learner name, then download a PDF certificate."
                    : "Submit the quiz to unlock the PDF certificate.");
        }
    }

    private void refreshQuizArea() {
        boolean courseCompleted = isCourseCompleted();
        boolean hasQuiz = courseQuiz != null;

        updateContentMode();

        if (!hasQuiz) {
            return;
        }

        lblQuizTitle.setText(courseQuiz.getTitle());
        lblQuizMeta.setText("Passing score: " + courseQuiz.getPassingScore() + "% | One learner attempt");

        if (!courseCompleted) {
            lblQuizAttemptStatus.setText("Finish every course section to unlock this quiz.");
            btnSubmitQuiz.setDisable(true);
            quizQuestionsContainer.getChildren().clear();
            lblQuizResult.setText("");
            return;
        }

        lblQuizAttemptStatus.setText(quizSubmitted
                ? "Quiz submitted. This learner flow allows only one attempt."
                : "The quiz is now unlocked. Answer every question carefully because you can only submit once.");
        btnSubmitQuiz.setDisable(quizSubmitted);

        if (quizQuestions.isEmpty()) {
            quizQuestionsContainer.getChildren().setAll(new Label("This quiz has no questions yet."));
            return;
        }

        if (quizQuestionsContainer.getChildren().isEmpty()) {
            renderQuizQuestions();
        }
    }

    private void renderQuizQuestions() {
        quizQuestionsContainer.getChildren().clear();
        answerGroupsByQuestionId.clear();

        for (QuizQuestion question : quizQuestions) {
            Label titleLabel = new Label("Question " + question.getOrderIndex());
            titleLabel.getStyleClass().add("course-player-panel-title");

            Label questionLabel = new Label(question.getQuestionText());
            questionLabel.getStyleClass().add("course-player-summary-copy");
            questionLabel.setWrapText(true);

            Label metaLabel = new Label(question.getPoints() + " point(s)");
            metaLabel.getStyleClass().add("course-player-progress-label");

            ToggleGroup group = new ToggleGroup();
            answerGroupsByQuestionId.put(question.getId(), group);

            VBox choicesBox = new VBox(10);
            List<QuizChoice> choices = quizChoiceService.getByQuestionId(question.getId());
            for (QuizChoice choice : choices) {
                RadioButton radioButton = new RadioButton(choice.getLabel());
                radioButton.setToggleGroup(group);
                radioButton.setUserData(choice);
                radioButton.getStyleClass().add("course-player-quiz-choice");
                radioButton.setWrapText(true);
                radioButton.setDisable(quizSubmitted);
                choicesBox.getChildren().add(radioButton);
            }

            VBox card = new VBox(12, titleLabel, questionLabel, metaLabel, choicesBox);
            card.getStyleClass().add("course-player-quiz-card");
            quizQuestionsContainer.getChildren().add(card);
        }
    }

    private void disableQuizInputs() {
        for (ToggleGroup group : answerGroupsByQuestionId.values()) {
            group.getToggles().forEach(toggle -> {
                if (toggle instanceof RadioButton radioButton) {
                    radioButton.setDisable(true);
                }
            });
        }
    }
}
