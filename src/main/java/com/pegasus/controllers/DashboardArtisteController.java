package com.pegasus.controllers;

import com.pegasus.dao.CategorieDAO;
import com.pegasus.dao.ProduitDAO;
import com.pegasus.models.Categorie;
import com.pegasus.models.Produit;
import com.pegasus.services.NotificationStockService;
import com.pegasus.services.StatsService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DashboardArtisteController implements Initializable {

    // Navbar
    @FXML private Button btnMesProduits;
    @FXML private Button btnStatuts;
    @FXML private Button btnAjouterCategorie;
    @FXML private Button btnStats;

    // Pages
    @FXML private StackPane  contentPane;
    @FXML private VBox       pageProduitsView;
    @FXML private VBox       pageStatutsView;
    @FXML private VBox       pageStatsView;
    @FXML private ScrollPane formView;
    @FXML private ScrollPane formCategorieView;

    // Badge notification stock
    @FXML private Label stockBadge;

    // Produits grid
    @FXML private FlowPane           produitsGrid;
    @FXML private TextField          searchField;
    @FXML private ComboBox<Categorie> filterCategorie;

    // Tableau statuts
    @FXML private TableView<Produit>           statutsTable;
    @FXML private TableColumn<Produit, String>  colStatutNom;
    @FXML private TableColumn<Produit, Float>   colStatutPrix;
    @FXML private TableColumn<Produit, Integer> colStatutStock;
    @FXML private TableColumn<Produit, String>  colStatutStatut;
    @FXML private TableColumn<Produit, Void>    colStatutStockEdit;
    @FXML private TableColumn<Produit, Void>    colStatutActions;

    // Formulaire produit
    @FXML private Label              formTitle;
    @FXML private TextField          nomField;
    @FXML private TextArea           descField;
    @FXML private TextField          prixField;
    @FXML private TextField          stockField;
    @FXML private TextField          imageField;
    @FXML private ComboBox<Categorie> categorieCombo;
    @FXML private Button             saveBtn;
    @FXML private Label              erreurNom;
    @FXML private Label              erreurPrix;
    @FXML private Label              erreurStock;
    @FXML private Label              erreurCategorie;

    // Formulaire catégorie
    @FXML private TextField nomCategorieField;
    @FXML private TextArea  descCategorieField;
    @FXML private Label     erreurNomCategorie;

    // Stats
    @FXML private BarChart<String, Number> statsChart;
    @FXML private CategoryAxis             xAxis;
    @FXML private NumberAxis               yAxis;
    @FXML private Label                    totalVentesLabel;
    @FXML private Label                    totalCommandesLabel;

    private final CategorieDAO categorieDAO    = new CategorieDAO();
    private final ProduitDAO   produitDAO      = new ProduitDAO();
    private       Produit      produitEnCours  = null;
    private       String       imagePath       = "";
    private       List<Produit> allProduits;

    // ─────────────────────────────────────────────
    // INITIALIZE
    // ─────────────────────────────────────────────

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupStatutsTable();
        loadData();
        searchField.textProperty().addListener((obs, old, nv) -> filterProduits());
        filterCategorie.setOnAction(e -> filterProduits());

        // Badge + window injectés après que la scène soit prête
        Platform.runLater(() -> {
            NotificationStockService.setBadge(
                    stockBadge,
                    produitsGrid.getScene().getWindow()
            );
            checkAllStocksOnStartup();
        });
    }

    // ─────────────────────────────────────────────
    // NOTIFICATIONS STOCK
    // ─────────────────────────────────────────────

    private void checkAllStocksOnStartup() {
        produitDAO.getAll().forEach(NotificationStockService::verifierStock);
    }

    private void checkStockNotification(Produit p) {
        if (p.getStock() == 0) {
            p.setStatut("rupture");
        } else if ("rupture".equals(p.getStatut())) {
            p.setStatut("disponible");
        }
        produitDAO.modifier(p);
        NotificationStockService.verifierStock(p);
    }

    // ─────────────────────────────────────────────
    // SETUP TABLE STATUTS
    // ─────────────────────────────────────────────

    private void setupStatutsTable() {
        colStatutNom  .setCellValueFactory(new PropertyValueFactory<>("nom"));
        colStatutStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        colStatutPrix.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Float prix, boolean empty) {
                super.updateItem(prix, empty);
                if (empty || prix == null) { setText(null); return; }
                setText(String.format("%.2f €", prix));
                setStyle("-fx-text-fill: #f0a500; -fx-font-weight: bold;");
            }
        });

        colStatutStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));
        colStatutStatut.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String statut, boolean empty) {
                super.updateItem(statut, empty);
                if (empty || statut == null) { setText(null); setStyle(""); return; }
                switch (statut) {
                    case "disponible" -> { setText("✔ Disponible");  setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;"); }
                    case "refuse"     -> { setText("✖ Refusé");      setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;"); }
                    case "en_attente" -> { setText("⏳ En attente"); setStyle("-fx-text-fill: #f0a500; -fx-font-weight: bold;"); }
                    case "rupture"    -> { setText("🚫 Rupture");    setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;"); }
                    default           -> { setText(statut);           setStyle(""); }
                }
            }
        });

        // ── Colonne Stock +/- + manuel ────────────────────────────────
        colStatutStockEdit.setCellFactory(col -> new TableCell<>() {
            final Button    btnMoins = new Button("−");
            final Button    btnPlus  = new Button("+");
            final TextField tfQte    = new TextField();
            final Button    btnSet   = new Button("✔");
            final HBox      box      = new HBox(5, btnMoins, tfQte, btnSet, btnPlus);
            {
                tfQte.setPrefWidth(50);
                tfQte.setPromptText("Qté");
                btnMoins.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
                btnPlus .setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
                btnSet  .setStyle("-fx-background-color: #1a73e8; -fx-text-fill: white; -fx-font-weight: bold;");

                btnPlus.setOnAction(e -> {
                    Produit p = getTableView().getItems().get(getIndex());
                    p.setStock(p.getStock() + 1);
                    checkStockNotification(p);
                    getTableView().refresh();
                });

                btnMoins.setOnAction(e -> {
                    Produit p = getTableView().getItems().get(getIndex());
                    if (p.getStock() > 0) {
                        p.setStock(p.getStock() - 1);
                        checkStockNotification(p);
                        getTableView().refresh();
                    }
                });

                btnSet.setOnAction(e -> {
                    Produit p = getTableView().getItems().get(getIndex());
                    try {
                        int val = Integer.parseInt(tfQte.getText().trim());
                        p.setStock(p.getStock() + val);
                        checkStockNotification(p);
                        getTableView().refresh();
                        tfQte.clear();
                        tfQte.setStyle("");
                    } catch (NumberFormatException ex) {
                        tfQte.setStyle("-fx-border-color: red;");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });

        // ── Actions modifier/supprimer ────────────────────────────────
        colStatutActions.setCellFactory(col -> new TableCell<>() {
            final Button btnModifier  = new Button("✎ Modifier");
            final Button btnSupprimer = new Button("Supprimer");
            final HBox   box          = new HBox(8, btnModifier, btnSupprimer);
            {
                btnModifier .getStyleClass().add("btn-secondary");
                btnSupprimer.getStyleClass().add("btn-danger");
                btnModifier.setOnAction(e ->
                        showFormModifier(getTableView().getItems().get(getIndex())));
                btnSupprimer.setOnAction(e -> {
                    produitDAO.supprimer(
                            getTableView().getItems().get(getIndex()).getId());
                    loadData();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });

        statutsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    // ─────────────────────────────────────────────
    // STATS — Likes + Achats + KPIs
    // ─────────────────────────────────────────────

    private void loadStats() {
        List<Produit> produits = produitDAO.getAll();

        // ── Série Likes (bleu) ─────────────────────────────────────────
        XYChart.Series<String, Number> seriesLikes = new XYChart.Series<>();
        seriesLikes.setName("❤️ Likes");
        Map<Produit, Integer> likesMap = StatsService.getLikesParProduit(produits);
        for (Map.Entry<Produit, Integer> e : likesMap.entrySet()) {
            seriesLikes.getData().add(
                    new XYChart.Data<>(e.getKey().getNom(), e.getValue()));
        }

        // ── Série Achats (orange) ──────────────────────────────────────
        XYChart.Series<String, Number> seriesAchats = new XYChart.Series<>();
        seriesAchats.setName("🛒 Achats");
        Map<String, Integer> achatsMap = StatsService.getAchatsParProduit();
        for (Map.Entry<String, Integer> e : achatsMap.entrySet()) {
            seriesAchats.getData().add(
                    new XYChart.Data<>(e.getKey(), e.getValue()));
        }

        statsChart.getData().clear();
        statsChart.getData().addAll(seriesLikes, seriesAchats);

        // ── Couleurs barres après rendu ────────────────────────────────
        Platform.runLater(() -> {
            for (XYChart.Data<String, Number> d : seriesLikes.getData())
                if (d.getNode() != null)
                    d.getNode().setStyle("-fx-bar-fill: #1a73e8;");
            for (XYChart.Data<String, Number> d : seriesAchats.getData())
                if (d.getNode() != null)
                    d.getNode().setStyle("-fx-bar-fill: #f0a500;");
        });

        // ── KPIs ──────────────────────────────────────────────────────
        if (totalVentesLabel != null)
            totalVentesLabel.setText(String.format(
                    "%.2f €", StatsService.getTotalVentes()));
        if (totalCommandesLabel != null)
            totalCommandesLabel.setText(
                    String.valueOf(StatsService.getTotalCommandes()));
    }

    // ─────────────────────────────────────────────
    // DONNÉES
    // ─────────────────────────────────────────────

    private void loadData() {
        allProduits = produitDAO.getAll();
        List<Categorie> categories = categorieDAO.getAll();
        filterCategorie.setItems(FXCollections.observableArrayList(categories));
        categorieCombo .setItems(FXCollections.observableArrayList(categories));
        afficherProduits(allProduits);
        statutsTable.setItems(FXCollections.observableArrayList(allProduits));
    }

    private void afficherProduits(List<Produit> produits) {
        produitsGrid.getChildren().clear();
        for (Produit p : produits)
            produitsGrid.getChildren().add(createProductCard(p));
    }

    private VBox createProductCard(Produit produit) {
        VBox card = new VBox(0);
        card.getStyleClass().add("product-card");
        card.setPrefWidth(280);

        Pane imagePlaceholder = new Pane();
        imagePlaceholder.setPrefHeight(180);
        imagePlaceholder.setPrefWidth(280);
        imagePlaceholder.setStyle(
                "-fx-background-color: #f0f0f0; -fx-background-radius: 10px 10px 0 0;");

        if (produit.getImage() != null && !produit.getImage().isEmpty()) {
            try {
                Image img = new Image(new File(produit.getImage()).toURI().toString());
                ImageView iv = new ImageView(img);
                iv.setFitWidth(280); iv.setFitHeight(180);
                iv.setPreserveRatio(false);
                imagePlaceholder.getChildren().add(iv);
            } catch (Exception e) { addPlaceholderIcon(imagePlaceholder); }
        } else { addPlaceholderIcon(imagePlaceholder); }

        VBox info = new VBox(6);
        info.setPadding(new Insets(10, 15, 15, 15));

        Label nom = new Label(produit.getNom());
        nom.getStyleClass().add("product-card-title");

        Label desc = new Label(
                produit.getDescription() != null ? produit.getDescription() : "");
        desc.getStyleClass().add("product-card-desc");
        desc.setWrapText(true);

        Label prix = new Label(String.format("%.2f €", produit.getPrix()));
        prix.getStyleClass().add("product-card-price");

        Label stockLabel = new Label("Stock : " + produit.getStock());
        stockLabel.setStyle(produit.getStock() == 0
                ? "-fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-font-size: 12px;"
                : "-fx-text-fill: #27ae60; -fx-font-size: 12px;");

        if (produit.getCategorie() != null) {
            Label cat = new Label(produit.getCategorie().getNom());
            cat.getStyleClass().add("category-badge");
            info.getChildren().add(cat);
        }

        info.getChildren().addAll(nom, desc, prix, stockLabel);
        card.getChildren().addAll(imagePlaceholder, info);
        return card;
    }

    private void addPlaceholderIcon(Pane pane) {
        Label l = new Label("🖼");
        l.setStyle("-fx-font-size: 40px;");
        l.setLayoutX(110); l.setLayoutY(65);
        pane.getChildren().add(l);
    }

    private void filterProduits() {
        String    search = searchField.getText().toLowerCase();
        Categorie cat    = filterCategorie.getSelectionModel().getSelectedItem();
        List<Produit> filtered = allProduits.stream()
                .filter(p -> p.getNom().toLowerCase().contains(search))
                .filter(p -> cat == null ||
                        (p.getCategorie() != null &&
                                p.getCategorie().getId() == cat.getId()))
                .collect(Collectors.toList());
        afficherProduits(filtered);
    }

    // ─────────────────────────────────────────────
    // NAVIGATION
    // ─────────────────────────────────────────────

    private void hideAll() {
        pageProduitsView .setVisible(false);
        pageStatutsView  .setVisible(false);
        pageStatsView    .setVisible(false);
        formView         .setVisible(false);
        formCategorieView.setVisible(false);
        btnMesProduits     .getStyleClass().setAll("navbar-btn");
        btnStatuts         .getStyleClass().setAll("navbar-btn");
        btnAjouterCategorie.getStyleClass().setAll("navbar-btn");
        if (btnStats != null) btnStats.getStyleClass().setAll("navbar-btn");
    }

    @FXML
    public void showMesProduits() {
        hideAll();
        pageProduitsView.setVisible(true);
        btnMesProduits.getStyleClass().setAll("navbar-btn-active");
        loadData();
    }

    @FXML
    public void showStatuts() {
        hideAll();
        pageStatutsView.setVisible(true);
        btnStatuts.getStyleClass().setAll("navbar-btn-active");
        loadData();
    }

    @FXML
    public void showStats() {
        hideAll();
        pageStatsView.setVisible(true);
        if (btnStats != null) btnStats.getStyleClass().setAll("navbar-btn-active");
        loadStats();
    }

    @FXML
    public void showFormAjouter() {
        hideAll();
        produitEnCours = null;
        formTitle.setText("Ajouter un produit");
        nomField.clear();   descField.clear();
        prixField.clear();  stockField.clear();
        imageField.clear(); imagePath = "";
        erreurNom.setText("");   erreurPrix.setText("");
        erreurStock.setText(""); erreurCategorie.setText("");
        categorieCombo.getSelectionModel().clearSelection();
        formView.setVisible(true);
    }

    private void showFormModifier(Produit p) {
        hideAll();
        produitEnCours = p;
        formTitle.setText("Modifier le produit");
        nomField .setText(p.getNom());
        descField.setText(p.getDescription() != null ? p.getDescription() : "");
        prixField .setText(String.valueOf(p.getPrix()));
        stockField.setText(String.valueOf(p.getStock()));
        imagePath = p.getImage() != null ? p.getImage() : "";
        imageField.setText(imagePath.isEmpty() ? "" : new File(imagePath).getName());
        erreurNom.setText("");   erreurPrix.setText("");
        erreurStock.setText(""); erreurCategorie.setText("");
        if (p.getCategorie() != null)
            categorieCombo.getItems().stream()
                    .filter(c -> c.getId() == p.getCategorie().getId())
                    .findFirst().ifPresent(categorieCombo::setValue);
        formView.setVisible(true);
    }

    @FXML
    public void showFormCategorie() {
        hideAll();
        formCategorieView.setVisible(true);
        btnAjouterCategorie.getStyleClass().setAll("navbar-btn-active");
        nomCategorieField .clear();
        descCategorieField.clear();
        erreurNomCategorie.setText("");
    }

    // ─────────────────────────────────────────────
    // ACTIONS
    // ─────────────────────────────────────────────

    @FXML
    public void handleChoisirImage() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choisir une image");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
        File file = fc.showOpenDialog((Stage) nomField.getScene().getWindow());
        if (file != null) {
            imagePath = file.getAbsolutePath();
            imageField.setText(file.getName());
        }
    }

    @FXML
    public void handleSaveProduit() {
        boolean valid = true;

        // ── Validation nom ────────────────────────────────────────────
        if (nomField.getText().trim().isEmpty()) {
            erreurNom.setText("⚠ Le nom est obligatoire");
            nomField.setStyle("-fx-border-color: red; -fx-border-radius: 5px;");
            valid = false;
        } else if (nomField.getText().trim().length() < 2) {
            erreurNom.setText("⚠ Le nom doit contenir au moins 2 caractères");
            nomField.setStyle("-fx-border-color: red; -fx-border-radius: 5px;");
            valid = false;
        } else if (produitEnCours == null &&
                produitDAO.existsByNom(nomField.getText().trim())) {
            erreurNom.setText("⚠ Un produit avec ce nom existe déjà");
            nomField.setStyle("-fx-border-color: red; -fx-border-radius: 5px;");
            valid = false;
        } else if (produitEnCours != null &&
                produitDAO.existsByNomExceptId(
                        nomField.getText().trim(), produitEnCours.getId())) {
            erreurNom.setText("⚠ Un produit avec ce nom existe déjà");
            nomField.setStyle("-fx-border-color: red; -fx-border-radius: 5px;");
            valid = false;
        } else {
            erreurNom.setText(""); nomField.setStyle("");
        }

        // ── Validation prix ───────────────────────────────────────────
        try {
            float prix = Float.parseFloat(prixField.getText().trim());
            if (prix <= 0) {
                erreurPrix.setText("⚠ Le prix doit être supérieur à 0");
                prixField.setStyle("-fx-border-color: red; -fx-border-radius: 5px;");
                valid = false;
            } else { erreurPrix.setText(""); prixField.setStyle(""); }
        } catch (NumberFormatException e) {
            erreurPrix.setText("⚠ Le prix doit être un nombre valide");
            prixField.setStyle("-fx-border-color: red; -fx-border-radius: 5px;");
            valid = false;
        }

        // ── Validation stock ──────────────────────────────────────────
        try {
            int stock = Integer.parseInt(stockField.getText().trim());
            if (stock < 0) {
                erreurStock.setText("⚠ Le stock ne peut pas être négatif");
                stockField.setStyle("-fx-border-color: red; -fx-border-radius: 5px;");
                valid = false;
            } else { erreurStock.setText(""); stockField.setStyle(""); }
        } catch (NumberFormatException e) {
            erreurStock.setText("⚠ Le stock doit être un nombre entier");
            stockField.setStyle("-fx-border-color: red; -fx-border-radius: 5px;");
            valid = false;
        }

        // ── Validation catégorie ──────────────────────────────────────
        if (categorieCombo.getSelectionModel().getSelectedItem() == null) {
            erreurCategorie.setText("⚠ Veuillez choisir une catégorie");
            valid = false;
        } else { erreurCategorie.setText(""); }

        if (!valid) return;

        // ── Sauvegarde ────────────────────────────────────────────────
        try {
            int    stockVal    = Integer.parseInt(stockField.getText().trim());
            String statutAuto  = stockVal == 0 ? "rupture" : "en_attente";

            if (produitEnCours == null) {
                produitDAO.ajouter(new Produit(
                        nomField.getText().trim(),
                        descField.getText().trim(),
                        Float.parseFloat(prixField.getText().trim()),
                        stockVal,
                        imagePath,
                        statutAuto,
                        categorieCombo.getSelectionModel().getSelectedItem()
                ));
            } else {
                produitEnCours.setNom(nomField.getText().trim());
                produitEnCours.setDescription(descField.getText().trim());
                produitEnCours.setPrix(Float.parseFloat(prixField.getText().trim()));
                produitEnCours.setStock(stockVal);
                produitEnCours.setImage(imagePath);
                produitEnCours.setCategorie(
                        categorieCombo.getSelectionModel().getSelectedItem());
                if (stockVal == 0) {
                    produitEnCours.setStatut("rupture");
                } else if ("rupture".equals(produitEnCours.getStatut())) {
                    produitEnCours.setStatut("disponible");
                }
                produitDAO.modifier(produitEnCours);
            }
            showMesProduits();

        } catch (NumberFormatException e) {
            System.err.println("Valeur invalide : " + e.getMessage());
        }
    }

    @FXML
    public void handleSaveCategorie() {
        boolean valid = true;

        if (nomCategorieField.getText().trim().isEmpty()) {
            erreurNomCategorie.setText("⚠ Le nom est obligatoire");
            nomCategorieField.setStyle("-fx-border-color: red; -fx-border-radius: 5px;");
            valid = false;
        } else if (nomCategorieField.getText().trim().length() < 3) {
            erreurNomCategorie.setText("⚠ Le nom doit contenir au moins 3 caractères");
            nomCategorieField.setStyle("-fx-border-color: red; -fx-border-radius: 5px;");
            valid = false;
        } else {
            erreurNomCategorie.setText("");
            nomCategorieField.setStyle("");
        }

        if (!valid) return;

        categorieDAO.ajouter(new Categorie(
                nomCategorieField.getText().trim(),
                descCategorieField.getText().trim()
        ));
        showMesProduits();
    }

    @FXML
    public void handleLogout() {
        try {
            SceneNavigator.clearSession();
            SceneNavigator.goTo("/views/signin-view.fxml");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    public void handleBackHome() {
        try {
            SceneNavigator.goTo("/views/home-view.fxml");
        } catch (Exception e) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/views/home-view.fxml"));
                Stage stage = (Stage) produitsGrid.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}
