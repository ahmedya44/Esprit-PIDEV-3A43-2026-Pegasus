package com.pegasus.controllers.front;

import com.pegasus.controllers.SceneNavigator;
import com.pegasus.entities.User;
import com.pegasus.services.ServiceUser;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ProfilePasswordController {
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private TextField newPasswordVisibleField;
    @FXML
    private Button toggleNewPasswordButton;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField confirmPasswordVisibleField;
    @FXML
    private Button toggleConfirmPasswordButton;

    private ServiceUser serviceUser;
    private User currentUser;

    @FXML
    public void initialize() {
        currentUser = SceneNavigator.getCurrentUser();
        if (currentUser == null) {
            showAlert(Alert.AlertType.ERROR, "Session", "You need to sign in first.");
            onBackToProfile();
            return;
        }
        newPasswordVisibleField.setManaged(false);
        newPasswordVisibleField.setVisible(false);
        confirmPasswordVisibleField.setManaged(false);
        confirmPasswordVisibleField.setVisible(false);
    }

    public void onSaveNewPassword() {
        if (!initService()) {
            return;
        }
        String newPassword = getNewPassword();
        String confirmPassword = getConfirmPassword();
        if (isBlank(newPassword) || isBlank(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Reset Password", "Both password fields are required.");
            return;
        }
        if (newPassword.length() < 8 || !newPassword.matches(".*[A-Za-z].*") || !newPassword.matches(".*[0-9].*")) {
            showAlert(Alert.AlertType.ERROR, "Reset Password", "Password must be at least 8 characters and include letters and numbers.");
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Reset Password", "New password and confirm password do not match.");
            return;
        }

        User refreshed = serviceUser.findById(currentUser.getId());
        if (refreshed != null) {
            currentUser = refreshed;
        }

        User updated = new User();
        updated.setId(currentUser.getId());
        updated.setEmail(currentUser.getEmail());
        updated.setRoles(currentUser.getRoles());
        updated.setDtype(currentUser.getDtype());
        updated.setStatus(currentUser.getStatus());
        updated.setUsername(currentUser.getUsername());
        updated.setPhone(currentUser.getPhone());
        updated.setAvatarUrl(currentUser.getAvatarUrl());
        updated.setPassword(newPassword);
        updated.setProvider(currentUser.getProvider());
        updated.setGoogleSub(currentUser.getGoogleSub());
        updated.setEmailVerificationToken(currentUser.getEmailVerificationToken());
        updated.setEmailVerificationTokenExpiresAt(currentUser.getEmailVerificationTokenExpiresAt());

        serviceUser.modifier(updated);
        User saved = serviceUser.findById(currentUser.getId());
        if (saved != null) {
            SceneNavigator.setCurrentUser(saved);
        }

        showAlert(Alert.AlertType.INFORMATION, "Reset Password", "Password updated successfully.");
        onBackToProfile();
    }

    public void onToggleNewPassword() {
        boolean showing = newPasswordVisibleField.isVisible();
        if (showing) {
            newPasswordField.setText(newPasswordVisibleField.getText());
            newPasswordVisibleField.setVisible(false);
            newPasswordVisibleField.setManaged(false);
            newPasswordField.setVisible(true);
            newPasswordField.setManaged(true);
            toggleNewPasswordButton.setText("Show");
        } else {
            newPasswordVisibleField.setText(newPasswordField.getText());
            newPasswordField.setVisible(false);
            newPasswordField.setManaged(false);
            newPasswordVisibleField.setVisible(true);
            newPasswordVisibleField.setManaged(true);
            toggleNewPasswordButton.setText("Hide");
        }
    }

    public void onToggleConfirmPassword() {
        boolean showing = confirmPasswordVisibleField.isVisible();
        if (showing) {
            confirmPasswordField.setText(confirmPasswordVisibleField.getText());
            confirmPasswordVisibleField.setVisible(false);
            confirmPasswordVisibleField.setManaged(false);
            confirmPasswordField.setVisible(true);
            confirmPasswordField.setManaged(true);
            toggleConfirmPasswordButton.setText("Show");
        } else {
            confirmPasswordVisibleField.setText(confirmPasswordField.getText());
            confirmPasswordField.setVisible(false);
            confirmPasswordField.setManaged(false);
            confirmPasswordVisibleField.setVisible(true);
            confirmPasswordVisibleField.setManaged(true);
            toggleConfirmPasswordButton.setText("Hide");
        }
    }

    public void onBackToEditProfile() {
        try {
            SceneNavigator.goTo("/views/front/profile-edit-view.fxml");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open profile edit page.");
        }
    }

    public void onBackToProfile() {
        try {
            SceneNavigator.goTo("/views/front/profile-view.fxml");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open profile page.");
        }
    }

    public void onLogout() {
        SceneNavigator.logoutToFrontHome();
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

    private String getNewPassword() {
        return newPasswordVisibleField.isVisible() ? newPasswordVisibleField.getText() : newPasswordField.getText();
    }

    private String getConfirmPassword() {
        return confirmPasswordVisibleField.isVisible() ? confirmPasswordVisibleField.getText() : confirmPasswordField.getText();
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        boolean isError = type == Alert.AlertType.ERROR;
        SceneNavigator.showSnackbar(title, content, isError);
    }
}
