package com.pegasus.controllers;

import com.pegasus.entities.Art;
import com.pegasus.services.QuotesService;
import com.pegasus.services.RecommendationService;
import com.pegasus.services.ServiceArt;
import com.pegasus.services.ServiceArtLike;
import com.pegasus.services.SpotifyService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ArtDetailController {

    @FXML private ImageView artImageView;
    @FXML private Label titleLabel;
    @FXML private Label artistLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label statusLabel;
    @FXML private Label dateLabel;
    @FXML private Label likesLabel;
    @FXML private Button likeButton;
    @FXML private Button spotifyButton;
    @FXML private Button editButton;
    @FXML private Button deleteButton;
    @FXML private TilePane recommendationsTilePane;

    private Art currentArt;
    private final ServiceArt serviceArt = new ServiceArt();
    private final ServiceArtLike serviceArtLike = new ServiceArtLike();
    private final SpotifyService spotifyService = new SpotifyService();
    private final RecommendationService recommendationService = new RecommendationService();
    private final QuotesService quotesService = new QuotesService();
    private final String sessionId = "session_" + System.currentTimeMillis() + "_new";

    public void setArt(Art art) {
        this.currentArt = art;
        displayArtDetails();
        loadRecommendations();
    }

    private void displayArtDetails() {
        if (currentArt == null) return;

        titleLabel.setText(currentArt.getTitle());
        artistLabel.setText((currentArt.getArtist() == null || currentArt.getArtist().isBlank()) ? "Artiste inconnu" : currentArt.getArtist());
        descriptionLabel.setText((currentArt.getDescription() == null || currentArt.getDescription().isBlank()) ? "Pas de description disponible." : currentArt.getDescription());

        if ("published".equalsIgnoreCase(currentArt.getStatus())) {
            statusLabel.setText("Publie");
            statusLabel.setStyle("-fx-text-fill: #28a745; -fx-font-weight: bold;");
        } else {
            statusLabel.setText("En attente");
            statusLabel.setStyle("-fx-text-fill: #ffc107; -fx-font-weight: bold;");
        }

        if (currentArt.getCreatedAt() != null) {
            dateLabel.setText(currentArt.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        } else {
            dateLabel.setText("Date inconnue");
        }

        updateLikesDisplay();
        updateLikeButtonState();
        loadImage();
    }

    private void loadImage() {
        try {
            String imageUrl = currentArt.getImageUrl();
            if (imageUrl != null && !imageUrl.isBlank()) {
                Image image = new Image(imageUrl, true);
                if (!image.isError()) {
                    artImageView.setImage(image);
                    return;
                }
            }
            Image fallback = new Image("/images/default-artwork.jpg", true);
            if (!fallback.isError()) {
                artImageView.setImage(fallback);
            }
        } catch (Exception ignored) {
        }
    }

    private void updateLikesDisplay() {
        likesLabel.setText((currentArt == null ? 0 : currentArt.getLikes()) + " likes");
    }

    private void updateLikeButtonState() {
        if (currentArt == null) return;
        if (serviceArtLike.hasLiked(currentArt.getId(), sessionId)) {
            likeButton.setText("Liked");
        } else {
            likeButton.setText("Like");
        }
    }

    private void loadRecommendations() {
        if (recommendationsTilePane == null || currentArt == null) return;
        recommendationsTilePane.getChildren().clear();
        try {
            List<Art> recommendations = recommendationService.getSimilarArtworks(currentArt.getId(), 8);
            if (recommendations == null || recommendations.isEmpty()) {
                recommendationsTilePane.getChildren().add(new Label("Aucune recommandation disponible"));
                return;
            }
            for (Art art : recommendations) {
                if (art.getId() == currentArt.getId()) continue;
                recommendationsTilePane.getChildren().add(createRecommendationCard(art));
            }
        } catch (Exception e) {
            recommendationsTilePane.getChildren().add(new Label("Erreur lors du chargement des recommandations"));
        }
    }

    private VBox createRecommendationCard(Art art) {
        VBox card = new VBox(8);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 12;");
        card.getChildren().addAll(
                new Label(art.getTitle()),
                new Label(art.getArtist() == null ? "Artiste inconnu" : art.getArtist()),
                new Label(art.getLikes() + " likes")
        );
        return card;
    }

    @FXML
    private void handleLike() {
        if (currentArt == null) return;
        try {
            if (serviceArtLike.toggleLike(currentArt.getId(), sessionId)) {
                if (serviceArtLike.hasLiked(currentArt.getId(), sessionId)) {
                    currentArt.setLikes(currentArt.getLikes() + 1);
                } else {
                    currentArt.setLikes(Math.max(0, currentArt.getLikes() - 1));
                }
                updateLikesDisplay();
                updateLikeButtonState();
            }
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors du like.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleSpotify() {
        if (currentArt == null) return;
        try {
            String playlist = spotifyService.findPlaylistForArtwork(currentArt.getTitle(), currentArt.getDescription(), currentArt.getArtist());
            showAlert("Spotify", playlist, Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue avec Spotify.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleEdit() {
        showAlert("Fonctionnalite", "La modification sera bientot disponible.", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void handleDelete() {
        if (currentArt == null) return;
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation");
        confirm.setHeaderText("Supprimer cette oeuvre ?");
        confirm.setContentText(currentArt.getTitle());
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            if (serviceArt.deleteArt(currentArt.getId())) {
                showAlert("Succes", "Oeuvre supprimee.", Alert.AlertType.INFORMATION);
                handleBack();
            } else {
                showAlert("Erreur", "Suppression impossible.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleBack() {
        try {
            SceneNavigator.goTo("/views/menu-view.fxml");
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de revenir a la galerie.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleGoHome() {
        try {
            SceneNavigator.goTo("/views/home-view.fxml");
        } catch (IOException e) {
            showAlert("Erreur", "Impossible d'ouvrir la page d'accueil.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleQuotes() {
        showAlert("Citation", quotesService.getFormattedQuote(), Alert.AlertType.INFORMATION);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
