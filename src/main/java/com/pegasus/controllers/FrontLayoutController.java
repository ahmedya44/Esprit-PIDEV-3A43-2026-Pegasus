package com.pegasus.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import com.pegasus.entities.Course;

import java.io.IOException;
import java.util.function.Consumer;

public class FrontLayoutController {

    @FXML
    private AnchorPane contentArea;

    @FXML
    public void initialize() {
        loadPage("/views/CoursesContent.fxml");
    }

    @FXML
    public void showCourses(ActionEvent event) {
        loadPage("/views/CoursesContent.fxml");
    }

    @FXML
    public void showCoursesDashboard(ActionEvent event) {
        loadPage("/views/CoursesDashboardContent.fxml");
    }

    private void loadPage(String path) {
        loadPage(path, null);
    }

    public void showCoursePlayer(Course course) {
        loadPage("/views/CoursePlayerContent.fxml", controller -> {
            if (controller instanceof CoursePlayerController coursePlayerController) {
                coursePlayerController.setCourse(course);
            }
        });
    }

    private void loadPage(String path, Consumer<Object> controllerConfigurer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent page = loader.load();
            Object controller = loader.getController();
            if (controller instanceof FrontContentAware aware) {
                aware.setFrontLayoutController(this);
            }
            if (controllerConfigurer != null) {
                controllerConfigurer.accept(controller);
            }
            contentArea.getChildren().setAll(page);
            AnchorPane.setTopAnchor(page, 0.0);
            AnchorPane.setRightAnchor(page, 0.0);
            AnchorPane.setBottomAnchor(page, 0.0);
            AnchorPane.setLeftAnchor(page, 0.0);
        } catch (IOException e) {
            System.out.println("Load page error: " + path);
            e.printStackTrace();
        }
    }
}
