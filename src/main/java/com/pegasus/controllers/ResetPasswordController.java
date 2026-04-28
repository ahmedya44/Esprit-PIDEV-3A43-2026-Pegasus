package com.pegasus.controllers;

import com.pegasus.entities.User;
import com.pegasus.services.EmailService;
import com.pegasus.services.ServiceUser;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ResetPasswordController {
    @FXML
    private TextField emailField;

    @FXML
    private TextField resetCodeField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    private ServiceUser serviceUser;
    private EmailService emailService;

    public void onSendResetCode() {
        String email = trimToNull(emailField.getText());
        if (email == null) {
            showAlert(Alert.AlertType.ERROR, "Reset Password", "Email is required.");
            return;
        }
        if (!initServices()) {
            return;
        }

        User user = serviceUser.findByEmail(email);
        if (user == null) {
            showAlert(Alert.AlertType.INFORMATION, "Reset Password", "If this email exists, a reset code has been sent.");
            return;
        }

        try {
            String token = serviceUser.createPasswordResetToken(user);
            emailService.sendPasswordResetEmail(user.getEmail(), user.getUsername(), token);
            showAlert(Alert.AlertType.INFORMATION, "Reset Password", "Reset code sent. Check your email.");
        } catch (IllegalStateException e) {
            showAlert(Alert.AlertType.ERROR, "Reset Password", e.getMessage());
        }
    }

    public void onResetPassword() {
        String token = trimToNull(resetCodeField.getText());
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (token == null) {
            showAlert(Alert.AlertType.ERROR, "Reset Password", "Reset code is required.");
            return;
        }
        if (isBlank(newPassword) || isBlank(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Reset Password", "New password and confirmation are required.");
            return;
        }
        if (newPassword.length() < 8 || !newPassword.matches(".*[A-Za-z].*") || !newPassword.matches(".*[0-9].*")) {
            showAlert(Alert.AlertType.ERROR, "Reset Password", "Password must be at least 8 characters and include letters and numbers.");
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Reset Password", "Password and confirmation do not match.");
            return;
        }
        if (!initServices()) {
            return;
        }

        boolean ok = serviceUser.resetPassword(token, newPassword);
        if (!ok) {
            showAlert(Alert.AlertType.ERROR, "Reset Password", serviceUser.getLastError());
            return;
        }

        showAlert(Alert.AlertType.INFORMATION, "Reset Password", "Password updated successfully. You can sign in now.");
        try {
            SceneNavigator.goTo("/views/signin-view.fxml");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open sign in page.");
        }
    }

    public void onBackToSignIn() {
        try {
            SceneNavigator.goTo("/views/signin-view.fxml");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open sign in page.");
        }
    }

    public void onGoHome() {
        try {
            SceneNavigator.goTo("/views/home-view.fxml");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open home page.");
        }
    }

    public void onGoToSignIn() {
        onBackToSignIn();
    }

    public void onGoToSignUp() {
        try {
            SceneNavigator.goTo("/views/role-selection-view.fxml");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open sign up page.");
        }
    }

    private boolean initServices() {
        try {
            if (serviceUser == null) {
                serviceUser = new ServiceUser();
            }
            if (emailService == null) {
                emailService = new EmailService();
            }
            return true;
        } catch (RuntimeException e) {
            String message = e.getMessage();
            if (message == null || message.isBlank()) {
                message = "Could not connect to database.";
            }
            showAlert(Alert.AlertType.ERROR, "Database Error", message);
            return false;
        }
    }

    private String trimToNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
