package com.pegasus.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.pegasus.entities.Course;
import com.pegasus.entities.Quiz;
import com.pegasus.services.CourseService;
import com.pegasus.services.QuizService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.io.IOException;

public class CoursesDashboardController {
    private static final int DEFAULT_ARTIST_ID = 2;
    private static final double COURSE_ROW_HEIGHT = 72;
    private static final double QUIZ_ROW_HEIGHT = 64;
    private static final double COURSE_THUMBNAIL_WIDTH = 72;
    private static final double COURSE_THUMBNAIL_HEIGHT = 48;

    @FXML
    private Button btnCoursesTab;

    @FXML
    private Button btnQuizzesTab;

    @FXML
    private VBox coursesPane;

    @FXML
    private VBox quizzesPane;

    @FXML
    private TextField txtCourseSearch;

    @FXML
    private TextField txtQuizSearch;

    @FXML
    private TableView<Course> tvCourses;

    @FXML
    private TableColumn<Course, String> colThumbnail;

    @FXML
    private TableColumn<Course, String> colCourse;

    @FXML
    private TableColumn<Course, String> colStatus;

    @FXML
    private TableColumn<Course, LocalDateTime> colCreated;

    @FXML
    private TableColumn<Course, Void> colActions;

    @FXML
    private TableView<Quiz> tvQuizzes;

    @FXML
    private TableColumn<Quiz, String> colQuizTitle;

    @FXML
    private TableColumn<Quiz, Integer> colQuizPassing;

    @FXML
    private TableColumn<Quiz, Integer> colQuizAttempts;

    @FXML
    private TableColumn<Quiz, Integer> colQuizCourseId;

    @FXML
    private TableColumn<Quiz, Void> colQuizActions;

    private final CourseService courseService = new CourseService();
    private final QuizService quizService = new QuizService();
    private final ObservableList<Course> courseObservableList = FXCollections.observableArrayList();
    private final ObservableList<Quiz> quizObservableList = FXCollections.observableArrayList();
    private final FilteredList<Course> filteredCourses = new FilteredList<>(courseObservableList, course -> true);
    private final FilteredList<Quiz> filteredQuizzes = new FilteredList<>(quizObservableList, quiz -> true);
    private final Map<Integer, String> courseTitleById = new HashMap<>();
    private static final DateTimeFormatter CREATED_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @FXML
    public void initialize() {
        setupCourseTable();
        setupQuizTable();
        setupSearch();
        loadCourses();
        loadQuizzes();
        showCoursesView();
    }

    @FXML
    public void showCoursesView() {
        coursesPane.setVisible(true);
        coursesPane.setManaged(true);
        quizzesPane.setVisible(false);
        quizzesPane.setManaged(false);

        btnCoursesTab.getStyleClass().setAll("dashboard-nav-item", "dashboard-nav-item-active");
        btnQuizzesTab.getStyleClass().setAll("dashboard-nav-item");
    }

    @FXML
    public void showQuizzesView() {
        quizzesPane.setVisible(true);
        quizzesPane.setManaged(true);
        coursesPane.setVisible(false);
        coursesPane.setManaged(false);

        btnQuizzesTab.getStyleClass().setAll("dashboard-nav-item", "dashboard-nav-item-active");
        btnCoursesTab.getStyleClass().setAll("dashboard-nav-item");
    }

    @FXML
    public void openCreateCourseDialog() {
        showCourseDialog(null);
    }

    @FXML
    public void openCreateQuizDialog() {
        showQuizDialog(null);
    }

    private void setupCourseTable() {
        tvCourses.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tvCourses.setFixedCellSize(COURSE_ROW_HEIGHT);
        tvCourses.setPlaceholder(new Label("No courses found."));
        tvCourses.setRowFactory(table -> {
            javafx.scene.control.TableRow<Course> row = new javafx.scene.control.TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    openCourseSectionsWindow(row.getItem());
                }
            });
            return row;
        });

        colThumbnail.setCellValueFactory(new PropertyValueFactory<>("thumbnailUrl"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("title"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colCreated.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        colThumbnail.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();
            private final Label fallbackLabel = new Label("No Image");

            {
                imageView.setFitWidth(COURSE_THUMBNAIL_WIDTH);
                imageView.setFitHeight(COURSE_THUMBNAIL_HEIGHT);
                imageView.setPreserveRatio(false);
                imageView.getStyleClass().add("course-thumb-view");
                fallbackLabel.getStyleClass().add("thumbnail-fallback");
                setAlignment(Pos.CENTER_LEFT);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    return;
                }

                if (item == null || item.isBlank()) {
                    setGraphic(fallbackLabel);
                    return;
                }

                try {
                    Image image = new Image(item, COURSE_THUMBNAIL_WIDTH, COURSE_THUMBNAIL_HEIGHT, false, true, true);
                    imageView.setImage(image);
                    setGraphic(imageView);
                } catch (Exception e) {
                    setGraphic(fallbackLabel);
                }
            }
        });
        colCourse.setCellFactory(column -> new TableCell<>() {
            private final Label titleLabel = new Label();
            private final Label descriptionLabel = new Label();
            private final VBox content = new VBox(6);

            {
                titleLabel.getStyleClass().add("dashboard-course-title");
                descriptionLabel.getStyleClass().add("dashboard-course-description");
                descriptionLabel.setWrapText(true);
                content.getChildren().addAll(titleLabel, descriptionLabel);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }

                Course course = getTableView().getItems().get(getIndex());
                titleLabel.setText(course.getTitle());
                descriptionLabel.setText(course.getDescription());
                setGraphic(content);
            }
        });

        colStatus.setCellFactory(column -> createStatusCell());
        colCreated.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.format(CREATED_FORMATTER));
            }
        });

        colActions.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox actionsBox = new HBox(10, editButton, deleteButton);

            {
                editButton.getStyleClass().add("warning-button");
                deleteButton.getStyleClass().add("delete-red-button");
                actionsBox.setAlignment(Pos.CENTER_LEFT);

                editButton.setOnAction(event -> showCourseDialog(getTableView().getItems().get(getIndex())));
                deleteButton.setOnAction(event -> deleteCourse(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : actionsBox);
            }
        });

        SortedList<Course> sortedCourses = new SortedList<>(filteredCourses);
        sortedCourses.comparatorProperty().bind(tvCourses.comparatorProperty());
        tvCourses.setItems(sortedCourses);
    }

    private void openCourseSectionsWindow(Course course) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CourseSectionsContent.fxml"));
            Parent root = loader.load();
            CourseSectionsController controller = loader.getController();
            controller.setCourse(course);

            Scene scene = new Scene(root, 1180, 760);
            scene.getStylesheets().add(getClass().getResource("/css/courses.css").toExternalForm());

            Stage stage = new Stage();
            stage.initOwner(tvCourses.getScene().getWindow());
            stage.initModality(Modality.NONE);
            stage.setTitle(course.getTitle() + " - Sections");
            stage.setScene(scene);
            stage.setMinWidth(980);
            stage.setMinHeight(680);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Open Sections Error", "Could not open the course sections view.");
        }
    }

    private void setupQuizTable() {
        tvQuizzes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tvQuizzes.setFixedCellSize(QUIZ_ROW_HEIGHT);
        tvQuizzes.setPlaceholder(new Label("No quizzes found."));
        tvQuizzes.setRowFactory(table -> {
            javafx.scene.control.TableRow<Quiz> row = new javafx.scene.control.TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    openQuizQuestionsWindow(row.getItem());
                }
            });
            return row;
        });

        colQuizTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colQuizPassing.setCellValueFactory(new PropertyValueFactory<>("passingScore"));
        colQuizAttempts.setCellValueFactory(new PropertyValueFactory<>("attemptLimit"));
        colQuizCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));

        colQuizTitle.setCellFactory(column -> new TableCell<>() {
            private final Label titleLabel = new Label();
            private final Label detailsLabel = new Label();
            private final VBox content = new VBox(6);

            {
                titleLabel.getStyleClass().add("dashboard-course-title");
                detailsLabel.getStyleClass().add("dashboard-course-description");
                detailsLabel.setWrapText(true);
                content.getChildren().addAll(titleLabel, detailsLabel);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }

                Quiz quiz = getTableView().getItems().get(getIndex());
                titleLabel.setText(quiz.getTitle());
                String timeLimitText = quiz.getTimeLimitMin() == null ? "No time limit" : quiz.getTimeLimitMin() + " min";
                String attemptText = quiz.getAttemptLimit() == null ? "Unlimited attempts" : quiz.getAttemptLimit() + " attempt(s)";
                detailsLabel.setText(timeLimitText + " | " + attemptText);
                setGraphic(content);
            }
        });

        colQuizPassing.setCellFactory(column -> new TableCell<>() {
            private final Label badge = new Label();

            {
                badge.getStyleClass().add("green-badge");
                setAlignment(Pos.CENTER_LEFT);
            }

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    return;
                }

                badge.setText(item + "%");
                setGraphic(badge);
            }
        });

        colQuizAttempts.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item == null ? "Unlimited" : String.valueOf(item));
            }
        });

        colQuizCourseId.setCellFactory(column -> new TableCell<>() {
            private final Label courseNameLabel = new Label();

            {
                courseNameLabel.getStyleClass().add("dashboard-course-title");
            }

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    return;
                }

                courseNameLabel.setText(courseTitleById.getOrDefault(item, "Course #" + item));
                setGraphic(courseNameLabel);
            }
        });

        colQuizActions.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox actionsBox = new HBox(10, editButton, deleteButton);

            {
                editButton.getStyleClass().add("warning-button");
                deleteButton.getStyleClass().add("delete-red-button");
                actionsBox.setAlignment(Pos.CENTER_LEFT);

                editButton.setOnAction(event -> showQuizDialog(getTableView().getItems().get(getIndex())));
                deleteButton.setOnAction(event -> deleteQuiz(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : actionsBox);
            }
        });

        SortedList<Quiz> sortedQuizzes = new SortedList<>(filteredQuizzes);
        sortedQuizzes.comparatorProperty().bind(tvQuizzes.comparatorProperty());
        tvQuizzes.setItems(sortedQuizzes);
    }

    private void setupSearch() {
        if (txtCourseSearch == null || txtQuizSearch == null) {
            return;
        }

        txtCourseSearch.textProperty().addListener((obs, oldValue, newValue) ->
                filteredCourses.setPredicate(course -> matchesCourseSearch(course, newValue)));

        txtQuizSearch.textProperty().addListener((obs, oldValue, newValue) ->
                filteredQuizzes.setPredicate(quiz -> matchesQuizSearch(quiz, newValue)));
    }

    private void openQuizQuestionsWindow(Quiz quiz) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/QuizQuestionsContent.fxml"));
            Parent root = loader.load();
            QuizQuestionsController controller = loader.getController();
            controller.setQuiz(quiz);

            Scene scene = new Scene(root, 1180, 760);
            scene.getStylesheets().add(getClass().getResource("/css/courses.css").toExternalForm());

            Stage stage = new Stage();
            stage.initOwner(tvQuizzes.getScene().getWindow());
            stage.initModality(Modality.NONE);
            stage.setTitle(quiz.getTitle() + " - Questions");
            stage.setScene(scene);
            stage.setMinWidth(980);
            stage.setMinHeight(680);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Open Quiz Builder Error", "Could not open the quiz questions view.");
        }
    }

    private TableCell<Course, String> createStatusCell() {
        return new TableCell<>() {
            private final Label statusLabel = new Label();

            {
                statusLabel.getStyleClass().add("green-badge");
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    return;
                }

                statusLabel.getStyleClass().setAll("green-badge");
                if ("DRAFT".equalsIgnoreCase(item)) {
                    statusLabel.getStyleClass().setAll("neutral-badge");
                } else if ("HIDDEN".equalsIgnoreCase(item)) {
                    statusLabel.getStyleClass().setAll("dark-badge");
                }

                statusLabel.setText(item);
                setGraphic(statusLabel);
            }
        };
    }

    private void loadCourses() {
        try {
            List<Course> courses = courseService.getAll();
            courseObservableList.setAll(courses);
            courseTitleById.clear();
            for (Course course : courses) {
                courseTitleById.put(course.getId(), course.getTitle());
            }
            refreshSearchFilters();
            tvQuizzes.refresh();
        } catch (Exception e) {
            courseObservableList.clear();
            courseTitleById.clear();
            System.out.println("Dashboard load courses error: " + e.getMessage());
        }
    }

    private void loadQuizzes() {
        try {
            List<Quiz> quizzes = quizService.getAll();
            quizObservableList.setAll(quizzes);
            refreshSearchFilters();
        } catch (Exception e) {
            quizObservableList.clear();
            System.out.println("Dashboard load quizzes error: " + e.getMessage());
        }
    }

    private boolean matchesCourseSearch(Course course, String query) {
        if (course == null) {
            return false;
        }

        String normalizedQuery = normalizeSearch(query);
        if (normalizedQuery.isEmpty()) {
            return true;
        }

        return containsIgnoreCase(course.getTitle(), normalizedQuery)
                || containsIgnoreCase(course.getDescription(), normalizedQuery)
                || containsIgnoreCase(course.getStatus(), normalizedQuery)
                || containsIgnoreCase(course.getCreatedAt() == null ? null : course.getCreatedAt().format(CREATED_FORMATTER), normalizedQuery);
    }

    private boolean matchesQuizSearch(Quiz quiz, String query) {
        if (quiz == null) {
            return false;
        }

        String normalizedQuery = normalizeSearch(query);
        if (normalizedQuery.isEmpty()) {
            return true;
        }

        String courseName = courseTitleById.getOrDefault(quiz.getCourseId(), "Course #" + quiz.getCourseId());
        return containsIgnoreCase(quiz.getTitle(), normalizedQuery)
                || containsIgnoreCase(courseName, normalizedQuery)
                || containsIgnoreCase(quiz.getPassingScore() + "%", normalizedQuery)
                || containsIgnoreCase(quiz.getAttemptLimit() == null ? "unlimited" : String.valueOf(quiz.getAttemptLimit()), normalizedQuery)
                || containsIgnoreCase(quiz.getTimeLimitMin() == null ? "no time limit" : quiz.getTimeLimitMin() + " min", normalizedQuery);
    }

    private boolean containsIgnoreCase(String value, String normalizedQuery) {
        return value != null && value.toLowerCase().contains(normalizedQuery);
    }

    private String normalizeSearch(String value) {
        return value == null ? "" : value.trim().toLowerCase();
    }

    private void refreshSearchFilters() {
        String courseQuery = txtCourseSearch == null ? "" : txtCourseSearch.getText();
        String quizQuery = txtQuizSearch == null ? "" : txtQuizSearch.getText();
        filteredCourses.setPredicate(course -> matchesCourseSearch(course, courseQuery));
        filteredQuizzes.setPredicate(quiz -> matchesQuizSearch(quiz, quizQuery));
    }

    private void deleteCourse(Course course) {
        if (confirmDelete("Delete Course", "Do you want to delete this course?")) {
            courseService.delete(course.getId());
            loadCourses();
            loadQuizzes();
        }
    }

    private void deleteQuiz(Quiz quiz) {
        if (confirmDelete("Delete Quiz", "Do you want to delete this quiz?")) {
            quizService.delete(quiz.getId());
            loadQuizzes();
        }
    }

    private boolean confirmDelete(String title, String message) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle(title);
        confirm.setHeaderText(null);
        confirm.setContentText(message);
        return confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    private void showCourseDialog(Course courseToEdit) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(courseToEdit == null ? "Add Course" : "Edit Course");
        dialog.setHeaderText(null);

        ButtonType saveButtonType = new ButtonType(courseToEdit == null ? "Save" : "Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        TextField titleField = new TextField();
        TextArea descriptionArea = new TextArea();
        TextField thumbnailField = new TextField();
        ComboBox<String> statusBox = new ComboBox<>(FXCollections.observableArrayList("PUBLISHED", "DRAFT", "HIDDEN"));

        titleField.setPromptText("Course title");
        descriptionArea.setPromptText("Course description");
        descriptionArea.setWrapText(true);
        descriptionArea.setPrefRowCount(4);
        thumbnailField.setPromptText("Thumbnail URL");
        statusBox.setPromptText("Select status");
        statusBox.setMaxWidth(Double.MAX_VALUE);

        if (courseToEdit != null) {
            titleField.setText(courseToEdit.getTitle());
            descriptionArea.setText(courseToEdit.getDescription());
            thumbnailField.setText(courseToEdit.getThumbnailUrl() == null ? "" : courseToEdit.getThumbnailUrl());
            statusBox.setValue(courseToEdit.getStatus());
        }

        VBox content = buildDialogContent(
                courseToEdit == null ? "Add New Course" : "Edit Course",
                courseToEdit == null ? "Create a polished new course entry." : "Update the selected course details.",
                createFieldBlock("Course Title", titleField),
                createFieldBlock("Description", descriptionArea),
                createFieldBlock("Thumbnail URL", thumbnailField),
                createFieldBlock("Status", statusBox)
        );

        configureDialogPane(dialog.getDialogPane(), saveButtonType);
        dialog.getDialogPane().setContent(content);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == saveButtonType) {
            saveCourseFromDialog(courseToEdit, titleField.getText().trim(), descriptionArea.getText().trim(),
                    thumbnailField.getText().trim(), statusBox.getValue());
        }
    }

    private void showQuizDialog(Quiz quizToEdit) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(quizToEdit == null ? "Add Quiz" : "Edit Quiz");
        dialog.setHeaderText(null);

        ButtonType saveButtonType = new ButtonType(quizToEdit == null ? "Save" : "Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        TextField titleField = new TextField();
        Spinner<Integer> timeLimitSpinner = new Spinner<>();
        Slider passingScoreSlider = new Slider(0, 100, 70);
        Label passingScoreValueLabel = new Label("70%");
        Spinner<Integer> attemptLimitSpinner = new Spinner<>();
        CheckBox unlimitedAttemptsCheckBox = new CheckBox("Unlimited attempts");
        ComboBox<Course> courseBox = new ComboBox<>(courseObservableList);

        titleField.setPromptText("Quiz title");
        courseBox.setPromptText("Select course");
        courseBox.setMaxWidth(Double.MAX_VALUE);

        timeLimitSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 300, 30, 5));
        timeLimitSpinner.setEditable(true);
        timeLimitSpinner.setMaxWidth(Double.MAX_VALUE);

        passingScoreSlider.setShowTickLabels(true);
        passingScoreSlider.setShowTickMarks(true);
        passingScoreSlider.setMajorTickUnit(10);
        passingScoreSlider.setMinorTickCount(4);
        passingScoreSlider.setBlockIncrement(1);
        passingScoreSlider.valueProperty().addListener((obs, oldValue, newValue) ->
                passingScoreValueLabel.setText(String.format("%d%%", Math.round(newValue.doubleValue())))
        );

        attemptLimitSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1, 1));
        attemptLimitSpinner.setEditable(true);
        attemptLimitSpinner.setMaxWidth(Double.MAX_VALUE);
        unlimitedAttemptsCheckBox.setSelected(false);
        attemptLimitSpinner.disableProperty().bind(unlimitedAttemptsCheckBox.selectedProperty());

        courseBox.setCellFactory(listView -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Course item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getTitle());
            }
        });
        courseBox.setButtonCell(new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Course item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getTitle());
            }
        });

        if (quizToEdit != null) {
            titleField.setText(quizToEdit.getTitle());
            timeLimitSpinner.getValueFactory().setValue(quizToEdit.getTimeLimitMin() == null ? 30 : quizToEdit.getTimeLimitMin());
            passingScoreSlider.setValue(quizToEdit.getPassingScore());
            passingScoreValueLabel.setText(quizToEdit.getPassingScore() + "%");
            if (quizToEdit.getAttemptLimit() == null) {
                unlimitedAttemptsCheckBox.setSelected(true);
            } else {
                attemptLimitSpinner.getValueFactory().setValue(quizToEdit.getAttemptLimit());
            }
            courseObservableList.stream()
                    .filter(course -> course.getId() == quizToEdit.getCourseId())
                    .findFirst()
                    .ifPresent(courseBox::setValue);
        }

        VBox content = buildDialogContent(
                quizToEdit == null ? "Add New Quiz" : "Edit Quiz",
                quizToEdit == null ? "Create a quiz that fits the dashboard style." : "Update the selected quiz details.",
                createFieldBlock("Quiz Title", titleField),
                createFieldBlock("Time Limit", buildSpinnerBlock(timeLimitSpinner, "minutes")),
                createFieldBlock("Passing Score", buildSliderBlock(passingScoreSlider, passingScoreValueLabel)),
                createFieldBlock("Attempt Limit", buildAttemptLimitBlock(attemptLimitSpinner, unlimitedAttemptsCheckBox)),
                createFieldBlock("Course Name", courseBox)
        );

        configureDialogPane(dialog.getDialogPane(), saveButtonType);
        dialog.getDialogPane().setContent(content);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == saveButtonType) {
            saveQuizFromDialog(
                    quizToEdit,
                    titleField.getText().trim(),
                    timeLimitSpinner.getValue(),
                    (int) Math.round(passingScoreSlider.getValue()),
                    unlimitedAttemptsCheckBox.isSelected() ? null : attemptLimitSpinner.getValue(),
                    courseBox.getValue()
            );
        }
    }

    private VBox buildDialogContent(String title, String subtitle, VBox... fieldBlocks) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("dialog-form-title");

        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.getStyleClass().add("dialog-form-subtitle");
        subtitleLabel.setWrapText(true);

        VBox fieldsBox = new VBox(14);
        fieldsBox.getChildren().addAll(fieldBlocks);

        VBox content = new VBox(18);
        content.getStyleClass().add("dialog-form-root");
        content.getChildren().addAll(titleLabel, subtitleLabel, fieldsBox);
        return content;
    }

    private VBox createFieldBlock(String labelText, javafx.scene.Node field) {
        Label label = new Label(labelText);
        label.getStyleClass().add("dialog-field-label");

        if (field instanceof Region region) {
            region.setMaxWidth(Double.MAX_VALUE);
        }

        VBox box = new VBox(8);
        box.getChildren().addAll(label, field);
        return box;
    }

    private VBox buildSpinnerBlock(Spinner<Integer> spinner, String suffix) {
        Label hintLabel = new Label("Choose a value in " + suffix + ".");
        hintLabel.getStyleClass().add("dialog-field-hint");

        VBox box = new VBox(8);
        box.getChildren().addAll(spinner, hintLabel);
        return box;
    }

    private VBox buildSliderBlock(Slider slider, Label valueLabel) {
        valueLabel.getStyleClass().add("dialog-slider-value");

        HBox header = new HBox();
        Label hintLabel = new Label("Score needed to pass the quiz.");
        hintLabel.getStyleClass().add("dialog-field-hint");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(hintLabel, spacer, valueLabel);

        VBox box = new VBox(8);
        box.getChildren().addAll(header, slider);
        return box;
    }

    private VBox buildAttemptLimitBlock(Spinner<Integer> spinner, CheckBox unlimitedCheckBox) {
        Label hintLabel = new Label("Set a maximum number of tries or allow unlimited access.");
        hintLabel.getStyleClass().add("dialog-field-hint");

        VBox box = new VBox(8);
        box.getChildren().addAll(spinner, unlimitedCheckBox, hintLabel);
        return box;
    }

    private void configureDialogPane(DialogPane dialogPane, ButtonType saveButtonType) {
        dialogPane.getStylesheets().add(getClass().getResource("/css/courses.css").toExternalForm());
        dialogPane.getStyleClass().add("app-dialog-pane");
        dialogPane.setPrefWidth(520);
        dialogPane.setMinHeight(Region.USE_PREF_SIZE);

        Button saveButton = (Button) dialogPane.lookupButton(saveButtonType);
        Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
        saveButton.getStyleClass().add("dialog-primary-button");
        cancelButton.getStyleClass().add("dialog-secondary-button");
    }

    private void saveCourseFromDialog(Course courseToEdit, String title, String description, String thumbnail,
                                      String status) {
        String error = validateCourseInputs(title, description, status);
        if (error != null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", error);
            return;
        }

        try {
            if (courseToEdit == null) {
                courseService.add(new Course(title, description, thumbnail.isEmpty() ? null : thumbnail, status, LocalDateTime.now(), DEFAULT_ARTIST_ID));
            } else {
                courseToEdit.setTitle(title);
                courseToEdit.setDescription(description);
                courseToEdit.setThumbnailUrl(thumbnail.isEmpty() ? null : thumbnail);
                courseToEdit.setStatus(status);
                courseToEdit.setArtistId(DEFAULT_ARTIST_ID);
                courseService.update(courseToEdit);
            }

            loadCourses();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void saveQuizFromDialog(Quiz quizToEdit, String title, Integer timeLimit, Integer passingScore,
                                    Integer attemptLimit, Course selectedCourse) {
        String error = validateQuizInputs(title, passingScore, selectedCourse, timeLimit, attemptLimit);
        if (error != null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", error);
            return;
        }

        try {
            int courseId = selectedCourse.getId();

            if (quizToEdit == null) {
                quizService.add(new Quiz(title, timeLimit, passingScore, attemptLimit, courseId));
            } else {
                quizToEdit.setTitle(title);
                quizToEdit.setTimeLimitMin(timeLimit);
                quizToEdit.setPassingScore(passingScore);
                quizToEdit.setAttemptLimit(attemptLimit);
                quizToEdit.setCourseId(courseId);
                quizService.update(quizToEdit);
            }

            loadQuizzes();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private String validateCourseInputs(String title, String description, String status) {
        if (title.isEmpty()) {
            return "Title is required.";
        }
        if (title.length() < 3) {
            return "Title must contain at least 3 characters.";
        }
        if (description.isEmpty()) {
            return "Description is required.";
        }
        if (description.length() < 10) {
            return "Description must contain at least 10 characters.";
        }
        if (status == null || status.isEmpty()) {
            return "Please choose a status.";
        }
        return null;
    }

    private String validateQuizInputs(String title, Integer passingScore, Course selectedCourse, Integer timeLimit, Integer attemptLimit) {
        if (title.isEmpty()) {
            return "Quiz title is required.";
        }
        if (title.length() < 3) {
            return "Quiz title must contain at least 3 characters.";
        }
        if (selectedCourse == null) {
            return "Please choose a course.";
        }
        if (timeLimit == null || timeLimit < 1 || timeLimit > 300) {
            return "Time limit must be between 1 and 300 minutes.";
        }
        if (passingScore == null || passingScore < 0 || passingScore > 100) {
            return "Passing score must be between 0 and 100.";
        }
        if (attemptLimit != null && (attemptLimit < 1 || attemptLimit > 10)) {
            return "Attempt limit must be between 1 and 10.";
        }
        return null;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
