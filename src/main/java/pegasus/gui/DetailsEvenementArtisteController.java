package pegasus.gui;

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
import pegasus.gui.AjouterEvenementController;
import pegasus.gui.DetailsEvenementSponsorController;
import pegasus.gui.DetailsEvenementParticipantController;
import pegasus.gui.ModifierEvenementController;
import pegasus.gui.ListeParticipantsController;

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
    }

    @FXML
    void modifier(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifier-evenement-view.fxml"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sponsoring-pack-view.fxml"));
        Parent root = loader.load();
        
        SponsoringPackController controller = loader.getController();
        controller.initData(currentEvenement);
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    @FXML
    void participant(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/liste-participants-view.fxml"));
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
        Parent root = FXMLLoader.load(getClass().getResource("/liste-evenement-artiste.fxml"));
        stage.getScene().setRoot(root);
    }
}
