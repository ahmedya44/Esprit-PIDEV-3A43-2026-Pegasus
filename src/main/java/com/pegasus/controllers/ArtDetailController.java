package com.pegasus.controllers;

import com.pegasus.entities.Art;
import com.pegasus.services.ServiceArt;
import com.pegasus.services.ServiceArtLike;
import com.pegasus.services.SpotifyService;
import com.pegasus.services.RecommendationService;
import com.pegasus.services.QuotesService;
// import com.pegasus.services.TranslationService; // Désactivé - fonctionnalité de traduction supprimée
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ArtDetailController {
    
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
    
        
    @FXML
    private TilePane recommendationsTilePane;
    
        
    private Art currentArt;
    private ServiceArt serviceArt = new ServiceArt();
    private ServiceArtLike serviceArtLike = new ServiceArtLike();
    private SpotifyService spotifyService = new SpotifyService();
    private RecommendationService recommendationService = new RecommendationService();
    private QuotesService quotesService = new QuotesService();
    // TranslationService désactivé - fonctionnalité de traduction supprimée
    // private TranslationService translationService = new TranslationService();
    private String sessionId = "session_" + System.currentTimeMillis() + "_new"; // Session simple
    
    public void setArt(Art art) {
        this.currentArt = art;
        displayArtDetails();
        loadRecommendations();
        
            }
    
    private void displayArtDetails() {
        if (currentArt == null) return;
        
        // Title
        titleLabel.setText(currentArt.getTitle());
        
        // Artist
        String artistName = currentArt.getArtist();
        if (artistName == null || artistName.trim().isEmpty()) {
            artistName = "Artiste inconnu";
        }
        artistLabel.setText(artistName);
        
        // Description
        String description = currentArt.getDescription();
        if (description == null || description.trim().isEmpty()) {
            description = "Pas de description disponible pour cette œuvre.";
        }
        descriptionLabel.setText(description);
        
                
        // Status
        String status = currentArt.getStatus();
        if ("published".equals(status)) {
            statusLabel.setText("Publié");
            statusLabel.setStyle("-fx-text-fill: #28a745; -fx-font-weight: bold;");
        } else {
            statusLabel.setText("En attente");
            statusLabel.setStyle("-fx-text-fill: #ffc107; -fx-font-weight: bold;");
        }
        
        // Date
        if (currentArt.getCreatedAt() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            dateLabel.setText(currentArt.getCreatedAt().format(formatter));
        } else {
            dateLabel.setText("Date inconnue");
        }
        
        // Likes
        updateLikesDisplay();
        
        // Image
        loadImage();
        
        // Check if already liked
        updateLikeButtonState();
    }
    
    private void loadImage() {
        try {
            String imageUrl = currentArt.getImageUrl();
            if (imageUrl != null && !imageUrl.isEmpty() && !imageUrl.contains("testttt")) {
                Image image = new Image(imageUrl, true);
                if (!image.isError()) {
                    artImageView.setImage(image);
                    return;
                }
            }
            
            // Fallback: try to load from resources
            try {
                Image defaultImage = new Image("/images/default-artwork.jpg", true);
                if (!defaultImage.isError()) {
                    artImageView.setImage(defaultImage);
                }
            } catch (Exception e) {
                // Final fallback: colored background
                artImageView.setStyle("-fx-background-color: #e9ecef; -fx-background-radius: 10; -fx-border-color: #dee2e6; -fx-border-width: 1; -fx-border-radius: 10;");
            }
            
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            artImageView.setStyle("-fx-background-color: #e9ecef; -fx-background-radius: 10; -fx-border-color: #dee2e6; -fx-border-width: 1; -fx-border-radius: 10;");
        }
    }
    
    private void updateLikesDisplay() {
        likesLabel.setText(currentArt.getLikes() + " likes");
    }
    
    private void updateLikeButtonState() {
        if (serviceArtLike.hasLiked(currentArt.getId(), sessionId)) {
            likeButton.setText("♥ Liked");
            likeButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: linear-gradient(#28a745, #20c997); -fx-background-radius: 15; -fx-padding: 10px 20px; -fx-cursor: hand;");
        } else {
            likeButton.setText("♡ Like");
            likeButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: linear-gradient(#e74c3c, #c0392b); -fx-background-radius: 15; -fx-padding: 10px 20px; -fx-cursor: hand;");
        }
    }
    
    private void loadRecommendations() {
        try {
            List<Art> recommendations = recommendationService.getSimilarArtworks(currentArt.getId(), 8);
            
            recommendationsTilePane.getChildren().clear();
            
            if (recommendations.isEmpty()) {
                Label noRecommendations = new Label("Aucune recommandation disponible");
                noRecommendations.setStyle("-fx-font-size: 14px; -fx-text-fill: #6c757d; -fx-font-style: italic;");
                recommendationsTilePane.getChildren().add(noRecommendations);
            } else {
                for (Art recommendation : recommendations) {
                    if (recommendation.getId() != currentArt.getId()) { // Exclure l'œuvre actuelle
                        recommendationsTilePane.getChildren().add(createRecommendationCard(recommendation));
                    }
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error loading recommendations: " + e.getMessage());
            Label errorLabel = new Label("Erreur lors du chargement des recommandations");
            errorLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #dc3545; -fx-font-style: italic;");
            recommendationsTilePane.getChildren().add(errorLabel);
        }
    }
    
    private VBox createRecommendationCard(Art art) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 15px; -fx-border-color: #dee2e6; -fx-border-width: 1; -fx-border-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3); -fx-cursor: hand;");
        card.setPrefSize(180, 200);
        
        // Title
        Label titleLabel = new Label(art.getTitle());
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-wrap-text: true;");
        titleLabel.setWrapText(true);
        
        // Artist
        Label artistLabel = new Label(art.getArtist());
        artistLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #6c757d; -fx-wrap-text: true;");
        artistLabel.setWrapText(true);
        
        // Likes
        Label likesLabel = new Label("♥ " + art.getLikes() + " likes");
        likesLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #e74c3c; -fx-font-weight: bold;");
        
        card.getChildren().addAll(titleLabel, artistLabel, likesLabel);
        
        // Click handler
        card.setOnMouseClicked(e -> {
            try {
                // Implémenter l'ouverture dans une nouvelle fenêtre
                System.out.println("Clicked on recommendation: " + art.getTitle());
            } catch (Exception ex) {
                System.err.println("Error opening recommendation: " + ex.getMessage());
            }
        });
        
        // Hover effect
        card.setOnMouseEntered(e -> {
            card.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 10; -fx-padding: 15px; -fx-border-color: #007bff; -fx-border-width: 2; -fx-border-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 12, 0, 0, 5); -fx-cursor: hand;");
        });
        
        card.setOnMouseExited(e -> {
            card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 15px; -fx-border-color: #dee2e6; -fx-border-width: 1; -fx-border-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 3); -fx-cursor: hand;");
        });
        
        return card;
    }
    
    @FXML
    private void handleLike() {
        try {
            if (serviceArtLike.toggleLike(currentArt.getId(), sessionId)) {
                // Mettre à jour le nombre de likes dans l'objet Art
                if (serviceArtLike.hasLiked(currentArt.getId(), sessionId)) {
                    currentArt.setLikes(currentArt.getLikes() + 1);
                } else {
                    currentArt.setLikes(Math.max(0, currentArt.getLikes() - 1));
                }
                
                updateLikesDisplay();
                updateLikeButtonState();
                
                System.out.println("Like toggled for artwork " + currentArt.getId() + ". Total likes: " + currentArt.getLikes());
            }
        } catch (Exception e) {
            System.err.println("Error handling like: " + e.getMessage());
            showAlert("Erreur", "Une erreur est survenue lors du like.", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void handleSpotify() {
        try {
            String playlist = spotifyService.findPlaylistForArtwork(
                currentArt.getTitle(), 
                currentArt.getDescription(), 
                currentArt.getArtist()
            );
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Playlist Spotify");
            alert.setHeaderText("Musique pour: " + currentArt.getTitle());
            alert.setContentText(playlist);
            alert.showAndWait();
            
            // Optionnel: ouvrir Spotify dans le navigateur
            try {
                java.awt.Desktop.getDesktop().browse(
                    new java.net.URI("https://open.spotify.com/search/" + 
                    java.net.URLEncoder.encode(currentArt.getTitle() + " " + currentArt.getArtist(), "UTF-8"))
                );
            } catch (Exception ex) {
                System.err.println("Could not open Spotify: " + ex.getMessage());
            }
            
        } catch (Exception e) {
            System.err.println("Error handling Spotify: " + e.getMessage());
            showAlert("Erreur", "Une erreur est survenue avec Spotify.", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void handleEdit() {
        try {
            // Implémenter la modification
            showAlert("Fonctionnalité", "La modification sera bientôt disponible!", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            System.err.println("Error handling edit: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleDelete() {
        try {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmation");
            confirmAlert.setHeaderText("Supprimer cette œuvre?");
            confirmAlert.setContentText("Êtes-vous sûr de vouloir supprimer \"" + currentArt.getTitle() + "\"?");
            
            if (confirmAlert.showAndWait().get() == ButtonType.OK) {
                if (serviceArt.deleteArt(currentArt.getId())) {
                    showAlert("Succès", "Œuvre supprimée avec succès!", Alert.AlertType.INFORMATION);
                    handleBack(); // Retour à la galerie
                } else {
                    showAlert("Erreur", "Impossible de supprimer cette œuvre.", Alert.AlertType.ERROR);
                }
            }
        } catch (Exception e) {
            System.err.println("Error handling delete: " + e.getMessage());
            showAlert("Erreur", "Une erreur est survenue lors de la suppression.", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void handleBack() {
        try {
            SceneNavigator.goTo("/views/menu-view.fxml");
        } catch (IOException e) {
            System.err.println("Error navigating back: " + e.getMessage());
            showAlert("Erreur", "Impossible de revenir à la galerie.", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void handleQuotes() {
        String quote = quotesService.getFormattedQuote();
        showAlert("💬 Citation d'artiste", quote, Alert.AlertType.INFORMATION);
    }
    
        
        
        
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
                alert.showAndWait();
    }
}
