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
    private Label statusLabel;

    private ServiceArt serviceArt;
    private ObservableList<Art> artworksList;

    @FXML
    public void initialize() {
        serviceArt = new ServiceArt();
        artworksList = FXCollections.observableArrayList();

        statusFilter.getItems().addAll("Tous", "En attente", "Publie", "Rejete");
        statusFilter.setValue("Tous");
        statusFilter.setOnAction(e -> filterArtworks());

        setupTableColumns();
        artworksTable.setItems(artworksList);
        refreshTable();
    }

    private void setupTableColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colImageUrl.setCellValueFactory(new PropertyValueFactory<>("imageUrl"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        colDescription.setCellFactory(tc -> new TableCell<Art, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    String truncated = item.length() > 50 ? item.substring(0, 47) + "..." : item;
                    setText(truncated);
                    setTooltip(new Tooltip(item));
                }
            }
        });

        colImageUrl.setCellFactory(tc -> new TableCell<Art, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    String truncated = item.length() > 30 ? item.substring(0, 27) + "..." : item;
                    setText(truncated);
                    setTooltip(new Tooltip(item));
                }
            }
        });

        colStatus.setCellFactory(tc -> new TableCell<Art, String>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                    return;
                }

                String normalized = status.trim().toLowerCase();
                switch (normalized) {
                    case "pending":
                        setText("En attente");
                        setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-background-radius: 10;");
                        break;
                    case "published":
                    case "active":
                    case "approved":
                        setText("Publie");
                        setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-background-radius: 10;");
                        break;
                    case "rejected":
                        setText("Rejete");
                        setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 10;");
                        break;
                    default:
                        setText(status);
                        setStyle("");
                        break;
                }
            }
        });

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
                    return;
                }

                // Keep all actions visible so admins can change status from any state.
                publishButton.setVisible(true);
                publishButton.setManaged(true);
                rejectButton.setVisible(true);
                rejectButton.setManaged(true);
                setGraphic(buttons);
            }
        });
    }

    @FXML
    public void refreshTable() {
        try (Connection conn = dbConnection.getConnection()) {
            List<Art> artworks = serviceArt.getAllArts();
            artworksList.setAll(artworks);
            artworksTable.refresh();
            statusLabel.setText("Total: " + artworks.size() + " oeuvre(s)");
            filterArtworks();
        } catch (SQLException e) {
            statusLabel.setText("Erreur de connexion: " + e.getMessage());
        } catch (Exception e) {
            statusLabel.setText("Erreur de chargement: " + e.getMessage());
        }
    }

    private void filterArtworks() {
        String filter = statusFilter.getValue();
        if (filter == null || "Tous".equals(filter)) {
            artworksTable.setItems(artworksList);
            return;
        }

        String statusKey = switch (filter) {
            case "En attente" -> "pending";
            case "Publie" -> "published";
            case "Rejete" -> "rejected";
            default -> "";
        };

        ObservableList<Art> filtered = artworksList.filtered(art -> {
            String status = art.getStatus() == null ? "" : art.getStatus().trim().toLowerCase();
            if ("published".equals(statusKey)) {
                return status.equals("published") || status.equals("active") || status.equals("approved");
            }
            return statusKey.equals(status);
        });

        artworksTable.setItems(filtered);
    }

    private void publishArt(Art art) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Publier l'oeuvre");
        confirm.setHeaderText("Voulez-vous publier cette oeuvre ?");
        confirm.setContentText("Elle sera visible dans la galerie.");

        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            art.setStatus("published");
            if (serviceArt.updateArt(art)) {
                refreshTable();
                showAlert("Succes", "L'oeuvre a ete publiee.");
            } else {
                showAlert("Erreur", "Impossible de publier l'oeuvre (ID " + art.getId() + ").");
            }
        }
    }

    private void rejectArt(Art art) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Rejeter l'oeuvre");
        confirm.setHeaderText("Voulez-vous rejeter cette oeuvre ?");
        confirm.setContentText("Elle ne sera pas visible dans la galerie.");

        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            art.setStatus("rejected");
            if (serviceArt.updateArt(art)) {
                refreshTable();
                showAlert("Succes", "L'oeuvre a ete rejetee.");
            } else {
                showAlert("Erreur", "Impossible de rejeter l'oeuvre (ID " + art.getId() + ").");
            }
        }
    }

    private void deleteArt(Art art) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Supprimer l'oeuvre");
        confirm.setHeaderText("Voulez-vous supprimer cette oeuvre ?");
        confirm.setContentText("Cette action est irreversible.");

        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            if (serviceArt.deleteArt(art.getId())) {
                refreshTable();
                showAlert("Succes", "L'oeuvre a ete supprimee.");
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
            showAlert("Erreur", "Navigation impossible: " + e.getMessage());
        }
    }

    @FXML
    public void goToGallery() {
        try {
            SceneNavigator.goTo("/views/menu-view.fxml");
        } catch (IOException e) {
            showAlert("Erreur", "Navigation impossible: " + e.getMessage());
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
