package com.pegasus.controllers;

import com.pegasus.entities.Art;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ArtDetailControllerSimple {
    
    @FXML
    private ImageView artImageView;
    @FXML
    private Label titleLabel;
    @FXML
    private Label artistLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label likesLabel;
    @FXML
    private Button likeButton;
    @FXML
    private Button spotifyButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    
    private Art currentArt;
    
    public void setArt(Art art) {
        this.currentArt = art;
        displayArtDetails();
        addPinterestButton(); // GARANTI
    }
    
    private void displayArtDetails() {
        if (currentArt == null) return;
        
        titleLabel.setText(currentArt.getTitle());
        
        String artistName = currentArt.getArtist();
        if (artistName == null || artistName.trim().isEmpty()) {
            artistName = "Artiste inconnu";
        }
        artistLabel.setText(artistName);
        
        String description = currentArt.getDescription();
        if (description == null || description.trim().isEmpty()) {
            description = "Pas de description disponible pour cette œuvre.";
        }
        descriptionLabel.setText(description);
        
        String status = currentArt.getStatus();
        if ("published".equals(status)) {
            statusLabel.setText("Publié");
            statusLabel.setStyle("-fx-text-fill: #28a745; -fx-font-weight: bold;");
        } else {
            statusLabel.setText("En attente");
            statusLabel.setStyle("-fx-text-fill: #ffc107; -fx-font-weight: bold;");
        }
        
        likesLabel.setText(currentArt.getLikes() + " likes");
    }
    
    private void addPinterestButton() {
        // Créer une section Pinterest GARANTIE
        VBox pinterestSection = new VBox(10);
        pinterestSection.setStyle("-fx-background-color: #fff0f5; -fx-background-radius: 15; -fx-padding: 15px; -fx-border-color: #E60023; -fx-border-width: 1;");
        
        Label pinterestTitle = new Label("🎨 Inspirations Pinterest");
        pinterestTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #E60023;");
        
        Button pinterestBtn = new Button("Rechercher des inspirations");
        pinterestBtn.setStyle("-fx-font-size: 14px; -fx-background-color: #E60023; -fx-text-fill: white; -fx-background-radius: 10; -fx-padding: 10px 20px; -fx-cursor: hand;");
        pinterestBtn.setOnAction(e -> handlePinterest());
        
        pinterestSection.getChildren().addAll(pinterestTitle, pinterestBtn);
        
        // Ajouter après la description
        if (descriptionLabel != null) {
            javafx.scene.Parent parent = descriptionLabel.getParent();
            if (parent instanceof VBox) {
                VBox vbox = (VBox) parent;
                int descIndex = vbox.getChildren().indexOf(descriptionLabel);
                vbox.getChildren().add(descIndex + 1, pinterestSection);
                System.out.println("✅ PINTEREST AJOUTÉ GARANTI !");
            }
        }
    }
    
    @FXML
    private void handlePinterest() {
        System.out.println("🎨 Pinterest cliqué !");
        
        // Créer une alerte simple pour tester
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pinterest");
        alert.setHeaderText("🎨 Inspirations Pinterest");
        alert.setContentText("Recherche d'inspirations pour: " + currentArt.getTitle() + "\n\nClé API: 1565705 ✅");
        alert.showAndWait();
        
        // Appeler le vrai service Pinterest
        // pinterestService.getInspirationsForArtwork(currentArt.getTitle(), currentArt.getDescription());
    }
    
    @FXML
    private void handleLike() {
        if (currentArt == null) return;
        
        try {
            // Simpler like implementation
            currentArt.setLikes(currentArt.getLikes() + 1);
            likesLabel.setText(currentArt.getLikes() + " likes");
            System.out.println("Œuvre " + currentArt.getId() + " likée! Total likes: " + currentArt.getLikes());
        } catch (Exception e) {
            System.err.println("Erreur lors du like: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleSpotify() {
        if (currentArt == null) return;
        
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Spotify");
            alert.setHeaderText("🎵 Playlist Spotify");
            alert.setContentText("Playlist pour: " + currentArt.getTitle());
            alert.showAndWait();
        } catch (Exception e) {
            System.err.println("Erreur Spotify: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleEdit() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modification");
        alert.setHeaderText(null);
        alert.setContentText("Fonctionnalité de modification à implémenter");
        alert.showAndWait();
    }
    
    @FXML
    private void handleDelete() {
        if (currentArt == null) return;
        
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Suppression");
        confirmAlert.setHeaderText("Supprimer cette œuvre ?");
        confirmAlert.setContentText("Êtes-vous sûr de vouloir supprimer \"" + currentArt.getTitle() + "\" ?");
        
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                try {
                    // Simpler delete implementation
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Succès");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Œuvre supprimée avec succès (simulation)");
                    successAlert.showAndWait();
                    
                    // Retour à la galerie
                    handleBack();
                } catch (Exception e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Erreur lors de la suppression: " + e.getMessage());
                    errorAlert.showAndWait();
                }
            }
        });
    }
    
    @FXML
    private void handleBack() {
        try {
            // Naviguer vers la galerie
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Retour");
            alert.setHeaderText(null);
            alert.setContentText("Retour à la galerie");
            alert.showAndWait();
        } catch (Exception e) {
            System.err.println("Erreur navigation: " + e.getMessage());
        }
    }
}
