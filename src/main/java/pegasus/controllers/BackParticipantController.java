package pegasus.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pegasus.entities.Evenement;
import pegasus.entities.User;
import pegasus.services.ServiceEvenement;
import pegasus.services.ServiceParticipation;

import java.io.IOException;
import java.util.List;

public class BackParticipantController {

    @FXML private TableView<Evenement> eventsTable;
    @FXML private TableView<User> participantsTable;
    @FXML private TextField searchEventField;
    @FXML private Label selectedEventLabel;
    @FXML private Button btnSupprimer;

    private ServiceEvenement serviceEvenement = new ServiceEvenement();
    private ServiceParticipation serviceParticipation = new ServiceParticipation();

    private ObservableList<Evenement> evenementsList;
    private ObservableList<User> participantsList;
    private Evenement currentSelectedEvent;

    @FXML
    public void initialize() {
        // Redimensionnement automatique des colonnes
        eventsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        participantsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        chargerEvenements();

        // Écouter la recherche d'événements
        searchEventField.textProperty().addListener((obs, old, newValue) -> {
            filtrerEvenements(newValue);
        });

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
            btnSupprimer.setDisable(newSelection == null);
        });
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
        participantsList = FXCollections.observableArrayList(list);
        participantsTable.setItems(participantsList);
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
        Parent root = FXMLLoader.load(getClass().getResource("/views/back-view.fxml"));
        stage.getScene().setRoot(root);
    }
}
