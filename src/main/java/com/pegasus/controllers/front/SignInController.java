package com.pegasus.controllers.front;

import com.pegasus.controllers.SceneNavigator;
import com.pegasus.entities.User;
import com.pegasus.services.GoogleAuthService;
import com.pegasus.services.GoogleUserProfile;
import com.pegasus.services.ServiceUser;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignInController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField passwordVisibleField;

    @FXML
    private Button togglePasswordButton;

    private ServiceUser serviceUser;

    @FXML
    public void initialize() {
        passwordVisibleField.setManaged(false);
        passwordVisibleField.setVisible(false);
    }

    public void onSignIn() {
        SceneNavigator.clearPendingGoogleUserProfile();
        String passwordInput = getPasswordInput();
        if (isBlank(emailField.getText()) || isBlank(passwordInput)) {
            showAlert(Alert.AlertType.ERROR, "Sign In", "Email and password are required.");
            return;
        }

        if (!initService()) {
            return;
        }

        User user = serviceUser.authenticate(emailField.getText(), passwordInput);
        if (user == null) {
            String error = serviceUser.getLastError();
            showAlert(Alert.AlertType.ERROR, "Sign In", error == null ? "Invalid email or password." : error);
            return;
        }

        SceneNavigator.setCurrentUser(user);
        try {
            SceneNavigator.goTo(resolvePostLoginView(user));
            SceneNavigator.showWelcomeBack(user);
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", e.getMessage());
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
                completeSignIn(user);
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
                completeSignIn(user);
                return;
            }

            SceneNavigator.setSelectedRole("NORMAL_USER");
            SceneNavigator.setPendingGoogleUserProfile(profile);
            SceneNavigator.goTo("/views/front/signup-view.fxml");
        } catch (IllegalStateException e) {
            showAlert(Alert.AlertType.ERROR, "Google Sign In", e.getMessage());
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open sign-up page.");
        }
    }

    public void onGoToSignUp() {
        try {
            SceneNavigator.setSelectedRole("NORMAL_USER");
            SceneNavigator.goTo("/views/front/signup-view.fxml");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", e.getMessage());
        }
    }

    public void onGoToSignIn() {
        // Already on sign-in page.
    }

    public void onTogglePassword() {
        boolean showing = passwordVisibleField.isVisible();
        if (showing) {
            passwordField.setText(passwordVisibleField.getText());
            passwordVisibleField.setVisible(false);
            passwordVisibleField.setManaged(false);
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            togglePasswordButton.setText("Show");
        } else {
            passwordVisibleField.setText(passwordField.getText());
            passwordField.setVisible(false);
            passwordField.setManaged(false);
            passwordVisibleField.setVisible(true);
            passwordVisibleField.setManaged(true);
            togglePasswordButton.setText("Hide");
        }
    }

    public void onBackHome() {
        try {
            SceneNavigator.goTo("/views/front/home-view.fxml");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", e.getMessage());
        }
    }

    public void onGoToEmailVerification() {
        try {
            SceneNavigator.goTo("/views/front/email-verification-view.fxml");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open email verification page.");
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

    public void onGoToForgotPassword() {
        try {
            SceneNavigator.goTo("/views/front/reset-password-view.fxml");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open password reset page.");
        }
    }

    private boolean initService() {
        return initServices();
    }

    private void completeSignIn(User user) {
        SceneNavigator.clearPendingGoogleUserProfile();
        SceneNavigator.setCurrentUser(user);
        try {
            SceneNavigator.goTo(resolvePostLoginView(user));
            SceneNavigator.showWelcomeBack(user);
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open workspace.");
        }
    }

    private String resolvePostLoginView(User user) {
        if (user != null && "admin".equalsIgnoreCase(user.getDtype())) {
            return "/views/back/AdminLayout.fxml";
        }
        return "/views/front/home-view.fxml";
    }

    private String getPasswordInput() {
        return passwordVisibleField.isVisible() ? passwordVisibleField.getText() : passwordField.getText();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        boolean isError = type == Alert.AlertType.ERROR;
        SceneNavigator.showSnackbar(title, content, isError);
    }
}
