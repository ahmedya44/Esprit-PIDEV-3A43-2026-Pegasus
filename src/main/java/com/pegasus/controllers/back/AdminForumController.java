package com.pegasus.controllers.back;

import com.pegasus.controllers.front.ForumModuleLauncher;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminForumController {
    @FXML private Label statusLabel;

    @FXML
    public void openForumModeration() {
        ForumModuleLauncher.openForumWindow();
        if (statusLabel != null) {
            statusLabel.setText("Forum workspace opened in its dedicated window.");
        }
    }
}
