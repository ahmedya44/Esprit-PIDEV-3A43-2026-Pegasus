package com.pegasus.controllers.front;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.pegasus.entities.Evenement;
import com.pegasus.entities.User;
import com.pegasus.services.ServiceParticipation;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import javafx.stage.FileChooser;

public class ListeParticipantsController {

    @FXML private Label evenementTitreLabel;
    @FXML private TableView<User> participantsTable;
    @FXML private TableColumn<User, String> nomCol;
    @FXML private TableColumn<User, String> prenomCol;
    @FXML private TableColumn<User, String> emailCol;
    @FXML private TableColumn<User, String> telCol;
    @FXML private TableColumn<User, Void> actionCol;
    @FXML private Label totalLabel;

    private ServiceParticipation serviceParticipation = new ServiceParticipation();
    private Evenement currentEvent;

    public void initData(Evenement e) {
        this.currentEvent = e;
        evenementTitreLabel.setText(e.getTitre());
        chargerParticipants();
    }

    private void chargerParticipants() {
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        telCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        // Action column with delete button
        actionCol.setCellFactory(new Callback<>() {
            @Override
            public TableCell<User, Void> call(TableColumn<User, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("🗑️ Supprimer");
                    {
                        btn.setStyle("-fx-background-color: #eb4d4b; -fx-text-fill: white; -fx-font-weight: bold; " +
                                    "-fx-cursor: hand; -fx-background-radius: 20; -fx-padding: 8 15; -fx-font-size: 13px;");
                        btn.setOnAction(event -> {
                            User user = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Suppression");
                            alert.setHeaderText("Retirer " + user.getNom() + " ?");
                            alert.setContentText("Ce participant sera retiré de l'événement.");

                            if (alert.showAndWait().get() == ButtonType.OK) {
                                serviceParticipation.annulerParticipation(user.getId(), currentEvent.getId());
                                chargerParticipants();
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : btn);
                    }
                };
            }
        });

        List<User> list = serviceParticipation.getParticipantsByEvenement(currentEvent.getId());
        ObservableList<User> observableList = FXCollections.observableArrayList(list);
        participantsTable.setItems(observableList);

        totalLabel.setText("Total : " + list.size() + " inscrits");
    }

    @FXML
    void exporterPDF(ActionEvent event) {
        List<User> participants = participantsTable.getItems();
        if (participants.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Export PDF");
            alert.setHeaderText(null);
            alert.setContentText("Il n'y a aucun participant à exporter.");
            alert.showAndWait();
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le PDF");
        fileChooser.setInitialFileName("Participants_" + currentEvent.getTitre() + ".pdf");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
        
        File file = fileChooser.showSaveDialog(participantsTable.getScene().getWindow());
        
        if (file != null) {
            try {
                PdfWriter writer = new PdfWriter(file.getAbsolutePath());
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                document.add(new Paragraph("Liste des Participants Inscrits").setFontSize(20).setBold());
                document.add(new Paragraph("Événement : " + currentEvent.getTitre()));
                document.add(new Paragraph("Date d'export : " + java.time.LocalDate.now()));
                document.add(new Paragraph("\n"));

                Table table = new Table(UnitValue.createPercentArray(new float[]{25, 25, 30, 20})).useAllAvailableWidth();
                table.addHeaderCell("Nom");
                table.addHeaderCell("Prénom");
                table.addHeaderCell("Email");
                table.addHeaderCell("Téléphone");

                for (User u : participants) {
                    table.addCell(u.getNom());
                    table.addCell(u.getPrenom());
                    table.addCell(u.getEmail());
                    table.addCell(u.getTelephone());
                }

                document.add(table);
                document.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Export PDF");
                alert.setHeaderText(null);
                alert.setContentText("Le fichier PDF a été généré avec succès !");
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/front/details-evenement-artiste.fxml"));
        Parent root = loader.load();

        DetailsEvenementArtisteController controller = loader.getController();
        controller.initData(currentEvent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }
}
