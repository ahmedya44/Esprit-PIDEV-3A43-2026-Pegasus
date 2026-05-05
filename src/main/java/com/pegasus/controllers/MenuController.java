package com.pegasus.controllers;

import com.pegasus.entities.Art;
import com.pegasus.entities.User;
import com.pegasus.services.RecommendationService;
import com.pegasus.services.ServiceArt;
import com.pegasus.services.ServiceArtComment;
import com.pegasus.services.ServiceArtDislike;
import com.pegasus.services.QuotesService;
import com.pegasus.services.ArtistsService;
import javafx.fxml.FXML;
import java.io.IOException;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import javafx.application.Platform;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.awt.Desktop;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class MenuController {
    
    @FXML
    private TilePane galleryGrid;
    
    @FXML
    private TextField searchField;

    @FXML
    private ToggleButton toggleRecent;

    @FXML
    private ToggleButton toggleOlder;

    @FXML
    private ToggleButton toggleLiked;

    @FXML
    private Button navBackofficeButton;

    @FXML
    private Button navEventsBackofficeButton;
    
    private ServiceArt artService = new ServiceArt();
    private ServiceArtComment commentService = new ServiceArtComment();
    private ServiceArtDislike dislikeService = new ServiceArtDislike();
    
    // Compteurs pour likes/dislikes
    private Map<Integer, Integer> likeCounts = new HashMap<>();
    private Map<Integer, Integer> dislikeCounts = new HashMap<>();
    private RecommendationService recommendationService = new RecommendationService();
    private QuotesService quotesService = new QuotesService();
    private ArtistsService artistsService = new ArtistsService();
    
    public void initialize() {
        updateNavbarByRole();
        try {
            loadArtworks(null);
        } catch (Exception e) {
            System.err.println("Menu initialization failed: " + e.getMessage());
            if (galleryGrid != null) {
                galleryGrid.getChildren().clear();
                Label errorLabel = new Label("Could not load artworks right now.");
                errorLabel.setStyle("-fx-text-fill: #dc2626; -fx-font-size: 14px;");
                galleryGrid.getChildren().add(errorLabel);
            }
        }
    }

    private void updateNavbarByRole() {
        if (navBackofficeButton == null) {
            return;
        }
        User currentUser = SceneNavigator.getCurrentUser();
        boolean isAdmin = currentUser != null && "admin".equalsIgnoreCase(currentUser.getDtype());
        navBackofficeButton.setVisible(isAdmin);
        navBackofficeButton.setManaged(isAdmin);
        if (navEventsBackofficeButton != null) {
            navEventsBackofficeButton.setVisible(isAdmin);
            navEventsBackofficeButton.setManaged(isAdmin);
        }
    }
    
    private void loadArtworks(String filter) {
        if (galleryGrid == null) {
            return;
        }
        galleryGrid.getChildren().clear();
        try {
            List<Art> allArtworks = artService.getAllArts();
            if (allArtworks == null) {
                return;
            }

            String normalizedFilter = filter == null ? "" : filter.toLowerCase();
            for (Art art : allArtworks) {
                if (!isVisibleStatus(art.getStatus())) {
                    continue;
                }
                String title = art.getTitle() == null ? "" : art.getTitle().toLowerCase();
                String description = art.getDescription() == null ? "" : art.getDescription().toLowerCase();
                if (normalizedFilter.isEmpty() || title.contains(normalizedFilter) || description.contains(normalizedFilter)) {
                    galleryGrid.getChildren().add(createArtworkCard(art));
                }
            }
        } catch (Exception e) {
            System.err.println("loadArtworks error: " + e.getMessage());
            Label errorLabel = new Label("Error loading gallery data.");
            errorLabel.setStyle("-fx-text-fill: #dc2626; -fx-font-size: 14px;");
            galleryGrid.getChildren().add(errorLabel);
        }
    }
    
    private VBox createArtworkCard(Art art) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-padding: 15; -fx-border-color: #e9ecef; -fx-border-width: 1;");
        card.setPrefWidth(300);
        
        ImageView imageView = new ImageView();
        try {
            String imageUrl = art.getImageUrl();
            System.out.println("ÃƒÂ°Ã…Â¸Ã¢â‚¬â€œÃ‚Â¼ÃƒÂ¯Ã‚Â¸Ã‚Â Tentative de chargement de l'image: " + imageUrl);
            
            if (imageUrl != null && !imageUrl.isEmpty()) {
                // Forcer le chargement de VOTRE image sans fallback automatique
                Image image = new Image(imageUrl, false); // false = chargement synchrone
                
                // TOUJOURS essayer d'afficher votre image, mÃƒÆ’Ã‚Âªme en cas d'erreur
                imageView.setImage(image);
                
                if (image.isError()) {
                    System.err.println("ÃƒÂ¢Ã‚ÂÃ…â€™ Erreur de chargement de l'image: " + imageUrl);
                    System.err.println("ÃƒÂ¢Ã‚ÂÃ…â€™ Exception: " + image.getException());
                    // Ne PAS remplacer par une image alÃƒÆ’Ã‚Â©atoire - garder votre image mÃƒÆ’Ã‚Âªme si elle a une erreur
                    System.out.println("ÃƒÂ¢Ã…Â¡Ã‚Â ÃƒÂ¯Ã‚Â¸Ã‚Â Conservation de votre image malgrÃƒÆ’Ã‚Â© l'erreur");
                } else {
                    System.out.println("ÃƒÂ¢Ã…â€œÃ¢â‚¬Â¦ Image chargÃƒÆ’Ã‚Â©e avec succÃƒÆ’Ã‚Â¨s: " + imageUrl);
                }
                
                // Listener pour suivre l'ÃƒÆ’Ã‚Â©tat mais ne PAS remplacer
                image.errorProperty().addListener((obs, oldVal, newVal) -> {
                    if (newVal) {
                        System.err.println("ÃƒÂ¢Ã‚ÂÃ…â€™ Erreur asynchrone de l'image: " + imageUrl);
                        System.err.println("ÃƒÂ¢Ã‚ÂÃ…â€™ Exception: " + image.getException());
                        System.out.println("ÃƒÂ¢Ã…Â¡Ã‚Â ÃƒÂ¯Ã‚Â¸Ã‚Â Votre image reste affichÃƒÆ’Ã‚Â©e malgrÃƒÆ’Ã‚Â© l'erreur");
                    }
                });
                
                // Listener pour le succÃƒÆ’Ã‚Â¨s
                image.progressProperty().addListener((obs, oldVal, newVal) -> {
                    if (newVal.doubleValue() == 1.0 && !image.isError()) {
                        System.out.println("ÃƒÂ¢Ã…â€œÃ¢â‚¬Â¦ Image 100% chargÃƒÆ’Ã‚Â©e: " + imageUrl);
                    }
                });
                
            } else {
                System.out.println("ÃƒÂ°Ã…Â¸Ã¢â‚¬Å“Ã‚Â· Aucune URL fournie - pas d'image affichÃƒÆ’Ã‚Â©e");
                // Ne PAS mettre d'image par dÃƒÆ’Ã‚Â©faut
            }
            
            imageView.setFitWidth(280);
            imageView.setFitHeight(200);
            imageView.setPreserveRatio(true);
            
        } catch (Exception e) {
            System.err.println("ÃƒÂ¢Ã‚ÂÃ…â€™ Exception lors du chargement de l'image: " + e.getMessage());
            e.printStackTrace();
            // Ne PAS mettre d'image par dÃƒÆ’Ã‚Â©faut - laisser vide
            System.out.println("ÃƒÂ°Ã…Â¸Ã¢â‚¬Å“Ã‚Â· Pas d'image affichÃƒÆ’Ã‚Â©e suite ÃƒÆ’Ã‚Â  l'exception");
        }
        
        Label titleLabel = new Label(art.getTitle());
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        Label descLabel = new Label(art.getDescription());
        descLabel.setStyle("-fx-text-fill: #6c757d; -fx-font-size: 14px; -fx-wrap-text: true;");
        descLabel.setPrefWidth(280);
        
        HBox socialButtons = createSocialButtons(art);
        HBox actionButtons = createActionButtons(art);
        
        card.getChildren().addAll(imageView, titleLabel, descLabel, socialButtons, actionButtons);
        return card;
    }
    
    private HBox createActionButtons(Art art) {
        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        
        Button suggestionButton = new Button("\uD83D\uDCA1");
        suggestionButton.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-background-radius: 12; -fx-padding: 8px 14px; -fx-font-size: 12px;");
        suggestionButton.setOnAction(e -> showSuggestionsDialog(art));
        
        Button spotifyButton = new Button("\u266B");
        spotifyButton.setStyle("-fx-background-color: #1db954; -fx-text-fill: white; -fx-background-radius: 12; -fx-padding: 8px 14px; -fx-font-size: 14px;");
        spotifyButton.setOnAction(e -> handleSpotify(art));
        
        Button editButton = new Button("\u270E");
        editButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 12; -fx-padding: 8px 14px; -fx-font-size: 14px;");
        editButton.setOnAction(e -> showEditDialog(art));
        
        Button deleteButton = new Button("\uD83D\uDDD1");
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 12; -fx-padding: 8px 14px; -fx-font-size: 14px;");
        deleteButton.setOnAction(e -> handleDeleteArt(art));
        
        buttons.getChildren().addAll(suggestionButton, spotifyButton, editButton, deleteButton);
        return buttons;
    }
    
    private void handleSpotify(Art art) {
        try {
            Alert spotifyAlert = new Alert(Alert.AlertType.CONFIRMATION);
            spotifyAlert.setTitle("Spotify Playlist");
            spotifyAlert.setHeaderText("Spotify pour : " + art.getTitle());
            spotifyAlert.setContentText("Voulez-vous ouvrir Spotify pour cette Ãƒâ€¦Ã¢â‚¬Å“uvre ?\n\nTitre : " + art.getTitle() + "\nArtiste : " + art.getArtist());
            spotifyAlert.getButtonTypes().setAll(new ButtonType("Ouvrir Spotify", ButtonBar.ButtonData.OK_DONE), ButtonType.CANCEL);
            Optional<ButtonType> result = spotifyAlert.showAndWait();
            if (result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                String query = URLEncoder.encode(art.getTitle() + " " + art.getArtist(), StandardCharsets.UTF_8);
                Desktop.getDesktop().browse(new URI("https://open.spotify.com/search/" + query));
            }
        } catch (Exception e) {
            System.err.println("Erreur Spotify: " + e.getMessage());
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur Spotify");
            errorAlert.setHeaderText("Impossible d'ouvrir Spotify");
            errorAlert.setContentText("VÃƒÆ’Ã‚Â©rifiez votre connexion et rÃƒÆ’Ã‚Â©essayez.");
            errorAlert.showAndWait();
        }
    }
    
    private void showSuggestionsDialog(Art art) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Suggestions pour : " + art.getTitle());
        dialog.setHeaderText("Ãƒâ€¦Ã¢â‚¬â„¢uvres similaires ou recommandÃƒÆ’Ã‚Â©es");
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        
        try {
            List<Art> recommendations = recommendationService.getSimilarArtworks(art.getId(), 4);
            if (recommendations == null || recommendations.isEmpty()) {
                content.getChildren().add(new Label("Aucune suggestion disponible pour le moment."));
            } else {
                for (Art rec : recommendations) {
                    Label recLabel = new Label("ÃƒÂ¢Ã¢â€šÂ¬Ã‚Â¢ " + rec.getTitle() + " ÃƒÂ¢Ã¢â€šÂ¬Ã¢â‚¬Â " + rec.getArtist());
                    recLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #333;");
                    content.getChildren().add(recLabel);
                }
            }
        } catch (Exception e) {
            content.getChildren().add(new Label("Erreur lors du chargement des suggestions."));
            System.err.println("Erreur suggestions: " + e.getMessage());
        }
        
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.showAndWait();
    }
    
    private void showEditDialog(Art art) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Modifier l'Ãƒâ€¦Ã¢â‚¬Å“uvre");
        dialog.setHeaderText("Modifier les informations de l'Ãƒâ€¦Ã¢â‚¬Å“uvre");
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField titleField = new TextField(art.getTitle());
        TextArea descField = new TextArea(art.getDescription());
        descField.setPrefRowCount(3);
        TextField urlField = new TextField(art.getImageUrl());
        TextField artistField = new TextField(art.getArtist());
        
        grid.add(new Label("Titre:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descField, 1, 1);
        grid.add(new Label("Image URL:"), 0, 2);
        grid.add(urlField, 1, 2);
        grid.add(new Label("Artiste:"), 0, 3);
        grid.add(artistField, 1, 3);
        
        dialog.getDialogPane().setContent(grid);
        ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);
        
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == saveButtonType) {
            art.setTitle(titleField.getText().trim());
            art.setDescription(descField.getText().trim());
            art.setImageUrl(urlField.getText().trim());
            art.setArtist(artistField.getText().trim());
            if (artService.updateArt(art)) {
                loadArtworks(searchField.getText().trim());
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText("Modification impossible");
                errorAlert.setContentText("Impossible de mettre ÃƒÆ’Ã‚Â  jour l'Ãƒâ€¦Ã¢â‚¬Å“uvre.");
                errorAlert.showAndWait();
            }
        }
    }
    
    private void handleDeleteArt(Art art) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Supprimer l'Ãƒâ€¦Ã¢â‚¬Å“uvre");
        confirm.setHeaderText("Voulez-vous vraiment supprimer cette Ãƒâ€¦Ã¢â‚¬Å“uvre ?");
        confirm.setContentText(art.getTitle());
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (artService.deleteArt(art.getId())) {
                loadArtworks(searchField.getText().trim());
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText("Suppression impossible");
                errorAlert.setContentText("Impossible de supprimer l'Ãƒâ€¦Ã¢â‚¬Å“uvre.");
                errorAlert.showAndWait();
            }
        }
    }
    
    private HBox createSocialButtons(Art art) {
        HBox buttons = new HBox(15);
        buttons.setAlignment(Pos.CENTER_LEFT);
        
        // Charger les vrais compteurs depuis la base de donnÃƒÆ’Ã‚Â©es
        int currentLikes = dislikeService.getDislikeCount(art.getId()); // Utilise la mÃƒÆ’Ã‚Âªme table pour likes
        int currentDislikes = dislikeService.getDislikeCount(art.getId());
        
        likeCounts.put(art.getId(), currentLikes);
        dislikeCounts.put(art.getId(), currentDislikes);
        
        // Bouton Like avec compteur
        VBox likeContainer = new VBox(2);
        likeContainer.setAlignment(Pos.CENTER);
        
        Button likeButton = new Button("\uD83D\uDC4D");
        likeButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 8px 12px; -fx-font-size: 16px;");
        
        Label likeCount = new Label(String.valueOf(currentLikes));
        likeCount.setStyle("-fx-text-fill: #28a745; -fx-font-size: 12px; -fx-font-weight: bold;");
        
        likeContainer.getChildren().addAll(likeButton, likeCount);
        
        // Bouton Dislike avec compteur
        VBox dislikeContainer = new VBox(2);
        dislikeContainer.setAlignment(Pos.CENTER);
        
        Button dislikeButton = new Button("\uD83D\uDC4E");
        dislikeButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 8px 12px; -fx-font-size: 16px;");
        
        Label dislikeCount = new Label(String.valueOf(currentDislikes));
        dislikeCount.setStyle("-fx-text-fill: #dc3545; -fx-font-size: 12px; -fx-font-weight: bold;");
        
        dislikeContainer.getChildren().addAll(dislikeButton, dislikeCount);
        
        // Bouton Commentaire
        Button commentButton = new Button("\uD83D\uDCAC");
        commentButton.setStyle("-fx-background-color: #17a2b8; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 8px 12px; -fx-font-size: 16px;");
        
        // Actions avec animations
        likeButton.setOnAction(e -> handleLikeWithAnimation(art, likeButton, likeCount, "like_" + System.currentTimeMillis()));
        dislikeButton.setOnAction(e -> handleDislikeWithAnimation(art, dislikeButton, dislikeCount, "dislike_" + System.currentTimeMillis()));
        commentButton.setOnAction(e -> openCommentDialog(art));
        
        buttons.getChildren().addAll(likeContainer, dislikeContainer, commentButton);
        return buttons;
    }
    
    private void handleLikeWithAnimation(Art art, Button likeButton, Label likeCount, String sessionId) {
        if (dislikeService.addDislike(art.getId(), sessionId)) {
            // Recharger le compteur depuis la base de donnÃƒÆ’Ã‚Â©es
            int newCount = dislikeService.getDislikeCount(art.getId());
            likeCounts.put(art.getId(), newCount);
            
            // Animation de poussÃƒÆ’Ã‚Â©e vers le haut
            Timeline pushUpAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(likeButton.translateYProperty(), 0)),
                new KeyFrame(Duration.millis(200), new KeyValue(likeButton.translateYProperty(), -10)),
                new KeyFrame(Duration.millis(400), new KeyValue(likeButton.translateYProperty(), 0))
            );
            
            // Animation de changement de couleur
            Timeline colorAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(likeButton.styleProperty(), "-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 8px 12px; -fx-font-size: 16px;")),
                new KeyFrame(Duration.millis(200), new KeyValue(likeButton.styleProperty(), "-fx-background-color: #1e7e34; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 8px 12px; -fx-font-size: 18px;")),
                new KeyFrame(Duration.millis(400), new KeyValue(likeButton.styleProperty(), "-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 8px 12px; -fx-font-size: 16px;"))
            );
            
            // Mettre ÃƒÆ’Ã‚Â  jour le compteur
            likeCount.setText(String.valueOf(newCount));
            
            pushUpAnimation.play();
            colorAnimation.play();
            
            System.out.println("Like ajoutÃƒÆ’Ã‚Â© pour: " + art.getTitle() + " (Total: " + newCount + ")");
        }
    }
    
    private void handleDislikeWithAnimation(Art art, Button dislikeButton, Label dislikeCount, String sessionId) {
        if (dislikeService.addDislike(art.getId(), sessionId)) {
            // Recharger le compteur depuis la base de donnÃƒÆ’Ã‚Â©es
            int newCount = dislikeService.getDislikeCount(art.getId());
            dislikeCounts.put(art.getId(), newCount);
            
            // Animation de poussÃƒÆ’Ã‚Â©e vers le bas
            Timeline pushDownAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(dislikeButton.translateYProperty(), 0)),
                new KeyFrame(Duration.millis(200), new KeyValue(dislikeButton.translateYProperty(), 10)),
                new KeyFrame(Duration.millis(400), new KeyValue(dislikeButton.translateYProperty(), 0))
            );
            
            // Animation de changement de couleur
            Timeline colorAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(dislikeButton.styleProperty(), "-fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 8px 12px; -fx-font-size: 16px;")),
                new KeyFrame(Duration.millis(200), new KeyValue(dislikeButton.styleProperty(), "-fx-background-color: #c82333; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 8px 12px; -fx-font-size: 18px;")),
                new KeyFrame(Duration.millis(400), new KeyValue(dislikeButton.styleProperty(), "-fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 8px 12px; -fx-font-size: 16px;"))
            );
            
            // Mettre ÃƒÆ’Ã‚Â  jour le compteur
            dislikeCount.setText(String.valueOf(newCount));
            
            pushDownAnimation.play();
            colorAnimation.play();
            
            System.out.println("Dislike ajoutÃƒÆ’Ã‚Â© pour: " + art.getTitle() + " (Total: " + newCount + ")");
        }
    }
    
    private void openCommentDialog(Art art) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Commentaires");
        dialog.setHeaderText("Commentaires sur: " + art.getTitle());
        
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setPrefWidth(500);
        
        VBox commentsContainer = new VBox(5);
        List<ServiceArtComment.Comment> comments = commentService.getCommentsByArtId(art.getId());
        displayComments(comments, commentsContainer, art);
        
        TextField usernameField = new TextField();
        usernameField.setPromptText("Votre nom");
        
        TextArea commentArea = new TextArea();
        commentArea.setPromptText("Votre commentaire...");
        commentArea.setPrefWidth(460);
        commentArea.setPrefHeight(80);
        commentArea.setWrapText(true);
        
        Button submitButton = new Button("Envoyer");
        submitButton.setStyle("-fx-background-color: #17a2b8; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 8px 15px;");
        
        submitButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String commentText = commentArea.getText().trim();
            
            if (!username.isEmpty() && !commentText.isEmpty()) {
                if (commentService.addComment(art.getId(), username, commentText)) {
                    usernameField.clear();
                    commentArea.clear();
                    List<ServiceArtComment.Comment> updatedComments = commentService.getCommentsByArtId(art.getId());
                    displayComments(updatedComments, commentsContainer, art);
                }
            }
        });
        
        content.getChildren().addAll(new Label("Commentaires existants:"), commentsContainer, 
                                   new Separator(), new Label("Ajouter un commentaire:"), 
                                   usernameField, commentArea, submitButton);
        
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }
    
    private void displayComments(List<ServiceArtComment.Comment> comments, VBox container, Art art) {
        container.getChildren().clear();
        
        if (comments.isEmpty()) {
            Label noCommentsLabel = new Label("Soyez le premier ÃƒÆ’Ã‚Â  commenter !");
            noCommentsLabel.setStyle("-fx-text-fill: #6c757d; -fx-font-style: italic;");
            container.getChildren().add(noCommentsLabel);
            return;
        }
        
        // SÃƒÆ’Ã‚Â©parer les commentaires principaux des rÃƒÆ’Ã‚Â©ponses
        List<ServiceArtComment.Comment> mainComments = new ArrayList<>();
        List<ServiceArtComment.Comment> replies = new ArrayList<>();
        
        for (ServiceArtComment.Comment comment : comments) {
            if (comment.getContent().startsWith("ÃƒÂ¢Ã¢â‚¬Â Ã‚Â© @")) {
                replies.add(comment);
            } else {
                mainComments.add(comment);
            }
        }
        
        // Afficher d'abord les commentaires principaux avec leurs rÃƒÆ’Ã‚Â©ponses
        for (ServiceArtComment.Comment mainComment : mainComments) {
            VBox commentBox = new VBox(5);
            commentBox.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-padding: 10; -fx-border-color: #e9ecef; -fx-border-width: 1;");
            
            HBox headerBox = new HBox();
            headerBox.setAlignment(Pos.CENTER_LEFT);
            
            Label usernameLabel = new Label(mainComment.getUsername());
            usernameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #495057;");
            
            Label dateLabel = new Label(mainComment.getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            dateLabel.setStyle("-fx-text-fill: #6c757d; -fx-font-size: 11px;");
            
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            
            headerBox.getChildren().addAll(usernameLabel, spacer, dateLabel);
            
            Label contentLabel = new Label(mainComment.getContent());
            contentLabel.setStyle("-fx-text-fill: #212529; -fx-wrap-text: true;");
            contentLabel.setPrefWidth(450);
            
            // Bouton de rÃƒÆ’Ã‚Â©ponse minimaliste
            Button replyButton = new Button("ÃƒÂ¢Ã¢â‚¬Â Ã‚Â©");
            replyButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #17a2b8; -fx-border-color: #17a2b8; -fx-border-width: 1; -fx-background-radius: 10; -fx-padding: 4px 8px; -fx-font-size: 12px; -fx-cursor: hand;");
            replyButton.setOnAction(e -> openReplyDialog(mainComment, art));
            
            HBox contentWithReply = new HBox(10);
            contentWithReply.setAlignment(Pos.CENTER_LEFT);
            contentWithReply.getChildren().addAll(contentLabel, replyButton);
            
            commentBox.getChildren().addAll(headerBox, contentWithReply);
            
            // Ajouter les rÃƒÆ’Ã‚Â©ponses sous ce commentaire
            for (ServiceArtComment.Comment reply : replies) {
                if (reply.getContent().contains("@" + mainComment.getUsername())) {
                    // CrÃƒÆ’Ã‚Â©er une boÃƒÆ’Ã‚Â®te de rÃƒÆ’Ã‚Â©ponse plus petite et indentÃƒÆ’Ã‚Â©e
                    VBox replyBox = new VBox(3);
                    replyBox.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 6; -fx-padding: 8px 8px 8px 20px; -fx-border-color: #dee2e6; -fx-border-width: 1;");
                    
                    HBox replyHeader = new HBox();
                    replyHeader.setAlignment(Pos.CENTER_LEFT);
                    
                    Label replyUsernameLabel = new Label(reply.getUsername());
                    replyUsernameLabel.setStyle("-fx-font-weight: normal; -fx-text-fill: #6c757d; -fx-font-size: 12px;");
                    
                    Label replyDateLabel = new Label(reply.getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM HH:mm")));
                    replyDateLabel.setStyle("-fx-text-fill: #adb5bd; -fx-font-size: 10px;");
                    
                    Region replySpacer = new Region();
                    HBox.setHgrow(replySpacer, Priority.ALWAYS);
                    
                    replyHeader.getChildren().addAll(replyUsernameLabel, replySpacer, replyDateLabel);
                    
                    // Extraire le contenu de la rÃƒÆ’Ã‚Â©ponse (enlever "ÃƒÂ¢Ã¢â‚¬Â Ã‚Â© @username: ")
                    String replyContent = reply.getContent().replaceFirst("ÃƒÂ¢Ã¢â‚¬Â Ã‚Â© @" + mainComment.getUsername() + ": ", "");
                    Label replyContentLabel = new Label(replyContent);
                    replyContentLabel.setStyle("-fx-text-fill: #495057; -fx-wrap-text: true; -fx-font-size: 12px;");
                    replyContentLabel.setPrefWidth(420);
                    
                    replyBox.getChildren().addAll(replyHeader, replyContentLabel);
                    commentBox.getChildren().add(replyBox);
                }
            }
            
            container.getChildren().add(commentBox);
        }
    }
    
    private void openReplyDialog(ServiceArtComment.Comment parentComment, Art art) {
        Dialog<String> replyDialog = new Dialog<>();
        replyDialog.setTitle("RÃƒÆ’Ã‚Â©pondre au commentaire");
        replyDialog.setHeaderText("RÃƒÆ’Ã‚Â©pondre ÃƒÆ’Ã‚Â : " + parentComment.getUsername());
        
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setPrefWidth(400);
        
        // Affichage du commentaire parent
        Label parentLabel = new Label("@" + parentComment.getUsername() + ": " + parentComment.getContent());
        parentLabel.setStyle("-fx-text-fill: #6c757d; -fx-font-style: italic; -fx-wrap-text: true;");
        parentLabel.setPrefWidth(360);
        
        // Champ pour la rÃƒÆ’Ã‚Â©ponse
        TextField replyNameField = new TextField();
        replyNameField.setPromptText("Votre nom");
        
        TextArea replyArea = new TextArea();
        replyArea.setPromptText("Votre rÃƒÆ’Ã‚Â©ponse...");
        replyArea.setPrefWidth(360);
        replyArea.setPrefHeight(60);
        replyArea.setWrapText(true);
        
        content.getChildren().addAll(parentLabel, new Separator(), replyNameField, replyArea);
        
        replyDialog.getDialogPane().setContent(content);
        
        ButtonType replyButton = new ButtonType("RÃƒÆ’Ã‚Â©pondre", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        replyDialog.getDialogPane().getButtonTypes().addAll(replyButton, cancelButton);
        
        replyDialog.setResultConverter(dialogButton -> {
            if (dialogButton == replyButton) {
                String name = replyNameField.getText().trim();
                String reply = replyArea.getText().trim();
                
                if (!name.isEmpty() && !reply.isEmpty()) {
                    String replyText = "ÃƒÂ¢Ã¢â‚¬Â Ã‚Â© @" + parentComment.getUsername() + ": " + reply;
                    return replyText;
                }
            }
            return null;
        });
        
        Optional<String> result = replyDialog.showAndWait();
        result.ifPresent(replyText -> {
            if (commentService.addComment(art.getId(), replyNameField.getText().trim(), replyText)) {
                System.out.println("ÃƒÂ¢Ã…â€œÃ¢â‚¬Â¦ RÃƒÆ’Ã‚Â©ponse ajoutÃƒÆ’Ã‚Â©e: " + replyText);
                
                // Fermer le dialog principal et le rouvrir pour rafraÃƒÆ’Ã‚Â®chir
                replyDialog.close();
                
                // Rouvrir le dialog de commentaires avec les nouveaux commentaires
                Platform.runLater(() -> {
                    openCommentDialog(art);
                });
            }
        });
    }
    
    @FXML
    private void handleSearch() {
        loadArtworks(searchField.getText().trim());
    }
    
    @FXML
    private void handleClearSearch() {
        searchField.clear();
        loadArtworks(null);
    }
    
    @FXML
    private void handleQuotes() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("ÃƒÂ°Ã…Â¸Ã¢â‚¬â„¢Ã‚Â¬ Citations d'Art");
        dialog.setHeaderText("Citations cÃƒÆ’Ã‚Â©lÃƒÆ’Ã‚Â¨bres d'artistes et penseurs");
        
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        content.setPrefWidth(500);
        
        // Afficher les citations
        VBox quotesContainer = new VBox(10);
        quotesContainer.setStyle("-fx-padding: 10px;");
        
        try {
            int totalQuotes = quotesService.getTotalQuotes();
            for (int i = 0; i < Math.min(5, totalQuotes); i++) {
                String quote = quotesService.getQuoteByIndex(i);
                Label quoteLabel = new Label(quote);
                quoteLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #2c3e50; -fx-wrap-text: true; -fx-padding: 10px; -fx-background-color: #f8f9fa; -fx-background-radius: 8;");
                quoteLabel.setWrapText(true);
                quotesContainer.getChildren().add(quoteLabel);
            }
        } catch (Exception e) {
            Label errorLabel = new Label("Erreur lors du chargement des citations");
            errorLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #e74c3c;");
            quotesContainer.getChildren().add(errorLabel);
        }
        
        ScrollPane scrollPane = new ScrollPane(quotesContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(300);
        
        // Bouton pour rafraÃƒÆ’Ã‚Â®chir les citations
        Button refreshButton = new Button("ÃƒÂ°Ã…Â¸Ã¢â‚¬ÂÃ¢â‚¬Å¾ Nouvelle citation");
        refreshButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 8px 16px;");
        refreshButton.setOnAction(e -> {
            Dialog<Void> newDialog = new Dialog<>();
            newDialog.setTitle("ÃƒÂ°Ã…Â¸Ã¢â‚¬â„¢Ã‚Â¬ Citation du jour");
            newDialog.setHeaderText("Citation alÃƒÆ’Ã‚Â©atoire");
            VBox newContent = new VBox(15);
            newContent.setPadding(new Insets(20));
            Label randomQuote = new Label(quotesService.getFormattedQuote());
            randomQuote.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #e74c3c; -fx-wrap-text: true; -fx-padding: 15px;");
            randomQuote.setWrapText(true);
            newContent.getChildren().add(randomQuote);
            newDialog.getDialogPane().setContent(newContent);
            newDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            newDialog.showAndWait();
        });
        
        content.getChildren().addAll(new Label("Citations populaires:"), scrollPane, refreshButton);
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.showAndWait();
    }
    
    @FXML
    private void handleArtists() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("ÃƒÂ°Ã…Â¸Ã¢â‚¬ËœÃ‚Â¨ÃƒÂ¢Ã¢â€šÂ¬Ã‚ÂÃƒÂ°Ã…Â¸Ã…Â½Ã‚Â¨ Artistes CÃƒÆ’Ã‚Â©lÃƒÆ’Ã‚Â¨bres");
        dialog.setHeaderText("Biographies des grands maÃƒÆ’Ã‚Â®tres");
        
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setPrefWidth(600);
        
        // Liste des artistes
        String[] artists = {
            "Vincent van Gogh",
            "Pablo Picasso", 
            "Claude Monet",
            "Leonardo da Vinci",
            "Henri Matisse",
            "Salvador DalÃƒÆ’Ã‚Â­",
            "Frida Kahlo",
            "Paul CÃƒÆ’Ã‚Â©zanne"
        };
        
        VBox artistsList = new VBox(8);
        artistsList.setStyle("-fx-padding: 10px;");
        
        for (String artist : artists) {
            Button artistBtn = new Button("ÃƒÂ°Ã…Â¸Ã¢â‚¬ËœÃ‚Â¨ÃƒÂ¢Ã¢â€šÂ¬Ã‚ÂÃƒÂ°Ã…Â¸Ã…Â½Ã‚Â¨ " + artist);
            artistBtn.setStyle("-fx-background-color: #667eea; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 10px 16px; -fx-font-size: 12px; -fx-cursor: hand; -fx-max-width: Infinity;");
            artistBtn.setMaxWidth(Double.MAX_VALUE);
            artistBtn.setOnAction(e -> showArtistBiography(artist));
            artistsList.getChildren().add(artistBtn);
        }
        
        ScrollPane scrollPane = new ScrollPane(artistsList);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(400);
        
        content.getChildren().addAll(new Label("SÃƒÆ’Ã‚Â©lectionnez un artiste pour sa biographie:"), scrollPane);
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.showAndWait();
    }
    
    private void showArtistBiography(String artistName) {
        Dialog<Void> bioDialog = new Dialog<>();
        bioDialog.setTitle("Biographie : " + artistName);
        bioDialog.setHeaderText("ÃƒÂ°Ã…Â¸Ã¢â‚¬ËœÃ‚Â¨ÃƒÂ¢Ã¢â€šÂ¬Ã‚ÂÃƒÂ°Ã…Â¸Ã…Â½Ã‚Â¨ " + artistName);
        
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setPrefWidth(500);
        
        try {
            String biography = artistsService.getArtistBiography(artistName);
            TextArea bioArea = new TextArea(biography);
            bioArea.setWrapText(true);
            bioArea.setEditable(false);
            bioArea.setPrefHeight(300);
            bioArea.setStyle("-fx-font-size: 12px; -fx-control-inner-background: #f8f9fa; -fx-text-fill: #2c3e50;");
            content.getChildren().add(bioArea);
        } catch (Exception e) {
            Label errorLabel = new Label("Erreur lors du chargement de la biographie");
            errorLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #e74c3c;");
            content.getChildren().add(errorLabel);
        }
        
        bioDialog.getDialogPane().setContent(content);
        bioDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        bioDialog.showAndWait();
    }
    
    @FXML
    private void handleSortRecent() {
        System.out.println("ÃƒÂ°Ã…Â¸Ã¢â‚¬Å“Ã¢â‚¬Â¦ Tri par plus rÃƒÆ’Ã‚Â©cent");
        loadArtworksSortedByDate(true); // true = plus rÃƒÆ’Ã‚Â©cent
    }
    
    @FXML
    private void handleSortOlder() {
        System.out.println("ÃƒÂ°Ã…Â¸Ã¢â‚¬Å“Ã…â€œ Tri par plus ancien");
        loadArtworksSortedByDate(false); // false = plus ancien
    }
    
    @FXML
    private void handleSortLiked() {
        System.out.println("ÃƒÂ¢Ã‚ÂÃ‚Â¤ÃƒÂ¯Ã‚Â¸Ã‚Â Tri par plus likÃƒÆ’Ã‚Â©");
        loadArtworksSortedByLikes();
    }
    
    private void loadArtworksSortedByDate(boolean mostRecent) {
        List<Art> artworks = artService.getAllArts();
        
        // Trier par date
        if (mostRecent) {
            artworks.sort((a1, a2) -> a2.getCreatedAt().compareTo(a1.getCreatedAt()));
        } else {
            artworks.sort((a1, a2) -> a1.getCreatedAt().compareTo(a2.getCreatedAt()));
        }
        
        // Filtrer et afficher
        galleryGrid.getChildren().clear();
        for (Art art : artworks) {
            if (isVisibleStatus(art.getStatus())) {
                galleryGrid.getChildren().add(createArtworkCard(art));
            }
        }
        
        System.out.println("ÃƒÂ¢Ã…â€œÃ¢â‚¬Â¦ Tri effectuÃƒÆ’Ã‚Â© : " + (mostRecent ? "plus rÃƒÆ’Ã‚Â©cent" : "plus ancien"));
    }
    
    private void loadArtworksSortedByLikes() {
        List<Art> artworks = artService.getAllArts();
        
        // Trier par nombre de likes (en utilisant les compteurs actuels)
        artworks.sort((a1, a2) -> {
            int likes1 = likeCounts.getOrDefault(a1.getId(), 0);
            int likes2 = likeCounts.getOrDefault(a2.getId(), 0);
            return Integer.compare(likes2, likes1); // ordre dÃƒÆ’Ã‚Â©croissant
        });
        
        // Filtrer et afficher
        galleryGrid.getChildren().clear();
        for (Art art : artworks) {
            if (isVisibleStatus(art.getStatus())) {
                galleryGrid.getChildren().add(createArtworkCard(art));
            }
        }
        
        System.out.println("ÃƒÂ¢Ã…â€œÃ¢â‚¬Â¦ Tri effectuÃƒÆ’Ã‚Â© : plus likÃƒÆ’Ã‚Â©");
    }
    
    @FXML
    private void handleResetSort() {
        searchField.clear();
        loadArtworks(null);
    }
    
        @FXML
    private void handleOpenGallery() {
        loadArtworks(null);
    }

    @FXML
    private void handleFilterAll() {
        loadArtworks(null);
    }

    @FXML
    private void handleFilterBurger() {
        loadArtworks("burger");
    }

    @FXML
    private void handleFilterPizza() {
        loadArtworks("pizza");
    }

    @FXML
    private void handleFilterPasta() {
        loadArtworks("pasta");
    }

    @FXML
    private void handleFilterFries() {
        loadArtworks("fries");
    }
    @FXML
    private void handleAddArtwork() {
        System.out.println("ÃƒÂ¢Ã…Â¾Ã¢â‚¬Â¢ Ajout d'Ãƒâ€¦Ã¢â‚¬Å“uvre cliquÃƒÆ’Ã‚Â©");
        showAddArtworkDialog();
    }

    private boolean isVisibleStatus(String status) {
        if (status == null || status.isBlank()) {
            return true;
        }
        String normalized = status.trim().toLowerCase();
        return normalized.equals("published")
                || normalized.equals("active")
                || normalized.equals("accepted")
                || normalized.equals("approved")
                || normalized.equals("public");
    }
    
    private void showAddArtworkDialog() {
        Dialog<Art> addDialog = new Dialog<>();
        addDialog.setTitle("Ajouter une Ãƒâ€¦Ã¢â‚¬Å“uvre");
        addDialog.setHeaderText("Ajouter une nouvelle Ãƒâ€¦Ã¢â‚¬Å“uvre ÃƒÆ’Ã‚Â  la collection");
        
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        content.setPrefWidth(500);
        
        // Champ pour le titre
        TextField titleField = new TextField();
        titleField.setPromptText("Titre de l'Ãƒâ€¦Ã¢â‚¬Å“uvre");
        titleField.setStyle("-fx-background-color: #f7fafc; -fx-border-color: #cbd5e0; -fx-border-radius: 8; -fx-padding: 10px;");
        
        // Champ pour la description
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Description de l'Ãƒâ€¦Ã¢â‚¬Å“uvre...");
        descriptionArea.setPrefWidth(460);
        descriptionArea.setPrefHeight(100);
        descriptionArea.setWrapText(true);
        descriptionArea.setStyle("-fx-background-color: #f7fafc; -fx-border-color: #cbd5e0; -fx-border-radius: 8; -fx-padding: 10px;");
        
        // Champ pour l'URL de l'image
        TextField imageField = new TextField();
        imageField.setPromptText("URL de l'image (optionnel)");
        imageField.setStyle("-fx-background-color: #f7fafc; -fx-border-color: #cbd5e0; -fx-border-radius: 8; -fx-padding: 10px;");
        
        // Champ pour l'artiste
        TextField artistField = new TextField();
        artistField.setPromptText("Nom de l'artiste");
        artistField.setStyle("-fx-background-color: #f7fafc; -fx-border-color: #cbd5e0; -fx-border-radius: 8; -fx-padding: 10px;");
        
        content.getChildren().addAll(
            new Label("Titre:"), titleField,
            new Label("Description:"), descriptionArea,
            new Label("URL de l'image:"), imageField,
            new Label("Artiste:"), artistField
        );
        
        addDialog.getDialogPane().setContent(content);
        
        ButtonType addButtonType = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        addDialog.getDialogPane().getButtonTypes().addAll(addButtonType, cancelButtonType);
        
        addDialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                String title = titleField.getText().trim();
                String description = descriptionArea.getText().trim();
                String imageUrl = imageField.getText().trim();
                String artist = artistField.getText().trim();
                
                if (!title.isEmpty() && !description.isEmpty()) {
                    // CrÃƒÆ’Ã‚Â©er une nouvelle Ãƒâ€¦Ã¢â‚¬Å“uvre avec statut "pending"
                    Art newArt = new Art();
                    newArt.setTitle(title);
                    newArt.setDescription(description);
                    newArt.setImageUrl(imageUrl.isEmpty() ? null : imageUrl);
                    newArt.setArtist(artist.isEmpty() ? "Artiste inconnu" : artist); // Nom de l'artiste
                    newArt.setStatus("pending"); // En attente de validation admin
                    newArt.setCreatedAt(java.time.LocalDateTime.now());
                    
                    // Afficher la confirmation
                    showConfirmationDialog(newArt);
                    return null; // Ne pas retourner l'Ãƒâ€¦Ã¢â‚¬Å“uvre directement
                }
            }
            return null;
        });
        
        addDialog.showAndWait();
    }
    
    private void showConfirmationDialog(Art art) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de publication");
        confirmationAlert.setHeaderText("ÃƒÆ’Ã…Â tes-vous sÃƒÆ’Ã‚Â»r de vouloir publier cette Ãƒâ€¦Ã¢â‚¬Å“uvre ?");
        confirmationAlert.setContentText("Titre: " + art.getTitle() + "\nArtiste: " + art.getArtist());
        
        ButtonType yesButton = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType noButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmationAlert.getButtonTypes().setAll(yesButton, noButton);
        
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            // L'utilisateur a confirmÃƒÆ’Ã‚Â©
            if (artService.createArt(art)) {
                System.out.println("ÃƒÂ¢Ã…â€œÃ¢â‚¬Â¦ Ãƒâ€¦Ã¢â‚¬â„¢uvre soumise pour validation: " + art.getTitle());
                showSubmissionMessage();
            } else {
                System.out.println("ÃƒÂ¢Ã‚ÂÃ…â€™ Erreur lors de la soumission de l'Ãƒâ€¦Ã¢â‚¬Å“uvre");
            }
        }
    }
    
    private void showSubmissionMessage() {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("Publication soumise");
        infoAlert.setHeaderText("Votre publication sera publiÃƒÆ’Ã‚Â©e lorsque l'admin l'acceptera");
        infoAlert.setContentText(null);
        infoAlert.showAndWait();
    }
    
    @FXML
    private void goHome() {
        try {
            SceneNavigator.goTo("/views/home-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onGoToBackoffice() {
        try {
            SceneNavigator.goTo("/views/backoffice-simple.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onGoToEventsFront() {
        try {
            SceneNavigator.goTo(EventsRoleRouter.resolveEventsEntryFxml());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onGoToEventsBackoffice() {
        try {
            SceneNavigator.goTo("/views/backevent-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onGoToProduit() {
        try {
            User currentUser = SceneNavigator.getCurrentUser();
            if (currentUser == null) {
                SceneNavigator.goTo("/views/signin-view.fxml");
                return;
            }
            String role = currentUser.getDtype() == null ? "" : currentUser.getDtype().toLowerCase();
            if ("artiste".equals(role)) {
                SceneNavigator.goTo("/fxml/DashboardArtiste.fxml");
            } else {
                SceneNavigator.goTo("/fxml/DashboardUser.fxml");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onGoToForum() {
        ForumModuleLauncher.openForumWindow();
    }
}


