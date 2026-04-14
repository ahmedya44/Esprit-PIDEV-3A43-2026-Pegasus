package pegasus.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import pegasus.services.ServiceEvenement;
import pegasus.entities.Evenement;
import java.util.List;
import javafx.stage.FileChooser;
import java.io.File;

public class AjouterEvenementController {

    @FXML
    private TextField titreField;
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField heureField;
    @FXML
    private TextField lieuField;
    @FXML
    private TextField capaciteField;
    @FXML
    private TextField prixField;

    @FXML
    private TextField imageField;
    @FXML
    private TextArea descField;
    @FXML
    private Label messageLabel;

    private ServiceEvenement serviceEvenement = new ServiceEvenement();
    private int selectedId = -1;
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



    public void remplirFormulaire(Evenement e) {
        selectedId = e.getId();
        titreField.setText(e.getTitre());
        // For simpler interaction keep it simple or try setting Date object if possible
        // Actually keep the DatePicker blank or we can parse it, let's keep it simple:
        if (e.getDate() != null && !e.getDate().isEmpty()) {
            try {
                dateField.setValue(java.time.LocalDate.parse(e.getDate()));
            } catch(Exception ignored) {}
        }
        heureField.setText(e.getHeure());
        lieuField.setText(e.getLieu());
        capaciteField.setText(String.valueOf(e.getCapacite_max()));
        prixField.setText(String.valueOf(e.getPrix()));

        imageField.setText(e.getImage());
        descField.setText(e.getDescription());
        messageLabel.setText("Modification prête pour l'ID: " + e.getId());
    }

    @FXML
    void ajouterEvenement(ActionEvent event) {
        if (!validerChamps()) {
            return;
        }

        try {
            String dateStr = dateField.getValue().toString();
            Evenement e = new Evenement(
                titreField.getText().trim(),
                dateStr,
                heureField.getText().trim(),
                lieuField.getText().trim(),
                descField.getText().trim(),
                imageField.getText().trim(),
                Integer.parseInt(capaciteField.getText().trim()),
                Float.parseFloat(prixField.getText().trim()),
                "Confirmé" // Statut par défaut
            );
            serviceEvenement.ajouter(e);
            viderChamps(null);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("L'événement a été ajouté avec succès !");
            alert.showAndWait();
            
            // Retour dynamique
            String view = "FRONT".equals(origin) ? "/liste-evenements-view.fxml" : "/backevent-view.fxml";
            javafx.scene.Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource(view));
            javafx.stage.Stage stage = (javafx.stage.Stage) titreField.getScene().getWindow();
            stage.getScene().setRoot(root);
            
        } catch (Exception ex) {
            afficherErreur("Une erreur technique est survenue : " + ex.getMessage());
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
            sb.append("- Format d'heure invalide (utilisez HH:mm).\n");
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
                sb.append("- La capacité doit être un nombre entier valide.\n");
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



        if (imageField.getText().trim().isEmpty()) {
            sb.append("- L'affiche (image) est obligatoire.\n");
            isValid = false;
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
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(imageField.getScene().getWindow());
        if (file != null) {
            // Display absolute path or file URI
            imageField.setText(file.getAbsolutePath());
        }
    }

    @FXML
    void viderChamps(ActionEvent event) {
        selectedId = -1;
        titreField.clear();
        dateField.setValue(null);
        heureField.clear();
        lieuField.clear();
        capaciteField.clear();
        prixField.clear();

        imageField.clear();
        descField.clear();
        messageLabel.setText("");
    }

    @FXML
    void fermer(javafx.event.ActionEvent event) throws java.io.IOException {
        javafx.scene.Node source = (javafx.scene.Node) event.getSource();
        javafx.stage.Stage stage = (javafx.stage.Stage) source.getScene().getWindow();
        
        String view = "FRONT".equals(origin) ? "/liste-evenement-artiste.fxml" : "/backevent-view.fxml";
        javafx.scene.Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource(view));
        stage.getScene().setRoot(root);
    }
}