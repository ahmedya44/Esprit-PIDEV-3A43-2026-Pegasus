package com.pegasus.controllers.front;

import com.pegasus.controllers.SceneNavigator;
import com.pegasus.entities.User;
import com.pegasus.services.EmailService;
import com.pegasus.services.ServiceUser;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class EmailVerificationController {
    @FXML
    private TextField verificationCodeField;
    @FXML
    private TextField emailField;

    private ServiceUser serviceUser;
    private EmailService emailService;

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
            SceneNavigator.setSelectedRole("NORMAL_USER");
            SceneNavigator.goTo("/views/front/signup-view.fxml");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open sign up page.");
        }
    }

    public void onResendVerificationEmail() {
        String email = trimToNull(emailField.getText());
        if (email == null) {
            showAlert(Alert.AlertType.ERROR, "Resend Verification", "Enter your email first.");
            return;
        }
        if (!initService()) {
            return;
        }

        try {
            if (emailService == null) {
                emailService = new EmailService();
            }

            User user = serviceUser.findByEmail(email);
            if (user == null) {
                showAlert(Alert.AlertType.ERROR, "Resend Verification", "No account was found for that email.");
                return;
            }
            if (!"LOCAL".equalsIgnoreCase(user.getProvider())) {
                showAlert(Alert.AlertType.INFORMATION, "Resend Verification", "This account signs in with Google and does not need email verification.");
                return;
            }
            if (ServiceUser.STATUS_ACTIVE.equalsIgnoreCase(user.getStatus())) {
                showAlert(Alert.AlertType.INFORMATION, "Resend Verification", "This account is already active.");
                return;
            }

            String verificationToken = serviceUser.createEmailVerificationToken(user);
            emailService.sendVerificationEmail(user.getEmail(), user.getUsername(), verificationToken);
            showAlert(Alert.AlertType.INFORMATION, "Resend Verification", "A new verification email has been sent.");
        } catch (IllegalStateException e) {
            showAlert(Alert.AlertType.ERROR, "Resend Verification", e.getMessage());
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
        boolean isError = type == Alert.AlertType.ERROR;
        SceneNavigator.showSnackbar(title, content, isError);
    }

    private String trimToNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }
}
