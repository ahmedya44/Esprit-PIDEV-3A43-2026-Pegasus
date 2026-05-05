package com.pegasus.controllers;

import com.pegasus.entities.User;
import com.pegasus.services.GoogleUserProfile;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public final class SceneNavigator {
    private static Stage stage;
    private static String selectedRole;
    private static User currentUser;
    private static GoogleUserProfile pendingGoogleUserProfile;

    private SceneNavigator() {
    }

    public static void init(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Pegasus");
    }

    public static void goTo(String fxmlPath) throws IOException {
        if (stage == null) {
            throw new IllegalStateException("SceneNavigator is not initialized.");
        }
        URL fxmlUrl = SceneNavigator.class.getResource(fxmlPath);
        if (fxmlUrl == null) {
            throw new IOException("FXML not found: " + fxmlPath);
        }

        Parent root;
        try {
            root = FXMLLoader.load(fxmlUrl);
        } catch (Exception e) {
            Throwable rootCause = rootCauseOf(e);
            String message = "Failed to load " + fxmlPath
                    + "\nCause: " + rootCause.getClass().getSimpleName()
                    + " - " + (rootCause.getMessage() == null ? "(no details)" : rootCause.getMessage());
            throw new IOException(message, e);
        }

        applyButtonAnimations(root);
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.getStyleClass().add("app-scroll");
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPannable(true);
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(scrollPane, 800, 520);
            scene.getStylesheets().add(SceneNavigator.class.getResource("/styles/theme.css").toExternalForm());
            stage.setScene(scene);
        } else {
            scene.setRoot(scrollPane);
        }
        stage.show();
    }

    private static void applyButtonAnimations(Parent root) {
        root.lookupAll(".button").forEach(node -> {
            if (!(node instanceof ButtonBase)) {
                return;
            }

            node.setOnMouseEntered(event -> animateScale(node, 1.05, 120));
            node.setOnMouseExited(event -> animateScale(node, 1.0, 120));
            node.setOnMousePressed(event -> animateScale(node, 0.96, 70));
            node.setOnMouseReleased(event -> animateScale(node, 1.05, 90));
        });
    }

    private static void animateScale(javafx.scene.Node node, double targetScale, int millis) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(millis), node);
        transition.setToX(targetScale);
        transition.setToY(targetScale);
        transition.play();
    }

    private static Throwable rootCauseOf(Throwable throwable) {
        Throwable current = throwable;
        while (current.getCause() != null && current.getCause() != current) {
            current = current.getCause();
        }
        return current;
    }

    public static String getSelectedRole() {
        return selectedRole;
    }

    public static void setSelectedRole(String role) {
        selectedRole = role;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static void clearSession() {
        currentUser = null;
    }

    public static GoogleUserProfile getPendingGoogleUserProfile() {
        return pendingGoogleUserProfile;
    }

    public static void setPendingGoogleUserProfile(GoogleUserProfile profile) {
        pendingGoogleUserProfile = profile;
    }

    public static void clearPendingGoogleUserProfile() {
        pendingGoogleUserProfile = null;
    }
}
