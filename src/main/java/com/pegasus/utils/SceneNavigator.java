package com.pegasus.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneNavigator {
    
    public static void goTo(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneNavigator.class.getResource(fxmlPath));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            
            // Essayer d'obtenir la fenêtre actuelle
            try {
                Stage stage = (Stage) scene.getWindow();
                if (stage != null) {
                    stage.setScene(scene);
                    stage.show();
                }
            } catch (Exception e) {
                // Créer une nouvelle fenêtre si nécessaire
                Stage newStage = new Stage();
                newStage.setScene(scene);
                newStage.setTitle("Pegasus Gallery");
                newStage.show();
            }
            
        } catch (IOException e) {
            System.err.println("Error loading FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }
}
