package com.pegasus.controllers.front;

import com.pegasus.controllers.SceneNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.web.WebView;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import com.pegasus.entities.Evenement;

public class DetailsEvenementParticipantController extends FrontNavController {

    @FXML private ImageView bigImageView;
    @FXML private Label titreLabel;
    @FXML private Label dateLabel;
    @FXML private Label heureLabel;
    @FXML private Label lieuLabel;
    @FXML private Label prixLabel;

    @FXML private Label capaciteLabel;
    @FXML private Label descLabel;
    @FXML private WebView mapWebView;
    
    @FXML private Button btnParticiper;
    @FXML private Label confirmationLabel;
    private com.pegasus.services.ServiceParticipation serviceParticipation = new com.pegasus.services.ServiceParticipation();
    private Evenement event;

    @FXML
    private void initialize() {
        refreshFrontNavbarState();
    }

    @Override
    protected void beforeFrontNavigation() {
        if (mapWebView == null) {
            return;
        }
        try {
            mapWebView.getEngine().load("about:blank");
            mapWebView.getEngine().getLoadWorker().cancel();
            mapWebView.setVisible(false);
            mapWebView.setManaged(false);
        } catch (Exception ignored) {
        }
    }

    public void initData(Evenement e) {
        this.event = e;
        titreLabel.setText(e.getTitre());
        dateLabel.setText(e.getDate());
        heureLabel.setText(e.getHeure());
        lieuLabel.setText(e.getLieu());
        prixLabel.setText(String.format("%.2f DT", e.getPrix()));

        capaciteLabel.setText(e.getCapacite_max() + " Places restantes");
        descLabel.setText(e.getDescription() != null && !e.getDescription().isEmpty() ? e.getDescription() : "Aucun détail supplémentaire.");

        // Image clipping
        javafx.scene.shape.Rectangle clip = new javafx.scene.shape.Rectangle(600, 350);
        clip.setArcWidth(40);
        clip.setArcHeight(40);
        bigImageView.setClip(clip);

        try {
            if (e.getImage() != null && !e.getImage().isEmpty()) {
                String path = e.getImage();
                if (!path.startsWith("http") && !path.startsWith("file:")) {
                    path = "file:///" + path.replace("\\", "/");
                }
                Image img = new Image(path, true);
                bigImageView.setImage(img);
            }
        } catch (Exception ex) { }

        // Load Map
        try {
            String location = e.getLieu();
            if (location != null && !location.isEmpty()) {
                String encodedLocation = URLEncoder.encode(location, StandardCharsets.UTF_8.toString()).replace("+", "%20");
                String url = "https://www.google.com/maps/search/?api=1&query=" + encodedLocation;
                
                // Set UserAgent to ensure Google Maps works smoothly in WebView
                mapWebView.getEngine().setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36");

                // Inject CSS after page loads to hide the Google Maps left menu rail and top search bar
                mapWebView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue == javafx.concurrent.Worker.State.SUCCEEDED) {
                        try {
                            String hideUiScript = "var style = document.createElement('style');" +
                                                  "style.innerHTML = 'nav, div[role=\"navigation\"], #omnibox-container, div[aria-label=\"Menu principal\"] { display: none !important; }';" +
                                                  "document.head.appendChild(style);";
                            mapWebView.getEngine().executeScript(hideUiScript);
                        } catch (Exception ignore) {}
                    }
                });
                
                mapWebView.getEngine().load(url);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        updateButtonState();
    }

    private void updateButtonState() {
        com.pegasus.entities.User currentUser = SceneNavigator.getCurrentUser();
        if (currentUser != null && currentUser.getId() != null && serviceParticipation.isParticipated(currentUser.getId(), this.event.getId())) {
            btnParticiper.setText("ANNULER MA PARTICIPATION");
            btnParticiper.setStyle("-fx-background-color: #eb4d4b; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 15 40; -fx-background-radius: 40;");
            confirmationLabel.setText("Vous êtes inscrit à cet événement.");
            confirmationLabel.setStyle("-fx-text-fill: #27ae60;");
        } else {
            if (this.event.getCapacite_max() <= 0) {
                btnParticiper.setText("COMPLET");
                btnParticiper.setDisable(true);
                btnParticiper.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 15 40; -fx-background-radius: 40;");
                confirmationLabel.setText("Cet événement est complet.");
                confirmationLabel.setStyle("-fx-text-fill: #e74c3c;");
            } else {
                btnParticiper.setText("PARTICIPER");
                btnParticiper.setDisable(false);
                btnParticiper.setStyle("-fx-background-color: #6c5ce7; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 15 40; -fx-background-radius: 40; -fx-effect: dropshadow(three-pass-box, rgba(108, 92, 231, 0.3), 15, 0, 0, 8);");
                confirmationLabel.setText("");
            }
        }
    }

    @FXML
    void participer(ActionEvent event) {
        com.pegasus.entities.User currentUser = SceneNavigator.getCurrentUser();
        com.pegasus.services.ServiceEvenement serviceEvenement = new com.pegasus.services.ServiceEvenement();

        if (currentUser == null || currentUser.getId() == null) {
            confirmationLabel.setText("Veuillez vous connecter.");
            confirmationLabel.setStyle("-fx-text-fill: #e74c3c;");
            return;
        }

        if (serviceParticipation.isParticipated(currentUser.getId(), this.event.getId())) {
            // Annuler
            serviceParticipation.annulerParticipation(currentUser.getId(), this.event.getId());
            confirmationLabel.setText("Participation annulée.");
            confirmationLabel.setStyle("-fx-text-fill: #e67e22;");
        } else {
            // S'inscrire
            com.pegasus.entities.Participation p = new com.pegasus.entities.Participation(currentUser.getId(), this.event.getId());
            boolean success = serviceParticipation.ajouter(p);
            if (success) {
                confirmationLabel.setText("Inscription réussie !");
                confirmationLabel.setStyle("-fx-text-fill: #27ae60;");
            } else {
                confirmationLabel.setText("Désolé, cet événement est déjà complet.");
                confirmationLabel.setStyle("-fx-text-fill: #e74c3c;");
            }
        }

        // Rafraîchir les données de l'événement pour avoir la nouvelle capacité
        this.event = serviceEvenement.getEvenementById(this.event.getId());
        capaciteLabel.setText(this.event.getCapacite_max() + " Places restantes");
        updateButtonState();
    }

    @FXML
    void retourListe(ActionEvent event) throws IOException {
        beforeFrontNavigation();
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/views/front/liste-evenement-participant.fxml"));
        stage.getScene().setRoot(root);
    }
}
