package com.pegasus.controllers.front;

import com.pegasus.controllers.SceneNavigator;
import com.pegasus.controllers.EventsRoleRouter;
import com.pegasus.entities.Art;
import com.pegasus.entities.User;
import com.pegasus.services.RecommendationService;
import com.pegasus.services.ServiceArt;
import com.pegasus.services.ServiceArtComment;
import com.pegasus.services.ServiceArtDislike;
import com.pegasus.services.ServiceArtLike;
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
import javafx.concurrent.Task;
import java.util.Map;
import java.util.ArrayList;
import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MenuController {
    
    @FXML
    private TilePane galleryGrid;

    @FXML
    private Label galleryCountLabel;

    @FXML
    private Label galleryStatusLabel;
    
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

    @FXML
    private Button navCoursesDashboardButton;

    @FXML
    private Button navProfileButton;

    @FXML
    private Button navAuthButton;

    @FXML
    private MenuButton navAccountMenu;
    
    private ServiceArt artService = new ServiceArt();
    private ServiceArtComment commentService = new ServiceArtComment();
    private ServiceArtDislike dislikeService = new ServiceArtDislike();
    private ServiceArtLike likeService = new ServiceArtLike();
    
    // Compteurs pour likes/dislikes
    private Map<Integer, Integer> likeCounts = new ConcurrentHashMap<>();
    private Map<Integer, Integer> dislikeCounts = new ConcurrentHashMap<>();
    private Map<Integer, ReactionState> reactionStates = new ConcurrentHashMap<>();
    private RecommendationService recommendationService = new RecommendationService();
    private QuotesService quotesService = new QuotesService();
    private ArtistsService artistsService = new ArtistsService();
    private String currentFilter = "";
    private GallerySort currentSort = GallerySort.NONE;

    private enum GallerySort {
        NONE,
        NEWEST,
        OLDEST,
        LIKED
    }

    private enum ReactionState {
        NONE,
        LIKED,
        DISLIKED
    }
    
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
        User currentUser = SceneNavigator.getCurrentUser();
        boolean loggedIn = currentUser != null;
        if (navBackofficeButton != null) {
            boolean isAdmin = currentUser != null && "admin".equalsIgnoreCase(currentUser.getDtype());
            navBackofficeButton.setVisible(isAdmin);
            navBackofficeButton.setManaged(isAdmin);
        }
        if (navEventsBackofficeButton != null) {
            navEventsBackofficeButton.setVisible(false);
            navEventsBackofficeButton.setManaged(false);
        }
        if (navCoursesDashboardButton != null) {
            boolean isArtist = currentUser != null && "artiste".equalsIgnoreCase(currentUser.getDtype());
            navCoursesDashboardButton.setVisible(isArtist);
            navCoursesDashboardButton.setManaged(isArtist);
        }
        if (navProfileButton != null) {
            navProfileButton.setVisible(false);
            navProfileButton.setManaged(false);
        }
        if (navAuthButton != null) {
            navAuthButton.setVisible(!loggedIn);
            navAuthButton.setManaged(!loggedIn);
            navAuthButton.setText("Sign In");
        }
        if (navAccountMenu != null) {
            navAccountMenu.setVisible(loggedIn);
            navAccountMenu.setManaged(loggedIn);
            navAccountMenu.setText("\uD83D\uDC64");
        }
    }
    
    private void loadArtworks(String filter) {
        if (galleryGrid == null) {
            return;
        }
        currentFilter = filter == null ? "" : filter.trim();
        loadGalleryAsync();
    }

    private void loadGalleryAsync() {
        galleryGrid.getChildren().clear();
        showGalleryMessage("Loading gallery...", "Preparing artwork cards.");

        Task<List<Art>> task = new Task<>() {
            @Override
            protected List<Art> call() {
                List<Art> allArtworks = artService.getAllArts();
                if (allArtworks == null) {
                    return List.of();
                }
                String reactionSessionKey = resolveReactionSessionKey();
                String normalizedFilter = currentFilter.toLowerCase();
                List<Art> filtered = allArtworks.stream()
                        .filter(art -> isVisibleStatus(art.getStatus()))
                        .filter(art -> matchesFilter(art, normalizedFilter))
                        .collect(Collectors.toCollection(ArrayList::new));
                filtered.forEach(art -> {
                    likeCounts.put(art.getId(), art.getLikes());
                    dislikeCounts.put(art.getId(), art.getDislikes());
                    reactionStates.put(art.getId(), resolveReactionState(art.getId(), reactionSessionKey));
                });
                sortArtworks(filtered);
                return filtered;
            }
        };

        task.setOnSucceeded(event -> renderArtworks(task.getValue()));
        task.setOnFailed(event -> {
            Throwable error = task.getException();
            System.err.println("loadArtworks error: " + (error == null ? "unknown" : error.getMessage()));
            galleryGrid.getChildren().clear();
            showGalleryMessage("Could not load gallery", "Please try refreshing the gallery.");
        });

        Thread thread = new Thread(task, "pegasus-gallery-loader");
        thread.setDaemon(true);
        thread.start();
    }

    private boolean matchesFilter(Art art, String normalizedFilter) {
        if (normalizedFilter == null || normalizedFilter.isBlank()) {
            return true;
        }
        String title = art.getTitle() == null ? "" : art.getTitle().toLowerCase();
        String description = art.getDescription() == null ? "" : art.getDescription().toLowerCase();
        String artist = art.getArtist() == null ? "" : art.getArtist().toLowerCase();
        return title.contains(normalizedFilter)
                || description.contains(normalizedFilter)
                || artist.contains(normalizedFilter);
    }

    private void sortArtworks(List<Art> artworks) {
        if (currentSort == GallerySort.NEWEST) {
            artworks.sort(Comparator.comparing(Art::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder())).reversed());
        } else if (currentSort == GallerySort.OLDEST) {
            artworks.sort(Comparator.comparing(Art::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder())));
        } else if (currentSort == GallerySort.LIKED) {
            artworks.sort((a1, a2) -> Integer.compare(
                    likeCounts.getOrDefault(a2.getId(), 0),
                    likeCounts.getOrDefault(a1.getId(), 0)
            ));
        }
    }

    private void renderArtworks(List<Art> artworks) {
        galleryGrid.getChildren().clear();
        if (artworks == null || artworks.isEmpty()) {
            showGalleryMessage("No artworks found", "Try a different search or clear the filters.");
            updateGalleryCount(0);
            return;
        }
        clearGalleryMessage();
        for (Art art : artworks) {
            galleryGrid.getChildren().add(createArtworkCard(art));
        }
        updateGalleryCount(artworks.size());
    }

    private void updateGalleryCount(int count) {
        if (galleryCountLabel != null) {
            galleryCountLabel.setText(count + (count == 1 ? " artwork" : " artworks"));
        }
    }

    private void showGalleryMessage(String title, String copy) {
        if (galleryStatusLabel != null) {
            galleryStatusLabel.setText(title + " - " + copy);
            galleryStatusLabel.setVisible(true);
            galleryStatusLabel.setManaged(true);
        }
        if (galleryCountLabel != null) {
            galleryCountLabel.setText(title);
        }
    }

    private void clearGalleryMessage() {
        if (galleryStatusLabel != null) {
            galleryStatusLabel.setText("");
            galleryStatusLabel.setVisible(false);
            galleryStatusLabel.setManaged(false);
        }
    }

    private String resolveReactionSessionKey() {
        User currentUser = SceneNavigator.getCurrentUser();
        if (currentUser == null) {
            return null;
        }
        if (currentUser.getId() != null && currentUser.getId() > 0) {
            return "user:" + currentUser.getId();
        }
        if (currentUser.getEmail() != null && !currentUser.getEmail().isBlank()) {
            return "email:" + currentUser.getEmail().trim().toLowerCase();
        }
        if (currentUser.getUsername() != null && !currentUser.getUsername().isBlank()) {
            return "username:" + currentUser.getUsername().trim().toLowerCase();
        }
        return null;
    }

    private ReactionState resolveReactionState(int artId, String reactionSessionKey) {
        if (reactionSessionKey == null) {
            return ReactionState.NONE;
        }
        if (likeService.hasLiked(artId, reactionSessionKey)) {
            return ReactionState.LIKED;
        }
        if (dislikeService.hasDisliked(artId, reactionSessionKey)) {
            return ReactionState.DISLIKED;
        }
        return ReactionState.NONE;
    }
    
    private VBox createArtworkCard(Art art) {
        VBox card = new VBox(12);
        card.getStyleClass().add("gallery-art-card");
        card.setPrefWidth(292);
        
        ImageView imageView = new ImageView(loadArtworkImage(art.getImageUrl()));
        imageView.getStyleClass().add("gallery-art-image");
        imageView.setFitWidth(268);
        imageView.setFitHeight(178);
        imageView.setPreserveRatio(false);
        
        Label titleLabel = new Label(safeText(art.getTitle(), "Untitled artwork"));
        titleLabel.getStyleClass().add("gallery-art-title");
        titleLabel.setWrapText(true);
        
        Label artistLabel = new Label(safeText(art.getArtist(), "Unknown artist"));
        artistLabel.getStyleClass().add("gallery-art-artist");

        Label descLabel = new Label(truncate(safeText(art.getDescription(), "No description available."), 110));
        descLabel.getStyleClass().add("gallery-art-desc");
        descLabel.setWrapText(true);
        descLabel.setPrefWidth(268);
        
        HBox socialButtons = createSocialButtons(art);
        HBox actionButtons = createActionButtons(art);
        
        card.getChildren().addAll(imageView, titleLabel, artistLabel, descLabel, socialButtons, actionButtons);
        return card;
    }

    private Image loadArtworkImage(String imageUrl) {
        String source = resolveArtworkImageSource(imageUrl);
        Image image = new Image(source, 268, 178, false, true, true);
        if (image.isError()) {
            return new Image(getClass().getResource("/images/placeholder.jpg").toExternalForm(), 268, 178, false, true, true);
        }
        return image;
    }

    private String resolveArtworkImageSource(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            return getClass().getResource("/images/placeholder.jpg").toExternalForm();
        }

        String trimmed = imageUrl.trim();
        if (trimmed.startsWith("http://") || trimmed.startsWith("https://")) {
            return trimmed;
        }
        if (trimmed.startsWith("file:")) {
            return trimmed;
        }

        File directFile = new File(trimmed);
        if (directFile.isFile()) {
            return directFile.toURI().toString();
        }

        File resourceImage = new File("src/main/resources/images", trimmed);
        if (resourceImage.isFile()) {
            return resourceImage.toURI().toString();
        }

        return getClass().getResource("/images/placeholder.jpg").toExternalForm();
    }

    private String safeText(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value.trim();
    }

    private String truncate(String value, int maxLength) {
        if (value == null || value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, Math.max(0, maxLength - 1)).trim() + "...";
    }

    private HBox createActionButtons(Art art) {
        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        
        Button suggestionButton = new Button("\uD83D\uDCA1");
        suggestionButton.getStyleClass().addAll("gallery-icon-button", "gallery-suggestion-button");
        suggestionButton.setTooltip(new Tooltip("Recommendations"));
        suggestionButton.setOnAction(e -> showSuggestionsDialog(art));
        
        Button spotifyButton = new Button("\u266B");
        spotifyButton.getStyleClass().addAll("gallery-icon-button", "gallery-spotify-button");
        spotifyButton.setTooltip(new Tooltip("Open Spotify search"));
        spotifyButton.setOnAction(e -> handleSpotify(art));
        
        Button editButton = new Button("\u270E");
        editButton.getStyleClass().addAll("gallery-icon-button", "gallery-edit-button");
        editButton.setTooltip(new Tooltip("Edit artwork"));
        editButton.setOnAction(e -> showEditDialog(art));
        
        Button deleteButton = new Button("\uD83D\uDDD1");
        deleteButton.getStyleClass().addAll("gallery-icon-button", "gallery-delete-button");
        deleteButton.setTooltip(new Tooltip("Delete artwork"));
        deleteButton.setOnAction(e -> handleDeleteArt(art));
        
        buttons.getChildren().addAll(suggestionButton, spotifyButton, editButton, deleteButton);
        return buttons;
    }
    
    private void handleSpotify(Art art) {
        try {
            Alert spotifyAlert = new Alert(Alert.AlertType.CONFIRMATION);
            spotifyAlert.setTitle("Spotify Playlist");
            spotifyAlert.setHeaderText("Spotify pour : " + art.getTitle());
            spotifyAlert.setContentText("Voulez-vous ouvrir Spotify pour cette oeuvre ?\n\nTitre : " + art.getTitle() + "\nArtiste : " + art.getArtist());
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
            errorAlert.setContentText("Verifiez votre connexion et reessayez.");
            errorAlert.showAndWait();
        }
    }
    
    private void showSuggestionsDialog(Art art) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Suggestions pour : " + art.getTitle());
        dialog.setHeaderText("Oeuvres similaires ou recommandees");
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        
        try {
            List<Art> recommendations = recommendationService.getSimilarArtworks(art.getId(), 4);
            if (recommendations == null || recommendations.isEmpty()) {
                content.getChildren().add(new Label("Aucune suggestion disponible pour le moment."));
            } else {
                for (Art rec : recommendations) {
                    Label recLabel = new Label("- " + rec.getTitle() + " - " + rec.getArtist());
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
        dialog.setTitle("Modifier l\'oeuvre");
        dialog.setHeaderText("Modifier les informations de l\'oeuvre");
        
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
                errorAlert.setContentText("Impossible de mettre a jour l\'oeuvre.");
                errorAlert.showAndWait();
            }
        }
    }
    
    private void handleDeleteArt(Art art) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Supprimer l\'oeuvre");
        confirm.setHeaderText("Voulez-vous vraiment supprimer cette oeuvre ?");
        confirm.setContentText(art.getTitle());
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (artService.deleteArt(art.getId())) {
                loadArtworks(searchField.getText().trim());
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText("Suppression impossible");
                errorAlert.setContentText("Impossible de supprimer l\'oeuvre.");
                errorAlert.showAndWait();
            }
        }
    }
    
    private HBox createSocialButtons(Art art) {
        HBox buttons = new HBox(15);
        buttons.setAlignment(Pos.CENTER_LEFT);
        
        // Charger les vrais compteurs depuis la base de donnÃƒÆ’Ã‚Â©es
        int currentLikes = likeCounts.getOrDefault(art.getId(), 0);
        int currentDislikes = dislikeCounts.getOrDefault(art.getId(), 0);
        
        likeCounts.put(art.getId(), currentLikes);
        dislikeCounts.put(art.getId(), currentDislikes);
        
        // Bouton Like avec compteur
        VBox likeContainer = new VBox(2);
        likeContainer.setAlignment(Pos.CENTER);
        
        Button likeButton = new Button("\uD83D\uDC4D");
        likeButton.getStyleClass().addAll("gallery-reaction-button", "gallery-like-button");
        likeButton.setTooltip(new Tooltip("Like"));
        
        Label likeCount = new Label(String.valueOf(currentLikes));
        likeCount.getStyleClass().add("gallery-reaction-count");
        
        likeContainer.getChildren().addAll(likeButton, likeCount);
        
        // Bouton Dislike avec compteur
        VBox dislikeContainer = new VBox(2);
        dislikeContainer.setAlignment(Pos.CENTER);
        
        Button dislikeButton = new Button("\uD83D\uDC4E");
        dislikeButton.getStyleClass().addAll("gallery-reaction-button", "gallery-dislike-button");
        dislikeButton.setTooltip(new Tooltip("Dislike"));
        
        Label dislikeCount = new Label(String.valueOf(currentDislikes));
        dislikeCount.getStyleClass().add("gallery-reaction-count");
        
        dislikeContainer.getChildren().addAll(dislikeButton, dislikeCount);
        
        // Bouton Commentaire
        Button commentButton = new Button("\uD83D\uDCAC");
        commentButton.getStyleClass().addAll("gallery-reaction-button", "gallery-comment-button");
        commentButton.setTooltip(new Tooltip("Comments"));
        applyReactionState(likeButton, dislikeButton, reactionStates.getOrDefault(art.getId(), ReactionState.NONE));
        
        // Actions avec animations
        likeButton.setOnAction(e -> handleLikeReaction(art, likeButton, dislikeButton, likeCount, dislikeCount));
        dislikeButton.setOnAction(e -> handleDislikeReaction(art, likeButton, dislikeButton, likeCount, dislikeCount));
        commentButton.setOnAction(e -> openCommentDialog(art));
        
        buttons.getChildren().addAll(likeContainer, dislikeContainer, commentButton);
        return buttons;
    }

    private void handleLikeReaction(Art art, Button likeButton, Button dislikeButton, Label likeCount, Label dislikeCount) {
        updateReactionAsync(art, ReactionState.LIKED, likeButton, dislikeButton, likeCount, dislikeCount);
    }

    private void handleDislikeReaction(Art art, Button likeButton, Button dislikeButton, Label likeCount, Label dislikeCount) {
        updateReactionAsync(art, ReactionState.DISLIKED, likeButton, dislikeButton, likeCount, dislikeCount);
    }

    private void updateReactionAsync(Art art, ReactionState requestedState, Button likeButton, Button dislikeButton, Label likeCount, Label dislikeCount) {
        String sessionKey = resolveReactionSessionKey();
        if (sessionKey == null) {
            promptSignInForReaction();
            return;
        }

        likeButton.setDisable(true);
        dislikeButton.setDisable(true);
        ReactionState previousState = reactionStates.getOrDefault(art.getId(), ReactionState.NONE);

        Task<ReactionState> task = new Task<>() {
            @Override
            protected ReactionState call() {
                ReactionState nextState = previousState == requestedState ? ReactionState.NONE : requestedState;

                if (previousState == ReactionState.LIKED && nextState != ReactionState.LIKED) {
                    likeService.removeLike(art.getId(), sessionKey);
                }
                if (previousState == ReactionState.DISLIKED && nextState != ReactionState.DISLIKED) {
                    dislikeService.removeDislike(art.getId(), sessionKey);
                }
                if (nextState == ReactionState.LIKED) {
                    likeService.addLike(art.getId(), sessionKey);
                } else if (nextState == ReactionState.DISLIKED) {
                    dislikeService.addDislike(art.getId(), sessionKey);
                }

                likeCounts.put(art.getId(), likeService.getArtLikeCount(art.getId()));
                dislikeCounts.put(art.getId(), dislikeService.getDislikeCount(art.getId()));
                return nextState;
            }
        };

        task.setOnSucceeded(event -> {
            ReactionState nextState = task.getValue();
            reactionStates.put(art.getId(), nextState);
            art.setLikes(likeCounts.getOrDefault(art.getId(), 0));
            art.setDislikes(dislikeCounts.getOrDefault(art.getId(), 0));
            likeCount.setText(String.valueOf(art.getLikes()));
            dislikeCount.setText(String.valueOf(art.getDislikes()));
            applyReactionState(likeButton, dislikeButton, nextState);
            animateReactionButton(requestedState == ReactionState.DISLIKED ? dislikeButton : likeButton);
            likeButton.setDisable(false);
            dislikeButton.setDisable(false);
        });

        task.setOnFailed(event -> {
            likeButton.setDisable(false);
            dislikeButton.setDisable(false);
            showError("Reaction failed", "Could not update this reaction. Please try again.");
        });

        Thread thread = new Thread(task, "pegasus-gallery-reaction");
        thread.setDaemon(true);
        thread.start();
    }

    private void applyReactionState(Button likeButton, Button dislikeButton, ReactionState state) {
        likeButton.getStyleClass().remove("gallery-reaction-selected");
        dislikeButton.getStyleClass().remove("gallery-reaction-selected");
        if (state == ReactionState.LIKED) {
            likeButton.getStyleClass().add("gallery-reaction-selected");
        } else if (state == ReactionState.DISLIKED) {
            dislikeButton.getStyleClass().add("gallery-reaction-selected");
        }
    }

    private void animateReactionButton(Button button) {
        Timeline animation = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(button.scaleXProperty(), 1), new KeyValue(button.scaleYProperty(), 1)),
                new KeyFrame(Duration.millis(110), new KeyValue(button.scaleXProperty(), 1.14), new KeyValue(button.scaleYProperty(), 1.14)),
                new KeyFrame(Duration.millis(230), new KeyValue(button.scaleXProperty(), 1), new KeyValue(button.scaleYProperty(), 1))
        );
        animation.play();
    }

    private void promptSignInForReaction() {
        try {
            SceneNavigator.goTo("/views/front/signin-view.fxml");
        } catch (IOException e) {
            showError("Sign in required", "Please sign in before reacting to artworks.");
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void handleLikeWithAnimation(Art art, Button likeButton, Label likeCount, String sessionId) {
        boolean liked = likeService.addLike(art.getId(), sessionId);
        boolean countUpdated = artService.incrementLikes(art.getId());
        if (liked || countUpdated) {
            // Recharger le compteur depuis la base de donnÃƒÆ’Ã‚Â©es
            int newCount = likeCounts.getOrDefault(art.getId(), art.getLikes()) + 1;
            likeCounts.put(art.getId(), newCount);
            art.setLikes(newCount);
            
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
            Label noCommentsLabel = new Label("Soyez le premier a commenter !");
            noCommentsLabel.setStyle("-fx-text-fill: #6c757d; -fx-font-style: italic;");
            container.getChildren().add(noCommentsLabel);
            return;
        }
        
        // SÃƒÆ’Ã‚Â©parer les commentaires principaux des rÃƒÆ’Ã‚Â©ponses
        List<ServiceArtComment.Comment> mainComments = new ArrayList<>();
        List<ServiceArtComment.Comment> replies = new ArrayList<>();
        
        for (ServiceArtComment.Comment comment : comments) {
            if (comment.getContent().startsWith("@")) {
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
            Button replyButton = new Button("Reply");
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
                    String replyContent = reply.getContent().replaceFirst("@" + mainComment.getUsername() + ": ", "");
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
        replyDialog.setTitle("Repondre au commentaire");
        replyDialog.setHeaderText("Repondre a : " + parentComment.getUsername());
        
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
        replyArea.setPromptText("Votre reponse...");
        replyArea.setPrefWidth(360);
        replyArea.setPrefHeight(60);
        replyArea.setWrapText(true);
        
        content.getChildren().addAll(parentLabel, new Separator(), replyNameField, replyArea);
        
        replyDialog.getDialogPane().setContent(content);
        
        ButtonType replyButton = new ButtonType("Repondre", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        replyDialog.getDialogPane().getButtonTypes().addAll(replyButton, cancelButton);
        
        replyDialog.setResultConverter(dialogButton -> {
            if (dialogButton == replyButton) {
                String name = replyNameField.getText().trim();
                String reply = replyArea.getText().trim();
                
                if (!name.isEmpty() && !reply.isEmpty()) {
                    String replyText = "@" + parentComment.getUsername() + ": " + reply;
                    return replyText;
                }
            }
            return null;
        });
        
        Optional<String> result = replyDialog.showAndWait();
        result.ifPresent(replyText -> {
            if (commentService.addComment(art.getId(), replyNameField.getText().trim(), replyText)) {
                
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
        dialog.setTitle("Citations d\'Art");
        dialog.setHeaderText("Citations celebres d\'artistes et penseurs");
        
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
        Button refreshButton = new Button("Nouvelle citation");
        refreshButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 8px 16px;");
        refreshButton.setOnAction(e -> {
            Dialog<Void> newDialog = new Dialog<>();
            newDialog.setTitle("Citation du jour");
            newDialog.setHeaderText("Citation aleatoire");
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
        dialog.setTitle("Artistes celebres");
        dialog.setHeaderText("Biographies des grands maitres");
        
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
            "Salvador Dali",
            "Frida Kahlo",
            "Paul Cezanne"
        };
        
        VBox artistsList = new VBox(8);
        artistsList.setStyle("-fx-padding: 10px;");
        
        for (String artist : artists) {
            Button artistBtn = new Button(artist);
            artistBtn.setStyle("-fx-background-color: #667eea; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 10px 16px; -fx-font-size: 12px; -fx-cursor: hand; -fx-max-width: Infinity;");
            artistBtn.setMaxWidth(Double.MAX_VALUE);
            artistBtn.setOnAction(e -> showArtistBiography(artist));
            artistsList.getChildren().add(artistBtn);
        }
        
        ScrollPane scrollPane = new ScrollPane(artistsList);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(400);
        
        content.getChildren().addAll(new Label("Selectionnez un artiste pour sa biographie:"), scrollPane);
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.showAndWait();
    }
    
    private void showArtistBiography(String artistName) {
        Dialog<Void> bioDialog = new Dialog<>();
        bioDialog.setTitle("Biographie : " + artistName);
        bioDialog.setHeaderText(artistName);
        
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
        currentSort = GallerySort.NEWEST;
        loadGalleryAsync();
    }
    
    @FXML
    private void handleSortOlder() {
        currentSort = GallerySort.OLDEST;
        loadGalleryAsync();
    }
    
    @FXML
    private void handleSortLiked() {
        currentSort = GallerySort.LIKED;
        loadGalleryAsync();
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
        
    }
    
    @FXML
    private void handleResetSort() {
        searchField.clear();
        currentSort = GallerySort.NONE;
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
        addDialog.setTitle("Ajouter une oeuvre");
        addDialog.setHeaderText("Ajouter une nouvelle oeuvre a la collection");
        
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        content.setPrefWidth(500);
        
        // Champ pour le titre
        TextField titleField = new TextField();
        titleField.setPromptText("Titre de l\'oeuvre");
        titleField.setStyle("-fx-background-color: #f7fafc; -fx-border-color: #cbd5e0; -fx-border-radius: 8; -fx-padding: 10px;");
        
        // Champ pour la description
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Description de l\'oeuvre...");
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
        confirmationAlert.setHeaderText("Etes-vous sur de vouloir publier cette oeuvre ?");
        confirmationAlert.setContentText("Titre: " + art.getTitle() + "\nArtiste: " + art.getArtist());
        
        ButtonType yesButton = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType noButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmationAlert.getButtonTypes().setAll(yesButton, noButton);
        
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            // L'utilisateur a confirmÃƒÆ’Ã‚Â©
            if (artService.createArt(art)) {
                showSubmissionMessage();
            } else {
            }
        }
    }
    
    private void showSubmissionMessage() {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("Publication soumise");
        infoAlert.setHeaderText("Votre publication sera publiee lorsque l\'admin l\'acceptera");
        infoAlert.setContentText(null);
        infoAlert.showAndWait();
    }
    
    @FXML
    private void goHome() {
        try {
            SceneNavigator.goTo("/views/front/home-view.fxml");
        } catch (IOException e) {
        }
    }

    @FXML
    private void onGoToBackoffice() {
        try {
            SceneNavigator.goTo("/views/back/AdminLayout.fxml");
        } catch (IOException e) {
        }
    }

    @FXML
    private void onGoToEventsFront() {
        try {
            SceneNavigator.goTo(EventsRoleRouter.resolveEventsEntryFxml());
        } catch (IOException e) {
        }
    }

    @FXML
    private void onGoToCourses() {
        if (SceneNavigator.getCurrentUser() == null) {
            try {
                SceneNavigator.goTo("/views/front/signin-view.fxml");
            } catch (IOException e) {
            }
            return;
        }
        try {
            FrontLayoutController.showCoursesOnOpen();
            SceneNavigator.goTo("/views/front/FrontLayout.fxml");
        } catch (IOException e) {
        }
    }

    @FXML
    private void onGoToCoursesDashboard() {
        if (SceneNavigator.getCurrentUser() == null) {
            try {
                SceneNavigator.goTo("/views/front/signin-view.fxml");
            } catch (IOException e) {
            }
            return;
        }
        try {
            FrontLayoutController.showDashboardOnOpen();
            SceneNavigator.goTo("/views/front/FrontLayout.fxml");
        } catch (IOException e) {
        }
    }

    @FXML
    private void onGoToEventsBackoffice() {
        try {
            SceneNavigator.goTo("/views/back/backevent-view.fxml");
        } catch (IOException e) {
        }
    }

    @FXML
    private void onGoToProduit() {
        User currentUser = SceneNavigator.getCurrentUser();
        if (currentUser == null) {
            try {
                SceneNavigator.goTo("/views/front/signin-view.fxml");
            } catch (IOException e) {
            }
            return;
        }
        String target = "artiste".equalsIgnoreCase(currentUser.getDtype())
                ? "/views/back/DashboardArtiste.fxml"
                : "/views/front/DashboardUser.fxml";
        try {
            SceneNavigator.goTo(target);
        } catch (IOException e) {
        }
    }

    @FXML
    private void onGoToForum() {
        ForumModuleLauncher.openForumWindow();
    }

    @FXML
    private void onGoToProfile() {
        try {
            SceneNavigator.goTo(SceneNavigator.getCurrentUser() == null ? "/views/front/signin-view.fxml" : "/views/front/profile-view.fxml");
        } catch (IOException e) {
        }
    }

    @FXML
    private void onAuthAction() {
        try {
            if (SceneNavigator.getCurrentUser() != null) {
                SceneNavigator.logoutToFrontHome();
                return;
            }
            SceneNavigator.goTo("/views/front/signin-view.fxml");
        } catch (IOException e) {
        }
    }
}



