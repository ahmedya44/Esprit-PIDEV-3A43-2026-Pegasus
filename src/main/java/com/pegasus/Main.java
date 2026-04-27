package com.pegasus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.pegasus.controllers.SceneNavigator;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            System.out.println("Démarrage de l'application Pegasus...");
            
            // Initialiser le SceneNavigator
            SceneNavigator.init(primaryStage);
            
            // Charger la vue principale
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/home-view.fxml"));
            Parent root = loader.load();
            
            // Configurer la scène
            Scene scene = new Scene(root, 1200, 800);
            scene.getStylesheets().add(getClass().getResource("/styles/main-style.css").toExternalForm());
            
            // Configurer la fenêtre principale
            primaryStage.setTitle("Pegasus - Galerie d'Art");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);
            primaryStage.show();
            
            System.out.println("Application démarrée avec succès !");
            
        } catch (Exception e) {
            System.err.println("Erreur lors du démarrage: " + e.getMessage());
            e.printStackTrace();
            
            // Afficher une fenêtre d'erreur simple
            Stage errorStage = new Stage();
            errorStage.setTitle("Erreur de démarrage");
            javafx.scene.control.Label errorLabel = new javafx.scene.control.Label("Erreur: " + e.getMessage());
            javafx.scene.layout.VBox errorRoot = new javafx.scene.layout.VBox(errorLabel);
            errorRoot.setPadding(new javafx.geometry.Insets(20));
            Scene errorScene = new Scene(errorRoot, 400, 200);
            errorStage.setScene(errorScene);
            errorStage.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}