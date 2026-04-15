package com.pegasus.controllers;

import com.pegasus.entities.User;
import com.pegasus.services.ServiceUser;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class EditProfileController {
    @FXML
    private TextField emailField;

    @FXML
    private TextField roleField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField avatarUrlField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    private ServiceUser serviceUser;
    private User currentUser;

    @FXML
    public void initialize() {
        currentUser = SceneNavigator.getCurrentUser();
        if (currentUser == null) {
            showAlert(Alert.AlertType.ERROR, "Session", "You need to sign in first.");
            goHomeSafe();
            return;
        }

        if (!initService()) {
            return;
        }

        User refreshed = serviceUser.findById(currentUser.getId());
        if (refreshed != null) {
            currentUser = refreshed;
            SceneNavigator.setCurrentUser(refreshed);
        }

        fillForm(currentUser);
    }

    public void onSaveProfile() {
        if (currentUser == null || !initService()) {
            return;
        }

        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        if (!isBlank(newPassword) || !isBlank(confirmPassword)) {
            if (!newPassword.equals(confirmPassword)) {
                showAlert(Alert.AlertType.ERROR, "Edit Profile", "New password and confirm password do not match.");
                return;
            }
        }

        User updated = new User();
        updated.setId(currentUser.getId());
        updated.setEmail(currentUser.getEmail());
        updated.setRoles(currentUser.getRoles());
        updated.setDtype(currentUser.getDtype());
        updated.setStatus(currentUser.getStatus());
        updated.setUsername(usernameField.getText());
        updated.setPhone(textOrNull(phoneField.getText()));
        updated.setAvatarUrl(textOrNull(avatarUrlField.getText()));
        updated.setPassword(isBlank(newPassword) ? currentUser.getPassword() : newPassword);

        serviceUser.modifier(updated);

        User refreshed = serviceUser.findById(currentUser.getId());
        if (refreshed != null) {
            currentUser = refreshed;
            SceneNavigator.setCurrentUser(refreshed);
            fillForm(refreshed);
        }

        showAlert(Alert.AlertType.INFORMATION, "Edit Profile", "Profile updated successfully.");
        newPasswordField.clear();
        confirmPasswordField.clear();
    }

    public void onBackHome() {
        goHomeSafe();
    }

    public void onLogout() {
        SceneNavigator.clearSession();
        goHomeSafe();
    }

    private void fillForm(User user) {
        emailField.setText(user.getEmail());
        roleField.setText(user.getDtype());
        usernameField.setText(user.getUsername());
        phoneField.setText(user.getPhone());
        avatarUrlField.setText(user.getAvatarUrl());
    }

    private String textOrNull(String value) {
        if (isBlank(value)) {
            return null;
        }
        return value.trim();
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

    private void goHomeSafe() {
        try {
            SceneNavigator.goTo("/views/home-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
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
