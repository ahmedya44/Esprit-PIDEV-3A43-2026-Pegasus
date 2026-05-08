package com.pegasus.controllers.back;

import com.pegasus.controllers.SceneNavigator;
import com.pegasus.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminSettingsController {
    @FXML private Label adminNameLabel;
    @FXML private Label adminEmailLabel;
    @FXML private Label adminStatusLabel;

    @FXML
    public void initialize() {
        User currentUser = SceneNavigator.getCurrentUser();
        if (currentUser == null) {
            adminNameLabel.setText("No active admin session");
            adminEmailLabel.setText("-");
            adminStatusLabel.setText("-");
            return;
        }
        adminNameLabel.setText(nullToDash(currentUser.getUsername()));
        adminEmailLabel.setText(nullToDash(currentUser.getEmail()));
        adminStatusLabel.setText(nullToDash(currentUser.getStatus()));
    }

    private String nullToDash(String value) {
        return value == null || value.isBlank() ? "-" : value;
    }
}
