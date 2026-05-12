package com.pegasus.controllers.back;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.pegasus.entities.Course;
import com.pegasus.services.CourseService;

import java.time.LocalDateTime;
import java.util.List;

public class CourseBackController {

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

    private final CourseService courseService = new CourseService();

    @FXML
    public void initialize() {
        cbStatus.setItems(FXCollections.observableArrayList("PUBLISHED", "DRAFT", "HIDDEN"));

        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colThumbnail.setCellValueFactory(new PropertyValueFactory<>("thumbnailUrl"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colArtistId.setCellValueFactory(new PropertyValueFactory<>("artistId"));

        loadCourses();
    }

    @FXML
    public void addCourse() {
        String title = tfTitle.getText().trim();
        String description = taDescription.getText().trim();
        String thumbnail = tfThumbnail.getText().trim();
        String status = cbStatus.getValue();
        String artistIdText = tfArtistId.getText().trim();

        String validationError = validateInputs(title, description, status, artistIdText);
        if (validationError != null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", validationError);
            return;
        }

        try {
            int artistId = Integer.parseInt(artistIdText);

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
            clearFields();
            loadCourses();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Artist ID must be a valid number.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    public void loadCourses() {
        List<Course> courseList = courseService.getAll();
        ObservableList<Course> observableList = FXCollections.observableArrayList(courseList);
        tvCourses.setItems(observableList);
    }

    @FXML
    public void clearFields() {
        tfTitle.clear();
        taDescription.clear();
        tfThumbnail.clear();
        cbStatus.setValue(null);
        tfArtistId.clear();
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