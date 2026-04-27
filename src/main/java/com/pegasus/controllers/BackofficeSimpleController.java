package com.pegasus.controllers;

import com.pegasus.entities.Art;
import com.pegasus.services.ServiceArt;
import com.pegasus.tools.dbConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
    
    private ServiceArt serviceArt;
    
    @FXML
    public void initialize() {
        System.out.println("BackofficeSimpleController initialisé");
        
        serviceArt = new ServiceArt();
        refreshTable();
    }
    
    @FXML
    public void refreshTable() {
        try {
            System.out.println("Loading artworks...");
            statusLabel.setText("Loading...");
            
            // Test de connexion
            try (Connection conn = dbConnection.getConnection()) {
                System.out.println("Connexion OK");
            } catch (SQLException e) {
                System.err.println("Connection error: " + e.getMessage());
                statusLabel.setText("Connection error: " + e.getMessage());
                return;
            }
            
            List<Art> artworks = serviceArt.getAllArts();
            System.out.println("Found " + artworks.size() + " artworks");
            
            // Vider le container
            artworksContainer.getChildren().clear();
            
            if (artworks.isEmpty()) {
                Text noArtText = new Text("No artwork found. Add artworks from front office!");
                noArtText.setStyle("-fx-font-size: 16px; -fx-fill: #7f8c8d;");
                artworksContainer.getChildren().add(noArtText);
            } else {
                for (Art art : artworks) {
                    artworksContainer.getChildren().add(createArtworkCard(art));
                }
            }
            
            statusLabel.setText("Total: " + artworks.size() + " artwork(s)");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            statusLabel.setText("Error: " + e.getMessage());
        }
    }
    
    private VBox createArtworkCard(Art art) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 10; -fx-padding: 15;");
        
        // Header
        HBox header = new HBox(10);
        header.getChildren().addAll(
            new Text("ID: " + art.getId()),
            new Text("Status: " + getStatusDisplay(art.getStatus()))
        );
        
        // Content
        VBox content = new VBox(5);
        content.getChildren().addAll(
            new Text("Titre: " + art.getTitle()),
            new Text("Artiste: " + art.getArtist()),
            new Text("Description: " + (art.getDescription().length() > 100 ? art.getDescription().substring(0, 100) + "..." : art.getDescription())),
            new Text("URL: " + art.getImageUrl()),
            new Text("Date: " + art.getCreatedAt())
        );
        
        // DEBUG pour voir la valeur exacte
        System.out.println("DEBUG BACK - Œuvre ID " + art.getId() + " - Artiste: '" + art.getArtist() + "'");
        
        // Actions
        HBox actions = new HBox(10);
        Button publishBtn = new Button("Publier");
        Button deleteBtn = new Button("Supprimer");
        
        publishBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
        deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        
        // Actions
        publishBtn.setOnAction(e -> updateStatus(art, "published"));
        deleteBtn.setOnAction(e -> deleteArt(art));
        
        // Disable publish button if already published
        if ("published".equals(art.getStatus())) {
            publishBtn.setDisable(true);
        }
        
        actions.getChildren().addAll(publishBtn, deleteBtn);
        
        card.getChildren().addAll(header, content, actions);
        return card;
    }
    
    private String getStatusDisplay(String status) {
        switch (status) {
            case "pending": return "En attente";
            case "published": return "Publié";
            default: return "En attente"; // Par défaut, tout ce qui n'est pas publié est "En attente"
        }
    }
    
    private void updateStatus(Art art, String newStatus) {
        try {
            // Créer une copie de l'art pour éviter de modifier l'original
            Art artToUpdate = new Art();
            artToUpdate.setId(art.getId());
            artToUpdate.setTitle(art.getTitle());
            artToUpdate.setDescription(art.getDescription());
            artToUpdate.setImageUrl(art.getImageUrl());
            artToUpdate.setStatus(newStatus);
            artToUpdate.setCreatedAt(art.getCreatedAt());
            
            System.out.println("Updating artwork ID: " + art.getId() + " to status: " + newStatus);
            
            if (serviceArt.updateArt(artToUpdate)) {
                System.out.println("Artwork " + art.getId() + " updated successfully: " + newStatus);
                refreshTable();
            } else {
                System.err.println("Error updating artwork " + art.getId());
            }
        } catch (Exception e) {
            System.err.println("Error during update: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void displayArtworks(List<Art> artworks) {
        try {
            System.out.println("Displaying " + artworks.size() + " artworks");
            
            // Vider le container
            artworksContainer.getChildren().clear();
            
            if (artworks.isEmpty()) {
                Text noArtText = new Text("No artwork found. Add artworks from front office!");
                noArtText.setStyle("-fx-font-size: 16px; -fx-fill: #7f8c8d;");
                artworksContainer.getChildren().add(noArtText);
            } else {
                for (Art art : artworks) {
                    artworksContainer.getChildren().add(createArtworkCard(art));
                }
            }
            
            statusLabel.setText("Total: " + artworks.size() + " artwork(s)");
            
        } catch (Exception e) {
            System.err.println("Error displaying artworks: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void deleteArt(Art art) {
        try {
            if (serviceArt.deleteArt(art.getId())) {
                System.out.println("Artwork " + art.getId() + " deleted");
                refreshTable();
            } else {
                System.err.println("Delete error");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
        
    @FXML
    public void sortRecent() {
        try {
            System.out.println("Sorting by most recent...");
            statusLabel.setText("Tri par plus récent...");
            
            List<Art> arts = serviceArt.getAllArts();
            // Trier par date décroissante (plus récent en premier)
            arts.sort((a1, a2) -> a2.getCreatedAt().compareTo(a1.getCreatedAt()));
            
            displayArtworks(arts);
            statusLabel.setText("Trié par plus récent");
            
        } catch (Exception e) {
            System.err.println("Error sorting recent: " + e.getMessage());
            statusLabel.setText("Erreur de tri");
        }
    }
    
    @FXML
    public void sortOlder() {
        try {
            System.out.println("Sorting by oldest...");
            statusLabel.setText("Tri par plus ancien...");
            
            List<Art> arts = serviceArt.getAllArts();
            // Trier par date croissante (plus ancien en premier)
            arts.sort((a1, a2) -> a1.getCreatedAt().compareTo(a2.getCreatedAt()));
            
            displayArtworks(arts);
            statusLabel.setText("Trié par plus ancien");
            
        } catch (Exception e) {
            System.err.println("Error sorting older: " + e.getMessage());
            statusLabel.setText("Erreur de tri");
        }
    }
    
    @FXML
    public void goToHome() {
        try {
            SceneNavigator.goTo("/views/home-view.fxml");
        } catch (IOException e) {
            System.err.println("Navigation error: " + e.getMessage());
        }
    }
}
