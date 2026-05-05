package tn.esprit.pegasus.controllers.front;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import tn.esprit.pegasus.entities.Course;
import tn.esprit.pegasus.entities.CourseSection;
import tn.esprit.pegasus.entities.CourseVideo;
import tn.esprit.pegasus.services.CourseSectionService;
import tn.esprit.pegasus.services.CourseVideoService;

import java.util.List;
import java.util.Optional;

public class CourseSectionsController {
    private static final double VIDEO_ROW_HEIGHT = 64;

    @FXML
    private Label lblCourseTitle;

    @FXML
    private Label lblCourseSubtitle;

    @FXML
    private Label lblSectionCount;

    @FXML
    private Label lblVideoCount;

    @FXML
    private ListView<CourseSection> lvSections;

    @FXML
    private Label lblSelectedSectionTitle;

    @FXML
    private Label lblSelectedSectionMeta;

    @FXML
    private Label lblSelectedSectionHint;

    @FXML
    private Label lblEmptyVideos;

    @FXML
    private Button btnEditSection;

    @FXML
    private Button btnDeleteSection;

    @FXML
    private Button btnAddVideo;

    @FXML
    private TableView<CourseVideo> tvVideos;

    @FXML
    private TableColumn<CourseVideo, String> colVideoTitle;

    @FXML
    private TableColumn<CourseVideo, Integer> colVideoDuration;

    @FXML
    private TableColumn<CourseVideo, Boolean> colVideoPreview;

    @FXML
    private TableColumn<CourseVideo, String> colVideoLink;

    @FXML
    private TableColumn<CourseVideo, Void> colVideoActions;

    private final CourseSectionService courseSectionService = new CourseSectionService();
    private final CourseVideoService courseVideoService = new CourseVideoService();
    private final ObservableList<CourseSection> sectionObservableList = FXCollections.observableArrayList();
    private final ObservableList<CourseVideo> videoObservableList = FXCollections.observableArrayList();

    private Course course;
    private CourseSection selectedSection;

    @FXML
    public void initialize() {
        setupSectionList();
        setupVideoTable();
        updateSelectedSectionState(null);
    }

    public void setCourse(Course course) {
        this.course = course;
        lblCourseTitle.setText(course.getTitle());
        lblCourseSubtitle.setText("Build your learning flow with sections, lesson videos, and resource-friendly structure.");
        refreshSections();
    }

    @FXML
    public void openCreateSectionDialog() {
        showSectionDialog(null);
    }

    @FXML
    public void openEditSectionDialog() {
        if (selectedSection != null) {
            showSectionDialog(selectedSection);
        }
    }

    @FXML
    public void deleteSelectedSection() {
        if (selectedSection == null) {
            showAlert(Alert.AlertType.WARNING, "No Section Selected", "Choose a section before deleting it.");
            return;
        }

        if (confirmDelete("Delete Section", "This will remove the section and its videos.")) {
            courseSectionService.delete(selectedSection.getId());
            refreshSections();
        }
    }

    @FXML
    public void openCreateVideoDialog() {
        if (selectedSection == null) {
            showAlert(Alert.AlertType.WARNING, "No Section Selected", "Choose a section before adding a video.");
            return;
        }

        showVideoDialog(null, selectedSection);
    }

    private void setupSectionList() {
        lvSections.setItems(sectionObservableList);
        lvSections.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(CourseSection item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }

                Label orderBadge = new Label(String.format("%02d", item.getOrderIndex()));
                orderBadge.getStyleClass().add("section-order-badge");

                Label titleLabel = new Label(item.getTitle());
                titleLabel.getStyleClass().add("section-item-title");

                Label metaLabel = new Label(courseVideoService.countBySectionId(item.getId()) + " video(s)");
                metaLabel.getStyleClass().add("section-item-meta");

                VBox textBox = new VBox(4, titleLabel, metaLabel);
                HBox row = new HBox(12, orderBadge, textBox);
                row.getStyleClass().add("section-list-item");
                setGraphic(row);
            }
        });

        lvSections.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            selectedSection = newValue;
            updateSelectedSectionState(newValue);
            loadVideosForSelectedSection();
        });
    }

    private void setupVideoTable() {
        tvVideos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tvVideos.setFixedCellSize(VIDEO_ROW_HEIGHT);
        tvVideos.setPlaceholder(new Label("No videos inside this section yet."));

        colVideoTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colVideoDuration.setCellValueFactory(new PropertyValueFactory<>("durationSec"));
        colVideoPreview.setCellValueFactory(new PropertyValueFactory<>("preview"));
        colVideoLink.setCellValueFactory(new PropertyValueFactory<>("videoUrl"));

        colVideoTitle.setCellFactory(column -> new TableCell<>() {
            private final Label titleLabel = new Label();
            private final Label metaLabel = new Label();
            private final VBox box = new VBox(6, titleLabel, metaLabel);

            {
                titleLabel.getStyleClass().add("dashboard-course-title");
                metaLabel.getStyleClass().add("dashboard-course-description");
                metaLabel.setWrapText(true);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    return;
                }

                CourseVideo video = getTableView().getItems().get(getIndex());
                titleLabel.setText(video.getTitle());
                metaLabel.setText("Lesson " + video.getOrderIndex() + " in this section");
                setGraphic(box);
            }
        });

        colVideoDuration.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : formatDuration(item));
            }
        });

        colVideoPreview.setCellFactory(column -> new TableCell<>() {
            private final Label badge = new Label();

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                    return;
                }

                badge.getStyleClass().setAll(item ? "green-badge" : "neutral-badge");
                badge.setText(item ? "PREVIEW" : "LOCKED");
                setGraphic(badge);
            }
        });

        colVideoLink.setCellFactory(column -> new TableCell<>() {
            private final Label linkLabel = new Label();

            {
                linkLabel.getStyleClass().add("video-link-label");
                linkLabel.setWrapText(true);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    return;
                }

                linkLabel.setText(item);
                setGraphic(linkLabel);
            }
        });

        colVideoActions.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox actionsBox = new HBox(10, editButton, deleteButton);

            {
                editButton.getStyleClass().add("warning-button");
                deleteButton.getStyleClass().add("delete-red-button");
                actionsBox.setFillHeight(false);

                editButton.setOnAction(event -> {
                    CourseVideo video = getTableView().getItems().get(getIndex());
                    showVideoDialog(video, selectedSection);
                });
                deleteButton.setOnAction(event -> {
                    CourseVideo video = getTableView().getItems().get(getIndex());
                    if (confirmDelete("Delete Video", "Remove this lesson from the section?")) {
                        courseVideoService.delete(video.getId());
                        loadVideosForSelectedSection();
                        refreshHeaderCounts();
                        lvSections.refresh();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : actionsBox);
            }
        });

        tvVideos.setItems(videoObservableList);
    }

    private void refreshSections() {
        if (course == null) {
            return;
        }

        List<CourseSection> sections = courseSectionService.getByCourseId(course.getId());
        sectionObservableList.setAll(sections);
        refreshHeaderCounts();
        lvSections.refresh();

        if (sections.isEmpty()) {
            updateSelectedSectionState(null);
            videoObservableList.clear();
            lvSections.getSelectionModel().clearSelection();
            return;
        }

        if (selectedSection != null) {
            sections.stream()
                    .filter(section -> section.getId() == selectedSection.getId())
                    .findFirst()
                    .ifPresentOrElse(
                            section -> lvSections.getSelectionModel().select(section),
                            () -> lvSections.getSelectionModel().selectFirst()
                    );
        } else {
            lvSections.getSelectionModel().selectFirst();
        }
    }

    private void loadVideosForSelectedSection() {
        if (selectedSection == null) {
            videoObservableList.clear();
            lblEmptyVideos.setVisible(true);
            lblEmptyVideos.setManaged(true);
            return;
        }

        List<CourseVideo> videos = courseVideoService.getBySectionId(selectedSection.getId());
        videoObservableList.setAll(videos);
        lblEmptyVideos.setVisible(videos.isEmpty());
        lblEmptyVideos.setManaged(videos.isEmpty());
    }

    private void refreshHeaderCounts() {
        lblSectionCount.setText(sectionObservableList.size() + " section(s)");

        int totalVideos = 0;
        for (CourseSection section : sectionObservableList) {
            totalVideos += courseVideoService.countBySectionId(section.getId());
        }
        lblVideoCount.setText(totalVideos + " video lesson(s)");
    }

    private void updateSelectedSectionState(CourseSection section) {
        boolean hasSelection = section != null;
        btnEditSection.setDisable(!hasSelection);
        btnDeleteSection.setDisable(!hasSelection);
        btnAddVideo.setDisable(!hasSelection);

        if (!hasSelection) {
            lblSelectedSectionTitle.setText("Choose a section");
            lblSelectedSectionMeta.setText("Double-click a course, then build sections like intro, core lessons, and conclusion.");
            lblSelectedSectionHint.setText("You can structure each section with videos now, and the layout leaves room for text or PDF-style resources later.");
            return;
        }

        lblSelectedSectionTitle.setText(section.getTitle());
        lblSelectedSectionMeta.setText("Section " + section.getOrderIndex() + " of " + course.getTitle());
        lblSelectedSectionHint.setText(buildSectionHint(section));
    }

    private void showSectionDialog(CourseSection sectionToEdit) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(sectionToEdit == null ? "Add Section" : "Edit Section");
        dialog.setHeaderText(null);

        ButtonType saveButtonType = new ButtonType(sectionToEdit == null ? "Save" : "Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        TextField titleField = new TextField();
        Spinner<Integer> orderSpinner = new Spinner<>();
        titleField.setPromptText("Section title");
        orderSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, sectionToEdit == null ? sectionObservableList.size() + 1 : sectionToEdit.getOrderIndex()));
        orderSpinner.setEditable(true);
        orderSpinner.setMaxWidth(Double.MAX_VALUE);

        if (sectionToEdit != null) {
            titleField.setText(sectionToEdit.getTitle());
        }

        VBox content = buildDialogContent(
                sectionToEdit == null ? "Add New Section" : "Edit Section",
                "Use sections to create a clear learning path like introduction, main content, and conclusion.",
                createFieldBlock("Section Title", titleField),
                createFieldBlock("Section Order", buildSpinnerBlock(orderSpinner, "section position"))
        );

        configureDialogPane(dialog.getDialogPane(), saveButtonType);
        dialog.getDialogPane().setContent(content);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == saveButtonType) {
            String error = validateSectionInputs(titleField.getText().trim(), orderSpinner.getValue());
            if (error != null) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", error);
                return;
            }

            if (sectionToEdit == null) {
                courseSectionService.add(new CourseSection(titleField.getText().trim(), orderSpinner.getValue(), course.getId()));
            } else {
                sectionToEdit.setTitle(titleField.getText().trim());
                sectionToEdit.setOrderIndex(orderSpinner.getValue());
                courseSectionService.update(sectionToEdit);
            }

            refreshSections();
        }
    }

    private void showVideoDialog(CourseVideo videoToEdit, CourseSection targetSection) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(videoToEdit == null ? "Add Video" : "Edit Video");
        dialog.setHeaderText(null);

        ButtonType saveButtonType = new ButtonType(videoToEdit == null ? "Save" : "Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        TextField titleField = new TextField();
        TextField urlField = new TextField();
        Spinner<Integer> durationSpinner = new Spinner<>();
        Spinner<Integer> orderSpinner = new Spinner<>();
        CheckBox previewCheckBox = new CheckBox("Allow preview access");

        titleField.setPromptText("Lesson title");
        urlField.setPromptText("https://...");
        durationSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 14400, 300, 30));
        durationSpinner.setEditable(true);
        durationSpinner.setMaxWidth(Double.MAX_VALUE);
        orderSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, videoToEdit == null ? videoObservableList.size() + 1 : videoToEdit.getOrderIndex()));
        orderSpinner.setEditable(true);
        orderSpinner.setMaxWidth(Double.MAX_VALUE);

        if (videoToEdit != null) {
            titleField.setText(videoToEdit.getTitle());
            urlField.setText(videoToEdit.getVideoUrl());
            durationSpinner.getValueFactory().setValue(videoToEdit.getDurationSec());
            orderSpinner.getValueFactory().setValue(videoToEdit.getOrderIndex());
            previewCheckBox.setSelected(videoToEdit.isPreview());
        }

        VBox content = buildDialogContent(
                videoToEdit == null ? "Add New Video Lesson" : "Edit Video Lesson",
                "Attach a lesson video to this section so the course flow feels structured and easy to follow.",
                createFieldBlock("Video Title", titleField),
                createFieldBlock("Video URL", urlField),
                createFieldBlock("Duration", buildSpinnerBlock(durationSpinner, "seconds")),
                createFieldBlock("Lesson Order", buildSpinnerBlock(orderSpinner, "lesson position")),
                createFieldBlock("Access", previewCheckBox)
        );

        configureDialogPane(dialog.getDialogPane(), saveButtonType);
        dialog.getDialogPane().setContent(content);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == saveButtonType) {
            String error = validateVideoInputs(titleField.getText().trim(), urlField.getText().trim(), durationSpinner.getValue(), orderSpinner.getValue());
            if (error != null) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", error);
                return;
            }

            if (videoToEdit == null) {
                courseVideoService.add(new CourseVideo(
                        titleField.getText().trim(),
                        urlField.getText().trim(),
                        durationSpinner.getValue(),
                        orderSpinner.getValue(),
                        previewCheckBox.isSelected(),
                        targetSection.getId()
                ));
            } else {
                videoToEdit.setTitle(titleField.getText().trim());
                videoToEdit.setVideoUrl(urlField.getText().trim());
                videoToEdit.setDurationSec(durationSpinner.getValue());
                videoToEdit.setOrderIndex(orderSpinner.getValue());
                videoToEdit.setPreview(previewCheckBox.isSelected());
                courseVideoService.update(videoToEdit);
            }

            loadVideosForSelectedSection();
            refreshHeaderCounts();
            lvSections.refresh();
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
        Label hintLabel = new Label("Choose a value for the " + suffix + ".");
        hintLabel.getStyleClass().add("dialog-field-hint");

        VBox box = new VBox(8);
        box.getChildren().addAll(spinner, hintLabel);
        return box;
    }

    private void configureDialogPane(DialogPane dialogPane, ButtonType saveButtonType) {
        dialogPane.getStylesheets().add(getClass().getResource("/css/app.css").toExternalForm());
        dialogPane.getStyleClass().add("app-dialog-pane");
        dialogPane.setPrefWidth(560);
        dialogPane.setMinHeight(Region.USE_PREF_SIZE);

        Button saveButton = (Button) dialogPane.lookupButton(saveButtonType);
        Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
        saveButton.getStyleClass().add("dialog-primary-button");
        cancelButton.getStyleClass().add("dialog-secondary-button");
    }

    private String validateSectionInputs(String title, Integer orderIndex) {
        if (title.isEmpty()) {
            return "Section title is required.";
        }
        if (title.length() < 3) {
            return "Section title must contain at least 3 characters.";
        }
        if (orderIndex == null || orderIndex < 1 || orderIndex > 50) {
            return "Section order must be between 1 and 50.";
        }
        return null;
    }

    private String validateVideoInputs(String title, String url, Integer durationSec, Integer orderIndex) {
        if (title.isEmpty()) {
            return "Video title is required.";
        }
        if (title.length() < 3) {
            return "Video title must contain at least 3 characters.";
        }
        if (url.isEmpty()) {
            return "Video URL is required.";
        }
        if (!url.matches("https?://.+")) {
            return "Video URL must start with http:// or https://.";
        }
        if (durationSec == null || durationSec < 1 || durationSec > 14400) {
            return "Duration must be between 1 second and 4 hours.";
        }
        if (orderIndex == null || orderIndex < 1 || orderIndex > 100) {
            return "Lesson order must be between 1 and 100.";
        }
        return null;
    }

    private boolean confirmDelete(String title, String message) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle(title);
        confirm.setHeaderText(null);
        confirm.setContentText(message);
        return confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String buildSectionHint(CourseSection section) {
        String title = section.getTitle().toLowerCase();
        if (title.contains("intro")) {
            return "Great place for a warm welcome video, a quick PDF roadmap, or short orientation notes.";
        }
        if (title.contains("conclusion")) {
            return "Use this section for recap lessons, final resources, and wrap-up guidance.";
        }
        return "Mix structured videos with resource links so this section feels complete and easy to consume.";
    }

    private String formatDuration(int durationSec) {
        int minutes = durationSec / 60;
        int seconds = durationSec % 60;
        if (minutes == 0) {
            return seconds + " sec";
        }
        return String.format("%d:%02d", minutes, seconds);
    }
}
