package com.pegasus.controllers;

import com.pegasus.entities.Art;
import com.pegasus.services.ServiceArt;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;

public class AddArtworkController {
    
    @FXML
    private TextField titleField;
    
    @FXML
    private TextArea descriptionArea;
    
    @FXML
    private TextField imageUrlField;
    
    @FXML
    private Label titleError;
    
    @FXML
    private Label descriptionError;
    
    @FXML
    private Label imageError;
    
    @FXML
    private Label charCount;
    
    @FXML
    private ImageView imagePreview;
    
    @FXML
    private VBox previewContainer;
    
    private ServiceArt serviceArt = new ServiceArt();
    private Stage dialogStage;
    
    private static final int MAX_TITLE_LENGTH = 100;
    private static final int MAX_DESCRIPTION_LENGTH = 500;
    
    @FXML
    public void initialize() {
        System.out.println("AddArtworkController initialisé!");
        
        // Setup character counter for description
        descriptionArea.textProperty().addListener((obs, oldText, newText) -> {
            updateCharCount();
            validateDescription();
        });
        
        // Setup title validation
        titleField.textProperty().addListener((obs, oldText, newText) -> {
            validateTitle();
        });
        
        // Setup image URL validation
        imageUrlField.textProperty().addListener((obs, oldText, newText) -> {
            validateImageUrl();
        });
    }
    
    private void updateCharCount() {
        String text = descriptionArea.getText();
        int length = text != null ? text.length() : 0;
        charCount.setText(length + " / " + MAX_DESCRIPTION_LENGTH + " caractères");
        
        if (length > MAX_DESCRIPTION_LENGTH) {
            charCount.setStyle("-fx-text-fill: #dc3545;");
        } else {
            charCount.setStyle("-fx-text-fill: #6c757d;");
        }
    }
    
    @FXML
    private void handlePreviewImage() {
        String imageUrl = imageUrlField.getText().trim();
        if (imageUrl.isEmpty()) {
            showError(imageError, "Veuillez entrer une URL d'image");
            return;
        }
        
        try {
            URL url = new URL(imageUrl);
            Image image = new Image(url.toExternalForm(), true);
            
            imagePreview.setImage(image);
            previewContainer.setVisible(true);
            hideError(imageError);
            
        } catch (Exception e) {
            showError(imageError, "URL d'image invalide");
            previewContainer.setVisible(false);
        }
    }
    
    @FXML
    private void handleCancel() {
        closeDialog();
    }
    
    @FXML
    private void handlePublish() {
        System.out.println("Publication demandée!");
        
        if (!validateForm()) {
            System.out.println("Formulaire invalide");
            return;
        }
        
        // Show confirmation dialog
        boolean confirmed = showConfirmationDialog();
        if (!confirmed) {
            System.out.println("Publication annulée par l'utilisateur");
            return;
        }
        
        // Create and save the artwork
        try {
            Art artwork = createArtworkFromForm();
            System.out.println("🎨 Tentative d'ajout d'œuvre:");
            System.out.println("  📌 Titre: " + artwork.getTitle());
            System.out.println("  📝 Description: " + artwork.getDescription());
            System.out.println("  🖼️  Image URL: " + artwork.getImageUrl());
            System.out.println("  📊 Statut: " + artwork.getStatus());
            System.out.println("  👨 Artiste: " + artwork.getArtist());
            System.out.println("  📅 Créé le: " + artwork.getCreatedAt());
            
            boolean success = serviceArt.createArt(artwork);
            
            System.out.println("📊 Résultat de serviceArt.createArt(): " + success);
            
            if (success) {
                System.out.println("✅ Œuvre ajoutée avec ID: " + artwork.getId());
                showSuccessMessage();
                closeDialog();
            } else {
                System.err.println("❌ Échec de la sauvegarde de l'oeuvre");
                showError("Erreur lors de la sauvegarde de l'oeuvre");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la création: " + e.getMessage());
            e.printStackTrace();
            showError("Erreur: " + e.getMessage());
        }
    }
    
    private boolean validateForm() {
        boolean isValid = true;
        
        isValid = validateTitle() && isValid;
        isValid = validateDescription() && isValid;
        isValid = validateImageUrl() && isValid;
        
        return isValid;
    }
    
    private boolean validateTitle() {
        String title = titleField.getText().trim();
        
        if (title.isEmpty()) {
            showError(titleError, "Le titre est obligatoire");
            return false;
        }
        
        if (title.length() > MAX_TITLE_LENGTH) {
            showError(titleError, "Le titre ne doit pas dépasser " + MAX_TITLE_LENGTH + " caractères");
            return false;
        }
        
        if (title.length() < 3) {
            showError(titleError, "Le titre doit contenir au moins 3 caractères");
            return false;
        }
        
        hideError(titleError);
        return true;
    }
    
    private boolean validateDescription() {
        String description = descriptionArea.getText().trim();
        
        if (description.isEmpty()) {
            showError(descriptionError, "La description est obligatoire");
            return false;
        }
        
        if (description.length() > MAX_DESCRIPTION_LENGTH) {
            showError(descriptionError, "La description ne doit pas dépasser " + MAX_DESCRIPTION_LENGTH + " caractères");
            return false;
        }
        
        if (description.length() < 10) {
            showError(descriptionError, "La description doit contenir au moins 10 caractères");
            return false;
        }
        
        hideError(descriptionError);
        return true;
    }
    
    private boolean validateImageUrl() {
        String imageUrl = imageUrlField.getText().trim();
        
        if (imageUrl.isEmpty()) {
            showError(imageError, "L'URL de l'image est obligatoire");
            return false;
        }
        
        // Basic URL validation
        if (!isValidUrl(imageUrl)) {
            showError(imageError, "Veuillez entrer une URL valide (ex: https://example.com/image.jpg)");
            return false;
        }
        
        hideError(imageError);
        return true;
    }
    
    private boolean isValidUrl(String url) {
        try {
            new URL(url);
            return url.startsWith("http://") || url.startsWith("https://");
        } catch (Exception e) {
            return false;
        }
    }
    
    private Art createArtworkFromForm() {
        Art artwork = new Art();
        artwork.setTitle(titleField.getText().trim());
        artwork.setDescription(descriptionArea.getText().trim());
        artwork.setImageUrl(imageUrlField.getText().trim());
        artwork.setStatus("published"); // PUBLIÉ directement pour affichage immédiat
        artwork.setCreatedAt(LocalDateTime.now());
        return artwork;
    }
    
    private boolean showConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de publication");
        alert.setHeaderText("Êtes-vous sûr de vouloir publier cette oeuvre ?");
        alert.setContentText("Votre oeuvre sera publiée immédiatement et visible dans la galerie.");
        
        ButtonType confirmButton = new ButtonType("Oui, publier", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        
        alert.getButtonTypes().setAll(confirmButton, cancelButton);
        
        return alert.showAndWait().orElse(cancelButton) == confirmButton;
    }
    
    private void showSuccessMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Publication réussie");
        alert.setHeaderText("Oeuvre soumise avec succès !");
        alert.setContentText("Votre publication sera publiée dès que l'administrateur l'acceptera. Vous recevrez une notification une fois la validation effectuée.");
        alert.showAndWait();
    }
    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur est survenue");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showError(Label errorLabel, String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
    
    private void hideError(Label errorLabel) {
        errorLabel.setVisible(false);
    }
    
    private void closeDialog() {
        if (dialogStage != null) {
            dialogStage.close();
        }
    }
    
    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
        System.out.println("Dialog stage défini: " + stage);
    }
}
