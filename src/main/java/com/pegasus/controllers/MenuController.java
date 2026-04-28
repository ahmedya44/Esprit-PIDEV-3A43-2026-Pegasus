package com.pegasus.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.IOException;
import com.pegasus.entities.Art;
import com.pegasus.entities.MenuItem;
import com.pegasus.services.ServiceArt;
import com.pegasus.services.RecommendationService;
import com.pegasus.services.QuotesService;
import com.pegasus.services.ArtistsService;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

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
    
    // Sample menu items data
    private List<MenuItem> menuItems = new ArrayList<>();
    
    // Service pour la base de données
    private ServiceArt serviceArt = new ServiceArt();
    private RecommendationService recommendationService = new RecommendationService();
    private QuotesService quotesService = new QuotesService();
    private ArtistsService artistsService = new ArtistsService();
    
    private List<Art> allArtworks = new ArrayList<>();
    private String currentSortOrder = "default"; // default, recent, older, liked
    
    @FXML
    public void initialize() {
        initializeMenuItems();
        loadMenuItems("All");
    }
    
    private void initializeMenuItems() {
        menuItems.add(new MenuItem("Delicious Pizza", "Fresh mozzarella, tomato sauce, and basil", 12.99, "pizza.jpg"));
        menuItems.add(new MenuItem("Delicious Burger", "Beef patty with lettuce, tomato, and cheese", 10.99, "burger.jpg"));
        menuItems.add(new MenuItem("Delicious Pasta", "Creamy alfredo sauce with parmesan", 11.99, "pasta.jpg"));
        menuItems.add(new MenuItem("Crispy Fries", "Golden french fries with sea salt", 4.99, "fries.jpg"));
        menuItems.add(new MenuItem("Chicken Burger", "Grilled chicken with fresh vegetables", 9.99, "chicken_burger.jpg"));
        menuItems.add(new MenuItem("Veggie Pizza", "Bell peppers, mushrooms, and olives", 13.99, "veggie_pizza.jpg"));
        menuItems.add(new MenuItem("Spaghetti Carbonara", "Classic Italian pasta with bacon and eggs", 12.99, "carbonara.jpg"));
        menuItems.add(new MenuItem("Cheese Fries", "Loaded with melted cheese and bacon", 6.99, "cheese_fries.jpg"));
    }
    
    private void loadMenuItems(String category) {
        galleryGrid.getChildren().clear();
        
        try {
            // Charger TOUTES les œuvres depuis la base de données
            allArtworks = serviceArt.getArtsByStatus("published"); // Seulement les œuvres publiées
            
            // Filtrer et afficher les œuvres published
            displayFilteredArtworks(allArtworks);
            
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des œuvres: " + e.getMessage());
            
            // En cas d'erreur, afficher les données de test
            for (MenuItem item : menuItems) {
                if (category.equals("All") || item.getCategory().equals(category)) {
                    galleryGrid.getChildren().add(createMenuItemCard(item));
                }
            }
        }
    }
    
        
    private VBox createArtworkCard(Art art) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2); -fx-padding: 15;");
        card.setPrefSize(250, 300);
        
        // Image avec fallback coloré
        StackPane imageContainer = new StackPane();
        imageContainer.setPrefSize(220, 150);
        
        ImageView imageView = new ImageView();
        imageView.setFitWidth(220);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);
        
        String imageUrl = art.getImageUrl();
        boolean imageLoaded = false;
        
        // Essayer de charger l'image originale
        if (imageUrl != null && !imageUrl.isEmpty() && !imageUrl.contains("testttt")) {
            try {
                Image image = new Image(imageUrl, true);
                if (!image.isError()) {
                    imageView.setImage(image);
                    imageLoaded = true;
                }
            } catch (Exception e) {
                // Ignorer les erreurs
            }
        }
        
        if (!imageLoaded) {
            // Image par défaut avec une couleur différente pour chaque œuvre
            String[] colors = {"#FF6B6B", "#4ECDC4", "#45B7D1", "#96CEB4", "#FFEAA7", "#DDA0DD", "#98D8C8", "#F7DC6F"};
            String color = colors[Math.abs(art.getTitle().hashCode()) % colors.length];
            
            // Créer un fond coloré
            imageView.setStyle("-fx-background-color: " + color + "; -fx-border-color: white; -fx-border-width: 3px; -fx-border-radius: 8px;");
            
            // Ajouter le titre sur l'image
            Label imageLabel = new Label(art.getTitle().substring(0, Math.min(10, art.getTitle().length())));
            imageLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px; -fx-background-color: rgba(0,0,0,0.3); -fx-background-radius: 5px; -fx-padding: 5px;");
            
            imageContainer.getChildren().addAll(imageView, imageLabel);
        } else {
            imageContainer.getChildren().add(imageView);
        }
        
        // Titre
        Label titleLabel = new Label(art.getTitle());
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-font-family: 'Segoe UI', Arial, sans-serif;");
        titleLabel.setWrapText(true);
        
        // Artiste
        String artistName = art.getArtist();
        System.out.println("DEBUG FRONT - Affichage artwork ID " + art.getId() + " avec artiste: '" + artistName + "'");
        
        if (artistName == null || artistName.trim().isEmpty()) {
            artistName = "Artiste inconnu";
        }
        Label artistLabel = new Label(artistName);
        artistLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d; -fx-font-family: 'Segoe UI', Arial, sans-serif;");
        
        // Description
        Label descLabel;
        String description = art.getDescription();
        if (description != null && !description.isEmpty()) {
            if (description.length() > 50) {
                description = description.substring(0, 50) + "...";
            }
            descLabel = new Label(description);
            descLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #6c757d; -fx-font-family: 'Segoe UI', Arial, sans-serif; -fx-font-style: italic;");
            descLabel.setWrapText(true);
            descLabel.setMaxWidth(200);
        } else {
            descLabel = new Label("Pas de description");
            descLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #adb5bd; -fx-font-family: 'Segoe UI', Arial, sans-serif; -fx-font-style: italic;");
        }
        
        // Composant pour les likes
        HBox likeContainer = new HBox(8);
        likeContainer.setAlignment(Pos.CENTER);
        likeContainer.setStyle("-fx-background-color: #fff5f5; -fx-background-radius: 15; -fx-padding: 8px 12px; -fx-border-color: #ffe0e0; -fx-border-width: 1; -fx-border-radius: 15;");
        
        Button likeButton = new Button("LIKE");
        likeButton.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: linear-gradient(#e74c3c, #c0392b); -fx-background-radius: 12; -fx-padding: 6px 12px; -fx-cursor: hand; -fx-border-radius: 12; -fx-border-color: #c0392b; -fx-border-width: 1; -fx-font-family: 'Segoe UI', Arial, sans-serif;");
        
        Label likeCount = new Label(String.valueOf(art.getLikes()));
        likeCount.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #e74c3c; -fx-font-family: 'Segoe UI', Arial, sans-serif;");
        
        Label likesText = new Label("likes");
        likesText.setStyle("-fx-font-size: 11px; -fx-text-fill: #95a5a6; -fx-font-family: 'Segoe UI', Arial, sans-serif; -fx-font-style: italic;");
        
        // Action pour le like
        likeButton.setOnAction(e -> {
            try {
                if (serviceArt.incrementLikes(art.getId())) {
                    art.setLikes(art.getLikes() + 1);
                    likeCount.setText(String.valueOf(art.getLikes()));
                    likeButton.setScaleX(1.1);
                    likeButton.setScaleY(1.1);
                    new Timeline(new KeyFrame(Duration.millis(150), ev -> {
                        likeButton.setScaleX(1.0);
                        likeButton.setScaleY(1.0);
                    })).play();
                    System.out.println("Oeuvre " + art.getId() + " likée! Total likes: " + art.getLikes());
                }
            } catch (Exception ex) {
                System.err.println("Erreur lors du like: " + ex.getMessage());
            }
        });
        
        // Effet de survol pour le bouton like
        likeButton.setOnMouseEntered(e -> {
            likeButton.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: linear-gradient(#c0392b, #a93226); -fx-background-radius: 12; -fx-padding: 6px 12px; -fx-cursor: hand; -fx-border-radius: 12; -fx-border-color: #a93226; -fx-border-width: 1; -fx-font-family: 'Segoe UI', Arial, sans-serif;");
        });
        
        likeButton.setOnMouseExited(e -> {
            likeButton.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: linear-gradient(#e74c3c, #c0392b); -fx-background-radius: 12; -fx-padding: 6px 12px; -fx-cursor: hand; -fx-border-radius: 12; -fx-border-color: #c0392b; -fx-border-width: 1; -fx-font-family: 'Segoe UI', Arial, sans-serif;");
        });
        
        likeContainer.getChildren().addAll(likeButton, likeCount, likesText);
        
        // Boutons d'action
        HBox buttonContainer = new HBox(8);
        buttonContainer.setAlignment(Pos.CENTER);
        
        // Bouton Spotify
        Button spotifyButton = new Button("Spotify");
        spotifyButton.setStyle("-fx-font-size: 11px; -fx-background-color: #1db954; -fx-text-fill: white; -fx-background-radius: 15; -fx-padding: 6px 12px; -fx-cursor: hand;");
        spotifyButton.setOnAction(e -> openSpotifyForArtwork(art));
        
        // Bouton Suggestions
        Button suggestionsButton = new Button("Suggest");
        suggestionsButton.setStyle("-fx-font-size: 11px; -fx-background-color: #6c757d; -fx-text-fill: white; -fx-background-radius: 15; -fx-padding: 6px 12px; -fx-cursor: hand;");
        suggestionsButton.setOnAction(e -> showSuggestionsDialog(art));
        
        buttonContainer.getChildren().addAll(spotifyButton, suggestionsButton);
        
        // Ajouter les boutons Modifier et Supprimer
        HBox actionButtonsContainer = new HBox(5);
        actionButtonsContainer.setAlignment(Pos.CENTER);
        
        Button modifyButton = new Button("Modifier");
        modifyButton.setStyle("-fx-font-size: 10px; -fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 10; -fx-padding: 4px 8px; -fx-cursor: hand;");
        modifyButton.setOnAction(e -> openModifyDialog(art));
        
        Button deleteButton = new Button("Supprimer");
        deleteButton.setStyle("-fx-font-size: 10px; -fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 10; -fx-padding: 4px 8px; -fx-cursor: hand;");
        deleteButton.setOnAction(e -> openDeleteDialog(art));
        
        actionButtonsContainer.getChildren().addAll(modifyButton, deleteButton);
        
        card.getChildren().addAll(imageContainer, titleLabel, artistLabel, descLabel, likeContainer, buttonContainer, actionButtonsContainer);
        
        return card;
    }
    
    private VBox createMenuItemCard(MenuItem item) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2); -fx-padding: 15;");
        card.setPrefSize(200, 250);
        
        // Image placeholder
        ImageView imageView = new ImageView();
        imageView.setFitWidth(170);
        imageView.setFitHeight(120);
        imageView.setPreserveRatio(true);
        imageView.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 10;");
        
        // Titre
        Label titleLabel = new Label(item.getName());
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        titleLabel.setWrapText(true);
        
        // Description
        Label descLabel = new Label(item.getDescription());
        descLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #7f8c8d;");
        descLabel.setWrapText(true);
        
        // Prix
        Label priceLabel = new Label("$" + item.getPrice());
        priceLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #27ae60;");
        
        card.getChildren().addAll(imageView, titleLabel, descLabel, priceLabel);
        
        return card;
    }
    
    private void displayFilteredArtworks(List<Art> artworks) {
        galleryGrid.getChildren().clear();
        
        List<Art> filteredArtworks = artworks.stream()
            .filter(art -> "published".equals(art.getStatus()))
            .collect(Collectors.toList());
        
        for (Art art : filteredArtworks) {
            galleryGrid.getChildren().add(createArtworkCard(art));
        }
    }
    
    private void openSpotifyForArtwork(Art art) {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Spotify");
            alert.setHeaderText("Musique pour: " + art.getTitle());
            alert.setContentText("Recherche de musique adaptée à cette œuvre sur Spotify...");
            alert.showAndWait();
            
            // Ouvrir Spotify dans le navigateur
            java.awt.Desktop.getDesktop().browse(
                new java.net.URI("https://open.spotify.com/search/" + 
                java.net.URLEncoder.encode(art.getTitle() + " " + art.getArtist(), "UTF-8"))
            );
            
        } catch (Exception ex) {
            System.err.println("Error opening Spotify: " + ex.getMessage());
        }
    }
    
    private void showSuggestionsDialog(Art art) {
        try {
            // Créer une fenêtre de dialogue personnalisée
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Suggestions pour: " + art.getTitle());
            dialog.setHeaderText("Les gens qui aiment cette œuvre aiment aussi...");
            
            // Créer le contenu personnalisé
            VBox content = new VBox(15);
            content.setStyle("-fx-padding: 20px; -fx-background-color: #f8f9fa;");
            
            // Titre de l'œuvre actuelle
            Label currentArtLabel = new Label("Oeuvre actuelle: " + art.getTitle());
            currentArtLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-font-family: 'Segoe UI', Arial, sans-serif;");
            
            // Séparateur
            Separator separator = new Separator();
            separator.setStyle("-fx-opacity: 0.3;");
            
            // Section des recommandations
            Label recommendationsTitle = new Label("Recommandations similaires:");
            recommendationsTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #495057; -fx-font-family: 'Segoe UI', Arial, sans-serif;");
            
            // Container pour les recommandations
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            scrollPane.setFitToWidth(true);
            scrollPane.setPrefHeight(300);
            
            VBox recommendationsContainer = new VBox(10);
            recommendationsContainer.setStyle("-fx-padding: 10px;");
            
            try {
                // Obtenir les recommandations depuis l'API locale
                List<Art> recommendations = recommendationService.getSimilarArtworks(art.getId(), 10);
                
                if (recommendations.isEmpty()) {
                    Label noRecommendations = new Label("Aucune recommandation disponible pour le moment.");
                    noRecommendations.setStyle("-fx-font-size: 12px; -fx-text-fill: #6c757d; -fx-font-style: italic;");
                    recommendationsContainer.getChildren().add(noRecommendations);
                } else {
                    for (Art recommendedArt : recommendations) {
                        VBox recommendationCard = createRecommendationCard(recommendedArt);
                        recommendationsContainer.getChildren().add(recommendationCard);
                    }
                }
                
            } catch (Exception e) {
                Label errorLabel = new Label("Erreur lors du chargement des suggestions...");
                errorLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #dc3545; -fx-font-style: italic;");
                recommendationsContainer.getChildren().add(errorLabel);
            }
            
            scrollPane.setContent(recommendationsContainer);
            
            // Assembler le contenu
            content.getChildren().addAll(currentArtLabel, separator, recommendationsTitle, scrollPane);
            
            // Configurer le dialogue
            dialog.getDialogPane().setContent(content);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
            
            // Personnaliser le bouton OK
            Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-background-color: #007bff; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 8px 16px;");
            
            // Afficher la fenêtre
            dialog.showAndWait();
            
        } catch (Exception e) {
            System.err.println("Erreur lors de l'affichage des suggestions: " + e.getMessage());
            
            // Afficher une alerte simple en cas d'erreur
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText("Impossible d'afficher les suggestions");
            errorAlert.setContentText("Une erreur est survenue. Veuillez réessayer.");
            errorAlert.showAndWait();
        }
    }
    
    private VBox createRecommendationCard(Art art) {
        VBox card = new VBox(8);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-padding: 12px; -fx-border-color: #dee2e6; -fx-border-width: 1; -fx-border-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 5, 0, 0, 2);");
        card.setPrefWidth(400);
        
        // Titre de l'œuvre recommandée
        Label titleLabel = new Label("Oeuvre: " + art.getTitle());
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-font-family: 'Segoe UI', Arial, sans-serif; -fx-wrap-text: true;");
        
        // Artiste
        Label artistLabel = new Label("Artiste: " + art.getArtist());
        artistLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #6c757d; -fx-font-family: 'Segoe UI', Arial, sans-serif; -fx-wrap-text: true;");
        
        // Description (tronquée si trop longue)
        String description = art.getDescription();
        if (description != null && description.length() > 100) {
            description = description.substring(0, 100) + "...";
        }
        Label descriptionLabel = new Label(description);
        descriptionLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #495057; -fx-font-family: 'Segoe UI', Arial, sans-serif; -fx-wrap-text: true;");
        
        // Similarité et likes
        HBox statsContainer = new HBox(10);
        statsContainer.setAlignment(Pos.CENTER_LEFT);
        
        // Similarité - basée sur le vrai score
        String similarityText;
        String similarityColor;
        
        // Pour l'instant, on vérifie si c'est du même artiste
        if (art.getArtist() != null) {
            similarityText = "Même artiste: " + art.getArtist();
            similarityColor = "#28a745"; // Vert
        } else {
            similarityText = "Oeuvre recommandée";
            similarityColor = "#6c757d"; // Gris
        }
        
        Label similarityLabel = new Label(similarityText);
        similarityLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: " + similarityColor + "; -fx-font-weight: bold; -fx-font-family: 'Segoe UI', Arial, sans-serif;");
        
        // Likes
        Label likesLabel = new Label("Coeur " + art.getLikes() + " likes");
        likesLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #e74c3c; -fx-font-family: 'Segoe UI', Arial, sans-serif;");
        
        statsContainer.getChildren().addAll(similarityLabel, likesLabel);
        
        card.getChildren().addAll(titleLabel, artistLabel, descriptionLabel, statsContainer);
        
        // Effet de survol
        card.setOnMouseEntered(e -> {
            card.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 8; -fx-padding: 12px; -fx-border-color: #007bff; -fx-border-width: 2; -fx-border-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 12, 0, 0, 5); -fx-cursor: hand;");
        });
        
        card.setOnMouseExited(e -> {
            card.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-padding: 12px; -fx-border-color: #dee2e6; -fx-border-width: 1; -fx-border-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 5, 0, 0, 2); -fx-cursor: hand;");
        });
        
        return card;
    }
    
    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText().toLowerCase().trim();
        
        if (searchTerm.isEmpty()) {
            displayFilteredArtworks(allArtworks);
            return;
        }
        
        List<Art> filteredArtworks = allArtworks.stream()
            .filter(art -> "published".equals(art.getStatus()))
            .filter(art -> 
                art.getTitle().toLowerCase().contains(searchTerm) ||
                art.getArtist().toLowerCase().contains(searchTerm) ||
                art.getDescription().toLowerCase().contains(searchTerm)
            )
            .collect(Collectors.toList());
        
        galleryGrid.getChildren().clear();
        for (Art art : filteredArtworks) {
            galleryGrid.getChildren().add(createArtworkCard(art));
        }
    }
    
    @FXML
    private void handleClearSearch() {
        searchField.clear();
        displayFilteredArtworks(allArtworks);
    }
    
    @FXML
    private void handleResetSort() {
        toggleRecent.setSelected(false);
        toggleOlder.setSelected(false);
        toggleLiked.setSelected(false);
        currentSortOrder = "default";
        displayFilteredArtworks(allArtworks);
    }
    
    @FXML
    private void handleSortRecent() {
        try {
            toggleOlder.setSelected(false);
            toggleLiked.setSelected(false);
            if (toggleRecent.isSelected()) {
                currentSortOrder = "recent";
                System.out.println("Sorting by most recent");
            } else {
                currentSortOrder = "default";
                System.out.println("Reset to default order");
            }
            applyCurrentSortAndDisplay();
        } catch (Exception e) {
            System.err.println("Error sorting by recent: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleSortOlder() {
        try {
            toggleRecent.setSelected(false);
            toggleLiked.setSelected(false);
            if (toggleOlder.isSelected()) {
                currentSortOrder = "older";
                System.out.println("Sorting by oldest");
            } else {
                currentSortOrder = "default";
                System.out.println("Reset to default order");
            }
            applyCurrentSortAndDisplay();
        } catch (Exception e) {
            System.err.println("Error sorting by older: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleSortLiked() {
        try {
            toggleRecent.setSelected(false);
            toggleOlder.setSelected(false);
            if (toggleLiked.isSelected()) {
                currentSortOrder = "liked";
                System.out.println("Sorting by most liked");
            } else {
                currentSortOrder = "default";
                System.out.println("Reset to default order");
            }
            applyCurrentSortAndDisplay();
        } catch (Exception e) {
            System.err.println("Error sorting by liked: " + e.getMessage());
        }
    }
    
    private void applyCurrentSortAndDisplay() {
        List<Art> filteredArtworks = allArtworks.stream()
            .filter(art -> "published".equals(art.getStatus()))
            .collect(Collectors.toList());
        
        switch (currentSortOrder) {
            case "recent":
                filteredArtworks.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
                break;
            case "older":
                filteredArtworks.sort((a, b) -> a.getCreatedAt().compareTo(b.getCreatedAt()));
                break;
            case "liked":
                filteredArtworks.sort((a, b) -> Integer.compare(b.getLikes(), a.getLikes()));
                break;
            default:
                // Ordre par défaut
                break;
        }
        
        galleryGrid.getChildren().clear();
        for (Art art : filteredArtworks) {
            galleryGrid.getChildren().add(createArtworkCard(art));
        }
    }
    
        
    private void openModifyDialog(Art art) {
        try {
            DialogPane dialogPane = new DialogPane();
            dialogPane.setHeaderText("Modifier l'œuvre");
            
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            
            TextField titleField = new TextField(art.getTitle());
            TextField artistField = new TextField(art.getArtist());
            TextField urlField = new TextField(art.getImageUrl());
            TextArea descArea = new TextArea(art.getDescription());
            descArea.setPrefRowCount(3);
            
            grid.add(new Label("Titre:"), 0, 0);
            grid.add(titleField, 1, 0);
            grid.add(new Label("Artiste:"), 0, 1);
            grid.add(artistField, 1, 1);
            grid.add(new Label("URL Image:"), 0, 2);
            grid.add(urlField, 1, 2);
            grid.add(new Label("Description:"), 0, 3);
            grid.add(descArea, 1, 3);
            
            dialogPane.setContent(grid);
            
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Modifier une œuvre");
            
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            
            Optional<ButtonType> result = dialog.showAndWait();
            
            if (result.isPresent() && result.get() == ButtonType.OK) {
                art.setTitle(titleField.getText().trim());
                art.setArtist(artistField.getText().trim());
                art.setDescription(descArea.getText().trim());
                art.setImageUrl(urlField.getText().trim());
                
                if (serviceArt.updateArt(art)) {
                    System.out.println("Œuvre modifiée avec succès!");
                    loadMenuItems("All");
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la modification: " + e.getMessage());
        }
    }
    
    private void openDeleteDialog(Art art) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Supprimer l'œuvre");
            alert.setHeaderText("Voulez-vous vraiment supprimer cette œuvre ?");
            alert.setContentText("Titre: " + art.getTitle() + "\nArtiste: " + art.getArtist());
            
            if (alert.showAndWait().get() == ButtonType.OK) {
                if (serviceArt.deleteArt(art.getId())) {
                    System.out.println("Œuvre supprimée avec succès!");
                    loadMenuItems("All");
                } else {
                    System.err.println("Erreur lors de la suppression");
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression: " + e.getMessage());
        }
    }
    
    @FXML
    private void goHome() {
        try {
            System.out.println("Retour à l'accueil");
            SceneNavigator.goTo("/views/home-view.fxml");
        } catch (Exception e) {
            System.err.println("Erreur lors du retour à l'accueil: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleQuotes() {
        String quote = quotesService.getFormattedQuote();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("💬 Citation d'artiste");
        alert.setHeaderText(null);
        alert.setContentText(quote);
        alert.showAndWait();
    }
    
    @FXML
    private void handleArtists() {
        // Créer une boîte de dialogue pour entrer le nom de l'artiste
        javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog();
        dialog.setTitle("🎨 Biographie d'artiste");
        dialog.setHeaderText("Entrez le nom d'un artiste");
        dialog.setContentText("Nom de l'artiste :");
        
        // Suggestions d'artistes
        dialog.getDialogPane().setExpanded(true);
        
        dialog.showAndWait().ifPresent(artistName -> {
            if (!artistName.trim().isEmpty()) {
                String biography = artistsService.getFormattedBiography(artistName.trim());
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("🎨 " + artistName);
                alert.setHeaderText(null);
                alert.setContentText(biography);
                alert.getDialogPane().setMinWidth(500);
                alert.showAndWait();
            }
        });
    }
    
    // Méthodes de filtrage
    @FXML
    private void handleFilterAll() {
        loadMenuItems("All");
    }
    
    @FXML
    private void handleFilterBurger() {
        loadMenuItems("Burger");
    }
    
    @FXML
    private void handleFilterPizza() {
        loadMenuItems("Pizza");
    }
    
    @FXML
    private void handleFilterPasta() {
        loadMenuItems("Pasta");
    }
    
    @FXML
    private void handleFilterFries() {
        loadMenuItems("Fries");
    }
    
    @FXML
    private void handleOpenGallery() {
        try {
            SceneNavigator.goTo("/views/gallery-main-view.fxml");
        } catch (IOException e) {
            System.err.println("Error opening gallery: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleAddArtwork() {
        try {
            System.out.println("Ouverture du formulaire d'ajout d'œuvre...");
            
            // Créer un dialogue simple qui fonctionne
            DialogPane dialogPane = new DialogPane();
            dialogPane.setHeaderText("Ajouter une nouvelle œuvre");
            
            // Créer les champs
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            
            TextField titleField = new TextField();
            titleField.setPromptText("Titre de l'œuvre");
            
            TextField artistField = new TextField();
            artistField.setPromptText("Nom de l'artiste");
            
            TextField urlField = new TextField();
            urlField.setPromptText("URL de l'image");
            
            TextArea descArea = new TextArea();
            descArea.setPromptText("Description");
            descArea.setPrefRowCount(3);
            
            grid.add(new Label("Titre:"), 0, 0);
            grid.add(titleField, 1, 0);
            grid.add(new Label("Artiste:"), 0, 1);
            grid.add(artistField, 1, 1);
            grid.add(new Label("URL Image:"), 0, 2);
            grid.add(urlField, 1, 2);
            grid.add(new Label("Description:"), 0, 3);
            grid.add(descArea, 1, 3);
            
            dialogPane.setContent(grid);
            
            // Créer le dialogue
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Ajouter une œuvre");
            
            // Ajouter les boutons
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            
            // Afficher et attendre la réponse
            Optional<ButtonType> result = dialog.showAndWait();
            
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Créer l'objet Art 
                Art newArt = new Art();
                newArt.setTitle(titleField.getText().trim());
                
                // Gérer le champ artiste
                String artistName = artistField.getText().trim();
                if (artistName.isEmpty()) {
                    artistName = "Artiste inconnu";
                }
                newArt.setArtist(artistName);
                
                newArt.setDescription(descArea.getText().trim());
                newArt.setImageUrl(urlField.getText().trim());
                newArt.setStatus("pending"); // en attente de validation
                newArt.setCreatedAt(LocalDateTime.now());
                
                System.out.println("Ajout de l'œuvre - Artiste: " + artistName);
                
                // Sauvegarder avec retry
                boolean success = false;
                int retryCount = 0;
                while (!success && retryCount < 3) {
                    try {
                        serviceArt.createArt(newArt);
                        success = true;
                        System.out.println("Artwork added successfully!");
                        
                        // Recharger la galerie
                        loadMenuItems("All");
                        
                    } catch (Exception e) {
                        retryCount++;
                        System.err.println("Retry " + retryCount + ": " + e.getMessage());
                        if (retryCount < 3) {
                            try {
                                Thread.sleep(1000); // Attendre 1 seconde
                            } catch (InterruptedException ie) {
                                Thread.currentThread().interrupt();
                                break;
                            }
                        }
                    }
                }
                
                if (success) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Publication réussie");
                    successAlert.setHeaderText("Œuvre soumise avec succès !");
                    successAlert.setContentText("Votre publication sera publiée dès que l'administrateur l'acceptera.");
                    successAlert.showAndWait();
                    
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur");
                    errorAlert.setHeaderText("Échec de la publication");
                    errorAlert.setContentText("Impossible de sauvegarder l'œuvre. Veuillez réessayer.");
                    errorAlert.showAndWait();
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error adding artwork: " + e.getMessage());
            
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText("Erreur technique");
            errorAlert.setContentText("Une erreur est survenue. Veuillez réessayer plus tard.");
            errorAlert.showAndWait();
        }
    }
}
