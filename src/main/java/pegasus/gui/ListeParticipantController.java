package pegasus.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import pegasus.entities.Evenement;
import pegasus.services.ServiceEvenement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListeParticipantController {

    @FXML private FlowPane eventsFlowPane;
    @FXML private TextField searchField;
    @FXML private ComboBox<String> sortCombo;

    private ServiceEvenement serviceEvenement = new ServiceEvenement();
    private List<Evenement> tousEvenements = new ArrayList<>();

    @FXML
    void initialize() {
        // Initialize Sort Options
        sortCombo.getItems().addAll("Date (Plus récent)", "Date (Plus ancien)", "Prix (Croissant)", "Prix (Décroissant)", "Titre (A-Z)");
        sortCombo.setValue("Date (Plus récent)");

        chargerDonnees();
        
        searchField.textProperty().addListener((obs, old, newValue) -> filtrerEtAfficher());
        sortCombo.valueProperty().addListener((obs, old, newValue) -> filtrerEtAfficher());
    }

    private void chargerDonnees() {
        tousEvenements = serviceEvenement.afficherEvenements();
        filtrerEtAfficher();
    }

    private void filtrerEtAfficher() {
        eventsFlowPane.getChildren().clear();
        
        String searchText = searchField.getText().toLowerCase();

        List<Evenement> filteredList = tousEvenements.stream()
                .filter(e -> e.getTitre().toLowerCase().contains(searchText))
                .collect(Collectors.toList());

        // Sorting
        String sortOption = sortCombo.getValue();
        if (sortOption != null) {
            switch (sortOption) {
                case "Date (Plus récent)":
                    filteredList.sort(Comparator.comparing(Evenement::getDate).reversed());
                    break;
                case "Date (Plus ancien)":
                    filteredList.sort(Comparator.comparing(Evenement::getDate));
                    break;
                case "Prix (Croissant)":
                    filteredList.sort(Comparator.comparing(Evenement::getPrix));
                    break;
                case "Prix (Décroissant)":
                    filteredList.sort(Comparator.comparing(Evenement::getPrix).reversed());
                    break;
                case "Titre (A-Z)":
                    filteredList.sort(Comparator.comparing(Evenement::getTitre));
                    break;
            }
        }

        if (filteredList.isEmpty()) {
            Label noMatch = new Label("Aucun événement ne correspond à vos critères.");
            noMatch.setStyle("-fx-font-style: italic; -fx-text-fill: #7f8c8d; -fx-padding: 50;");
            eventsFlowPane.getChildren().add(noMatch);
            return;
        }

        for (Evenement e : filteredList) {
            eventsFlowPane.getChildren().add(createEventCard(e));
        }
    }

    private VBox createEventCard(Evenement e) {
        VBox card = new VBox(0);
        card.setPrefWidth(320);
        card.setMaxWidth(320);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 15, 0, 0, 5); -fx-cursor: hand;");

        card.setOnMouseClicked(event -> allerVersDetails(e));

        StackPane imageStack = new StackPane();
        imageStack.setPrefHeight(180);
        imageStack.setStyle("-fx-background-color: #ecf0f1; -fx-background-radius: 15 15 0 0;");
        
        ImageView imageView = new ImageView();
        imageView.setFitWidth(320);
        imageView.setFitHeight(180);
        imageView.setPreserveRatio(false);
        
        javafx.scene.shape.Rectangle clip = new javafx.scene.shape.Rectangle(320, 180);
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        imageStack.setClip(clip);

        try {
            if (e.getImage() != null && !e.getImage().isEmpty()) {
                String path = e.getImage();
                if (!path.startsWith("http") && !path.startsWith("file:")) {
                    path = "file:///" + path.replace("\\", "/");
                }
                Image img = new Image(path, true);
                imageView.setImage(img);
            }
        } catch (Exception ex) {}

        Label badge = new Label("Event");
        badge.setStyle("-fx-background-color: #6c5ce7; -fx-text-fill: white; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 4 10; -fx-background-radius: 10;");
        StackPane.setAlignment(badge, Pos.TOP_RIGHT);
        StackPane.setMargin(badge, new Insets(10));

        imageStack.getChildren().addAll(imageView, badge);

        VBox content = new VBox(12);
        content.setPadding(new Insets(20));

        Label title = new Label(e.getTitre());
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2d3436;");
        title.setWrapText(true);

        Label desc = new Label(e.getDescription());
        desc.setStyle("-fx-font-size: 13px; -fx-text-fill: #636e72;");
        desc.setWrapText(true);
        desc.setMaxHeight(40);
        desc.setMinHeight(40);

        VBox meta = new VBox(8);
        Label date = new Label("📅  " + e.getDate());
        date.setStyle("-fx-font-size: 12px; -fx-text-fill: #b2bec3;");
        Label lieu = new Label("📍  " + e.getLieu());
        lieu.setStyle("-fx-font-size: 12px; -fx-text-fill: #b2bec3;");
        meta.getChildren().addAll(date, lieu);

        Label prix = new Label(String.format("%.2f DT", e.getPrix()));
        prix.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #fdcb6e;");

        HBox actions = new HBox(15);
        actions.setAlignment(Pos.CENTER_LEFT);
        actions.setPadding(new Insets(10, 0, 0, 0));
        Button viewBtn = new Button("👁 Voir Détails");
        viewBtn.setStyle("-fx-background-color: #6c5ce7; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 20; -fx-background-radius: 20;");
        viewBtn.setOnAction(event -> allerVersDetails(e));
        actions.getChildren().add(viewBtn);

        content.getChildren().addAll(title, desc, meta, prix, actions);
        card.getChildren().addAll(imageStack, content);

        return card;
    }

    private void allerVersDetails(Evenement e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/details-evenement-participant.fxml"));
            Parent root = loader.load();
            DetailsEvenementParticipantController controller = loader.getController();
            controller.initData(e);
            searchField.getScene().setRoot(root);
        } catch (IOException ex) { ex.printStackTrace(); }
    }
}
