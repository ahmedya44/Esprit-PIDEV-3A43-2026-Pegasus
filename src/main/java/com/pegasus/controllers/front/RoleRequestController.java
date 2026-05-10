package com.pegasus.controllers.front;

import com.pegasus.controllers.SceneNavigator;
import com.pegasus.entities.User;
import com.pegasus.services.RoleRequestService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RoleRequestController {
    @FXML private ComboBox<String> requestedRoleBox;
    @FXML private VBox artistFieldsBox;
    @FXML private VBox sponsorFieldsBox;

    @FXML private TextArea artistBioField;
    @FXML private TextField artistStylesField;
    @FXML private TextField artistPortfolioField;

    @FXML private TextField sponsorCompanyField;
    @FXML private TextField sponsorWebsiteField;
    @FXML private TextField sponsorAddressField;
    @FXML private TextArea sponsorDescriptionField;

    private final RoleRequestService roleRequestService = new RoleRequestService();

    @FXML
    public void initialize() {
        User currentUser = SceneNavigator.getCurrentUser();
        if (currentUser == null) {
            showAlert(Alert.AlertType.ERROR, "Session", "You need to sign in first.");
            onGoHome();
            return;
        }
        if (!"normal_user".equalsIgnoreCase(currentUser.getDtype())) {
            showAlert(Alert.AlertType.ERROR, "Role Request", "Only normal users can request a role change.");
            onBackToProfile();
            return;
        }
        requestedRoleBox.setItems(FXCollections.observableArrayList("artiste", "sponsor"));
        requestedRoleBox.getSelectionModel().select("artiste");
        onRoleSelectionChanged();
    }

    @FXML
    public void onRoleSelectionChanged() {
        String role = requestedRoleBox.getValue();
        boolean artist = "artiste".equalsIgnoreCase(role);
        artistFieldsBox.setVisible(artist);
        artistFieldsBox.setManaged(artist);
        sponsorFieldsBox.setVisible(!artist);
        sponsorFieldsBox.setManaged(!artist);
    }

    @FXML
    public void onSubmitRequest() {
        User currentUser = SceneNavigator.getCurrentUser();
        if (currentUser == null) {
            showAlert(Alert.AlertType.ERROR, "Session", "You need to sign in first.");
            return;
        }
        String requestedRole = requestedRoleBox.getValue();
        if (requestedRole == null || requestedRole.isBlank()) {
            showAlert(Alert.AlertType.ERROR, "Role Request", "Please choose a target role.");
            return;
        }

        Map<String, String> details = new HashMap<>();
        if ("artiste".equalsIgnoreCase(requestedRole)) {
            details.put("bio", clean(artistBioField.getText()));
            details.put("styles", clean(artistStylesField.getText()));
            details.put("portfolioUrl", clean(artistPortfolioField.getText()));
        } else {
            details.put("companyName", clean(sponsorCompanyField.getText()));
            details.put("website", clean(sponsorWebsiteField.getText()));
            details.put("address", clean(sponsorAddressField.getText()));
            details.put("description", clean(sponsorDescriptionField.getText()));
        }

        boolean ok = roleRequestService.createRequest(currentUser, requestedRole, details);
        if (!ok) {
            showAlert(Alert.AlertType.ERROR, "Role Request", roleRequestService.getLastError());
            return;
        }
        showAlert(Alert.AlertType.INFORMATION, "Role Request", "Request submitted successfully. Please wait for admin review.");
        onBackToProfile();
    }

    @FXML
    public void onBackToProfile() {
        try {
            SceneNavigator.goTo("/views/front/profile-view.fxml");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open profile page.");
        }
    }

    @FXML
    public void onGoHome() {
        try {
            SceneNavigator.goTo("/views/front/home-view.fxml");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not open home page.");
        }
    }

    private String clean(String value) {
        return value == null ? "" : value.trim();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        SceneNavigator.showSnackbar(title, content, type == Alert.AlertType.ERROR);
    }
}
