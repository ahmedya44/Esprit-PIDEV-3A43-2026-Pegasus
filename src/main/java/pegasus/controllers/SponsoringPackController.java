package pegasus.controllers;

import com.pegasus.controllers.EventsRoleRouter;
import com.pegasus.controllers.SceneNavigator;
import com.pegasus.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pegasus.entities.Evenement;
import pegasus.entities.SponsoringPack;
import pegasus.services.ServiceSponsoringPack;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SponsoringPackController {

    @FXML private GridPane packsContainer;
    @FXML private VBox formSection;
    @FXML private VBox sponsorsContainer;
    @FXML private TextField nomField;
    @FXML private TextField prixField;
    @FXML private TextArea descField;
    @FXML private Label messageLabel;
    @FXML private Button navBackofficeButton;

    private ServiceSponsoringPack servicePack = new ServiceSponsoringPack();
    private int selectedPackId = -1;
    private Evenement currentEvenement;

    public void initData(Evenement e) {
        this.currentEvenement = e;
        chargerLister();
        chargerSponsors();
    }

    @FXML
    public void initialize() {
        User currentUser = SceneNavigator.getCurrentUser();
        boolean isAdmin = currentUser != null && "admin".equalsIgnoreCase(currentUser.getDtype());
        if (navBackofficeButton != null) {
            navBackofficeButton.setVisible(isAdmin);
            navBackofficeButton.setManaged(isAdmin);
        }
        setupRealTimeValidation();
    }

    private void setupRealTimeValidation() {
        // Validation en temps réel pour le prix (chiffres et point décimal)
        if (prixField != null) {
            prixField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*([\\.]\\d*)?")) {
                    prixField.setText(oldValue);
                }
            });
        }
    }

    private void chargerLister() {
        packsContainer.getChildren().clear();
        
        if (currentEvenement == null) return;
        
        List<SponsoringPack> list = servicePack.afficherParEvenement(currentEvenement.getId());
        if (list.isEmpty()) {
            List<SponsoringPack> allPacks = servicePack.afficherTout();
            if (!allPacks.isEmpty()) {
                list = allPacks;
                if (messageLabel != null) {
                    messageLabel.setText("Aucun pack lie a cet evenement. Affichage de tous les packs de la base.");
                    messageLabel.setStyle("-fx-text-fill: #e67e22;");
                }
            }
        }
        
        int row = 0;
        int col = 0;

        for (SponsoringPack p : list) {
            VBox card = new VBox(15);
            card.setPrefWidth(260); // Ajusté pour 3 colonnes
            card.setMinHeight(280);
            card.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-border-color: #ecf0f1; " +
                         "-fx-border-radius: 15; -fx-background-radius: 15; " +
                         "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");
            
            // Header: Name and Price Badge
            HBox header = new HBox(10);
            header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            Label nomLabel = new Label(p.getNom_pack());
            nomLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
            
            Pane spacer = new Pane();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            
            Label prixLabel = new Label((int)p.getPrix() + " DT");
            prixLabel.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-padding: 5 15; " +
                              "-fx-background-radius: 15; -fx-font-weight: bold;");
            
            header.getChildren().addAll(nomLabel, spacer, prixLabel);
            
            // Description
            Label descLabel = new Label(p.getDescription());
            descLabel.setWrapText(true);
            descLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 14px;");
            descLabel.setMinHeight(100);
            descLabel.setAlignment(javafx.geometry.Pos.TOP_LEFT);
            
            // Sponsor info
            Label sponsorLabel;
            if (p.getId_sponsor() != 0) {
                String sponsorName = servicePack.getSponsorNameById(p.getId_sponsor());
                sponsorLabel = new Label("🤝 Sponsor : " + sponsorName);
                sponsorLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold; -fx-font-size: 13px; " +
                                    "-fx-background-color: #eafaf1; -fx-padding: 5 10; -fx-background-radius: 5;");
            } else {
                sponsorLabel = new Label("⏳ Aucun sponsor");
                sponsorLabel.setStyle("-fx-text-fill: #95a5a6; -fx-font-size: 13px; " +
                                    "-fx-background-color: #f5f6fa; -fx-padding: 5 10; -fx-background-radius: 5;");
            }

            // Buttons Row
            HBox buttons = new HBox(10);
            buttons.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            
            Button btnModifier = new Button("📝 Modifier");
            btnModifier.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; " +
                                "-fx-cursor: hand; -fx-background-radius: 20; -fx-padding: 8 15;");
            btnModifier.setOnAction(e -> remplirFormulairePack(p));

            Button btnSupprimer = new Button("🗑️ Supprimer");
            btnSupprimer.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; " +
                                 "-fx-cursor: hand; -fx-background-radius: 20; -fx-padding: 8 15;");
            btnSupprimer.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Suppression");
                alert.setHeaderText("Supprimer le pack : " + p.getNom_pack() + " ?");
                alert.setContentText("Cette action est irréversible.");
                
                if (alert.showAndWait().get() == ButtonType.OK) {
                    try {
                        servicePack.supprimer(p);
                        chargerLister();
                    } catch (Exception ex) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Erreur");
                        errorAlert.setHeaderText("Impossible de supprimer le pack");
                        errorAlert.setContentText("Détail : " + ex.getMessage());
                        errorAlert.showAndWait();
                    }
                }
            });

            buttons.getChildren().addAll(btnModifier, btnSupprimer);
            
            card.getChildren().addAll(header, descLabel, sponsorLabel, buttons);
            
            // Ajouter à la grille (colonne, ligne)
            packsContainer.add(card, col, row);
            
            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }
    }

    private void remplirFormulairePack(SponsoringPack p) {
        selectedPackId = p.getId_pack();
        nomField.setText(p.getNom_pack());
        prixField.setText(String.valueOf(p.getPrix()));
        descField.setText(p.getDescription());
        afficherFormulaire(null);
    }

    @FXML
    void afficherFormulaire(ActionEvent event) {
        formSection.setVisible(true);
        formSection.setManaged(true);
    }

    @FXML
    void cacherFormulaire(ActionEvent event) {
        formSection.setVisible(false);
        formSection.setManaged(false);
        viderChamps();
    }

    @FXML
    void enregistrerPack(ActionEvent event) {
        if (!validerPacks()) {
            return;
        }

        try {
            if (currentEvenement == null || currentEvenement.getId() <= 0) {
                afficherErreur("Evenement invalide: impossible de lier le pack.");
                return;
            }

            SponsoringPack p = new SponsoringPack();
            p.setNom_pack(nomField.getText().trim());
            p.setDescription(descField.getText().trim());
            p.setPrix(Float.parseFloat(prixField.getText().trim()));
            p.setId_evenement(currentEvenement.getId());
            p.setId_sponsor(0);
            
            if (selectedPackId != -1) {
                p.setId_pack(selectedPackId);
                servicePack.modifier(p);
            } else {
                servicePack.ajouter(p);
            }

            if (servicePack.getLastError() != null && !servicePack.getLastError().isBlank()) {
                afficherErreur("Erreur DB: " + servicePack.getLastError());
                return;
            }
            
            chargerLister();
            chargerSponsors();
            cacherFormulaire(null);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Le pack de sponsoring a été enregistré avec succès !");
            alert.showAndWait();

        } catch (Exception ex) {
            afficherErreur("Erreur technique : " + ex.getMessage());
        }
    }

    private boolean validerPacks() {
        StringBuilder sb = new StringBuilder();

        if (nomField.getText().trim().isEmpty()) {
            sb.append("- Le nom du pack est obligatoire.\n");
        } else if (nomField.getText().trim().length() < 3) {
            sb.append("- Le nom du pack doit contenir au moins 3 caractères.\n");
        }

        if (prixField.getText().trim().isEmpty()) {
            sb.append("- Le prix est obligatoire.\n");
        } else {
            try {
                float pr = Float.parseFloat(prixField.getText().trim());
                if (pr <= 0) {
                    sb.append("- Le prix doit être strictement supérieur à 0 DT.\n");
                }
            } catch (NumberFormatException e) {
                sb.append("- Le prix doit être un nombre valide.\n");
            }
        }

        if (descField.getText().trim().isEmpty()) {
            sb.append("- La description est obligatoire.\n");
        } else if (descField.getText().trim().length() < 10) {
            sb.append("- La description doit contenir au moins 10 caractères.\n");
        }

        if (sb.length() > 0) {
            afficherErreur(sb.toString());
            return false;
        }
        return true;
    }

    private void afficherErreur(String message) {
        if (messageLabel != null) {
            messageLabel.setText(message);
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private void viderChamps() {
        selectedPackId = -1;
        nomField.clear();
        prixField.clear();
        descField.clear();
        messageLabel.setText("");
    }

    @FXML
    void exporterSponsorsPDF(ActionEvent event) {
        List<String[]> list = servicePack.getSponsorsForEvent(currentEvenement.getId());
        
        if (list.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Export PDF");
            alert.setHeaderText(null);
            alert.setContentText("Il n'y a aucun sponsor à exporter pour cet événement.");
            alert.showAndWait();
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer la liste des sponsors");
        fileChooser.setInitialFileName("Sponsors_" + currentEvenement.getTitre() + ".pdf");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
        
        File file = fileChooser.showSaveDialog(packsContainer.getScene().getWindow());
        
        if (file != null) {
            try {
                PdfWriter writer = new PdfWriter(file.getAbsolutePath());
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                document.add(new Paragraph("Liste des Sponsors de l'Événement").setFontSize(22).setBold());
                document.add(new Paragraph("Événement : " + currentEvenement.getTitre()));
                document.add(new Paragraph("Date d'export : " + java.time.LocalDate.now()));
                document.add(new Paragraph("\n"));

                Table table = new Table(UnitValue.createPercentArray(new float[]{20, 25, 15, 25, 15})).useAllAvailableWidth();
                table.addHeaderCell("Sponsor");
                table.addHeaderCell("Email");
                table.addHeaderCell("Téléphone");
                table.addHeaderCell("Pack réservé");
                table.addHeaderCell("Prix");

                for (String[] s : list) {
                    table.addCell(s[0]);
                    table.addCell(s[1]);
                    table.addCell(s[2]);
                    table.addCell(s[3]);
                    table.addCell(s[4] + " DT");
                }

                document.add(table);
                document.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Export PDF");
                alert.setHeaderText(null);
                alert.setContentText("Le fichier PDF des sponsors a été généré avec succès !");
                alert.showAndWait();

            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Export PDF");
                alert.setHeaderText("Erreur lors de la génération");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    void retour(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/details-evenement-artiste.fxml"));
        Parent root = loader.load();
        
        DetailsEvenementArtisteController controller = loader.getController();
        controller.initData(currentEvenement);
        
        stage.getScene().setRoot(root);
    }

    private void chargerSponsors() {
        sponsorsContainer.getChildren().clear();
        if (currentEvenement == null) return;

        List<String[]> sponsors = servicePack.getSponsorsForEvent(currentEvenement.getId());

        if (sponsors.isEmpty()) {
            Label emptyLabel = new Label("Aucun sponsor pour le moment.");
            emptyLabel.setStyle("-fx-text-fill: #95a5a6; -fx-font-size: 14px; -fx-padding: 10;");
            sponsorsContainer.getChildren().add(emptyLabel);
            return;
        }

        // Table header
        HBox headerRow = new HBox(0);
        headerRow.setStyle("-fx-background-color: #2c3e50; -fx-padding: 10; -fx-background-radius: 5 5 0 0;");
        headerRow.getChildren().addAll(
            createHeaderCell("Sponsor", 160),
            createHeaderCell("Email", 180),
            createHeaderCell("Téléphone", 120),
            createHeaderCell("Pack réservé", 140),
            createHeaderCell("Prix", 90),
            createHeaderCell("Action", 100)
        );
        sponsorsContainer.getChildren().add(headerRow);

        // Rows
        boolean alternate = false;
        for (String[] s : sponsors) {
            HBox row = new HBox(0);
            row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            String bg = alternate ? "#f8f9fa" : "white";
            row.setStyle("-fx-background-color: " + bg + "; -fx-padding: 10; -fx-border-color: #ecf0f1; -fx-border-width: 0 0 1 0;");

            String username = s[0];
            float prix = Float.parseFloat(s[4]);
            int packId = Integer.parseInt(s[5]);

            Button btnSupprimer = new Button("🗑️");
            btnSupprimer.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; " +
                                 "-fx-cursor: hand; -fx-background-radius: 5; -fx-padding: 5 10;");
            btnSupprimer.setOnAction(e -> {
                servicePack.reserverPack(packId, 0);
                chargerSponsors();
                chargerLister();
            });

            row.getChildren().addAll(
                createDataCell(username, 160, "-fx-font-weight: bold; -fx-text-fill: #2c3e50;"),
                createDataCell(s[1], 180, "-fx-text-fill: #3498db;"),    // email
                createDataCell(s[2], 120, "-fx-text-fill: #7f8c8d;"),    // phone
                createDataCell(s[3], 140, "-fx-text-fill: #27ae60; -fx-font-weight: bold;"), // nom_pack
                createDataCell(String.format("%.0f TND", prix), 90, "-fx-text-fill: #e67e22; -fx-font-weight: bold;"),
                btnSupprimer
            );

            sponsorsContainer.getChildren().add(row);
            alternate = !alternate;
        }
    }

    private Label createHeaderCell(String text, double width) {
        Label label = new Label(text);
        label.setPrefWidth(width);
        label.setMinWidth(width);
        label.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px;");
        return label;
    }

    private Label createDataCell(String text, double width, String extraStyle) {
        Label label = new Label(text != null ? text : "-");
        label.setPrefWidth(width);
        label.setMinWidth(width);
        label.setStyle("-fx-font-size: 13px; " + extraStyle);
        return label;
    }

    @FXML
    private void goHome() throws IOException {
        SceneNavigator.goTo("/views/home-view.fxml");
    }

    @FXML
    private void goGallery() throws IOException {
        SceneNavigator.goTo("/views/menu-view.fxml");
    }

    @FXML
    private void goEvents() throws IOException {
        SceneNavigator.goTo(EventsRoleRouter.resolveEventsEntryFxml());
    }

    @FXML
    private void goBackoffice() throws IOException {
        SceneNavigator.goTo("/views/backoffice-simple.fxml");
    }
}


