package com.pegasus.controllers.front;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.pegasus.entities.Evenement;
import com.pegasus.services.ServiceEvenement;
import com.pegasus.controllers.back.AdminLayoutController;
import com.pegasus.controllers.SceneNavigator;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class AjouterEvenementController {

    @FXML private TextField titreField;
    @FXML private DatePicker dateField;
    @FXML private TextField heureField;
    @FXML private TextField lieuField;
    @FXML private TextField capaciteField;
    @FXML private TextField prixField;
    @FXML private TextField imageField;
    @FXML private TextArea descField;
    @FXML private Label messageLabel;

    private final ServiceEvenement serviceEvenement = new ServiceEvenement();
    private String origin = "BACK";

    private static final String GROQ_CONFIG_PATH = "/groq.properties";

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @FXML
    void initialize() {
        setupRealTimeValidation();
    }

    private void setupRealTimeValidation() {
        capaciteField.textProperty().addListener((obs, old, newValue) -> {
            if (!newValue.matches("\\d*")) {
                capaciteField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        prixField.textProperty().addListener((obs, old, newValue) -> {
            if (!newValue.matches("\\d*([\\.]\\d*)?")) {
                prixField.setText(old);
            }
        });

        heureField.textProperty().addListener((obs, old, newValue) -> {
            if (newValue.length() > 5) {
                heureField.setText(old);
            }
        });
    }

    @FXML
    void ajouterEvenement(ActionEvent event) {
        if (!validerChamps()) {
            return;
        }

        try {
            Evenement e = new Evenement(
                    titreField.getText().trim(),
                    dateField.getValue().toString(),
                    heureField.getText().trim(),
                    lieuField.getText().trim(),
                    descField.getText().trim(),
                    imageField.getText().trim(),
                    Integer.parseInt(capaciteField.getText().trim()),
                    Float.parseFloat(prixField.getText().trim()),
                    "Confirme"
            );
            serviceEvenement.ajouter(e);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.setContentText("L'evenement a ete ajoute avec succes !");
            alert.showAndWait();

            fermer(event);
        } catch (Exception ex) {
            afficherErreur("Erreur technique : " + ex.getMessage());
        }
    }

    private boolean validerChamps() {
        StringBuilder sb = new StringBuilder();
        boolean valid = true;

        if (titreField.getText().trim().length() < 3) {
            sb.append("- Le titre doit contenir au moins 3 caracteres\n");
            valid = false;
        }
        if (dateField.getValue() == null) {
            sb.append("- La date est obligatoire.\n");
            valid = false;
        } else if (dateField.getValue().isBefore(java.time.LocalDate.now().plusDays(14))) {
            sb.append("- L'evenement doit etre prevu au moins 14 jours a l'avance.\n");
            valid = false;
        }
        if (heureField.getText().trim().isEmpty()) {
            sb.append("- L'heure est obligatoire.\n");
            valid = false;
        } else if (!heureField.getText().matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")) {
            sb.append("- Format d'heure invalide (HH:mm).\n");
            valid = false;
        }
        if (lieuField.getText().trim().isEmpty() || lieuField.getText().trim().length() < 3) {
            sb.append("- Le lieu doit contenir au moins 3 caracteres.\n");
            valid = false;
        }

        if (capaciteField.getText().trim().isEmpty()) {
            sb.append("- La capacite est obligatoire.\n");
            valid = false;
        } else {
            try {
                int cap = Integer.parseInt(capaciteField.getText().trim());
                if (cap <= 0) {
                    sb.append("- La capacite doit etre superieure a 0.\n");
                    valid = false;
                }
            } catch (NumberFormatException e) {
                sb.append("- La capacite doit etre un nombre valide.\n");
                valid = false;
            }
        }

        if (prixField.getText().trim().isEmpty()) {
            sb.append("- Le prix est obligatoire.\n");
            valid = false;
        } else {
            try {
                float pr = Float.parseFloat(prixField.getText().trim());
                if (pr < 0) {
                    sb.append("- Le prix ne peut pas etre negatif.\n");
                    valid = false;
                }
            } catch (NumberFormatException e) {
                sb.append("- Le prix doit etre un nombre valide.\n");
                valid = false;
            }
        }
        if (descField.getText().trim().length() < 10) {
            sb.append("- La description doit contenir au moins 10 caracteres\n");
            valid = false;
        }

        if (!valid) {
            afficherErreur(sb.toString());
        }
        return valid;
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

    private String loadGroqApiKey() {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getResourceAsStream(GROQ_CONFIG_PATH)) {
            if (inputStream == null) {
                return null;
            }
            properties.load(inputStream);
            String value = properties.getProperty("groq.api.key");
            if (value == null || value.trim().isEmpty()) {
                return null;
            }
            return value.trim();
        } catch (Exception ignored) {
            return null;
        }
    }

    @FXML
    void genererDescription(ActionEvent event) {
        String titre = titreField.getText().trim();
        if (titre.isEmpty()) {
            afficherErreur("Veuillez saisir un titre avant de generer la description.");
            return;
        }

        String groqApiKey = loadGroqApiKey();
        if (groqApiKey == null) {
            afficherErreur("Cle Groq introuvable. Ajoutez groq.api.key dans src/main/resources/groq.properties");
            return;
        }

        descField.setText("Generation par IA en cours...");
        messageLabel.setText("");

        new Thread(() -> {
            try {
                String escapedTitre = titre.replace("\\", "\\\\").replace("\"", "\\\"");
                String jsonBody = "{"
                        + "\"model\":\"llama-3.3-70b-versatile\","
                        + "\"messages\":[{\"role\":\"user\",\"content\":\"Genere une description courte et attrayante (max 3 phrases) pour un evenement intitule : " + escapedTitre + "\"}]"
                        + "}";

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://api.groq.com/openai/v1/chat/completions"))
                        .header("Authorization", "Bearer " + groqApiKey)
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                String body = response.body();

                if (response.statusCode() == 200) {
                    int start = body.indexOf("\"content\":\"") + 11;
                    int end = body.indexOf("\"", start);

                    while (end > 0 && body.charAt(end - 1) == '\\') {
                        end = body.indexOf("\"", end + 1);
                    }

                    if (start > 10 && end > start) {
                        String content = body.substring(start, end)
                                .replace("\\n", "\n")
                                .replace("\\\"", "\"")
                                .replace("\\\\", "\\");

                        Platform.runLater(() -> descField.setText(content.trim()));
                    } else {
                        throw new Exception("Reponse malformee");
                    }
                } else {
                    Platform.runLater(() -> {
                        descField.setText("");
                        String errorHint = body.length() > 160 ? body.substring(0, 160) : body;
                        afficherErreur("Erreur Groq (" + response.statusCode() + ") : " + errorHint);
                    });
                }
            } catch (Exception e) {
                Platform.runLater(() -> {
                    descField.setText("");
                    afficherErreur("Erreur : " + e.getMessage());
                });
            }
        }).start();
    }

    @FXML
    void viderChamps(ActionEvent event) {
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
    void fermer(ActionEvent event) throws java.io.IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        if ("ADMIN".equals(origin)) {
            AdminLayoutController.showEventsOnOpen();
            SceneNavigator.goTo("/views/back/AdminLayout.fxml");
            return;
        }
        String view = "FRONT".equals(origin) ? "/views/front/liste-evenement-artiste.fxml" : "/views/back/backevent-view.fxml";
        Parent root = FXMLLoader.load(getClass().getResource(view));
        stage.getScene().setRoot(root);
    }
}
