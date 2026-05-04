package com.pegasus.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;


public class RoleSelectionController {
    @FXML
    private ComboBox<String> roleCombo;

    @FXML
    public void initialize() {
        roleCombo.setItems(FXCollections.observableArrayList("ADMIN", "ARTISTE", "NORMAL_USER"));
        roleCombo.getSelectionModel().selectFirst();
    }

    public void onContinueToSignUp() {
        try {
            SceneNavigator.setSelectedRole(roleCombo.getValue());
            SceneNavigator.goTo("/views/signup-view.fxml");
        } catch (Exception e) {
            showAlert("Navigation Error", "Could not open sign up page.");
        }
    }

    public void onBackToSignIn() {
        try {
            SceneNavigator.goTo("/views/signin-view.fxml");
        } catch (Exception e) {
            showAlert("Navigation Error", "Could not open sign in page.");
        }
    }

    public void onGoHome() {
        try {
            SceneNavigator.goTo("/views/home-view.fxml");
        } catch (Exception e) {
            showAlert("Navigation Error", "Could not open home page.");
        }
    }

    public void onGoToSignIn() {
        onBackToSignIn();
    }

    public void onGoToSignUp() {
        onContinueToSignUp();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
