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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.regex.Pattern;

public class SignUpController {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\+?[0-9]{8,15}$");

    @FXML
    private Label roleLabel;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField passwordVisibleField;

    @FXML
    private Button togglePasswordButton;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField confirmPasswordVisibleField;

    @FXML
    private Button toggleConfirmPasswordButton;

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

        passwordVisibleField.setManaged(false);
        passwordVisibleField.setVisible(false);
        confirmPasswordVisibleField.setManaged(false);
        confirmPasswordVisibleField.setVisible(false);
        clearSignupMessage();
        applyPendingGoogleProfile();
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

    public void onCreateAccount() {
        clearSignupMessage();
        String email = safeTrim(emailField.getText());
        String username = safeTrim(usernameField.getText());
        String password = getPassword();
        String confirmPassword = getConfirmPassword();
        String phone = textOrNull(phoneField.getText());
        String avatarUrl = textOrNull(avatarUrlField.getText());
        if (!initServices()) {
            return;
        }

        String role = SceneNavigator.getSelectedRole();
        if (role == null || role.isBlank()) {
            role = "NORMAL_USER";
        }

        GoogleUserProfile googleProfile = SceneNavigator.getPendingGoogleUserProfile();
        boolean googleSignup = googleProfile != null;

        String validationError = validateSignupForm(email, username, password, confirmPassword, googleSignup);
        if (validationError != null) {
            showSignupError(validationError);
            return;
        }

        if (!isValidEmail(email)) {
            showSignupError("Please enter a valid email format. Example: name@example.com");
            return;
        }
        if (!googleSignup && !isValidPassword(password)) {
            showSignupError("Password must be at least 8 characters and include letters and numbers.");
            return;
        }
        if (!googleSignup && !password.equals(confirmPassword)) {
            showSignupError("Password and confirm password do not match.");
            return;
        }
        if (phone != null && !isValidPhone(phone)) {
            showSignupError("Phone must be 8 to 15 digits (optional leading +).");
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(googleSignup ? null : password);
        user.setUsername(username);
        user.setStatus(googleSignup ? ServiceUser.STATUS_ACTIVE : ServiceUser.STATUS_PENDING_VERIFICATION);
        user.setPhone(phone);
        user.setAvatarUrl(avatarUrl);
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

    private String getPassword() {
        return passwordVisibleField.isVisible()
                ? passwordVisibleField.getText()
                : passwordField.getText();
    }

    private String textOrNull(String value) {
        String clean = safeTrim(value);
        if (clean == null || clean.isEmpty()) {
            return null;
        }
        return clean;
    }

    private String safeTrim(String value) {
        return value == null ? null : value.trim();
    }

    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private boolean isValidPhone(String phone) {
        return PHONE_PATTERN.matcher(phone).matches();
    }

    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean hasLetter = password.matches(".*[A-Za-z].*");
        boolean hasDigit = password.matches(".*[0-9].*");
        return hasLetter && hasDigit;
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
        passwordVisibleField.clear();
        passwordVisibleField.setVisible(false);
        passwordVisibleField.setManaged(false);
        passwordField.setVisible(true);
        passwordField.setManaged(true);
        togglePasswordButton.setText("Show");
        confirmPasswordField.clear();
        confirmPasswordVisibleField.clear();
        confirmPasswordVisibleField.setVisible(false);
        confirmPasswordVisibleField.setManaged(false);
        confirmPasswordField.setVisible(true);
        confirmPasswordField.setManaged(true);
        toggleConfirmPasswordButton.setText("Show");
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
