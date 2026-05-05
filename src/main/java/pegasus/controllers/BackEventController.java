package pegasus.controllers;

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
import pegasus.entities.Evenement;
import pegasus.services.ServiceEvenement;

import java.io.IOException;
import java.util.List;

public class BackEventController {

    @FXML private TableView<Evenement> eventsTable;
    @FXML private TextField searchField;

    private ServiceEvenement serviceEvenement = new ServiceEvenement();
    private ObservableList<Evenement> evenementsList;

    @FXML
    public void initialize() {
        if (eventsTable != null) {
            eventsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }
        chargerDonnees();

        // Recherche dynamique
        if (searchField != null) {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                rechercher(newValue);
            });
        }
    }

    private void chargerDonnees() {
        if (eventsTable == null) return;
        List<Evenement> list = serviceEvenement.afficherEvenements();
        evenementsList = FXCollections.observableArrayList(list);
        eventsTable.setItems(evenementsList);
    }

    private void rechercher(String query) {
        if (query == null || query.trim().isEmpty()) {
            eventsTable.setItems(evenementsList);
            return;
        }
        String lowerCaseQuery = query.toLowerCase();
        ObservableList<Evenement> filteredData = FXCollections.observableArrayList();
        for (Evenement e : evenementsList) {
            if (e.getTitre().toLowerCase().contains(lowerCaseQuery) || e.getLieu().toLowerCase().contains(lowerCaseQuery)) {
                filteredData.add(e);
            }
        }
        eventsTable.setItems(filteredData);
    }

    @FXML
    void ajouterEvenement(ActionEvent event) {
        ouvrirModalFormulaire("/views/ajouter-evenement.fxml", "Ajouter un Événement", null);
    }

    @FXML
    void modifierEvenement(ActionEvent event) {
        Evenement selectedEvent = eventsTable.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            afficherAlerte(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner un événement à modifier.");
            return;
        }

        ouvrirModalFormulaire("/views/modifier-evenement-view.fxml", "Modifier l'Événement", selectedEvent);
    }

    @FXML
    void supprimerEvenement(ActionEvent event) {
        Evenement selectedEvent = eventsTable.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            afficherAlerte(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner un événement à supprimer.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer l'événement : " + selectedEvent.getTitre() + " ?");
        
        if (alert.showAndWait().get() == ButtonType.OK) {
            serviceEvenement.supprimer(selectedEvent);
            chargerDonnees(); // Mettre à jour la table
        }
    }

    @FXML
    void retourDashboard(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/views/back-view.fxml"));
        stage.getScene().setRoot(root);
    }

    @FXML
    void goHome(ActionEvent event) throws IOException {
        SceneNavigator.goTo("/views/home-view.fxml");
    }

    @FXML
    void goGallery(ActionEvent event) throws IOException {
        SceneNavigator.goTo("/views/menu-view.fxml");
    }

    @FXML
    void goEvents(ActionEvent event) throws IOException {
        SceneNavigator.goTo(EventsRoleRouter.resolveEventsEntryFxml());
    }

    @FXML
    void goBackoffice(ActionEvent event) throws IOException {
        SceneNavigator.goTo("/views/backoffice-simple.fxml");
    }

    private void ouvrirModalFormulaire(String fxmlPath, String titrePage, Evenement e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Remplir les données du formulaire si c'est une modification
            if (e != null && fxmlPath.contains("modifier")) {
                ModifierEvenementController controller = loader.getController();
                controller.setOrigin("BACK");
                controller.initData(e);
            } else if (fxmlPath.contains("ajouter-evenement")) {
                AjouterEvenementController controller = loader.getController();
                controller.setOrigin("BACK");
            }

            // Remplacer la scène entière (PAS de popup)
            Stage stage = (Stage) eventsTable.getScene().getWindow();
            stage.getScene().setRoot(root);
            
        } catch (IOException ex) {
            ex.printStackTrace();
            afficherAlerte(Alert.AlertType.ERROR, "Erreur système", "Impossible d'ouvrir le formulaire : " + ex.getMessage());
        }
    }

    private void afficherAlerte(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
