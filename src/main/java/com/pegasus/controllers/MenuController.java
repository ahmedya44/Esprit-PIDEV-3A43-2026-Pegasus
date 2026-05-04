package com.pegasus.controllers;

import com.pegasus.entities.Art;
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
        loadArtworks(null);
    }
    
    private void loadArtworks(String filter) {
        List<Art> allArtworks = artService.getAllArts();
        galleryGrid.getChildren().clear();
        
        for (Art art : allArtworks) {
            if ("published".equals(art.getStatus())) {
                if (filter == null || filter.isEmpty() || 
                    art.getTitle().toLowerCase().contains(filter.toLowerCase()) || 
                    art.getDescription().toLowerCase().contains(filter.toLowerCase())) {
                    galleryGrid.getChildren().add(createArtworkCard(art));
                }
            }
        }
    }
    
    private VBox createArtworkCard(Art art) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-padding: 15; -fx-border-color: #e9ecef; -fx-border-width: 1;");
        card.setPrefWidth(300);
        
        ImageView imageView = new ImageView();
        try {
            String imageUrl = art.getImageUrl();
            System.out.println("🖼️ Tentative de chargement de l'image: " + imageUrl);
            
            if (imageUrl != null && !imageUrl.isEmpty()) {
                // Forcer le chargement de VOTRE image sans fallback automatique
                Image image = new Image(imageUrl, false); // false = chargement synchrone
                
                // TOUJOURS essayer d'afficher votre image, même en cas d'erreur
                imageView.setImage(image);
                
                if (image.isError()) {
                    System.err.println("❌ Erreur de chargement de l'image: " + imageUrl);
                    System.err.println("❌ Exception: " + image.getException());
                    // Ne PAS remplacer par une image aléatoire - garder votre image même si elle a une erreur
                    System.out.println("⚠️ Conservation de votre image malgré l'erreur");
                } else {
                    System.out.println("✅ Image chargée avec succès: " + imageUrl);
                }
                
                // Listener pour suivre l'état mais ne PAS remplacer
                image.errorProperty().addListener((obs, oldVal, newVal) -> {
                    if (newVal) {
                        System.err.println("❌ Erreur asynchrone de l'image: " + imageUrl);
                        System.err.println("❌ Exception: " + image.getException());
                        System.out.println("⚠️ Votre image reste affichée malgré l'erreur");
                    }
                });
                
                // Listener pour le succès
                image.progressProperty().addListener((obs, oldVal, newVal) -> {
                    if (newVal.doubleValue() == 1.0 && !image.isError()) {
                        System.out.println("✅ Image 100% chargée: " + imageUrl);
                    }
                });
                
            } else {
                System.out.println("📷 Aucune URL fournie - pas d'image affichée");
                // Ne PAS mettre d'image par défaut
            }
            
            imageView.setFitWidth(280);
            imageView.setFitHeight(200);
            imageView.setPreserveRatio(true);
            
        } catch (Exception e) {
            System.err.println("❌ Exception lors du chargement de l'image: " + e.getMessage());
            e.printStackTrace();
            // Ne PAS mettre d'image par défaut - laisser vide
            System.out.println("📷 Pas d'image affichée suite à l'exception");
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
        
        Button suggestionButton = new Button("💡 Suggestions");
        suggestionButton.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-background-radius: 12; -fx-padding: 8px 14px; -fx-font-size: 12px;");
        suggestionButton.setOnAction(e -> showSuggestionsDialog(art));
        
        Button spotifyButton = new Button("🎵");
        spotifyButton.setStyle("-fx-background-color: #1db954; -fx-text-fill: white; -fx-background-radius: 12; -fx-padding: 8px 14px; -fx-font-size: 14px;");
        spotifyButton.setOnAction(e -> handleSpotify(art));
        
        Button editButton = new Button("📝");
        editButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 12; -fx-padding: 8px 14px; -fx-font-size: 14px;");
        editButton.setOnAction(e -> showEditDialog(art));
        
        Button deleteButton = new Button("🗑");
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
            spotifyAlert.setContentText("Voulez-vous ouvrir Spotify pour cette œuvre ?\n\nTitre : " + art.getTitle() + "\nArtiste : " + art.getArtist());
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
            errorAlert.setContentText("Vérifiez votre connexion et réessayez.");
            errorAlert.showAndWait();
        }
    }
    
    private void showSuggestionsDialog(Art art) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Suggestions pour : " + art.getTitle());
        dialog.setHeaderText("Œuvres similaires ou recommandées");
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        
        try {
            List<Art> recommendations = recommendationService.getSimilarArtworks(art.getId(), 4);
            if (recommendations == null || recommendations.isEmpty()) {
                content.getChildren().add(new Label("Aucune suggestion disponible pour le moment."));
            } else {
                for (Art rec : recommendations) {
                    Label recLabel = new Label("• " + rec.getTitle() + " — " + rec.getArtist());
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
        dialog.setTitle("Modifier l'œuvre");
        dialog.setHeaderText("Modifier les informations de l'œuvre");
        
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
                errorAlert.setContentText("Impossible de mettre à jour l'œuvre.");
                errorAlert.showAndWait();
            }
        }
    }
    
    private void handleDeleteArt(Art art) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Supprimer l'œuvre");
        confirm.setHeaderText("Voulez-vous vraiment supprimer cette œuvre ?");
        confirm.setContentText(art.getTitle());
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (artService.deleteArt(art.getId())) {
                loadArtworks(searchField.getText().trim());
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText("Suppression impossible");
                errorAlert.setContentText("Impossible de supprimer l'œuvre.");
                errorAlert.showAndWait();
            }
        }
    }
    
    private HBox createSocialButtons(Art art) {
        HBox buttons = new HBox(15);
        buttons.setAlignment(Pos.CENTER_LEFT);
        
        // Charger les vrais compteurs depuis la base de données
        int currentLikes = dislikeService.getDislikeCount(art.getId()); // Utilise la même table pour likes
        int currentDislikes = dislikeService.getDislikeCount(art.getId());
        
        likeCounts.put(art.getId(), currentLikes);
        dislikeCounts.put(art.getId(), currentDislikes);
        
        // Bouton Like avec compteur
        VBox likeContainer = new VBox(2);
        likeContainer.setAlignment(Pos.CENTER);
        
        Button likeButton = new Button("👍");
        likeButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 8px 12px; -fx-font-size: 16px;");
        
        Label likeCount = new Label(String.valueOf(currentLikes));
        likeCount.setStyle("-fx-text-fill: #28a745; -fx-font-size: 12px; -fx-font-weight: bold;");
        
        likeContainer.getChildren().addAll(likeButton, likeCount);
        
        // Bouton Dislike avec compteur
        VBox dislikeContainer = new VBox(2);
        dislikeContainer.setAlignment(Pos.CENTER);
        
        Button dislikeButton = new Button("👎");
        dislikeButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 8px 12px; -fx-font-size: 16px;");
        
        Label dislikeCount = new Label(String.valueOf(currentDislikes));
        dislikeCount.setStyle("-fx-text-fill: #dc3545; -fx-font-size: 12px; -fx-font-weight: bold;");
        
        dislikeContainer.getChildren().addAll(dislikeButton, dislikeCount);
        
        // Bouton Commentaire
        Button commentButton = new Button("💬");
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
            // Recharger le compteur depuis la base de données
            int newCount = dislikeService.getDislikeCount(art.getId());
            likeCounts.put(art.getId(), newCount);
            
            // Animation de poussée vers le haut
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
            
            // Mettre à jour le compteur
            likeCount.setText(String.valueOf(newCount));
            
            pushUpAnimation.play();
            colorAnimation.play();
            
            System.out.println("Like ajouté pour: " + art.getTitle() + " (Total: " + newCount + ")");
        }
    }
    
    private void handleDislikeWithAnimation(Art art, Button dislikeButton, Label dislikeCount, String sessionId) {
        if (dislikeService.addDislike(art.getId(), sessionId)) {
            // Recharger le compteur depuis la base de données
            int newCount = dislikeService.getDislikeCount(art.getId());
            dislikeCounts.put(art.getId(), newCount);
            
            // Animation de poussée vers le bas
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
            
            // Mettre à jour le compteur
            dislikeCount.setText(String.valueOf(newCount));
            
            pushDownAnimation.play();
            colorAnimation.play();
            
            System.out.println("Dislike ajouté pour: " + art.getTitle() + " (Total: " + newCount + ")");
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
            Label noCommentsLabel = new Label("Soyez le premier à commenter !");
            noCommentsLabel.setStyle("-fx-text-fill: #6c757d; -fx-font-style: italic;");
            container.getChildren().add(noCommentsLabel);
            return;
        }
        
        // Séparer les commentaires principaux des réponses
        List<ServiceArtComment.Comment> mainComments = new ArrayList<>();
        List<ServiceArtComment.Comment> replies = new ArrayList<>();
        
        for (ServiceArtComment.Comment comment : comments) {
            if (comment.getContent().startsWith("↩ @")) {
                replies.add(comment);
            } else {
                mainComments.add(comment);
            }
        }
        
        // Afficher d'abord les commentaires principaux avec leurs réponses
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
            
            // Bouton de réponse minimaliste
            Button replyButton = new Button("↩");
            replyButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #17a2b8; -fx-border-color: #17a2b8; -fx-border-width: 1; -fx-background-radius: 10; -fx-padding: 4px 8px; -fx-font-size: 12px; -fx-cursor: hand;");
            replyButton.setOnAction(e -> openReplyDialog(mainComment, art));
            
            HBox contentWithReply = new HBox(10);
            contentWithReply.setAlignment(Pos.CENTER_LEFT);
            contentWithReply.getChildren().addAll(contentLabel, replyButton);
            
            commentBox.getChildren().addAll(headerBox, contentWithReply);
            
            // Ajouter les réponses sous ce commentaire
            for (ServiceArtComment.Comment reply : replies) {
                if (reply.getContent().contains("@" + mainComment.getUsername())) {
                    // Créer une boîte de réponse plus petite et indentée
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
                    
                    // Extraire le contenu de la réponse (enlever "↩ @username: ")
                    String replyContent = reply.getContent().replaceFirst("↩ @" + mainComment.getUsername() + ": ", "");
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
        replyDialog.setTitle("Répondre au commentaire");
        replyDialog.setHeaderText("Répondre à: " + parentComment.getUsername());
        
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setPrefWidth(400);
        
        // Affichage du commentaire parent
        Label parentLabel = new Label("@" + parentComment.getUsername() + ": " + parentComment.getContent());
        parentLabel.setStyle("-fx-text-fill: #6c757d; -fx-font-style: italic; -fx-wrap-text: true;");
        parentLabel.setPrefWidth(360);
        
        // Champ pour la réponse
        TextField replyNameField = new TextField();
        replyNameField.setPromptText("Votre nom");
        
        TextArea replyArea = new TextArea();
        replyArea.setPromptText("Votre réponse...");
        replyArea.setPrefWidth(360);
        replyArea.setPrefHeight(60);
        replyArea.setWrapText(true);
        
        content.getChildren().addAll(parentLabel, new Separator(), replyNameField, replyArea);
        
        replyDialog.getDialogPane().setContent(content);
        
        ButtonType replyButton = new ButtonType("Répondre", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        replyDialog.getDialogPane().getButtonTypes().addAll(replyButton, cancelButton);
        
        replyDialog.setResultConverter(dialogButton -> {
            if (dialogButton == replyButton) {
                String name = replyNameField.getText().trim();
                String reply = replyArea.getText().trim();
                
                if (!name.isEmpty() && !reply.isEmpty()) {
                    String replyText = "↩ @" + parentComment.getUsername() + ": " + reply;
                    return replyText;
                }
            }
            return null;
        });
        
        Optional<String> result = replyDialog.showAndWait();
        result.ifPresent(replyText -> {
            if (commentService.addComment(art.getId(), replyNameField.getText().trim(), replyText)) {
                System.out.println("✅ Réponse ajoutée: " + replyText);
                
                // Fermer le dialog principal et le rouvrir pour rafraîchir
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
        dialog.setTitle("💬 Citations d'Art");
        dialog.setHeaderText("Citations célèbres d'artistes et penseurs");
        
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
        
        // Bouton pour rafraîchir les citations
        Button refreshButton = new Button("🔄 Nouvelle citation");
        refreshButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 8px 16px;");
        refreshButton.setOnAction(e -> {
            Dialog<Void> newDialog = new Dialog<>();
            newDialog.setTitle("💬 Citation du jour");
            newDialog.setHeaderText("Citation aléatoire");
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
        dialog.setTitle("👨‍🎨 Artistes Célèbres");
        dialog.setHeaderText("Biographies des grands maîtres");
        
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
            "Salvador Dalí",
            "Frida Kahlo",
            "Paul Cézanne"
        };
        
        VBox artistsList = new VBox(8);
        artistsList.setStyle("-fx-padding: 10px;");
        
        for (String artist : artists) {
            Button artistBtn = new Button("👨‍🎨 " + artist);
            artistBtn.setStyle("-fx-background-color: #667eea; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 10px 16px; -fx-font-size: 12px; -fx-cursor: hand; -fx-max-width: Infinity;");
            artistBtn.setMaxWidth(Double.MAX_VALUE);
            artistBtn.setOnAction(e -> showArtistBiography(artist));
            artistsList.getChildren().add(artistBtn);
        }
        
        ScrollPane scrollPane = new ScrollPane(artistsList);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(400);
        
        content.getChildren().addAll(new Label("Sélectionnez un artiste pour sa biographie:"), scrollPane);
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.showAndWait();
    }
    
    private void showArtistBiography(String artistName) {
        Dialog<Void> bioDialog = new Dialog<>();
        bioDialog.setTitle("Biographie : " + artistName);
        bioDialog.setHeaderText("👨‍🎨 " + artistName);
        
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
        System.out.println("📅 Tri par plus récent");
        loadArtworksSortedByDate(true); // true = plus récent
    }
    
    @FXML
    private void handleSortOlder() {
        System.out.println("📜 Tri par plus ancien");
        loadArtworksSortedByDate(false); // false = plus ancien
    }
    
    @FXML
    private void handleSortLiked() {
        System.out.println("❤️ Tri par plus liké");
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
            if ("published".equals(art.getStatus())) {
                galleryGrid.getChildren().add(createArtworkCard(art));
            }
        }
        
        System.out.println("✅ Tri effectué : " + (mostRecent ? "plus récent" : "plus ancien"));
    }
    
    private void loadArtworksSortedByLikes() {
        List<Art> artworks = artService.getAllArts();
        
        // Trier par nombre de likes (en utilisant les compteurs actuels)
        artworks.sort((a1, a2) -> {
            int likes1 = likeCounts.getOrDefault(a1.getId(), 0);
            int likes2 = likeCounts.getOrDefault(a2.getId(), 0);
            return Integer.compare(likes2, likes1); // ordre décroissant
        });
        
        // Filtrer et afficher
        galleryGrid.getChildren().clear();
        for (Art art : artworks) {
            if ("published".equals(art.getStatus())) {
                galleryGrid.getChildren().add(createArtworkCard(art));
            }
        }
        
        System.out.println("✅ Tri effectué : plus liké");
    }
    
    @FXML
    private void handleResetSort() {
        searchField.clear();
        loadArtworks(null);
    }
    
    @FXML
    private void handleAddArtwork() {
        System.out.println("➕ Ajout d'œuvre cliqué");
        showAddArtworkDialog();
    }
    
    private void showAddArtworkDialog() {
        Dialog<Art> addDialog = new Dialog<>();
        addDialog.setTitle("Ajouter une œuvre");
        addDialog.setHeaderText("Ajouter une nouvelle œuvre à la collection");
        
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        content.setPrefWidth(500);
        
        // Champ pour le titre
        TextField titleField = new TextField();
        titleField.setPromptText("Titre de l'œuvre");
        titleField.setStyle("-fx-background-color: #f7fafc; -fx-border-color: #cbd5e0; -fx-border-radius: 8; -fx-padding: 10px;");
        
        // Champ pour la description
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Description de l'œuvre...");
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
                    // Créer une nouvelle œuvre avec statut "pending"
                    Art newArt = new Art();
                    newArt.setTitle(title);
                    newArt.setDescription(description);
                    newArt.setImageUrl(imageUrl.isEmpty() ? null : imageUrl);
                    newArt.setArtist(artist.isEmpty() ? "Artiste inconnu" : artist); // Nom de l'artiste
                    newArt.setStatus("pending"); // En attente de validation admin
                    newArt.setCreatedAt(java.time.LocalDateTime.now());
                    
                    // Afficher la confirmation
                    showConfirmationDialog(newArt);
                    return null; // Ne pas retourner l'œuvre directement
                }
            }
            return null;
        });
        
        addDialog.showAndWait();
    }
    
    private void showConfirmationDialog(Art art) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de publication");
        confirmationAlert.setHeaderText("Êtes-vous sûr de vouloir publier cette œuvre ?");
        confirmationAlert.setContentText("Titre: " + art.getTitle() + "\nArtiste: " + art.getArtist());
        
        ButtonType yesButton = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType noButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmationAlert.getButtonTypes().setAll(yesButton, noButton);
        
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            // L'utilisateur a confirmé
            if (artService.createArt(art)) {
                System.out.println("✅ Œuvre soumise pour validation: " + art.getTitle());
                showSubmissionMessage();
            } else {
                System.out.println("❌ Erreur lors de la soumission de l'œuvre");
            }
        }
    }
    
    private void showSubmissionMessage() {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("Publication soumise");
        infoAlert.setHeaderText("Votre publication sera publiée lorsque l'admin l'acceptera");
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
}

