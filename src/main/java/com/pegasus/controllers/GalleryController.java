package com.pegasus.controllers;

import com.pegasus.entities.Art;
import com.pegasus.services.ServiceArt;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class GalleryController {
    
    @FXML
    private TilePane galleryGrid;
    
    private ServiceArt serviceArt = new ServiceArt();
    
    @FXML
    public void initialize() {
        loadArtGallery();
    }
    
    private void loadArtGallery() {
        try {
            List<Art> artworks = serviceArt.getAllArts();
            
            for (Art art : artworks) {
                galleryGrid.getChildren().add(createArtCard(art));
            }
            
            // Add some sample items if no arts found
            if (artworks.isEmpty()) {
                loadSampleGallery();
            }
            
        } catch (Exception e) {
            System.err.println("Error loading gallery: " + e.getMessage());
            e.printStackTrace();
            // Load sample gallery on error
            loadSampleGallery();
        }
    }
    
    private void loadSampleGallery() {
        String[] sampleImages = {
            "art1.jpg", "art2.jpg", "art3.jpg", "art4.jpg",
            "art5.jpg", "art6.jpg", "art7.jpg", "art8.jpg"
        };
        
        for (int i = 0; i < sampleImages.length; i++) {
            galleryGrid.getChildren().add(createSampleArtCard("Artwork " + (i + 1), sampleImages[i]));
        }
    }
    
    private VBox createArtCard(Art art) {
        VBox card = new VBox();
        card.getStyleClass().add("art-card");
        card.setSpacing(15);
        card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12; -fx-padding: 20px; -fx-border-color: #e9ecef; -fx-border-width: 1; -fx-border-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 12, 0, 0, 4);");
        card.setPrefSize(280, 340);
        
        // Art Image avec design moderne
        ImageView imageView = new ImageView();
        imageView.setFitHeight(180);
        imageView.setFitWidth(240);
        imageView.setPreserveRatio(true);
        imageView.setStyle("-fx-background-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 4);");
        
        // Use placeholder for now
        try {
            imageView.setImage(new Image(getClass().getResourceAsStream("/images/placeholder.jpg")));
        } catch (Exception e) {
            // Skip image if not found
        }
        
        imageView.getStyleClass().add("art-image");
        
        // Art Title avec design moderne
        Label titleLabel = new Label(art.getTitle());
        titleLabel.getStyleClass().add("art-title");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-font-family: 'Segoe UI', Arial, sans-serif; -fx-wrap-text: true;");
        titleLabel.setWrapText(true);
        
        // Art Status avec design moderne
        Label statusLabel = new Label(art.getStatus());
        statusLabel.getStyleClass().add("art-status");
        statusLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d; -fx-font-family: 'Segoe UI', Arial, sans-serif; -fx-font-style: italic;");
        
        card.getChildren().addAll(imageView, titleLabel, statusLabel);
        
        // Add click handler to view details
        card.setOnMouseClicked(e -> handleArtClick(art));
        
        return card;
    }
    
    private VBox createSampleArtCard(String title, String imageName) {
        VBox card = new VBox();
        card.getStyleClass().add("art-card");
        card.setSpacing(10);
        
        // Art Image
        ImageView imageView = new ImageView();
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        
        try {
            imageView.setImage(new Image(getClass().getResourceAsStream("/images/placeholder.jpg")));
        } catch (Exception e) {
            // Skip image if not found
        }
        
        imageView.getStyleClass().add("art-image");
        
        // Art Title
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("art-title");
        titleLabel.setWrapText(true);
        
        // Art Status
        Label statusLabel = new Label("Available");
        statusLabel.getStyleClass().add("art-status");
        
        card.getChildren().addAll(imageView, titleLabel, statusLabel);
        
        // Add click handler
        card.setOnMouseClicked(e -> handleSampleArtClick(title));
        
        return card;
    }
    
    private void handleArtClick(Art art) {
        System.out.println("Clicked on art: " + art.getTitle());
        // TODO: Open art detail view or modal
        showArtDetails(art);
    }
    
    private void handleSampleArtClick(String title) {
        System.out.println("Clicked on sample art: " + title);
        // TODO: Open sample art detail view or modal
        showSampleArtDetails(title);
    }
    
    private void showArtDetails(Art art) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/art-detail-view.fxml"));
            Parent root = loader.load();
            
            // Pass art data to detail controller
            ArtDetailController controller = loader.getController();
            controller.setArt(art);
            
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Art Details - " + art.getTitle());
            stage.show();
            
        } catch (IOException e) {
            System.err.println("Error loading art details: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void showSampleArtDetails(String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Art Details");
        alert.setHeaderText("Sample Artwork");
        alert.setContentText("This is a sample artwork: " + title);
        alert.showAndWait();
    }
    
    @FXML
    private void handleBackToMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/menu-view.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) galleryGrid.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Menu");
            stage.show();
            
        } catch (IOException e) {
            System.err.println("Error loading menu view: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
