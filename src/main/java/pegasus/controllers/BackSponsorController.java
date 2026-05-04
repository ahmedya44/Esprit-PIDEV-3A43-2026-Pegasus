package pegasus.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pegasus.services.ServiceSponsoringPack;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class BackSponsorController {

    @FXML private TableView<SponsorRow> sponsorsTable;
    @FXML private TableColumn<SponsorRow, String> colSponsor;
    @FXML private TableColumn<SponsorRow, String> colEmail;
    @FXML private TableColumn<SponsorRow, String> colTel;
    @FXML private TableColumn<SponsorRow, String> colPack;
    @FXML private TableColumn<SponsorRow, String> colPrix;
    @FXML private TableColumn<SponsorRow, String> colEvenement;
    @FXML private TextField searchField;
    @FXML private ComboBox<String> eventFilterCombo;
    private ServiceSponsoringPack servicePack = new ServiceSponsoringPack();
    private ObservableList<SponsorRow> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Redimensionnement auto
        sponsorsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Configuration des colonnes
        colSponsor.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().nom));
        colEmail.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().email));
        colTel.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().tel));
        colPack.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().pack));
        colPrix.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().prix + " DT"));
        colEvenement.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().evenement));

        chargerDonnees();

        // Configurer le combo box de filtrage par événement
        List<String> eventNames = masterData.stream()
                .map(s -> s.evenement)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        eventFilterCombo.getItems().add("Tous les événements");
        eventFilterCombo.getItems().addAll(eventNames);
        eventFilterCombo.setValue("Tous les événements");

        // Recherche dynamique
        searchField.textProperty().addListener((obs, old, newValue) -> {
            filtrer();
        });

        eventFilterCombo.valueProperty().addListener((obs, old, newValue) -> {
            filtrer();
        });
    }

    private void chargerDonnees() {
        List<String[]> list = servicePack.getSponsorsGlobalList();
        masterData.clear();
        for (String[] s : list) {
            masterData.add(new SponsorRow(s[0], s[1], s[2], s[3], s[4], s[5], Integer.parseInt(s[6])));
        }
        sponsorsTable.setItems(masterData);
    }



    @FXML
    void exporterPDFGlobal(ActionEvent event) {
        if (masterData.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Export PDF");
            alert.setHeaderText(null);
            alert.setContentText("Il n'y a aucune donnée à exporter.");
            alert.showAndWait();
            return;
        }

        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.setTitle("Enregistrer le rapport des sponsors");
        fileChooser.setInitialFileName("Rapport_Global_Sponsors.pdf");
        fileChooser.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
        
        java.io.File file = fileChooser.showSaveDialog(sponsorsTable.getScene().getWindow());
        
        if (file != null) {
            try {
                com.itextpdf.kernel.pdf.PdfWriter writer = new com.itextpdf.kernel.pdf.PdfWriter(file.getAbsolutePath());
                com.itextpdf.kernel.pdf.PdfDocument pdf = new com.itextpdf.kernel.pdf.PdfDocument(writer);
                com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdf);

                document.add(new com.itextpdf.layout.element.Paragraph("Rapport Global de Sponsoring").setFontSize(18).setBold());
                document.add(new com.itextpdf.layout.element.Paragraph("Projet Pegasus - Pegasus Back-Office").setFontSize(12));
                document.add(new com.itextpdf.layout.element.Paragraph("Date : " + java.time.LocalDate.now()));
                document.add(new com.itextpdf.layout.element.Paragraph("\n"));


                com.itextpdf.layout.element.Table table = new com.itextpdf.layout.element.Table(com.itextpdf.layout.properties.UnitValue.createPercentArray(new float[]{20, 20, 20, 20, 20})).useAllAvailableWidth();
                table.addHeaderCell("Sponsor");
                table.addHeaderCell("Pack");
                table.addHeaderCell("Prix");
                table.addHeaderCell("Événement");
                table.addHeaderCell("Contact");

                for (SponsorRow s : masterData) {
                    table.addCell(s.nom);
                    table.addCell(s.pack);
                    table.addCell(s.prix + " DT");
                    table.addCell(s.evenement);
                    table.addCell(s.email);
                }

                document.add(table);
                document.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Export PDF");
                alert.setHeaderText(null);
                alert.setContentText("Rapport PDF généré avec succès !");
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
    }

    private void filtrer() {
        String searchText = searchField.getText() == null ? "" : searchField.getText().toLowerCase();
        String selectedEvent = eventFilterCombo.getValue();

        ObservableList<SponsorRow> filtered = masterData.stream()
                .filter(s -> {
                    boolean matchesSearch = s.nom.toLowerCase().contains(searchText);
                    boolean matchesEvent = "Tous les événements".equals(selectedEvent) || s.evenement.equals(selectedEvent);
                    return matchesSearch && matchesEvent;
                })
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        
        sponsorsTable.setItems(filtered);
    }



    @FXML
    void retourDashboard(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/views/back-view.fxml"));
        stage.getScene().setRoot(root);
    }

    // Classe utilitaire pour le tableau
    public static class SponsorRow {
        String nom, email, tel, pack, prix, evenement;
        int idPack;

        SponsorRow(String nom, String email, String tel, String pack, String prix, String evenement, int idPack) {
            this.nom = nom; this.email = email; this.tel = tel;
            this.pack = pack; this.prix = prix; this.evenement = evenement;
            this.idPack = idPack;
        }
    }
}
