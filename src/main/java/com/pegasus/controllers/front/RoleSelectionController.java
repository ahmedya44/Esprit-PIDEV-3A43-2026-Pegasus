package com.pegasus.controllers.front;

import com.pegasus.controllers.SceneNavigator;
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
            SceneNavigator.goTo("/views/front/signup-view.fxml");
        } catch (Exception e) {
            showAlert("Navigation Error", e.getMessage());
        }
    }

    public void onBackToSignIn() {
        try {
            SceneNavigator.goTo("/views/front/signin-view.fxml");
        } catch (Exception e) {
            showAlert("Navigation Error", e.getMessage());
        }
    }

    public void onGoHome() {
        try {
            SceneNavigator.goTo("/views/front/home-view.fxml");
        } catch (Exception e) {
            showAlert("Navigation Error", e.getMessage());
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
