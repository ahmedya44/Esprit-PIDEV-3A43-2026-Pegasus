package com.pegasus.controllers.back;

import com.pegasus.entities.Course;
import com.pegasus.entities.User;
import com.pegasus.services.CourseService;
import com.pegasus.services.ServiceUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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

public class AdminCourseModerationController {
    private static final String MODE_ACTIVE_CLASS = "admin-mode-button-active";

    @FXML private Label draftCountLabel;
    @FXML private Label totalCountLabel;
    @FXML private Label viewTitleLabel;
    @FXML private Label viewSubtitleLabel;
    @FXML private Button approvalModeButton;
    @FXML private Button allCoursesModeButton;
    @FXML private TableView<CourseRow> coursesTable;
    @FXML private TableColumn<CourseRow, String> titleColumn;
    @FXML private TableColumn<CourseRow, String> artistColumn;
    @FXML private TableColumn<CourseRow, String> statusColumn;
    @FXML private TableColumn<CourseRow, Void> actionsColumn;

    private final CourseService courseService = new CourseService();
    private final ObservableList<CourseRow> rows = FXCollections.observableArrayList();
    private final Map<Integer, String> artistNames = new HashMap<>();
    private ServiceUser userService;
    private boolean approvalMode = true;

    @FXML
    public void initialize() {
        setupTable();
        setActiveMode(approvalModeButton);
        refreshCourses();
    }

    @FXML
    public void showApprovalQueue() {
        approvalMode = true;
        viewTitleLabel.setText("Approval Queue");
        viewSubtitleLabel.setText("Draft courses stay here until an admin publishes them.");
        setActiveMode(approvalModeButton);
        refreshCourses();
    }

    @FXML
    public void showAllCourses() {
        approvalMode = false;
        viewTitleLabel.setText("All Courses");
        viewSubtitleLabel.setText("Published, draft and hidden courses with edit and delete controls.");
        setActiveMode(allCoursesModeButton);
        refreshCourses();
    }

    @FXML
    public void refreshCourses() {
        List<Course> courses = courseService.getAll();
        int draftCount = (int) courses.stream()
                .filter(course -> "DRAFT".equalsIgnoreCase(course.getStatus()))
                .count();

        draftCountLabel.setText(String.valueOf(draftCount));
        totalCountLabel.setText(String.valueOf(courses.size()));

        List<CourseRow> mappedRows = courses.stream()
                .sorted(Comparator.comparing(Course::getId).reversed())
                .filter(course -> !approvalMode || "DRAFT".equalsIgnoreCase(course.getStatus()))
                .map(course -> new CourseRow(course, resolveArtistName(course.getArtistId())))
                .toList();
        rows.setAll(mappedRows);
        coursesTable.setPlaceholder(new Label(approvalMode ? "No draft courses waiting for approval." : "No courses found."));
        coursesTable.refresh();
    }

    private void setupTable() {
        coursesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        coursesTable.setItems(rows);

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artistName"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(column -> createStatusCell());
        actionsColumn.setCellFactory(column -> createActionsCell());
    }

    private TableCell<CourseRow, String> createStatusCell() {
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

    private TableCell<CourseRow, Void> createActionsCell() {
        return new TableCell<>() {
            private final Button approveButton = new Button("Approve");
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox actionsBox = new HBox(8);

            {
                approveButton.getStyleClass().add("admin-table-approve-button");
                editButton.getStyleClass().add("admin-table-edit-button");
                deleteButton.getStyleClass().add("admin-table-delete-button");

                approveButton.setOnAction(event -> {
                    CourseRow row = currentRow();
                    if (row != null) {
                        approveCourse(row.getCourse());
                    }
                });
                editButton.setOnAction(event -> {
                    CourseRow row = currentRow();
                    if (row != null) {
                        showEditDialog(row.getCourse());
                    }
                });
                deleteButton.setOnAction(event -> {
                    CourseRow row = currentRow();
                    if (row != null) {
                        deleteCourse(row.getCourse());
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() < 0 || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                    return;
                }

                CourseRow row = currentRow();
                actionsBox.getChildren().clear();
                if (approvalMode && row != null) {
                    approveButton.setDisable(!"DRAFT".equalsIgnoreCase(row.getStatus()));
                    actionsBox.getChildren().add(approveButton);
                }
                actionsBox.getChildren().addAll(editButton, deleteButton);
                actionsBox.getStyleClass().setAll("admin-table-action-group");
                setGraphic(actionsBox);
            }

            private CourseRow currentRow() {
                int index = getIndex();
                if (index < 0 || index >= getTableView().getItems().size()) {
                    return null;
                }
                return getTableView().getItems().get(index);
            }
        };
    }

    private void approveCourse(Course course) {
        if (course == null) {
            return;
        }
        course.setStatus("PUBLISHED");
        courseService.update(course);
        refreshCourses();
        showAlert(Alert.AlertType.INFORMATION, "Course Approved", "The course is now published.");
    }

    private void showEditDialog(Course course) {
        if (course == null) {
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Course");
        dialog.setHeaderText(null);

        ButtonType saveButtonType = new ButtonType("Update Course", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        TextField titleField = new TextField(nullToEmpty(course.getTitle()));
        TextArea descriptionArea = new TextArea(nullToEmpty(course.getDescription()));
        TextField thumbnailField = new TextField(nullToEmpty(course.getThumbnailUrl()));
        ComboBox<String> statusBox = new ComboBox<>(FXCollections.observableArrayList("PUBLISHED", "DRAFT", "HIDDEN"));

        titleField.setPromptText("Course title");
        descriptionArea.setPromptText("Course description");
        descriptionArea.setPrefRowCount(4);
        descriptionArea.setWrapText(true);
        thumbnailField.setPromptText("Thumbnail URL");
        statusBox.setValue(course.getStatus() == null || course.getStatus().isBlank() ? "DRAFT" : course.getStatus());
        statusBox.setMaxWidth(Double.MAX_VALUE);

        VBox content = buildDialogContent(
                "Edit Course",
                "Admins can correct the record, publish drafts, hide courses or delete from the table.",
                createFieldBlock("Course Name", titleField),
                createFieldBlock("Description", descriptionArea),
                createFieldBlock("Thumbnail URL", thumbnailField),
                createFieldBlock("Status", statusBox)
        );

        configureDialog(dialog.getDialogPane(), saveButtonType);
        dialog.getDialogPane().setContent(content);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == saveButtonType) {
            String title = titleField.getText().trim();
            if (title.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Course name is required.");
                return;
            }

            course.setTitle(title);
            course.setDescription(descriptionArea.getText().trim());
            course.setThumbnailUrl(thumbnailField.getText().trim().isEmpty() ? null : thumbnailField.getText().trim());
            course.setStatus(statusBox.getValue());
            courseService.update(course);
            refreshCourses();
        }
    }

    private void deleteCourse(Course course) {
        if (course == null) {
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Course");
        confirm.setHeaderText(null);
        confirm.setContentText("Delete \"" + course.getTitle() + "\" and its quizzes, sections and videos?");
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            courseService.delete(course.getId());
            refreshCourses();
        }
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

    private void configureDialog(DialogPane pane, ButtonType saveButtonType) {
        pane.getStylesheets().add(getClass().getResource("/css/admin.css").toExternalForm());
        pane.getStyleClass().add("app-dialog-pane");
        pane.setPrefWidth(560);
        pane.setMinHeight(Region.USE_PREF_SIZE);

        Button saveButton = (Button) pane.lookupButton(saveButtonType);
        Button cancelButton = (Button) pane.lookupButton(ButtonType.CANCEL);
        saveButton.getStyleClass().add("dialog-primary-button");
        cancelButton.getStyleClass().add("dialog-secondary-button");
    }

    private void setActiveMode(Button activeButton) {
        approvalModeButton.getStyleClass().setAll("admin-mode-button");
        allCoursesModeButton.getStyleClass().setAll("admin-mode-button");
        activeButton.getStyleClass().add(MODE_ACTIVE_CLASS);
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

    private String statusClass(String status) {
        if ("PUBLISHED".equalsIgnoreCase(status)) {
            return "admin-status-published";
        }
        if ("HIDDEN".equalsIgnoreCase(status)) {
            return "admin-status-hidden";
        }
        return "admin-status-draft";
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

    public static final class CourseRow {
        private final Course course;
        private final String artistName;

        private CourseRow(Course course, String artistName) {
            this.course = course;
            this.artistName = artistName;
        }

        public Course getCourse() {
            return course;
        }

        public String getTitle() {
            return course.getTitle();
        }

        public String getArtistName() {
            return artistName;
        }

        public String getStatus() {
            return course.getStatus();
        }
    }
}
