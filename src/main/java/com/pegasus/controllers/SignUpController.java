package com.pegasus.controllers;

import com.pegasus.entities.Admin;
import com.pegasus.entities.Artiste;
import com.pegasus.entities.NormalUser;
import com.pegasus.entities.User;
import com.pegasus.services.ServiceAdmin;
import com.pegasus.services.ServiceArtiste;
import com.pegasus.services.ServiceNormalUser;
import com.pegasus.services.ServiceUser;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SignUpController {
    @FXML
    private Label roleLabel;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField confirmPasswordVisibleField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField avatarUrlField;

    @FXML
    private VBox adminBox;

    @FXML
    private CheckBox adminSuperAdminCheckBox;

    @FXML
    private DatePicker adminBirthDatePicker;

    @FXML
    private VBox artisteBox;

    @FXML
    private TextArea artisteBioArea;

    @FXML
    private TextField artisteStylesField;

    @FXML
    private TextField artisteFacebookField;

    @FXML
    private TextField artisteInstagramField;

    @FXML
    private TextField artistePortfolioUrlField;

    @FXML
    private CheckBox artisteVerifiedCheckBox;

    @FXML
    private DatePicker artisteBirthDatePicker;

    @FXML
    private VBox normalUserBox;

    @FXML
    private DatePicker normalBirthDatePicker;

    private ServiceUser serviceUser;
    private ServiceAdmin serviceAdmin;
    private ServiceArtiste serviceArtiste;
    private ServiceNormalUser serviceNormalUser;

    @FXML
    public void initialize() {
        String role = SceneNavigator.getSelectedRole();
        if (role == null || role.isBlank()) {
            role = "NORMAL_USER";
            SceneNavigator.setSelectedRole(role);
        }
        roleLabel.setText("Role: " + role);
        configureRoleSections(role);

        confirmPasswordVisibleField.setManaged(false);
        confirmPasswordVisibleField.setVisible(false);
    }

    public void onToggleConfirmPassword() {
        boolean showing = confirmPasswordVisibleField.isVisible();
        if (showing) {
            confirmPasswordField.setText(confirmPasswordVisibleField.getText());
            confirmPasswordVisibleField.setVisible(false);
            confirmPasswordVisibleField.setManaged(false);
            confirmPasswordField.setVisible(true);
            confirmPasswordField.setManaged(true);
        } else {
            confirmPasswordVisibleField.setText(confirmPasswordField.getText());
            confirmPasswordField.setVisible(false);
            confirmPasswordField.setManaged(false);
            confirmPasswordVisibleField.setVisible(true);
            confirmPasswordVisibleField.setManaged(true);
        }
    }

    public void onCreateAccount() {
        if (!initServices()) {
            return;
        }

        String role = SceneNavigator.getSelectedRole();
        String password = passwordField.getText();
        String confirmPassword = getConfirmPassword();

        if (password == null || !password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Sign Up", "Password and confirm password do not match.");
            return;
        }

        User user = new User();
        user.setEmail(emailField.getText());
        user.setPassword(password);
        user.setUsername(usernameField.getText());
        user.setStatus("ACTIVE");
        user.setPhone(textOrNull(phoneField.getText()));
        user.setAvatarUrl(textOrNull(avatarUrlField.getText()));

        if ("ADMIN".equals(role)) {
            user.setRoles("[\"ROLE_ADMIN\"]");
            user.setDtype("admin");
        } else if ("ARTISTE".equals(role)) {
            user.setRoles("[\"ROLE_ARTISTE\"]");
            user.setDtype("artiste");
        } else {
            user.setRoles("[\"ROLE_USER\"]");
            user.setDtype("normal_user");
        }

        serviceUser.ajouter(user);
        if (user.getId() == null) {
            showAlert(Alert.AlertType.ERROR, "Sign Up", "Could not create user. Check required fields.");
            return;
        }

        if ("ADMIN".equals(role)) {
            Admin admin = new Admin();
            admin.setId(user.getId());
            admin.setSuperAdmin(adminSuperAdminCheckBox.isSelected());
            admin.setBirthDate(adminBirthDatePicker.getValue());
            serviceAdmin.ajouter(admin);
        } else if ("ARTISTE".equals(role)) {
            Artiste artiste = new Artiste();
            artiste.setId(user.getId());
            artiste.setBio(textOrNull(artisteBioArea.getText()));
            artiste.setStyles(textOrNull(artisteStylesField.getText()));
            artiste.setFacebook(textOrNull(artisteFacebookField.getText()));
            artiste.setInstagram(textOrNull(artisteInstagramField.getText()));
            artiste.setPortfolioUrl(textOrNull(artistePortfolioUrlField.getText()));
            artiste.setVerified(artisteVerifiedCheckBox.isSelected());
            artiste.setBirthDate(artisteBirthDatePicker.getValue());
            serviceArtiste.ajouter(artiste);
        } else {
            NormalUser normalUser = new NormalUser();
            normalUser.setId(user.getId());
            normalUser.setBirthDate(normalBirthDatePicker.getValue());
            serviceNormalUser.ajouter(normalUser);
        }

        SceneNavigator.setCurrentUser(user);
        showAlert(Alert.AlertType.INFORMATION, "Sign Up", "Account created for role: " + role);
        clearForm();
        try {
            SceneNavigator.goTo("/views/home-view.fxml");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open home page.");
        }
    }

    private void configureRoleSections(String role) {
        setSectionVisible(adminBox, "ADMIN".equals(role));
        setSectionVisible(artisteBox, "ARTISTE".equals(role));
        setSectionVisible(normalUserBox, "NORMAL_USER".equals(role));
    }

    private void setSectionVisible(VBox section, boolean visible) {
        section.setVisible(visible);
        section.setManaged(visible);
    }

    private String getConfirmPassword() {
        return confirmPasswordVisibleField.isVisible()
                ? confirmPasswordVisibleField.getText()
                : confirmPasswordField.getText();
    }

    private String textOrNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }

    private void clearForm() {
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        confirmPasswordVisibleField.clear();
        confirmPasswordVisibleField.setVisible(false);
        confirmPasswordVisibleField.setManaged(false);
        confirmPasswordField.setVisible(true);
        confirmPasswordField.setManaged(true);
        usernameField.clear();
        phoneField.clear();
        avatarUrlField.clear();

        adminSuperAdminCheckBox.setSelected(false);
        adminBirthDatePicker.setValue(null);

        artisteBioArea.clear();
        artisteStylesField.clear();
        artisteFacebookField.clear();
        artisteInstagramField.clear();
        artistePortfolioUrlField.clear();
        artisteVerifiedCheckBox.setSelected(false);
        artisteBirthDatePicker.setValue(null);

        normalBirthDatePicker.setValue(null);
    }

    public void onBackToRoleSelection() {
        try {
            SceneNavigator.goTo("/views/role-selection-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onGoHome() {
        try {
            SceneNavigator.goTo("/views/home-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onGoToSignIn() {
        try {
            SceneNavigator.goTo("/views/signin-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onGoToSignUp() {
        // Already on sign-up page.
    }

    private boolean initServices() {
        try {
            if (serviceUser == null) {
                serviceUser = new ServiceUser();
            }
            if (serviceAdmin == null) {
                serviceAdmin = new ServiceAdmin();
            }
            if (serviceArtiste == null) {
                serviceArtiste = new ServiceArtiste();
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

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
