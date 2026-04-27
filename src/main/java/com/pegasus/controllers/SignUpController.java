package com.pegasus.controllers;

import com.pegasus.entities.Admin;
import com.pegasus.entities.Artiste;
import com.pegasus.entities.NormalUser;
import com.pegasus.entities.User;
import com.pegasus.services.GoogleUserProfile;
import com.pegasus.services.EmailService;
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

    @FXML
    private Label signupMessageLabel;

    private ServiceUser serviceUser;
    private ServiceAdmin serviceAdmin;
    private ServiceArtiste serviceArtiste;
    private ServiceNormalUser serviceNormalUser;
    private EmailService emailService;

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
        clearSignupMessage();
        applyPendingGoogleProfile();
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
        clearSignupMessage();
        if (!initServices()) {
            return;
        }

        String role = SceneNavigator.getSelectedRole();
        if (role == null || role.isBlank()) {
            role = "NORMAL_USER";
        }

        String email = textOrNull(emailField.getText());
        String username = textOrNull(usernameField.getText());
        String password = passwordField.getText();
        String confirmPassword = getConfirmPassword();
        GoogleUserProfile googleProfile = SceneNavigator.getPendingGoogleUserProfile();
        boolean googleSignup = googleProfile != null;

        String validationError = validateSignupForm(email, username, password, confirmPassword, googleSignup);
        if (validationError != null) {
            showSignupError(validationError);
            return;
        }

        if (!googleSignup && !password.equals(confirmPassword)) {
            showSignupError("Password and confirm password do not match.");
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(googleSignup ? null : password);
        user.setUsername(username);
        user.setStatus(googleSignup ? ServiceUser.STATUS_ACTIVE : ServiceUser.STATUS_PENDING_VERIFICATION);
        user.setPhone(textOrNull(phoneField.getText()));
        user.setAvatarUrl(textOrNull(avatarUrlField.getText()));
        if (googleSignup) {
            user.setProvider("GOOGLE");
            user.setGoogleSub(googleProfile.sub());
            user.setEmailVerificationToken(null);
            user.setEmailVerificationTokenExpiresAt(null);
        } else {
            user.setProvider("LOCAL");
            user.setGoogleSub(null);
        }

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
            showSignupError(resolveServiceError(serviceUser.getLastError(), "Could not create user. Check required fields."));
            return;
        }

        if ("ADMIN".equals(role)) {
            Admin admin = new Admin();
            admin.setId(user.getId());
            admin.setSuperAdmin(adminSuperAdminCheckBox.isSelected());
            admin.setBirthDate(adminBirthDatePicker.getValue());
            serviceAdmin.ajouter(admin);
            if (serviceAdmin.getLastError() != null) {
                rollbackUser(user);
                showSignupError(serviceAdmin.getLastError());
                return;
            }
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
            if (serviceArtiste.getLastError() != null) {
                rollbackUser(user);
                showSignupError(serviceArtiste.getLastError());
                return;
            }
        } else {
            NormalUser normalUser = new NormalUser();
            normalUser.setId(user.getId());
            normalUser.setBirthDate(normalBirthDatePicker.getValue());
            serviceNormalUser.ajouter(normalUser);
            if (serviceNormalUser.getLastError() != null) {
                rollbackUser(user);
                showSignupError(serviceNormalUser.getLastError());
                return;
            }
        }

        if (!googleSignup) {
            try {
                if (emailService == null) {
                    emailService = new EmailService();
                }
                String verificationToken = serviceUser.createEmailVerificationToken(user);
                emailService.sendVerificationEmail(user.getEmail(), user.getUsername(), verificationToken);
            } catch (IllegalStateException e) {
                rollbackUser(user);
                showSignupError(e.getMessage());
                return;
            }
            SceneNavigator.setCurrentUser(null);
            SceneNavigator.clearPendingGoogleUserProfile();
        } else {
            SceneNavigator.setCurrentUser(user);
            SceneNavigator.clearPendingGoogleUserProfile();
        }

        showAlert(Alert.AlertType.INFORMATION, "Sign Up",
                googleSignup
                        ? "Account created for role: " + role
                        : "Account created. Check your email for the verification code before signing in.");
        clearForm();
        try {
            SceneNavigator.goTo(googleSignup ? "/views/home-view.fxml" : "/views/email-verification-view.fxml");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", googleSignup ? "Could not open home page." : "Could not open email verification page.");
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

    private String validateSignupForm(String email, String username, String password, String confirmPassword, boolean googleSignup) {
        if (email == null) {
            return "Email is required.";
        }
        if (username == null) {
            return "Username is required.";
        }
        if (!googleSignup && (password == null || password.isBlank())) {
            return "Password is required.";
        }
        if (!googleSignup && (confirmPassword == null || confirmPassword.isBlank())) {
            return "Confirm password is required.";
        }
        return null;
    }

    private void applyPendingGoogleProfile() {
        GoogleUserProfile googleProfile = SceneNavigator.getPendingGoogleUserProfile();
        if (googleProfile == null) {
            configurePasswordFields(true);
            return;
        }

        SceneNavigator.setSelectedRole("NORMAL_USER");
        roleLabel.setText("Role: NORMAL_USER (Google)");
        emailField.setText(nullToEmpty(googleProfile.email()));
        usernameField.setText(resolveGoogleUsername(googleProfile));
        avatarUrlField.setText(nullToEmpty(googleProfile.pictureUrl()));

        emailField.setDisable(true);
        configurePasswordFields(false);
    }

    private void configurePasswordFields(boolean visible) {
        passwordField.clear();
        confirmPasswordField.clear();
        confirmPasswordVisibleField.clear();
        passwordField.setManaged(visible);
        passwordField.setVisible(visible);
        passwordField.setDisable(!visible);
        confirmPasswordField.setManaged(visible);
        confirmPasswordField.setVisible(visible);
        confirmPasswordField.setDisable(!visible);
        confirmPasswordVisibleField.setManaged(false);
        confirmPasswordVisibleField.setVisible(false);
        confirmPasswordVisibleField.setDisable(true);
    }

    private String resolveGoogleUsername(GoogleUserProfile googleProfile) {
        String preferred = textOrNull(googleProfile.name());
        if (preferred != null) {
            return preferred;
        }
        String email = textOrNull(googleProfile.email());
        if (email == null) {
            return "";
        }
        int atIndex = email.indexOf('@');
        return atIndex > 0 ? email.substring(0, atIndex) : email;
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }

    private void clearForm() {
        clearSignupMessage();
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
            showSignupError(resolveServiceError(e.getMessage(), "Could not connect to database."));
            return false;
        }
    }

    private void showSignupError(String message) {
        signupMessageLabel.setText(message);
        signupMessageLabel.setVisible(true);
        signupMessageLabel.setManaged(true);
    }

    private void clearSignupMessage() {
        signupMessageLabel.setText("");
        signupMessageLabel.setVisible(false);
        signupMessageLabel.setManaged(false);
    }

    private String resolveServiceError(String serviceMessage, String fallbackMessage) {
        if (serviceMessage == null || serviceMessage.isBlank()) {
            return fallbackMessage;
        }
        return serviceMessage;
    }

    private void rollbackUser(User user) {
        if (user != null && user.getId() != null) {
            serviceUser.supprimer(user);
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
