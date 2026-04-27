package com.pegasus.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class HomeController {
    
    @FXML
    private Button frontButton;
    
    @FXML
    private Button backButton;
    
    @FXML
    public void initialize() {
        System.out.println("HomeController initialisé - Page d'accueil avec choix FRONT/BACK");
    }
    
    @FXML
    public void goToFrontOffice() {
        try {
            System.out.println("Navigation vers le FRONT OFFICE (Gallery)");
            SceneNavigator.goTo("/views/gallery-main-view.fxml");
        } catch (IOException e) {
            System.err.println("Erreur lors de la navigation vers le front office: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    public void goToBackOffice() {
        try {
            System.out.println("Navigation vers le BACK OFFICE");
            SceneNavigator.goTo("/views/backoffice-simple.fxml");
        } catch (IOException e) {
            System.err.println("Erreur lors de la navigation vers le back office: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
