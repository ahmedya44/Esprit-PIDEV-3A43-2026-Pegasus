package tn.esprit.pegasus.controllers.front;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.pegasus.entities.Course;
import tn.esprit.pegasus.services.CourseService;

import java.time.LocalDateTime;
import java.util.List;

public class CoursesDashboardController {

    @FXML
    private TextField tfTitle;

    @FXML
    private TextArea taDescription;

    @FXML
    private TextField tfThumbnail;

    @FXML
    private ComboBox<String> cbStatus;

    @FXML
    private TextField tfArtistId;

    @FXML
    private TextField tfSearch;

    @FXML
    private TableView<Course> tvCourses;

    @FXML
    private TableColumn<Course, String> colTitle;

    @FXML
    private TableColumn<Course, String> colDescription;

    @FXML
    private TableColumn<Course, String> colThumbnail;

    @FXML
    private TableColumn<Course, String> colStatus;

    @FXML
    private TableColumn<Course, Integer> colArtistId;

    @FXML
    private TableColumn<Course, Void> colUpdate;

    @FXML
    private TableColumn<Course, Void> colDelete;

    private final CourseService courseService = new CourseService();
    private final ObservableList<Course> courseObservableList = FXCollections.observableArrayList();
    private Course selectedCourse = null;

    @FXML
    public void initialize() {
        cbStatus.setItems(FXCollections.observableArrayList("PUBLISHED", "DRAFT", "HIDDEN"));

        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colThumbnail.setCellValueFactory(new PropertyValueFactory<>("thumbnailUrl"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colArtistId.setCellValueFactory(new PropertyValueFactory<>("artistId"));

        addUpdateButtonToTable();
        addDeleteButtonToTable();
        setupSearch();
        loadCourses();
        setupRowSelection();
    }

    @FXML
    public void addCourse() {
        String title = tfTitle.getText().trim();
        String description = taDescription.getText().trim();
        String thumbnail = tfThumbnail.getText().trim();
        String status = cbStatus.getValue();
        String artistIdText = tfArtistId.getText().trim();

        String error = validateInputs(title, description, status, artistIdText);
        if (error != null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", error);
            return;
        }

        try {
            int artistId = Integer.parseInt(artistIdText);

            if (selectedCourse == null) {
                Course course = new Course(
                        title,
                        description,
                        thumbnail.isEmpty() ? null : thumbnail,
                        status,
                        LocalDateTime.now(),
                        artistId
                );
                courseService.add(course);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Course added successfully.");
            } else {
                selectedCourse.setTitle(title);
                selectedCourse.setDescription(description);
                selectedCourse.setThumbnailUrl(thumbnail.isEmpty() ? null : thumbnail);
                selectedCourse.setStatus(status);
                selectedCourse.setArtistId(artistId);

                courseService.update(selectedCourse);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Course updated successfully.");
            }

            clearFields();
            loadCourses();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Artist ID must be a valid number.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    public void clearFields() {
        tfTitle.clear();
        taDescription.clear();
        tfThumbnail.clear();
        cbStatus.setValue(null);
        tfArtistId.clear();
        selectedCourse = null;
        tvCourses.getSelectionModel().clearSelection();
    }

    private void loadCourses() {
        List<Course> courses = courseService.getAll();
        courseObservableList.setAll(courses);
    }

    private void setupSearch() {
        FilteredList<Course> filteredList = new FilteredList<>(courseObservableList, course -> true);

        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(course -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }

                String keyword = newValue.toLowerCase().trim();

                if (course.getTitle() != null && course.getTitle().toLowerCase().contains(keyword)) {
                    return true;
                }

                if (course.getDescription() != null && course.getDescription().toLowerCase().contains(keyword)) {
                    return true;
                }

                if (course.getStatus() != null && course.getStatus().toLowerCase().contains(keyword)) {
                    return true;
                }

                if (course.getThumbnailUrl() != null && course.getThumbnailUrl().toLowerCase().contains(keyword)) {
                    return true;
                }

                return String.valueOf(course.getArtistId()).contains(keyword);
            });
        });

        SortedList<Course> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tvCourses.comparatorProperty());
        tvCourses.setItems(sortedList);
    }

    private void setupRowSelection() {
        tvCourses.setOnMouseClicked(event -> {
            Course course = tvCourses.getSelectionModel().getSelectedItem();
            if (course != null) {
                selectedCourse = course;
                tfTitle.setText(course.getTitle());
                taDescription.setText(course.getDescription());
                tfThumbnail.setText(course.getThumbnailUrl() == null ? "" : course.getThumbnailUrl());
                cbStatus.setValue(course.getStatus());
                tfArtistId.setText(String.valueOf(course.getArtistId()));
            }
        });
    }

    private void addUpdateButtonToTable() {
        colUpdate.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("✏");

            {
                btn.getStyleClass().add("warning-button");

                btn.setOnAction(event -> {
                    Course course = getTableView().getItems().get(getIndex());
                    selectedCourse = course;

                    tfTitle.setText(course.getTitle());
                    taDescription.setText(course.getDescription());
                    tfThumbnail.setText(course.getThumbnailUrl() == null ? "" : course.getThumbnailUrl());
                    cbStatus.setValue(course.getStatus());
                    tfArtistId.setText(String.valueOf(course.getArtistId()));
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    private void addDeleteButtonToTable() {
        colDelete.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("🗑");

            {
                btn.getStyleClass().add("danger-button");

                btn.setOnAction(event -> {
                    Course course = getTableView().getItems().get(getIndex());

                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Delete Course");
                    confirm.setHeaderText(null);
                    confirm.setContentText("Do you want to delete this course?");

                    if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                        courseService.delete(course.getId());
                        loadCourses();
                        clearFields();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    private String validateInputs(String title, String description, String status, String artistIdText) {
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

        if (artistIdText.isEmpty()) {
            return "Artist ID is required.";
        }

        if (!artistIdText.matches("\\d+")) {
            return "Artist ID must contain only numbers.";
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