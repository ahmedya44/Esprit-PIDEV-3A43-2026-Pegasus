package com.pegasus.controllers;

import com.pegasus.entities.User;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public final class SceneNavigator {
    private static Stage stage;
    private static String selectedRole;
    private static User currentUser;

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
        
        System.out.println("Chargement du FXML: " + fxmlPath);
        System.out.println("Chemin de ressource: " + SceneNavigator.class.getResource(fxmlPath));
        
        if (SceneNavigator.class.getResource(fxmlPath) == null) {
            throw new IOException("Fichier FXML non trouvé: " + fxmlPath);
        }
        
        Parent root = FXMLLoader.load(SceneNavigator.class.getResource(fxmlPath));
        applyButtonAnimations(root);
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.getStyleClass().add("app-scroll");
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);
        scrollPane.setPannable(true);
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(scrollPane, 800, 520);
            scene.getStylesheets().add(SceneNavigator.class.getResource("/styles/theme.css").toExternalForm());
            scene.getStylesheets().add(SceneNavigator.class.getResource("/styles/menu-style.css").toExternalForm());
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

    public static void goToGallery() throws IOException {
        goTo("/views/gallery-main-view.fxml");
    }

    public static void clearSession() {
        currentUser = null;
    }
}
