package com.pegasus.controllers.back;

import com.pegasus.controllers.SceneNavigator;
import com.pegasus.entities.Art;
import com.pegasus.services.ServiceArt;
import com.pegasus.tools.dbConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BackofficeSimpleController {

    @FXML
    private VBox artworksContainer;

    @FXML
    private Label statusLabel;

    @FXML
    private Button refreshButton;

    @FXML
    private Button returnButton;

    @FXML
    private Button sortRecentButton;

    @FXML
    private Button sortOlderButton;

    private ServiceArt serviceArt;

    @FXML
    public void initialize() {
        serviceArt = new ServiceArt();
        refreshTable();
    }

    @FXML
    public void refreshTable() {
        try {
            statusLabel.setText("Loading...");

            try (Connection conn = dbConnection.getConnection()) {
                // Connection check.
            } catch (SQLException e) {
                statusLabel.setText("Connection error: " + e.getMessage());
                return;
            }

            List<Art> artworks = serviceArt.getAllArts();
            displayArtworks(artworks);
            statusLabel.setText("Total: " + artworks.size() + " artwork(s)");

        } catch (Exception e) {
            statusLabel.setText("Error: " + e.getMessage());
        }
    }

    private VBox createArtworkCard(Art art) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 10; -fx-padding: 15;");

        String artist = (art.getArtist() == null || art.getArtist().isBlank()) ? "Artiste inconnu" : art.getArtist();
        String description = art.getDescription() == null ? "" : art.getDescription();
        String imageUrl = art.getImageUrl() == null ? "" : art.getImageUrl();

        HBox header = new HBox(10);
        header.getChildren().addAll(
                new Text("ID: " + art.getId()),
                new Text("Status: " + getStatusDisplay(art.getStatus()))
        );

        VBox content = new VBox(5);
        content.getChildren().addAll(
                new Text("Titre: " + art.getTitle()),
                new Text("Artiste: " + artist),
                new Text("Description: " + (description.length() > 100 ? description.substring(0, 100) + "..." : description)),
                new Text("URL: " + imageUrl),
                new Text("Date: " + art.getCreatedAt())
        );

        HBox actions = new HBox(10);
        Button publishBtn = new Button("Publier");
        Button rejectBtn = new Button("Rejeter");
        Button deleteBtn = new Button("Supprimer");

        publishBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
        rejectBtn.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white;");
        deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

        publishBtn.setOnAction(e -> updateStatus(art, "published"));
        rejectBtn.setOnAction(e -> updateStatus(art, "rejected"));
        deleteBtn.setOnAction(e -> deleteArt(art));

        actions.getChildren().addAll(publishBtn, rejectBtn, deleteBtn);
        card.getChildren().addAll(header, content, actions);
        return card;
    }

    private String getStatusDisplay(String status) {
        if (status == null) {
            return "En attente";
        }
        return switch (status.toLowerCase()) {
            case "pending" -> "En attente";
            case "published", "approved", "active" -> "Publie";
            case "rejected" -> "Rejete";
            default -> status;
        };
    }

    private void updateStatus(Art art, String newStatus) {
        try {
            art.setStatus(newStatus);
            if (serviceArt.updateArt(art)) {
                refreshTable();
            }
        } catch (Exception ignored) {
        }
    }

    private void displayArtworks(List<Art> artworks) {
        artworksContainer.getChildren().clear();

        if (artworks.isEmpty()) {
            Text noArtText = new Text("No artwork found. Add artworks from front office!");
            noArtText.setStyle("-fx-font-size: 16px; -fx-fill: #7f8c8d;");
            artworksContainer.getChildren().add(noArtText);
            return;
        }

        for (Art art : artworks) {
            artworksContainer.getChildren().add(createArtworkCard(art));
        }
    }

    private void deleteArt(Art art) {
        try {
            if (serviceArt.deleteArt(art.getId())) {
                refreshTable();
            }
        } catch (Exception ignored) {
        }
    }

    @FXML
    public void sortRecent() {
        try {
            List<Art> arts = serviceArt.getAllArts();
            arts.sort((a1, a2) -> a2.getCreatedAt().compareTo(a1.getCreatedAt()));
            displayArtworks(arts);
            statusLabel.setText("Sorted by most recent");
        } catch (Exception e) {
            statusLabel.setText("Sort error");
        }
    }

    @FXML
    public void sortOlder() {
        try {
            List<Art> arts = serviceArt.getAllArts();
            arts.sort((a1, a2) -> a1.getCreatedAt().compareTo(a2.getCreatedAt()));
            displayArtworks(arts);
            statusLabel.setText("Sorted by oldest");
        } catch (Exception e) {
            statusLabel.setText("Sort error");
        }
    }

    @FXML
    public void goToHome() {
        try {
            SceneNavigator.goTo("/views/front/home-view.fxml");
        } catch (IOException ignored) {
        }
    }

    @FXML
    public void goToGallery() {
        try {
            SceneNavigator.goTo("/views/front/menu-view.fxml");
        } catch (IOException ignored) {
        }
    }
}