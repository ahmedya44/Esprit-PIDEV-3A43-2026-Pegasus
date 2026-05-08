package com.pegasus.controllers.back;

import com.pegasus.entities.Course;
import com.pegasus.entities.Quiz;
import com.pegasus.entities.User;
import com.pegasus.services.CourseService;
import com.pegasus.services.QuizService;
import com.pegasus.services.ServiceUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AdminQuizzesController {
    @FXML private Label quizCountLabel;
    @FXML private Label linkedCourseCountLabel;
    @FXML private Button allQuizzesModeButton;
    @FXML private TableView<QuizRow> quizzesTable;
    @FXML private TableColumn<QuizRow, String> titleColumn;
    @FXML private TableColumn<QuizRow, String> courseColumn;
    @FXML private TableColumn<QuizRow, String> artistColumn;
    @FXML private TableColumn<QuizRow, Integer> passingColumn;
    @FXML private TableColumn<QuizRow, Void> actionsColumn;

    private final QuizService quizService = new QuizService();
    private final CourseService courseService = new CourseService();
    private final ObservableList<QuizRow> rows = FXCollections.observableArrayList();
    private final ObservableList<Course> courses = FXCollections.observableArrayList();
    private final Map<Integer, Course> coursesById = new HashMap<>();
    private final Map<Integer, String> artistNames = new HashMap<>();
    private ServiceUser userService;

    @FXML
    public void initialize() {
        allQuizzesModeButton.getStyleClass().setAll("admin-mode-button", "admin-mode-button-active");
        setupTable();
        refreshQuizzes();
    }

    @FXML
    public void showAllQuizzes() {
        refreshQuizzes();
    }

    @FXML
    public void refreshQuizzes() {
        List<Course> courseList = courseService.getAll();
        courses.setAll(courseList);
        coursesById.clear();
        for (Course course : courseList) {
            coursesById.put(course.getId(), course);
        }

        List<Quiz> quizList = quizService.getAll();
        List<QuizRow> mappedRows = quizList.stream()
                .sorted(Comparator.comparing(Quiz::getId).reversed())
                .map(this::toRow)
                .toList();

        rows.setAll(mappedRows);
        quizCountLabel.setText(String.valueOf(quizList.size()));
        linkedCourseCountLabel.setText(String.valueOf(
                quizList.stream().map(Quiz::getCourseId).distinct().count()
        ));
        quizzesTable.setPlaceholder(new Label("No quizzes found."));
        quizzesTable.refresh();
    }

    private void setupTable() {
        quizzesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        quizzesTable.setItems(rows);

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artistName"));
        passingColumn.setCellValueFactory(new PropertyValueFactory<>("passingScore"));
        passingColumn.setCellFactory(column -> new TableCell<>() {
            private final Label badge = new Label();

            @Override
            protected void updateItem(Integer value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null) {
                    setGraphic(null);
                    return;
                }

                badge.setText(value + "%");
                badge.getStyleClass().setAll("admin-status-badge", "admin-status-published");
                setGraphic(badge);
            }
        });
        actionsColumn.setCellFactory(column -> createActionsCell());
    }

    private TableCell<QuizRow, Void> createActionsCell() {
        return new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox actionsBox = new HBox(8, editButton, deleteButton);

            {
                editButton.getStyleClass().add("admin-table-edit-button");
                deleteButton.getStyleClass().add("admin-table-delete-button");
                actionsBox.getStyleClass().add("admin-table-action-group");

                editButton.setOnAction(event -> {
                    QuizRow row = currentRow();
                    if (row != null) {
                        showEditDialog(row.getQuiz());
                    }
                });
                deleteButton.setOnAction(event -> {
                    QuizRow row = currentRow();
                    if (row != null) {
                        deleteQuiz(row.getQuiz());
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : actionsBox);
            }

            private QuizRow currentRow() {
                int index = getIndex();
                if (index < 0 || index >= getTableView().getItems().size()) {
                    return null;
                }
                return getTableView().getItems().get(index);
            }
        };
    }

    private QuizRow toRow(Quiz quiz) {
        Course course = coursesById.get(quiz.getCourseId());
        String courseName = course == null ? "Course #" + quiz.getCourseId() : course.getTitle();
        String artistName = course == null ? "Unknown artist" : resolveArtistName(course.getArtistId());
        return new QuizRow(quiz, courseName, artistName);
    }

    private void showEditDialog(Quiz quiz) {
        if (quiz == null) {
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Quiz");
        dialog.setHeaderText(null);

        ButtonType saveButtonType = new ButtonType("Update Quiz", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        TextField titleField = new TextField(nullToEmpty(quiz.getTitle()));
        Spinner<Integer> timeLimitSpinner = new Spinner<>();
        CheckBox noTimeLimitCheckBox = new CheckBox("No time limit");
        Spinner<Integer> passingScoreSpinner = new Spinner<>();
        Spinner<Integer> attemptLimitSpinner = new Spinner<>();
        CheckBox unlimitedAttemptsCheckBox = new CheckBox("Unlimited attempts");
        ComboBox<Course> courseBox = new ComboBox<>(courses);

        timeLimitSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 300, quiz.getTimeLimitMin() == null ? 30 : quiz.getTimeLimitMin(), 5));
        timeLimitSpinner.setEditable(true);
        timeLimitSpinner.disableProperty().bind(noTimeLimitCheckBox.selectedProperty());
        noTimeLimitCheckBox.setSelected(quiz.getTimeLimitMin() == null);

        passingScoreSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, quiz.getPassingScore(), 5));
        passingScoreSpinner.setEditable(true);

        attemptLimitSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, quiz.getAttemptLimit() == null ? 1 : quiz.getAttemptLimit(), 1));
        attemptLimitSpinner.setEditable(true);
        attemptLimitSpinner.disableProperty().bind(unlimitedAttemptsCheckBox.selectedProperty());
        unlimitedAttemptsCheckBox.setSelected(quiz.getAttemptLimit() == null);

        configureCourseCombo(courseBox);
        courses.stream()
                .filter(course -> course.getId() == quiz.getCourseId())
                .findFirst()
                .ifPresent(courseBox::setValue);

        VBox content = buildDialogContent(
                "Edit Quiz",
                "Update quiz information. This module has no approval status.",
                createFieldBlock("Quiz Name", titleField),
                createFieldBlock("Course", courseBox),
                createFieldBlock("Time Limit", stackWithCheck(timeLimitSpinner, noTimeLimitCheckBox)),
                createFieldBlock("Passing Score", passingScoreSpinner),
                createFieldBlock("Attempt Limit", stackWithCheck(attemptLimitSpinner, unlimitedAttemptsCheckBox))
        );

        configureDialog(dialog.getDialogPane(), saveButtonType);
        dialog.getDialogPane().setContent(content);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == saveButtonType) {
            Course selectedCourse = courseBox.getValue();
            String title = titleField.getText().trim();
            if (title.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Quiz name is required.");
                return;
            }
            if (selectedCourse == null) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Please select a course.");
                return;
            }

            quiz.setTitle(title);
            quiz.setCourseId(selectedCourse.getId());
            quiz.setTimeLimitMin(noTimeLimitCheckBox.isSelected() ? null : timeLimitSpinner.getValue());
            quiz.setPassingScore(passingScoreSpinner.getValue());
            quiz.setAttemptLimit(unlimitedAttemptsCheckBox.isSelected() ? null : attemptLimitSpinner.getValue());
            quizService.update(quiz);
            refreshQuizzes();
        }
    }

    private void deleteQuiz(Quiz quiz) {
        if (quiz == null) {
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Quiz");
        confirm.setHeaderText(null);
        confirm.setContentText("Delete \"" + quiz.getTitle() + "\" and its questions?");
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            quizService.delete(quiz.getId());
            refreshQuizzes();
        }
    }

    private void configureCourseCombo(ComboBox<Course> courseBox) {
        courseBox.setPromptText("Select course");
        courseBox.setMaxWidth(Double.MAX_VALUE);
        courseBox.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Course item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getTitle());
            }
        });
        courseBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Course item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getTitle());
            }
        });
    }

    private VBox buildDialogContent(String title, String subtitle, VBox... fields) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("dialog-form-title");

        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.getStyleClass().add("dialog-form-subtitle");
        subtitleLabel.setWrapText(true);

        VBox fieldsBox = new VBox(12);
        fieldsBox.getChildren().addAll(fields);

        VBox content = new VBox(16, titleLabel, subtitleLabel, fieldsBox);
        content.getStyleClass().add("dialog-form-root");
        return content;
    }

    private VBox createFieldBlock(String labelText, javafx.scene.Node field) {
        Label label = new Label(labelText);
        label.getStyleClass().add("dialog-field-label");
        if (field instanceof Region region) {
            region.setMaxWidth(Double.MAX_VALUE);
        }
        return new VBox(8, label, field);
    }

    private VBox stackWithCheck(javafx.scene.Node field, CheckBox checkBox) {
        return new VBox(8, field, checkBox);
    }

    private void configureDialog(DialogPane pane, ButtonType saveButtonType) {
        pane.getStylesheets().add(getClass().getResource("/css/admin.css").toExternalForm());
        pane.getStyleClass().add("app-dialog-pane");
        pane.setPrefWidth(540);
        pane.setMinHeight(Region.USE_PREF_SIZE);

        Button saveButton = (Button) pane.lookupButton(saveButtonType);
        Button cancelButton = (Button) pane.lookupButton(ButtonType.CANCEL);
        saveButton.getStyleClass().add("dialog-primary-button");
        cancelButton.getStyleClass().add("dialog-secondary-button");
    }

    private String resolveArtistName(int artistId) {
        if (artistId <= 0) {
            return "Unknown artist";
        }
        if (artistNames.containsKey(artistId)) {
            return artistNames.get(artistId);
        }

        String name = "Artist #" + artistId;
        try {
            if (userService == null) {
                userService = new ServiceUser();
            }
            User user = userService.findById(artistId);
            if (user != null && user.getUsername() != null && !user.getUsername().isBlank()) {
                name = user.getUsername();
            }
        } catch (RuntimeException ignored) {
            name = "Artist #" + artistId;
        }
        artistNames.put(artistId, name);
        return name;
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static final class QuizRow {
        private final Quiz quiz;
        private final String courseName;
        private final String artistName;

        private QuizRow(Quiz quiz, String courseName, String artistName) {
            this.quiz = quiz;
            this.courseName = courseName;
            this.artistName = artistName;
        }

        public Quiz getQuiz() {
            return quiz;
        }

        public String getTitle() {
            return quiz.getTitle();
        }

        public String getCourseName() {
            return courseName;
        }

        public String getArtistName() {
            return artistName;
        }

        public Integer getPassingScore() {
            return quiz.getPassingScore();
        }
    }
}
