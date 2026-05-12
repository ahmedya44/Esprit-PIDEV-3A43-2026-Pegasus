package com.pegasus.controllers.back;

import com.pegasus.controllers.EventsRoleRouter;
import com.pegasus.controllers.SceneNavigator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.pegasus.entities.Evenement;
import com.pegasus.entities.User;
import com.pegasus.services.ServiceEvenement;
import com.pegasus.services.ServiceParticipation;

import java.io.IOException;
import java.util.List;

public class BackParticipantController {

    @FXML private TableView<Evenement> eventsTable;
    @FXML private TableColumn<Evenement, String> colEventTitre;
    @FXML private TableColumn<Evenement, String> colEventDate;
    @FXML private TableView<User> participantsTable;
    @FXML private TableColumn<User, String> colUserNom;
    @FXML private TableColumn<User, String> colUserPrenom;
    @FXML private TableColumn<User, String> colUserEmail;
    @FXML private TableColumn<User, String> colUserTel;
    @FXML private TextField searchEventField;
    @FXML private Label selectedEventLabel;
    @FXML private Button btnSupprimer;

    private ServiceEvenement serviceEvenement = new ServiceEvenement();
    private ServiceParticipation serviceParticipation = new ServiceParticipation();

    private ObservableList<Evenement> evenementsList;
    private ObservableList<User> masterParticipantsList = FXCollections.observableArrayList();
    private ObservableList<User> filteredParticipantsList = FXCollections.observableArrayList();
    private Evenement currentSelectedEvent;

    @FXML private TextField searchParticipantField;

    @FXML
    public void initialize() {
        // Configuration des colonnes - Table Événements
        colEventTitre.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("titre"));
        colEventDate.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("date"));

        // Configuration des colonnes - Table Participants
        colUserNom.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("nom"));
        colUserPrenom.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("prenom"));
        colUserEmail.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("email"));
        colUserTel.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("telephone"));

        chargerEvenements();

        // Écouter la recherche d'événements (si présente)
        if (searchEventField != null) {
            searchEventField.textProperty().addListener((obs, old, newValue) -> {
                filtrerEvenements(newValue);
            });
        }

        // Écouter la sélection d'un événement
        eventsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                currentSelectedEvent = newSelection;
                selectedEventLabel.setText("Participants pour : " + newSelection.getTitre());
                chargerParticipants(newSelection.getId());
            } else {
                selectedEventLabel.setText("Sélectionnez un événement à gauche");
                participantsTable.setItems(FXCollections.observableArrayList());
            }
        });

        // Écouter la sélection d'un participant pour activer le bouton supprimer
        participantsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (btnSupprimer != null) {
                btnSupprimer.setDisable(newSelection == null);
            }
        });
        
        // Écouter la recherche de participants
        if (searchParticipantField != null) {
            searchParticipantField.textProperty().addListener((obs, old, newValue) -> {
                filtrerParticipants(newValue);
            });
        }
    }

    private void chargerEvenements() {
        List<Evenement> list = serviceEvenement.afficherEvenements();
        evenementsList = FXCollections.observableArrayList(list);
        eventsTable.setItems(evenementsList);
    }

    private void filtrerEvenements(String query) {
        if (query == null || query.trim().isEmpty()) {
            eventsTable.setItems(evenementsList);
            return;
        }
        String q = query.toLowerCase();
        List<Evenement> filteredData = evenementsList.stream()
                .filter(e -> e.getTitre().toLowerCase().contains(q))
                .collect(java.util.stream.Collectors.toList());
        eventsTable.setItems(FXCollections.observableArrayList(filteredData));
    }

    private void chargerParticipants(int eventId) {
        List<User> list = serviceParticipation.getParticipantsByEvenement(eventId);
        masterParticipantsList = FXCollections.observableArrayList(list);
        participantsTable.setItems(masterParticipantsList);
    }

    private void filtrerParticipants(String query) {
        if (query == null || query.trim().isEmpty()) {
            participantsTable.setItems(masterParticipantsList);
            return;
        }
        String q = query.toLowerCase();
        List<User> filteredData = masterParticipantsList.stream()
                .filter(u -> (u.getNom() + " " + u.getPrenom()).toLowerCase().contains(q) || u.getEmail().toLowerCase().contains(q))
                .collect(java.util.stream.Collectors.toList());
        participantsTable.setItems(FXCollections.observableArrayList(filteredData));
    }

    @FXML
    void supprimerParticipant(ActionEvent event) {
        User selectedUser = participantsTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null || currentSelectedEvent == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Retirer le participant ?");
        alert.setContentText("Voulez-vous vraiment retirer " + selectedUser.getNom() + " " + selectedUser.getPrenom() + " de l'événement ?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            serviceParticipation.annulerParticipation(selectedUser.getId(), currentSelectedEvent.getId());
            chargerParticipants(currentSelectedEvent.getId()); // Rafraîchir
        }
    }

    @FXML
    void retourDashboard(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/views/back/back-view.fxml"));
        stage.getScene().setRoot(root);
    }

    @FXML
    void goHome(ActionEvent event) throws IOException {
        SceneNavigator.goTo("/views/front/home-view.fxml");
    }

    @FXML
    void goGallery(ActionEvent event) throws IOException {
        SceneNavigator.goTo("/views/front/menu-view.fxml");
    }

    @FXML
    void goEvents(ActionEvent event) throws IOException {
        SceneNavigator.goTo(EventsRoleRouter.resolveEventsEntryFxml());
    }

    @FXML
    void exporterPDF(ActionEvent event) {
        if (currentSelectedEvent == null || masterParticipantsList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Export PDF");
            alert.setHeaderText(null);
            alert.setContentText("Sélectionnez un événement avec des participants pour exporter.");
            alert.showAndWait();
            return;
        }

        String userHome = System.getProperty("user.home");
        String fileName = "Participants_" + currentSelectedEvent.getTitre().replaceAll("\\W+", "_") + ".pdf";
        java.io.File file = new java.io.File(userHome + "/Downloads/" + fileName);

        try {
            com.itextpdf.kernel.pdf.PdfWriter writer = new com.itextpdf.kernel.pdf.PdfWriter(file.getAbsolutePath());
            com.itextpdf.kernel.pdf.PdfDocument pdf = new com.itextpdf.kernel.pdf.PdfDocument(writer);
            com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdf);

            document.add(new com.itextpdf.layout.element.Paragraph("Liste des Participants").setFontSize(18).setBold());
            document.add(new com.itextpdf.layout.element.Paragraph("Événement : " + currentSelectedEvent.getTitre()).setFontSize(14));
            document.add(new com.itextpdf.layout.element.Paragraph("Date : " + currentSelectedEvent.getDate() + " à " + currentSelectedEvent.getHeure()));
            document.add(new com.itextpdf.layout.element.Paragraph("\n"));

            com.itextpdf.layout.element.Table table = new com.itextpdf.layout.element.Table(com.itextpdf.layout.properties.UnitValue.createPercentArray(new float[]{25, 25, 35, 15})).useAllAvailableWidth();
            table.addHeaderCell("Nom");
            table.addHeaderCell("Prénom");
            table.addHeaderCell("Email");
            table.addHeaderCell("Téléphone");

            for (User u : participantsTable.getItems()) {
                table.addCell(u.getNom() == null ? "" : u.getNom());
                table.addCell(u.getPrenom() == null ? "" : u.getPrenom());
                table.addCell(u.getEmail() == null ? "" : u.getEmail());
                table.addCell(u.getTelephone() == null ? "" : u.getTelephone());
            }

            document.add(table);
            document.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Export PDF");
            alert.setHeaderText("Succès !");
            alert.setContentText("Le rapport a été enregistré dans vos Téléchargements :\n" + file.getName());
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur Export PDF");
            alert.setHeaderText("Impossible de générer le fichier");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void goBackoffice(ActionEvent event) throws IOException {
        SceneNavigator.goTo("/views/back/AdminLayout.fxml");
    }
}
