package tn.esprit.pegasus.controllers.front;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import tn.esprit.pegasus.entities.Course;
import tn.esprit.pegasus.services.CourseService;

import java.util.List;

public class CoursesContentController {

    @FXML
    private FlowPane coursesContainer;

    private final CourseService courseService = new CourseService();

    @FXML
    public void initialize() {
        loadPublishedCourses();
    }

    private void loadPublishedCourses() {
        List<Course> courses = courseService.getPublishedCourses();
        coursesContainer.getChildren().clear();

        for (Course course : courses) {
            VBox card = createCourseCard(course);
            coursesContainer.getChildren().add(card);
        }
    }

    private VBox createCourseCard(Course course) {
        StackPane thumbnailBox = new StackPane();
        thumbnailBox.getStyleClass().add("course-thumbnail-box");
        thumbnailBox.setPrefWidth(250);
        thumbnailBox.setMinHeight(120);
        thumbnailBox.setPrefHeight(120);

        Label thumbnailPlaceholder = new Label("Thumbnail");
        thumbnailPlaceholder.getStyleClass().add("course-thumbnail-placeholder");
        thumbnailBox.getChildren().add(thumbnailPlaceholder);

        Label titleLabel = new Label(course.getTitle());
        titleLabel.getStyleClass().add("course-title");
        titleLabel.setWrapText(true);

        Label descriptionLabel = new Label(course.getDescription());
        descriptionLabel.getStyleClass().add("course-description");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxWidth(220);

        Label statusLabel = new Label(course.getStatus());
        statusLabel.getStyleClass().add("course-status");

        VBox body = new VBox(10);
        body.getStyleClass().add("course-body");
        body.getChildren().addAll(titleLabel, descriptionLabel, statusLabel);

        VBox card = new VBox();
        card.getStyleClass().add("course-card");
        card.setPrefWidth(250);
        card.setPrefHeight(250);
        card.setAlignment(Pos.TOP_LEFT);
        card.getChildren().addAll(thumbnailBox, body);

        return card;
    }
}