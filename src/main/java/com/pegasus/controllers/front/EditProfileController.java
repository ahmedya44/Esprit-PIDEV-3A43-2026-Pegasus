package com.pegasus.controllers.front;

import com.pegasus.controllers.SceneNavigator;
import com.pegasus.entities.User;
import com.pegasus.services.ServiceUser;
import com.pegasus.tools.dbConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class EditProfileController {
    @FXML
    private Label emailField;

    @FXML
    private Label roleField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField phoneField;
    @FXML
    private VBox artistFieldsBox;
    @FXML
    private TextField artistBioField;
    @FXML
    private TextField artistStylesField;
    @FXML
    private TextField artistPortfolioField;
    @FXML
    private TextField artistFacebookField;
    @FXML
    private TextField artistInstagramField;

    @FXML
    private VBox sponsorFieldsBox;
    @FXML
    private TextField sponsorCompanyNameField;
    @FXML
    private TextField sponsorWebsiteField;
    @FXML
    private TextField sponsorAddressField;
    @FXML
    private TextField sponsorDescriptionField;

    @FXML
    private VBox normalUserFieldsBox;
    @FXML
    private DatePicker normalUserBirthDatePicker;

    private ServiceUser serviceUser;
    private User currentUser;

    @FXML
    public void initialize() {
        currentUser = SceneNavigator.getCurrentUser();
        if (currentUser == null) {
            showAlert(Alert.AlertType.ERROR, "Session", "You need to sign in first.");
            goToProfileSafe();
            return;
        }

        if (!initService()) {
            return;
        }

        if (currentUser.getId() != null) {
            User refreshed = serviceUser.findById(currentUser.getId());
            if (refreshed != null) {
                currentUser = refreshed;
                SceneNavigator.setCurrentUser(refreshed);
            }
        }

        fillForm(currentUser);
        configureRoleSections(currentUser);
        loadRoleSpecificFields();
    }

    public void onSaveProfile() {
        if (currentUser == null || !initService()) {
            return;
        }
        String validationError = validateRoleSpecificFields();
        if (validationError != null) {
            showAlert(Alert.AlertType.ERROR, "Edit Profile", validationError);
            return;
        }

        User updated = new User();
        updated.setId(currentUser.getId());
        updated.setEmail(currentUser.getEmail());
        updated.setRoles(currentUser.getRoles());
        updated.setDtype(currentUser.getDtype());
        updated.setStatus(currentUser.getStatus());
        updated.setUsername(usernameField.getText());
        updated.setPhone(textOrNull(phoneField.getText()));
        updated.setAvatarUrl(currentUser.getAvatarUrl());
        updated.setPassword(currentUser.getPassword());

        serviceUser.modifier(updated);
        saveRoleSpecificFields();

        User refreshed = serviceUser.findById(currentUser.getId());
        if (refreshed != null) {
            currentUser = refreshed;
            SceneNavigator.setCurrentUser(refreshed);
            fillForm(refreshed);
            configureRoleSections(refreshed);
            loadRoleSpecificFields();
        }

        showAlert(Alert.AlertType.INFORMATION, "Edit Profile", "Profile updated successfully.");
    }

    public void onBackHome() {
        try {
            SceneNavigator.goTo("/views/front/home-view.fxml");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open home page.");
        }
    }

    public void onBackToProfile() {
        goToProfileSafe();
    }

    public void onLogout() {
        SceneNavigator.logoutToFrontHome();
    }

    public void onGoToResetPasswordPage() {
        try {
            SceneNavigator.goTo("/views/front/profile-password-view.fxml");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open reset password page.");
        }
    }

    private void fillForm(User user) {
        emailField.setText(user.getEmail());
        roleField.setText(user.getDtype());
        usernameField.setText(user.getUsername());
        phoneField.setText(user.getPhone());
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

    private void configureRoleSections(User user) {
        String dtype = user == null ? "" : safeLower(user.getDtype());
        setSectionVisible(artistFieldsBox, "artiste".equals(dtype));
        setSectionVisible(sponsorFieldsBox, "sponsor".equals(dtype));
        setSectionVisible(normalUserFieldsBox, "normal_user".equals(dtype));
    }

    private void setSectionVisible(VBox section, boolean visible) {
        if (section == null) {
            return;
        }
        section.setVisible(visible);
        section.setManaged(visible);
    }

    private void loadRoleSpecificFields() {
        if (currentUser == null || currentUser.getId() == null) {
            return;
        }
        String dtype = safeLower(currentUser.getDtype());
        try (Connection connection = dbConnection.getConnection()) {
            if ("artiste".equals(dtype)) {
                loadArtistFields(connection, currentUser.getId());
            } else if ("sponsor".equals(dtype)) {
                loadSponsorFields(connection, currentUser.getId());
            } else if ("normal_user".equals(dtype)) {
                loadNormalUserFields(connection, currentUser.getId());
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Edit Profile", "Could not load role-specific fields.");
        }
    }

    private void loadArtistFields(Connection connection, Integer userId) throws Exception {
        String sql = "SELECT bio, styles, facebook, instagram, portfolio_url FROM artiste WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    artistBioField.setText(nullToEmpty(rs.getString("bio")));
                    artistStylesField.setText(nullToEmpty(rs.getString("styles")));
                    artistFacebookField.setText(nullToEmpty(rs.getString("facebook")));
                    artistInstagramField.setText(nullToEmpty(rs.getString("instagram")));
                    artistPortfolioField.setText(nullToEmpty(rs.getString("portfolio_url")));
                }
            }
        }
    }

    private void loadSponsorFields(Connection connection, Integer userId) throws Exception {
        String sql = "SELECT company_name, website, address, description FROM sponsor WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    sponsorCompanyNameField.setText(nullToEmpty(rs.getString("company_name")));
                    sponsorWebsiteField.setText(nullToEmpty(rs.getString("website")));
                    sponsorAddressField.setText(nullToEmpty(rs.getString("address")));
                    sponsorDescriptionField.setText(nullToEmpty(rs.getString("description")));
                }
            }
        }
    }

    private void loadNormalUserFields(Connection connection, Integer userId) throws Exception {
        String sql = "SELECT birth_date FROM normal_user WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next() && rs.getDate("birth_date") != null) {
                    normalUserBirthDatePicker.setValue(rs.getDate("birth_date").toLocalDate());
                }
            }
        }
    }

    private void saveRoleSpecificFields() {
        if (currentUser == null || currentUser.getId() == null) {
            return;
        }
        String dtype = safeLower(currentUser.getDtype());
        try (Connection connection = dbConnection.getConnection()) {
            if ("artiste".equals(dtype)) {
                String sql = "UPDATE artiste SET bio=?, styles=?, facebook=?, instagram=?, portfolio_url=? WHERE id=?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, textOrNull(artistBioField.getText()));
                    statement.setString(2, textOrNull(artistStylesField.getText()));
                    statement.setString(3, textOrNull(artistFacebookField.getText()));
                    statement.setString(4, textOrNull(artistInstagramField.getText()));
                    statement.setString(5, textOrNull(artistPortfolioField.getText()));
                    statement.setInt(6, currentUser.getId());
                    statement.executeUpdate();
                }
            } else if ("sponsor".equals(dtype)) {
                String sql = "UPDATE sponsor SET company_name=?, website=?, address=?, description=? WHERE id=?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, textOrNull(sponsorCompanyNameField.getText()));
                    statement.setString(2, textOrNull(sponsorWebsiteField.getText()));
                    statement.setString(3, textOrNull(sponsorAddressField.getText()));
                    statement.setString(4, textOrNull(sponsorDescriptionField.getText()));
                    statement.setInt(5, currentUser.getId());
                    statement.executeUpdate();
                }
            } else if ("normal_user".equals(dtype)) {
                String sql = "UPDATE normal_user SET birth_date=? WHERE id=?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    LocalDate birthDate = normalUserBirthDatePicker.getValue();
                    if (birthDate == null) {
                        statement.setDate(1, null);
                    } else {
                        statement.setDate(1, Date.valueOf(birthDate));
                    }
                    statement.setInt(2, currentUser.getId());
                    statement.executeUpdate();
                }
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Edit Profile", "Could not save role-specific fields.");
        }
    }

    private String validateRoleSpecificFields() {
        // Role-specific fields are optional and can be null.
        return null;
    }

    private String safeLower(String value) {
        return value == null ? "" : value.trim().toLowerCase();
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
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

    private void goToProfileSafe() {
        try {
            SceneNavigator.goTo("/views/front/profile-view.fxml");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open profile page.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        boolean isError = type == Alert.AlertType.ERROR;
        SceneNavigator.showSnackbar(title, content, isError);
    }
}
