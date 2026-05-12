package com.pegasus.controllers.back;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import com.pegasus.entities.SponsoringPack;
import com.pegasus.services.ServiceSponsoringPack;
import com.pegasus.services.ServiceEvenement;

import java.io.File;
import java.io.FileOutputStream;
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

public class BackSponsoringPackController {

    @FXML private TableView<SponsoringPack> packsTable;
    @FXML private TableColumn<SponsoringPack, String> colDescription;
    @FXML private TableColumn<SponsoringPack, Integer> colEvenement;
    @FXML private TableColumn<SponsoringPack, Void> colActions;
    @FXML private TextField searchField;
    @FXML private ComboBox<com.pegasus.entities.Evenement> eventFilterCombo;

    private ServiceSponsoringPack servicePack = new ServiceSponsoringPack();
    private ServiceEvenement serviceEvenement = new ServiceEvenement();
    private ObservableList<SponsoringPack> packsList;
    private java.util.Map<Integer, String> eventTitlesCache = new java.util.HashMap<>();

    @FXML
    public void initialize() {
        if (packsTable != null) {
            packsTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
            packsTable.setFixedCellSize(80.0);
            setupDescriptionColumn();
            setupEvenementColumn();
            setupActionsColumn();
        }

        if (eventFilterCombo != null) {
            eventFilterCombo.setConverter(new javafx.util.StringConverter<com.pegasus.entities.Evenement>() {
                @Override
                public String toString(com.pegasus.entities.Evenement e) {
                    return e == null ? "" : e.getTitre();
                }
                @Override
                public com.pegasus.entities.Evenement fromString(String s) {
                    return null;
                }
            });
        }

        chargerDonnees();

        if (searchField != null) {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> filtrerDonnees());
        }
        if (eventFilterCombo != null) {
            eventFilterCombo.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> filtrerDonnees());
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

    private void setupEvenementColumn() {
        colEvenement.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer idEvt, boolean empty) {
                super.updateItem(idEvt, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else if (idEvt == null || idEvt <= 0) {
                    setText("Aucun");
                } else {
                    String title = eventTitlesCache.getOrDefault(idEvt, "ID #" + idEvt);
                    setText(title);
                }
            }
        });
    }

    private void setupActionsColumn() {
        colActions.setCellFactory(param -> new TableCell<>() {
            private final Button deleteBtn = new Button("Supprimer");
            private final HBox container = new HBox(5, deleteBtn);
            {
                container.setAlignment(Pos.CENTER);
                deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 4 8; -fx-background-radius: 4;");
                deleteBtn.setMinWidth(80);
                deleteBtn.setOnAction(event -> {
                    SponsoringPack p = getTableView().getItems().get(getIndex());
                    confirmAndSupprimer(p);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else setGraphic(container);
            }
        });
    }

    private void chargerDonnees() {
        // Charger les noms des événements pour le cache
        eventTitlesCache.clear();
        List<com.pegasus.entities.Evenement> allEvents = serviceEvenement.afficherEvenements();
        allEvents.forEach(e -> eventTitlesCache.put(e.getId(), e.getTitre()));

        if (eventFilterCombo != null) {
            ObservableList<com.pegasus.entities.Evenement> comboItems = FXCollections.observableArrayList();
            com.pegasus.entities.Evenement all = new com.pegasus.entities.Evenement();
            all.setId(0);
            all.setTitre("Tous les événements");
            comboItems.add(all);
            comboItems.addAll(allEvents);
            eventFilterCombo.setItems(comboItems);
            eventFilterCombo.getSelectionModel().selectFirst();
        }

        List<SponsoringPack> list = servicePack.afficherTout();
        packsList = FXCollections.observableArrayList(list);
        packsTable.setItems(packsList);
    }

    private void filtrerDonnees() {
        String query = searchField != null ? searchField.getText().toLowerCase() : "";
        com.pegasus.entities.Evenement selectedEvt = eventFilterCombo != null ? eventFilterCombo.getSelectionModel().getSelectedItem() : null;

        ObservableList<SponsoringPack> filteredData = FXCollections.observableArrayList();
        for (SponsoringPack p : packsList) {
            boolean matchesSearch = p.getNom_pack().toLowerCase().contains(query);
            boolean matchesEvent = (selectedEvt == null || selectedEvt.getId() == 0 || p.getId_evenement() == selectedEvt.getId());

            if (matchesSearch && matchesEvent) {
                filteredData.add(p);
            }
        }
        packsTable.setItems(filteredData);
    }

    private void confirmAndSupprimer(SponsoringPack p) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Supprimer le pack : " + p.getNom_pack() + " ?");
        alert.setContentText("Cette action est irréversible.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            servicePack.supprimer(p);
            if (servicePack.getLastError() == null) {
                chargerDonnees();
                afficherAlerte(Alert.AlertType.INFORMATION, "Suppression réussie", "Le pack a été supprimé.");
            } else {
                String error = servicePack.getLastError();
                if (error != null && error.contains("foreign key constraint fails")) {
                    error = "Impossible de supprimer ce pack car il a déjà été réservé par un sponsor.";
                }
                afficherAlerte(Alert.AlertType.ERROR, "Erreur de suppression", error);
            }
        }
    }

    @FXML
    void exporterPDF(ActionEvent event) {
        try {
            String userHome = System.getProperty("user.home");
            File downloadsDir = new File(userHome, "Downloads");
            if (!downloadsDir.exists()) downloadsDir = new File(userHome, "Telechargements");
            File file = new File(downloadsDir, "Liste_Packs_Sponsoring_" + System.currentTimeMillis() + ".pdf");

            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, java.awt.Color.decode("#2c3e50"));
            Paragraph title = new Paragraph("PEGASUS - PACKS SPONSORING", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            float[] widths = {2f, 3f, 1f, 1.5f};
            table.setWidths(widths);

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, java.awt.Color.WHITE);
            String[] headers = {"Nom Pack", "Description", "Prix", "Événement"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, headFont));
                cell.setBackgroundColor(java.awt.Color.decode("#34495e"));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(8);
                table.addCell(cell);
            }

            Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10, java.awt.Color.BLACK);
            for (SponsoringPack p : packsTable.getItems()) {
                table.addCell(new Phrase(p.getNom_pack(), cellFont));
                table.addCell(new Phrase(p.getDescription(), cellFont));
                table.addCell(new Phrase(String.format("%.2f DT", p.getPrix()), cellFont));
                table.addCell(new Phrase("ID #" + p.getId_evenement(), cellFont));
            }

            document.add(table);
            document.close();
            afficherAlerte(Alert.AlertType.INFORMATION, "Export PDF", "Le rapport des packs a été généré dans Téléchargements.");
        } catch (Exception ex) {
            ex.printStackTrace();
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
