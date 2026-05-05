package com.pegasus.controllers;

import com.pegasus.entities.Art;
import com.pegasus.services.ServiceArt;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class GalleryControllerFinal {

    @FXML
    private VBox galleryContainer;

    private final ServiceArt serviceArt = new ServiceArt();

    @FXML
    public void initialize() {
        loadGallery();
    }

    private void loadGallery() {
        if (galleryContainer == null) {
            return;
        }
        galleryContainer.getChildren().clear();
        try {
            List<Art> artworks = serviceArt.getAllArts();
            if (artworks == null || artworks.isEmpty()) {
                galleryContainer.getChildren().add(new Label("No artworks available."));
                return;
            }

            for (Art art : artworks) {
                if (!"published".equalsIgnoreCase(art.getStatus())) {
                    continue;
                }
                Label line = new Label(art.getTitle() + " - " + (art.getArtist() == null ? "Unknown artist" : art.getArtist()));
                line.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50; -fx-padding: 8 0 8 0;");
                galleryContainer.getChildren().add(line);
            }

            if (galleryContainer.getChildren().isEmpty()) {
                galleryContainer.getChildren().add(new Label("No published artworks found."));
            }
        } catch (Exception e) {
            galleryContainer.getChildren().add(new Label("Error loading gallery: " + e.getMessage()));
        }
    }

    @FXML
    private void handleBack() {
        try {
            SceneNavigator.goTo("/views/menu-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goHome() {
        try {
            SceneNavigator.goTo("/views/home-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
