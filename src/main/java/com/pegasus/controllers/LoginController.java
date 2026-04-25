package com.pegasus.controllers;

import com.pegasus.services.LoginService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private LoginService loginService = new LoginService();

    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        String role = loginService.login(username, password);

        if (role == null) {
            errorLabel.setText("Identifiants  ou mot de passe incorrects !");
            return;
        }

        try {
            String fxml = role.equals("ROLE_ARTISTE") ? "/fxml/DashboardArtiste.fxml" : "/fxml/DashboardUser.fxml";
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
