package com.pegasus.controllers;

import com.pegasus.entities.User;
import com.pegasus.services.ServiceUser;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignInController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private ServiceUser serviceUser;

    public void onSignIn() {
        if (isBlank(emailField.getText()) || isBlank(passwordField.getText())) {
            showAlert(Alert.AlertType.ERROR, "Sign In", "Email and password are required.");
            return;
        }

        if (!initService()) {
            return;
        }

        User user = serviceUser.authenticate(emailField.getText(), passwordField.getText());
        if (user == null) {
            showAlert(Alert.AlertType.ERROR, "Sign In", "Invalid email or password.");
            return;
        }

        SceneNavigator.setCurrentUser(user);
        showAlert(Alert.AlertType.INFORMATION, "Sign In", "Welcome " + user.getUsername() + " (" + user.getDtype() + ")");
        try {
            SceneNavigator.goTo("/views/home-view.fxml");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open home page.");
        }
    }

    public void onGoToSignUp() {
        try {
            SceneNavigator.goTo("/views/role-selection-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onGoToSignIn() {
        // Already on sign-in page.
    }

    public void onBackHome() {
        try {
            SceneNavigator.goTo("/views/home-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean initService() {
        try {
            if (serviceUser == null) {
                serviceUser = new ServiceUser();
            }
            return true;
        } catch (RuntimeException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not connect to database.");
            return false;
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
