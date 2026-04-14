package tn.esprit.pegasus.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.IOException;

public class StartPageController {

    public void goToFrontOffice(ActionEvent event) {
        navigate(event, "/front/FrontLayout.fxml");
    }

    public void goToBackOffice(ActionEvent event) {
        navigate(event, "/back/BackHome.fxml");
    }

    private void navigate(ActionEvent event, String path) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(path));
            Button sourceButton = (Button) event.getSource();
            sourceButton.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Navigation Error");
            alert.setHeaderText(null);
            alert.setContentText("Could not open page: " + path);
            alert.showAndWait();
            System.out.println(e.getMessage());
        }
    }
}