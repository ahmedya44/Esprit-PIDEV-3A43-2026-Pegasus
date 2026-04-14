package pegasus.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pegasus.entities.Evenement;

import java.io.IOException;

public class DetailsEvenementParticipantController {

    @FXML private ImageView bigImageView;
    @FXML private Label titreLabel;
    @FXML private Label dateLabel;
    @FXML private Label heureLabel;
    @FXML private Label lieuLabel;
    @FXML private Label prixLabel;

    @FXML private Label capaciteLabel;
    @FXML private Label descLabel;
    
    @FXML private Button btnParticiper;
    @FXML private Label confirmationLabel;

    private pegasus.services.ServiceParticipation serviceParticipation = new pegasus.services.ServiceParticipation();
    private Evenement event;

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

        updateButtonState();
    }

    private void updateButtonState() {
        pegasus.entities.User currentUser = pegasus.tools.Session.getCurrentUser();
        if (currentUser != null && serviceParticipation.isParticipated(currentUser.getId(), this.event.getId())) {
            btnParticiper.setText("❌ ANNULER MA PARTICIPATION");
            btnParticiper.setStyle("-fx-background-color: #eb4d4b; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 15 40; -fx-background-radius: 40;");
            confirmationLabel.setText("✅ Vous êtes inscrit à cet événement.");
            confirmationLabel.setStyle("-fx-text-fill: #27ae60;");
        } else {
            if (this.event.getCapacite_max() <= 0) {
                btnParticiper.setText("🚫 COMPLET");
                btnParticiper.setDisable(true);
                btnParticiper.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 15 40; -fx-background-radius: 40;");
                confirmationLabel.setText("⚠️ Cet événement est complet.");
                confirmationLabel.setStyle("-fx-text-fill: #e74c3c;");
            } else {
                btnParticiper.setText("🚀 PARTICIPER");
                btnParticiper.setDisable(false);
                btnParticiper.setStyle("-fx-background-color: #6c5ce7; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 15 40; -fx-background-radius: 40; -fx-effect: dropshadow(three-pass-box, rgba(108, 92, 231, 0.3), 15, 0, 0, 8);");
                confirmationLabel.setText("");
            }
        }
    }

    @FXML
    void participer(ActionEvent event) {
        pegasus.entities.User currentUser = pegasus.tools.Session.getCurrentUser();
        pegasus.services.ServiceEvenement serviceEvenement = new pegasus.services.ServiceEvenement();

        if (currentUser == null) {
            confirmationLabel.setText("⚠️ Veuillez vous connecter.");
            confirmationLabel.setStyle("-fx-text-fill: #e74c3c;");
            return;
        }

        if (serviceParticipation.isParticipated(currentUser.getId(), this.event.getId())) {
            // Annuler
            serviceParticipation.annulerParticipation(currentUser.getId(), this.event.getId());
            confirmationLabel.setText("❌ Participation annulée.");
            confirmationLabel.setStyle("-fx-text-fill: #e67e22;");
        } else {
            // S'inscrire
            pegasus.entities.Participation p = new pegasus.entities.Participation(currentUser.getId(), this.event.getId());
            boolean success = serviceParticipation.ajouter(p);
            if (success) {
                confirmationLabel.setText("✅ Inscription réussie !");
                confirmationLabel.setStyle("-fx-text-fill: #27ae60;");
            } else {
                confirmationLabel.setText("⚠️ Désolé, cet événement est déjà complet.");
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
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/liste-evenement-participant.fxml"));
        stage.getScene().setRoot(root);
    }
}
