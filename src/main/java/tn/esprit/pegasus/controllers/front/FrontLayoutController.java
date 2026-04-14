package tn.esprit.pegasus.controllers.front;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class FrontLayoutController {

    @FXML
    private AnchorPane contentArea;

    @FXML
    public void initialize() {
        loadPage("/front/HomeContent.fxml");
    }

    @FXML
    public void showHome(ActionEvent event) {
        loadPage("/front/HomeContent.fxml");
    }

    @FXML
    public void showCourses(ActionEvent event) {
        loadPage("/front/CoursesContent.fxml");
    }

    @FXML
    public void showGallery(ActionEvent event) {
        loadPage("/front/GalleryContent.fxml");
    }

    @FXML
    public void showCoursesDashboard(ActionEvent event) {
        loadPage("/front/CoursesDashboardContent.fxml");
    }

    private void loadPage(String path) {
        try {
            Parent page = FXMLLoader.load(getClass().getResource(path));
            contentArea.getChildren().setAll(page);
        } catch (IOException e) {
            System.out.println("Load page error: " + e.getMessage());
        }
    }
}