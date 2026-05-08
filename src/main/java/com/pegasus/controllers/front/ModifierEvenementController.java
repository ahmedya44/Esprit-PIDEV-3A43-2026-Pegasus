package com.pegasus.controllers.front;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.pegasus.entities.Evenement;
import com.pegasus.services.ServiceEvenement;
import com.pegasus.controllers.back.AdminLayoutController;
import com.pegasus.controllers.SceneNavigator;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class ModifierEvenementController {

    @FXML private TextField titreField;
    @FXML private DatePicker dateField;
    @FXML private TextField heureField;
    @FXML private TextField lieuField;
    @FXML private TextField capaciteField;
    @FXML private TextField prixField;

    @FXML private TextField imageField;
    @FXML private TextArea descField;
    @FXML private Label messageLabel;

    private ServiceEvenement serviceEvenement = new ServiceEvenement();
    private Evenement currentEvenement;
    private String origin = "BACK"; // Valeur par défaut

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @FXML
    void initialize() {
        setupRealTimeValidation();
    }

    private void setupRealTimeValidation() {
        // Validation en temps réel pour n'accepter que les chiffres dans la capacité
        capaciteField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                capaciteField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Validation en temps réel pour le prix (chiffres et point décimal)
        prixField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*([\\.]\\d*)?")) {
                prixField.setText(oldValue);
            }
        });
        
        // Empêcher plus de 5 caractères pour l'heure (HH:mm)
        heureField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 5) {
                heureField.setText(oldValue);
            }
        });
    }

    public void initData(Evenement e) {
        this.currentEvenement = e;
        titreField.setText(e.getTitre());
        if (e.getDate() != null && !e.getDate().isEmpty()) {
            try {
                dateField.setValue(LocalDate.parse(e.getDate()));
            } catch (Exception ignored) { }
        }
        heureField.setText(e.getHeure());
        lieuField.setText(e.getLieu());
        capaciteField.setText(String.valueOf(e.getCapacite_max()));
        prixField.setText(String.valueOf(e.getPrix()));

        imageField.setText(e.getImage());
        descField.setText(e.getDescription());
    }

    @FXML
    void enregistrerModifications(ActionEvent event) throws IOException {
        if (!validerChamps()) {
            return;
        }

        try {
            String dateStr = dateField.getValue().toString();
            Evenement e = new Evenement(
                currentEvenement.getId(),
                titreField.getText().trim(),
                dateStr,
                heureField.getText().trim(),
                lieuField.getText().trim(),
                descField.getText().trim(),
                imageField.getText().trim(),
                Integer.parseInt(capaciteField.getText().trim()),
                Float.parseFloat(prixField.getText().trim()),
                currentEvenement.getStatut() // Conserver le statut actuel
            );
            
            serviceEvenement.modifier(e);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("L'événement a été mis à jour avec succès !");
            alert.showAndWait();

            // Retourner à la page précédente dynamiquement
            if ("DETAILS".equals(origin)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/front/details-evenement-artiste.fxml"));
                Parent root = loader.load();
                DetailsEvenementArtisteController controller = loader.getController();
                controller.initData(e); // Passer l'événement mis à jour
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.getScene().setRoot(root);
            } else if ("ADMIN".equals(origin)) {
                AdminLayoutController.showEventsOnOpen();
                SceneNavigator.goTo("/views/back/AdminLayout.fxml");
            } else {
                String view = "FRONT".equals(origin) ? "/views/front/liste-evenement-artiste.fxml" : "/views/back/backevent-view.fxml";
                FXMLLoader loader = new FXMLLoader(getClass().getResource(view));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.getScene().setRoot(root);
            }
            
        } catch (Exception ex) {
            afficherErreur("Erreur technique : " + ex.getMessage());
        }
    }

    private boolean validerChamps() {
        boolean isValid = true;
        StringBuilder sb = new StringBuilder();

        if (titreField.getText().trim().isEmpty() || titreField.getText().trim().length() < 3) {
            sb.append("- Le titre doit contenir au moins 3 caractères.\n");
            isValid = false;
        }
        
        if (dateField.getValue() == null) {
            sb.append("- La date est obligatoire.\n");
            isValid = false;
        } else if (dateField.getValue().isBefore(java.time.LocalDate.now().plusDays(14))) {
            sb.append("- L'événement doit être prévu au moins 14 jours à l'avance.\n");
            isValid = false;
        }
        
        if (heureField.getText().trim().isEmpty()) {
            sb.append("- L'heure est obligatoire.\n");
            isValid = false;
        } else if (!heureField.getText().matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")) {
            sb.append("- Format d'heure invalide (HH:mm).\n");
            isValid = false;
        }

        if (lieuField.getText().trim().isEmpty() || lieuField.getText().trim().length() < 3) {
            sb.append("- Le lieu doit contenir au moins 3 caractères.\n");
            isValid = false;
        }

        if (capaciteField.getText().trim().isEmpty()) {
            sb.append("- La capacité est obligatoire.\n");
            isValid = false;
        } else {
            try {
                int cap = Integer.parseInt(capaciteField.getText().trim());
                if (cap <= 0) {
                    sb.append("- La capacité doit être supérieure à 0.\n");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                sb.append("- La capacité doit être un nombre valide.\n");
                isValid = false;
            }
        }

        if (prixField.getText().trim().isEmpty()) {
            sb.append("- Le prix est obligatoire.\n");
            isValid = false;
        } else {
            try {
                float pr = Float.parseFloat(prixField.getText().trim());
                if (pr < 0) {
                    sb.append("- Le prix ne peut pas être négatif.\n");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                sb.append("- Le prix doit être un nombre valide.\n");
                isValid = false;
            }
        }


        if (descField.getText().trim().isEmpty() || descField.getText().trim().length() < 10) {
            sb.append("- La description doit contenir au moins 10 caractères.\n");
            isValid = false;
        }

        if (!isValid) {
            afficherErreur(sb.toString());
            return false;
        }
        return true;
    }

    private void afficherErreur(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: red;");
    }

    @FXML
    void choisirImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File file = fileChooser.showOpenDialog(imageField.getScene().getWindow());
        if (file != null) {
            imageField.setText(file.getAbsolutePath());
        }
    }

    @FXML
    void annuler(ActionEvent event) throws IOException {
        if ("DETAILS".equals(origin)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/front/details-evenement-artiste.fxml"));
            Parent root = loader.load();
            DetailsEvenementArtisteController controller = loader.getController();
            controller.initData(currentEvenement);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
        } else if ("ADMIN".equals(origin)) {
            AdminLayoutController.showEventsOnOpen();
            SceneNavigator.goTo("/views/back/AdminLayout.fxml");
        } else {
            String view = "FRONT".equals(origin) ? "/views/front/liste-evenement-artiste.fxml" : "/views/back/backevent-view.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(view));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
        }
    }
}
