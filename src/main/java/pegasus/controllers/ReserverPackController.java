package pegasus.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pegasus.entities.Evenement;
import pegasus.entities.SponsoringPack;
import pegasus.services.ServiceSponsoringPack;
import pegasus.tools.Session;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ReserverPackController {

    @FXML private Label eventTitleLabel;
    @FXML private GridPane packsGrid;
    @FXML private Label statusLabel;

    private ServiceSponsoringPack servicePack = new ServiceSponsoringPack();
    private Evenement currentEvenement;
    private int currentUserId;

    public void initData(Evenement e) {
        this.currentEvenement = e;
        this.currentUserId = Session.getCurrentUser().getId();
        eventTitleLabel.setText("Événement : " + e.getTitre());
        chargerPacks();
    }

    private void chargerPacks() {
        packsGrid.getChildren().clear();
        statusLabel.setText("");

        List<SponsoringPack> allPacks = servicePack.afficherParEvenement(currentEvenement.getId());

        if (allPacks.isEmpty()) {
            statusLabel.setText("Aucun pack de sponsoring disponible pour cet événement.");
            statusLabel.setStyle("-fx-text-fill: #e67e22; -fx-font-weight: bold; -fx-font-size: 14px;");
            return;
        }

        int column = 0;
        int row = 0;

        for (SponsoringPack pack : allPacks) {
            boolean reserveParMoi = (pack.getId_sponsor() == currentUserId);
            boolean reserveParAutre = (pack.getId_sponsor() != 0 && !reserveParMoi);

            VBox card = createPackCard(pack, reserveParMoi, reserveParAutre);
            packsGrid.add(card, column, row);

            column++;
            if (column >= 3) {
                column = 0;
                row++;
            }
        }
    }

    private VBox createPackCard(SponsoringPack pack, boolean reserveParMoi, boolean reserveParAutre) {
        VBox card = new VBox(12);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(25));
        card.setPrefWidth(260);

        // Style de base de la carte
        String cardStyle;
        if (reserveParMoi) {
            // Carte réservée par l'utilisateur actuel : bordure verte
            cardStyle = "-fx-background-color: #eafaf1; " +
                       "-fx-background-radius: 15; " +
                       "-fx-effect: dropshadow(three-pass-box, rgba(39,174,96,0.3), 10, 0, 0, 3); " +
                       "-fx-border-color: #27ae60; " +
                       "-fx-border-width: 2; " +
                       "-fx-border-radius: 15;";
        } else if (reserveParAutre) {
            // Carte réservée par quelqu'un d'autre : grisée
            cardStyle = "-fx-background-color: #f0f0f0; " +
                       "-fx-background-radius: 15; " +
                       "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 0, 2); " +
                       "-fx-border-color: #bdc3c7; " +
                       "-fx-border-radius: 15;";
        } else {
            // Carte disponible : blanche
            cardStyle = "-fx-background-color: white; " +
                       "-fx-background-radius: 15; " +
                       "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5); " +
                       "-fx-border-color: #ecf0f1; " +
                       "-fx-border-radius: 15;";
        }
        card.setStyle(cardStyle);

        // Nom du pack
        Label nomLabel = new Label(pack.getNom_pack());
        nomLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Prix
        Label prixLabel = new Label(String.format("%.0f TND", pack.getPrix()));
        prixLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #27ae60; -fx-font-weight: bold;");

        // Description
        Label descLabel = new Label(pack.getDescription());
        descLabel.setWrapText(true);
        descLabel.setAlignment(Pos.CENTER);
        descLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 13px;");
        descLabel.setMinHeight(60);

        card.getChildren().addAll(nomLabel, prixLabel, descLabel);

        if (reserveParMoi) {
            // Badge "Réservé par vous"
            Label badge = new Label("✅ Réservé par vous");
            badge.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold; -fx-font-size: 14px;");

            // Bouton d'annulation avec règle des 7 jours
            LocalDate eventDate = LocalDate.parse(currentEvenement.getDate());
            long daysRemaining = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), eventDate);

            Button annulerBtn = new Button();
            if (daysRemaining < 7) {
                annulerBtn.setText("🔒 Annulation bloquée (-7j)");
                annulerBtn.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold; " +
                                  "-fx-padding: 10 20; -fx-background-radius: 5;");
                annulerBtn.setDisable(true);
            } else {
                annulerBtn.setText("❌ Annuler la réservation");
                annulerBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; " +
                                  "-fx-cursor: hand; -fx-padding: 10 20; -fx-background-radius: 5;");
                annulerBtn.setOnAction(e -> annulerReservation(pack));
            }

            card.getChildren().addAll(badge, annulerBtn);

        } else if (reserveParAutre) {
            // Pack réservé par quelqu'un d'autre
            Label badge = new Label("🔒 Déjà réservé");
            badge.setStyle("-fx-text-fill: #95a5a6; -fx-font-weight: bold; -fx-font-size: 14px;");
            card.getChildren().add(badge);

        } else {
            // Pack disponible
            Button reserveBtn = new Button("Réserver ce pack");
            reserveBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; " +
                              "-fx-cursor: hand; -fx-padding: 10 20; -fx-background-radius: 5;");
            reserveBtn.setOnAction(e -> reserver(pack));
            card.getChildren().add(reserveBtn);
        }

        return card;
    }

    private void reserver(SponsoringPack pack) {
        servicePack.reserverPack(pack.getId_pack(), currentUserId);
        statusLabel.setText("✅ Félicitations ! Vous avez réservé le " + pack.getNom_pack());
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold; -fx-font-size: 14px;");
        chargerPacks();
    }

    private void annulerReservation(SponsoringPack pack) {
        // Sécurité supplémentaire : même si le bouton est activé, on vérifie la date
        LocalDate eventDate = LocalDate.parse(currentEvenement.getDate());
        long daysRemaining = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), eventDate);

        if (daysRemaining < 7) {
            statusLabel.setText("❌ Impossible d'annuler : la limite des 7 jours est dépassée.");
            statusLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-font-size: 14px;");
            return;
        }

        servicePack.reserverPack(pack.getId_pack(), 0); // Remettre id_sponsor à 0
        statusLabel.setText("🔄 Réservation du " + pack.getNom_pack() + " annulée.");
        statusLabel.setStyle("-fx-text-fill: #e67e22; -fx-font-weight: bold; -fx-font-size: 14px;");
        chargerPacks();
    }

    @FXML
    void retour(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/details-evenement-sponsor.fxml"));
        Parent root = loader.load();

        DetailsEvenementSponsorController controller = loader.getController();
        controller.initData(currentEvenement);

        stage.getScene().setRoot(root);
    }
}
