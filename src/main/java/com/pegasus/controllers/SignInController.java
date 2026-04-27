package com.pegasus.controllers;

import com.pegasus.entities.User;
import com.pegasus.services.EmailService;
import com.pegasus.services.GoogleAuthService;
import com.pegasus.services.GoogleUserProfile;
import com.pegasus.services.ServiceNormalUser;
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
    private ServiceNormalUser serviceNormalUser;
    private EmailService emailService;

    public void onSignIn() {
        SceneNavigator.clearPendingGoogleUserProfile();
        if (isBlank(emailField.getText()) || isBlank(passwordField.getText())) {
            showAlert(Alert.AlertType.ERROR, "Sign In", "Email and password are required.");
            return;
        }

        if (!initService()) {
            return;
        }

        User user = serviceUser.authenticate(emailField.getText(), passwordField.getText());
        if (user == null) {
            String error = serviceUser.getLastError();
            showAlert(Alert.AlertType.ERROR, "Sign In", error == null ? "Invalid email or password." : error);
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

    public void onSignInWithGoogle() {
        if (!initServices()) {
            return;
        }

        try {
            GoogleUserProfile profile = new GoogleAuthService().signIn();

            User user = serviceUser.findByGoogleSub(profile.sub());
            if (user != null) {
                completeSignIn(user, "Welcome " + user.getUsername() + " (" + user.getDtype() + ")");
                return;
            }

            user = serviceUser.findByEmail(profile.email());
            if (user != null) {
                user.setProvider("GOOGLE");
                user.setGoogleSub(profile.sub());
                if (isBlank(user.getAvatarUrl()) && !isBlank(profile.pictureUrl())) {
                    user.setAvatarUrl(profile.pictureUrl());
                }
                serviceUser.modifier(user);
                if (serviceUser.getLastError() != null) {
                    showAlert(Alert.AlertType.ERROR, "Google Sign In", serviceUser.getLastError());
                    return;
                }
                completeSignIn(user, "Welcome " + user.getUsername() + " (" + user.getDtype() + ")");
                return;
            }

            SceneNavigator.setSelectedRole("NORMAL_USER");
            SceneNavigator.setPendingGoogleUserProfile(profile);
            SceneNavigator.goTo("/views/signup-view.fxml");
        } catch (IllegalStateException e) {
            showAlert(Alert.AlertType.ERROR, "Google Sign In", e.getMessage());
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open sign-up page.");
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

    public void onGoToEmailVerification() {
        try {
            SceneNavigator.goTo("/views/email-verification-view.fxml");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open email verification page.");
        }
    }

    public void onResendVerificationEmail() {
        String email = emailField.getText();
        if (isBlank(email)) {
            showAlert(Alert.AlertType.ERROR, "Resend Verification", "Enter your email first.");
            return;
        }
        if (!initServices()) {
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

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean initServices() {
        try {
            if (serviceUser == null) {
                serviceUser = new ServiceUser();
            }
            if (serviceNormalUser == null) {
                serviceNormalUser = new ServiceNormalUser();
            }
            return true;
        } catch (RuntimeException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not connect to database.");
            return false;
        }
    }

    private boolean initService() {
        return initServices();
    }

    private void completeSignIn(User user, String message) {
        SceneNavigator.clearPendingGoogleUserProfile();
        SceneNavigator.setCurrentUser(user);
        showAlert(Alert.AlertType.INFORMATION, "Sign In", message);
        try {
            SceneNavigator.goTo("/views/home-view.fxml");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open home page.");
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
