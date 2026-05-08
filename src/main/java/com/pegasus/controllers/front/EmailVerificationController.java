package com.pegasus.controllers.front;

import com.pegasus.controllers.SceneNavigator;
import com.pegasus.entities.User;
import com.pegasus.services.ServiceUser;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class EmailVerificationController {
    @FXML
    private TextField verificationCodeField;

    private ServiceUser serviceUser;

    public void onVerifyEmail() {
        if (!initService()) {
            return;
        }

        User user = serviceUser.verifyEmailToken(verificationCodeField.getText());
        if (user == null) {
            showAlert(Alert.AlertType.ERROR, "Verify Email", serviceUser.getLastError());
            return;
        }

        showAlert(Alert.AlertType.INFORMATION, "Verify Email", "Your account is now active. You can sign in.");
        try {
            SceneNavigator.goTo("/views/front/signin-view.fxml");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open sign in page.");
        }
    }

    public void onBackToSignIn() {
        try {
            SceneNavigator.goTo("/views/front/signin-view.fxml");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open sign in page.");
        }
    }

    public void onGoHome() {
        try {
            SceneNavigator.goTo("/views/front/home-view.fxml");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open home page.");
        }
    }

    public void onGoToSignIn() {
        onBackToSignIn();
    }

    public void onGoToSignUp() {
        try {
            SceneNavigator.goTo("/views/front/role-selection-view.fxml");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open sign up page.");
        }
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
