package com.pegasus.controllers;

import com.pegasus.entities.Art;
import com.pegasus.services.ServiceArt;
import com.pegasus.tools.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BackofficeController {
    
    @FXML
    private TableView<Art> artworksTable;
    
    @FXML
    private TableColumn<Art, Integer> colId;
    
    @FXML
    private TableColumn<Art, String> colTitle;
    
    @FXML
    private TableColumn<Art, String> colDescription;
    
    @FXML
    private TableColumn<Art, String> colImageUrl;
    
    @FXML
    private TableColumn<Art, String> colStatus;
    
    @FXML
    private TableColumn<Art, String> colCreatedAt;
    
    @FXML
    private TableColumn<Art, Void> colActions;
    
    @FXML
    private ComboBox<String> statusFilter;
    
    @FXML
    private Button refreshButton;
    
    @FXML
    private Button returnButton;
    
    @FXML
    private Label statusLabel;
    
    private ServiceArt serviceArt;
    private ObservableList<Art> artworksList;
    
    @FXML
    public void initialize() {
        System.out.println("BackofficeController initialisé");
        
        // Initialiser le service
        serviceArt = new ServiceArt();
        artworksList = FXCollections.observableArrayList();
        
        // Configurer les filtres
        statusFilter.getItems().addAll("Tous", "En attente", "Publié", "Rejeté");
        statusFilter.setValue("Tous");
        statusFilter.setOnAction(e -> filterArtworks());
        
        // Configurer les colonnes
        setupTableColumns();
        
        // Charger les données
        refreshTable();
    }
    
    private void setupTableColumns() {
        // Colonne ID
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        // Colonne Titre
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        
        // Colonne Description (tronquée)
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDescription.setCellFactory(tc -> new TableCell<Art, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    String truncated = item.length() > 50 ? item.substring(0, 47) + "..." : item;
                    setText(truncated);
                    setTooltip(new Tooltip(item));
                }
            }
        });
        
        // Colonne Image URL
        colImageUrl.setCellValueFactory(new PropertyValueFactory<>("imageUrl"));
        colImageUrl.setCellFactory(tc -> new TableCell<Art, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    String truncated = item.length() > 30 ? item.substring(0, 27) + "..." : item;
                    setText(truncated);
                    setTooltip(new Tooltip(item));
                }
            }
        });
        
        // Colonne Statut avec style
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus.setCellFactory(tc -> new TableCell<Art, String>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(status);
                    switch (status) {
                        case "pending":
                            setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-background-radius: 10;");
                            setText("En attente");
                            break;
                        case "published":
                            setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-background-radius: 10;");
                            setText("Publié");
                            break;
                        case "rejected":
                            setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 10;");
                            setText("Rejeté");
                            break;
                        default:
                            setStyle("");
                            break;
                    }
                }
            }
        });
        
        // Colonne Date
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        colCreatedAt.setCellFactory(tc -> new TableCell<Art, String>() {
            @Override
            protected void updateItem(String dateString, boolean empty) {
                super.updateItem(dateString, empty);
                if (empty || dateString == null) {
                    setText(null);
                } else {
                    setText(dateString);
                }
            }
        });
        
        // Colonne Actions avec boutons
        colActions.setCellFactory(param -> new TableCell<Art, Void>() {
            private final Button publishButton = new Button("Publier");
            private final Button rejectButton = new Button("Rejeter");
            private final Button deleteButton = new Button("Supprimer");
            private final HBox buttons = new HBox(5, publishButton, rejectButton, deleteButton);
            
            {
                publishButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-background-radius: 5;");
                rejectButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-background-radius: 5;");
                deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 5;");
                
                publishButton.setOnAction(e -> {
                    Art art = getTableView().getItems().get(getIndex());
                    publishArt(art);
                });
                
                rejectButton.setOnAction(e -> {
                    Art art = getTableView().getItems().get(getIndex());
                    rejectArt(art);
                });
                
                deleteButton.setOnAction(e -> {
                    Art art = getTableView().getItems().get(getIndex());
                    deleteArt(art);
                });
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Art art = getTableView().getItems().get(getIndex());
                    // Afficher les boutons selon le statut
                    if ("pending".equals(art.getStatus())) {
                        publishButton.setVisible(true);
                        rejectButton.setVisible(true);
                        deleteButton.setVisible(true);
                    } else {
                        publishButton.setVisible(false);
                        rejectButton.setVisible(false);
                        deleteButton.setVisible(true);
                    }
                    setGraphic(buttons);
                }
            }
        });
        
        artworksTable.setItems(artworksList);
    }
    
    @FXML
    public void refreshTable() {
        try {
            System.out.println("Chargement des oeuvres depuis la base de données...");
            
            // Test de connexion
            try (Connection conn = dbConnection.getConnection()) {
                System.out.println("Connexion à la base réussie");
            } catch (SQLException e) {
                System.err.println("Erreur de connexion: " + e.getMessage());
                statusLabel.setText("Erreur de connexion: " + e.getMessage());
                return;
            }
            
            List<Art> artworks = serviceArt.getAllArts();
            System.out.println("Récupéré " + artworks.size() + " oeuvres du service");
            
            artworksList.clear();
            artworksList.addAll(artworks);
            
            // Afficher les détails des oeuvres
            for (Art art : artworks) {
                System.out.println("Oeuvre: " + art.getTitle() + " - Status: " + art.getStatus());
            }
            
            statusLabel.setText("Total: " + artworks.size() + " oeuvre(s)");
            System.out.println("Affiché " + artworks.size() + " oeuvres dans le tableau");
            
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement: " + e.getMessage());
            e.printStackTrace();
            statusLabel.setText("Erreur de chargement: " + e.getMessage());
        }
    }
    
    private void filterArtworks() {
        String filter = statusFilter.getValue();
        if (filter == null || "Tous".equals(filter)) {
            artworksTable.setItems(artworksList);
        } else {
            String statusKey = filter.equals("En attente") ? "pending" : 
                              filter.equals("Publié") ? "published" : "rejected";
            
            ObservableList<Art> filtered = artworksList.filtered(art -> statusKey.equals(art.getStatus()));
            artworksTable.setItems(filtered);
            statusLabel.setText(filtered.size() + " oeuvre(s) filtrées");
        }
    }
    
    private void publishArt(Art art) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Publier l'oeuvre");
        confirm.setHeaderText("Êtes-vous sûr de vouloir publier cette oeuvre ?");
        confirm.setContentText("Elle sera visible dans la gallery du front office.");
        
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            art.setStatus("published");
            if (serviceArt.updateArt(art)) {
                refreshTable();
                showAlert("Succès", "L'oeuvre a été publiée avec succès !");
            } else {
                showAlert("Erreur", "Impossible de publier l'oeuvre.");
            }
        }
    }
    
    private void rejectArt(Art art) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Rejeter l'oeuvre");
        confirm.setHeaderText("Êtes-vous sûr de vouloir rejeter cette oeuvre ?");
        confirm.setContentText("Elle ne sera pas visible dans la gallery.");
        
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            art.setStatus("rejected");
            if (serviceArt.updateArt(art)) {
                refreshTable();
                showAlert("Succès", "L'oeuvre a été rejetée.");
            } else {
                showAlert("Erreur", "Impossible de rejeter l'oeuvre.");
            }
        }
    }
    
    private void deleteArt(Art art) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Supprimer l'oeuvre");
        confirm.setHeaderText("Êtes-vous sûr de vouloir supprimer cette oeuvre ?");
        confirm.setContentText("Cette action est irréversible.");
        
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            if (serviceArt.deleteArt(art.getId())) {
                refreshTable();
                showAlert("Succès", "L'oeuvre a été supprimée.");
            } else {
                showAlert("Erreur", "Impossible de supprimer l'oeuvre.");
            }
        }
    }
    
    @FXML
    public void goToHome() {
        try {
            SceneNavigator.goTo("/views/home-view.fxml");
        } catch (IOException e) {
            System.err.println("Erreur lors de la navigation: " + e.getMessage());
        }
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
