package pegasus.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pegasus.entities.Evenement;
import pegasus.services.ServiceEvenement;
import javafx.application.Platform;
import javafx.scene.web.WebView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;

public class DetailsEvenementArtisteController {

    @FXML
    private ImageView bigImageView;
    @FXML
    private Label titreLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label heureLabel;
    @FXML
    private Label lieuLabel;
    @FXML
    private Label prixLabel;

    @FXML
    private Label capaciteLabel;
    @FXML
    private Label descLabel;
    @FXML
    private WebView mapWebView;

    private Evenement currentEvenement;
    private ServiceEvenement serviceEvenement = new ServiceEvenement();

    public void initData(Evenement e) {
        this.currentEvenement = e;
        titreLabel.setText(e.getTitre());
        dateLabel.setText(e.getDate());
        heureLabel.setText(e.getHeure());
        lieuLabel.setText(e.getLieu());
        prixLabel.setText(String.format("%.2f DT", e.getPrix()));

        capaciteLabel.setText(e.getCapacite_max() + " Personnes");
        descLabel.setText(e.getDescription() != null && !e.getDescription().isEmpty() ? e.getDescription() : "Aucun détail supplémentaire.");

        // Image clipping for rounded corners
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
        } catch (Exception ex) {
            // Pas d'image
        }

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
    }

    @FXML
    void modifier(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/modifier-evenement-view.fxml"));
        Parent root = loader.load();
        
        ModifierEvenementController controller = loader.getController();
        controller.setOrigin("DETAILS");
        controller.initData(currentEvenement);
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    @FXML
    void supprimer(ActionEvent event) throws IOException {
        serviceEvenement.supprimer(currentEvenement);
        retourCatalogue(event);
    }

    @FXML
    void packSponsoring(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/sponsoring-pack-view.fxml"));
        Parent root = loader.load();
        
        SponsoringPackController controller = loader.getController();
        controller.initData(currentEvenement);
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    @FXML
    void participant(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/liste-participants-view.fxml"));
        Parent root = loader.load();
        
        ListeParticipantsController controller = loader.getController();
        controller.initData(currentEvenement);
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    @FXML
    void retourCatalogue(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/views/liste-evenement-artiste.fxml"));
        stage.getScene().setRoot(root);
    }
}
