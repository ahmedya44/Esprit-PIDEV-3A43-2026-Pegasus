package com.pegasus.controllers.back;

import com.pegasus.controllers.front.ModifierEvenementController;
import com.pegasus.controllers.front.AjouterEvenementController;
import com.pegasus.controllers.EventsRoleRouter;
import com.pegasus.controllers.SceneNavigator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import com.pegasus.entities.Evenement;
import com.pegasus.services.ServiceEvenement;

import java.io.IOException;
import java.util.List;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import java.io.FileOutputStream;
import java.io.File;

public class BackEventController {

    @FXML private TableView<Evenement> eventsTable;
    @FXML private TableColumn<Evenement, String> colDescription;
    @FXML private TableColumn<Evenement, String> colImage;
    @FXML private TableColumn<Evenement, String> colStatut;
    @FXML private TableColumn<Evenement, Void> colActions;
    @FXML private TextField searchField;

    private ServiceEvenement serviceEvenement = new ServiceEvenement();
    private ObservableList<Evenement> evenementsList;

    @FXML
    public void initialize() {
        if (eventsTable != null) {
            eventsTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
            eventsTable.setFixedCellSize(100.0); // More height for long text
            setupDescriptionColumn();
            setupImageColumn();
            setupStatusColumn();
            setupActionsColumn();
        }
        chargerDonnees();

        // Recherche dynamique
        if (searchField != null) {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                rechercher(newValue);
            });
        }
    }

    private void setupDescriptionColumn() {
        colDescription.setCellFactory(column -> new TableCell<>() {
            private final Label label = new Label();
            {
                label.setWrapText(true);
                label.setStyle("-fx-font-size: 11px;");
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    label.setText(item);
                    label.prefWidthProperty().bind(colDescription.widthProperty().subtract(15));
                    setGraphic(label);
                }
            }
        });
    }

    private void setupImageColumn() {
        colImage.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();
            {
                imageView.setFitHeight(50);
                imageView.setFitWidth(80);
                imageView.setPreserveRatio(true);
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.isBlank()) {
                    setGraphic(null);
                } else {
                    try {
                        String imageUrl = item;
                        if (!imageUrl.startsWith("http") && !imageUrl.startsWith("file:")) {
                            imageUrl = "file:" + imageUrl;
                        }
                        Image image = new Image(imageUrl, true); // true for background loading
                        imageView.setImage(image);
                        setGraphic(imageView);
                        setAlignment(Pos.CENTER);
                    } catch (Exception e) {
                        setGraphic(new Label("No image"));
                    }
                }
            }
        });
    }

    private void setupStatusColumn() {
        colStatut.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    Label statusLabel = new Label(item.toUpperCase());
                    statusLabel.getStyleClass().add("status-pill");
                    
                    // Basic dynamic styling based on text
                    String style = "-fx-padding: 2 8; -fx-background-radius: 10; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 10px;";
                    if (item.equalsIgnoreCase("ACCEPTÉE") || item.equalsIgnoreCase("available") || item.equalsIgnoreCase("active")) {
                        style += "-fx-background-color: #27ae60;";
                    } else if (item.equalsIgnoreCase("REFUSÉE") || item.equalsIgnoreCase("annulé") || item.equalsIgnoreCase("cancelled") || item.equalsIgnoreCase("full")) {
                        style += "-fx-background-color: #e74c3c;";
                    } else if (item.equalsIgnoreCase("EN ATTENTE")) {
                        style += "-fx-background-color: #f39c12;";
                    } else {
                        style += "-fx-background-color: #3498db;";
                    }
                    statusLabel.setStyle(style);
                    setGraphic(statusLabel);
                    setText(null);
                }
            }
        });
    }

    private void setupActionsColumn() {
        colActions.setCellFactory(param -> new TableCell<>() {
            private final Button acceptBtn = new Button("Accepter");
            private final Button refuseBtn = new Button("Refuser");
            private final Button deleteBtn = new Button("Supprimer");
            private final HBox container = new HBox(5, acceptBtn, refuseBtn, deleteBtn);

            {
                container.setAlignment(Pos.CENTER);
                acceptBtn.getStyleClass().add("admin-primary-button");
                refuseBtn.getStyleClass().add("admin-secondary-button");
                deleteBtn.getStyleClass().add("admin-danger-button");
                
                String btnStyle = "-fx-font-size: 11px; -fx-padding: 4 8; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 4;";
                acceptBtn.setStyle(btnStyle + " -fx-background-color: #27ae60;");
                refuseBtn.setStyle(btnStyle + " -fx-background-color: #e67e22;");
                deleteBtn.setStyle(btnStyle + " -fx-background-color: #e74c3c;");
                
                // Ensure buttons don't shrink
                acceptBtn.setMinWidth(70);
                refuseBtn.setMinWidth(70);
                deleteBtn.setMinWidth(80);

                acceptBtn.setOnAction(event -> {
                    Evenement e = getTableView().getItems().get(getIndex());
                    serviceEvenement.updateStatut(e.getId(), "ACCEPTÉE");
                    chargerDonnees();
                });

                refuseBtn.setOnAction(event -> {
                    Evenement e = getTableView().getItems().get(getIndex());
                    serviceEvenement.updateStatut(e.getId(), "REFUSÉE");
                    chargerDonnees();
                });

                deleteBtn.setOnAction(event -> {
                    Evenement e = getTableView().getItems().get(getIndex());
                    confirmAndSupprimer(e);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(container);
                }
            }
        });
    }

    private void confirmAndSupprimer(Evenement e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Supprimer l'événement : " + e.getTitre() + " ?");
        alert.setContentText("Cette action est irréversible.");
        
        if (alert.showAndWait().get() == ButtonType.OK) {
            serviceEvenement.supprimer(e);
            if (serviceEvenement.getLastError() == null) {
                chargerDonnees();
                afficherAlerte(Alert.AlertType.INFORMATION, "Suppression réussie", "L'événement a été supprimé.");
            } else {
                String error = serviceEvenement.getLastError();
                if (error != null && error.contains("foreign key constraint fails")) {
                    error = "Impossible de supprimer cet événement car il contient déjà des participants ou des sponsors.";
                }
                afficherAlerte(Alert.AlertType.ERROR, "Erreur de suppression", error);
            }
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
            // Recherche par rapport au titre seulement
            if (e.getTitre() != null && e.getTitre().toLowerCase().contains(lowerCaseQuery)) {
                filteredData.add(e);
            }
        }
        eventsTable.setItems(filteredData);
    }

    @FXML
    void exporterPDF(ActionEvent event) {
        try {
            // Chemin vers le dossier Téléchargements
            String userHome = System.getProperty("user.home");
            File downloadsDir = new File(userHome, "Downloads");
            if (!downloadsDir.exists()) downloadsDir = new File(userHome, "Telechargements");
            
            File file = new File(downloadsDir, "Liste_Evenements_Pegasus_" + System.currentTimeMillis() + ".pdf");
            
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            
            // Design du Header
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, java.awt.Color.decode("#2c3e50"));
            Paragraph title = new Paragraph("PEGASUS - RAPPORT D'ÉVÉNEMENTS", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            
            Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA, 12, java.awt.Color.GRAY);
            Paragraph subTitle = new Paragraph("Généré le : " + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), subTitleFont);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);
            
            document.add(new Paragraph(" ")); // Spacer
            document.add(new Paragraph(" ")); 

            // Table de données
            PdfPTable table = new PdfPTable(7); // Titre, Date, Heure, Lieu, Capacité, Prix, Statut
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            
            // Largeurs des colonnes
            float[] columnWidths = {2f, 1.2f, 1f, 1.5f, 1f, 1f, 1.2f};
            table.setWidths(columnWidths);
            
            // Style de l'en-tête
            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, java.awt.Color.WHITE);
            String[] headers = {"Titre", "Date", "Heure", "Lieu", "Capacité", "Prix", "Statut"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headFont));
                cell.setBackgroundColor(java.awt.Color.decode("#34495e"));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(8);
                table.addCell(cell);
            }
            
            // Style des cellules
            for (Evenement e : eventsTable.getItems()) {
                Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10, java.awt.Color.BLACK);
                
                PdfPCell c1 = new PdfPCell(new Phrase(e.getTitre(), cellFont));
                c1.setPadding(5);
                table.addCell(c1);

                PdfPCell c2 = new PdfPCell(new Phrase(e.getDate(), cellFont));
                c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c2);

                PdfPCell c3 = new PdfPCell(new Phrase(e.getHeure(), cellFont));
                c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c3);

                PdfPCell c4 = new PdfPCell(new Phrase(e.getLieu(), cellFont));
                table.addCell(c4);

                PdfPCell c5 = new PdfPCell(new Phrase(String.valueOf(e.getCapacite_max()), cellFont));
                c5.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c5);

                PdfPCell c6 = new PdfPCell(new Phrase(String.format("%.2f DT", e.getPrix()), cellFont));
                c6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(c6);

                // Pour le statut, on crée une police spécifique pour ne pas impacter les autres cellules
                Font statusFont = FontFactory.getFont(FontFactory.HELVETICA, 10, java.awt.Color.BLACK);
                if ("REFUSÉE".equalsIgnoreCase(e.getStatut())) {
                    statusFont.setColor(java.awt.Color.decode("#e74c3c"));
                }
                
                PdfPCell c7 = new PdfPCell(new Phrase(e.getStatut(), statusFont));
                c7.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c7);
            }
            
            document.add(table);
            document.close();
            
            afficherAlerte(Alert.AlertType.INFORMATION, "Exportation PDF", "Le rapport a été téléchargé dans votre dossier Téléchargements :\n" + file.getName());
            
        } catch (Exception ex) {
            ex.printStackTrace();
            afficherAlerte(Alert.AlertType.ERROR, "Erreur d'exportation", "Impossible d'exporter en PDF : " + ex.getMessage());
        }
    }

    @FXML
    void ajouterEvenement(ActionEvent event) {
        ouvrirModalFormulaire("/views/front/ajouter-evenement.fxml", "Ajouter un Événement", null);
    }

    @FXML
    void modifierEvenement(ActionEvent event) {
        Evenement selectedEvent = eventsTable.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            afficherAlerte(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner un événement à modifier.");
            return;
        }

        ouvrirModalFormulaire("/views/front/modifier-evenement-view.fxml", "Modifier l'Événement", selectedEvent);
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
        Parent root = FXMLLoader.load(getClass().getResource("/views/back/AdminLayout.fxml"));
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
    void goBackoffice(ActionEvent event) throws IOException {
        SceneNavigator.goTo("/views/back/AdminLayout.fxml");
    }

    private void ouvrirModalFormulaire(String fxmlPath, String titrePage, Evenement e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Remplir les données du formulaire si c'est une modification
            if (e != null && fxmlPath.contains("modifier")) {
                ModifierEvenementController controller = loader.getController();
                controller.setOrigin("ADMIN");
                controller.initData(e);
            } else if (fxmlPath.contains("ajouter-evenement")) {
                AjouterEvenementController controller = loader.getController();
                controller.setOrigin("ADMIN");
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
