package com.pegasus.controllers;

import com.pegasus.entities.Admin;
import com.pegasus.entities.Artiste;
import com.pegasus.entities.NormalUser;
import com.pegasus.entities.User;
import com.pegasus.services.ServiceAdmin;
import com.pegasus.services.ServiceArtiste;
import com.pegasus.services.ServiceNormalUser;
import com.pegasus.services.ServiceUser;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class MainController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private ComboBox<String> roleCombo;

    private final ServiceUser serviceUser = new ServiceUser();
    private final ServiceAdmin serviceAdmin = new ServiceAdmin();
    private final ServiceArtiste serviceArtiste = new ServiceArtiste();
    private final ServiceNormalUser serviceNormalUser = new ServiceNormalUser();

    @FXML
    public void initialize() {
        roleCombo.setItems(FXCollections.observableArrayList("ADMIN", "ARTISTE", "NORMAL_USER"));
        roleCombo.getSelectionModel().selectFirst();
    }

    @FXML
    public void onTestClick() {
        String email = emailField.getText();
        String password = passwordField.getText();
        String username = usernameField.getText();
        String role = roleCombo.getValue();

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setUsername(username);
        user.setPhone(null);
        user.setAvatarUrl(null);
        user.setStatus("ACTIVE");

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
            showAlert(Alert.AlertType.ERROR, "Save Failed", "User insert failed. Check fields or DB connection.");
            return;
        }

        if ("ADMIN".equals(role)) {
            Admin admin = new Admin();
            admin.setId(user.getId());
            admin.setSuperAdmin(false);
            admin.setBirthDate(null);
            serviceAdmin.ajouter(admin);
        } else if ("ARTISTE".equals(role)) {
            Artiste artiste = new Artiste();
            artiste.setId(user.getId());
            artiste.setVerified(false);
            artiste.setBirthDate(null);
            serviceArtiste.ajouter(artiste);
        } else {
            NormalUser normalUser = new NormalUser();
            normalUser.setId(user.getId());
            normalUser.setBirthDate(null);
            serviceNormalUser.ajouter(normalUser);
        }

        showAlert(Alert.AlertType.INFORMATION, "Success", "User saved with role: " + role);
        emailField.clear();
        passwordField.clear();
        usernameField.clear();
        roleCombo.getSelectionModel().selectFirst();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
