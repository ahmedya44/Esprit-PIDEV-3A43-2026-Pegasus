package com.pegasus.controllers;

import com.pegasus.dao.CategorieDAO;
import com.pegasus.dao.ProduitDAO;
import com.pegasus.models.Categorie;
import com.pegasus.models.Produit;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DashboardArtisteController implements Initializable {

    // Navbar
    @FXML private Button btnMesProduits;
    @FXML private Button btnStatuts;
    @FXML private Button btnAjouterCategorie;

    // Pages
    @FXML private StackPane contentPane;
    @FXML private VBox pageProduitsView;
    @FXML private VBox pageStatutsView;
    @FXML private ScrollPane formView;
    @FXML private ScrollPane formCategorieView;

    // Produits grid
    @FXML private FlowPane produitsGrid;
    @FXML private TextField searchField;
    @FXML private ComboBox<Categorie> filterCategorie;

    // Tableau statuts
    @FXML private TableView<Produit> statutsTable;
    @FXML private TableColumn<Produit, String> colStatutNom;
    @FXML private TableColumn<Produit, Float> colStatutPrix;
    @FXML private TableColumn<Produit, Integer> colStatutStock;
    @FXML private TableColumn<Produit, String> colStatutStatut;
    @FXML private TableColumn<Produit, Void> colStatutActions;

    // Formulaire produit
    @FXML private Label formTitle;
    @FXML private TextField nomField;
    @FXML private TextArea descField;
    @FXML private TextField prixField;
    @FXML private TextField stockField;
    @FXML private TextField imageField;
    @FXML private ComboBox<Categorie> categorieCombo;
    @FXML private Button saveBtn;
    @FXML private Label erreurNom;
    @FXML private Label erreurPrix;
    @FXML private Label erreurStock;
    @FXML private Label erreurCategorie;

    // Formulaire catégorie
    @FXML private TextField nomCategorieField;
    @FXML private TextArea descCategorieField;
    @FXML private Label erreurNomCategorie;

    private CategorieDAO categorieDAO = new CategorieDAO();
    private ProduitDAO produitDAO = new ProduitDAO();
    private Produit produitEnCours = null;
    private String imagePath = "";
    private List<Produit> allProduits;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupStatutsTable();
        loadData();
        searchField.textProperty().addListener((obs, old, newVal) -> filterProduits());
        filterCategorie.setOnAction(e -> filterProduits());
    }

    private void setupStatutsTable() {
        colStatutNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colStatutStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        colStatutPrix.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Float prix, boolean empty) {
                super.updateItem(prix, empty);
                if (empty || prix == null) { setText(null); return; }
                setText(prix + " €");
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
                    case "disponible" -> { setText("✔ Accepté"); setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;"); }
                    case "refuse" -> { setText("✖ Refusé"); setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;"); }
                    case "en_attente" -> { setText("⏳ En attente"); setStyle("-fx-text-fill: #f0a500; -fx-font-weight: bold;"); }
                    case "rupture" -> { setText("✖ Rupture"); setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;"); }
                    default -> { setText(statut); setStyle(""); }
                }
            }
        });

        colStatutActions.setCellFactory(col -> new TableCell<>() {
            final Button btnModifier = new Button("✎ Modifier");
            final Button btnSupprimer = new Button("Supprimer");
            final HBox box = new HBox(8, btnModifier, btnSupprimer);
            {
                btnModifier.getStyleClass().add("btn-secondary");
                btnSupprimer.getStyleClass().add("btn-danger");
                btnModifier.setOnAction(e -> showFormModifier(
                        getTableView().getItems().get(getIndex())));
                btnSupprimer.setOnAction(e -> {
                    Produit p = getTableView().getItems().get(getIndex());
                    produitDAO.supprimer(p.getId());
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

    private void loadData() {
        allProduits = produitDAO.getAll();
        List<Categorie> categories = categorieDAO.getAll();
        filterCategorie.setItems(FXCollections.observableArrayList(categories));
        categorieCombo.setItems(FXCollections.observableArrayList(categories));
        afficherProduits(allProduits);
        statutsTable.setItems(FXCollections.observableArrayList(allProduits));
    }

    private void afficherProduits(List<Produit> produits) {
        produitsGrid.getChildren().clear();
        for (Produit p : produits) {
            produitsGrid.getChildren().add(createProductCard(p));
        }
    }

    private VBox createProductCard(Produit produit) {
        VBox card = new VBox(0);
        card.getStyleClass().add("product-card");
        card.setPrefWidth(280);

        Pane imagePlaceholder = new Pane();
        imagePlaceholder.setPrefHeight(180);
        imagePlaceholder.setPrefWidth(280);
        imagePlaceholder.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 10px 10px 0 0;");

        if (produit.getImage() != null && !produit.getImage().isEmpty()) {
            try {
                Image img = new Image(new File(produit.getImage()).toURI().toString());
                ImageView imageView = new ImageView(img);
                imageView.setFitWidth(280);
                imageView.setFitHeight(180);
                imageView.setPreserveRatio(false);
                imagePlaceholder.getChildren().add(imageView);
            } catch (Exception e) {
                addPlaceholderIcon(imagePlaceholder);
            }
        } else {
            addPlaceholderIcon(imagePlaceholder);
        }

        VBox info = new VBox(6);
        info.setPadding(new Insets(10, 15, 15, 15));

        Label nom = new Label(produit.getNom());
        nom.getStyleClass().add("product-card-title");

        Label desc = new Label(produit.getDescription() != null ? produit.getDescription() : "");
        desc.getStyleClass().add("product-card-desc");
        desc.setWrapText(true);

        Label prix = new Label(produit.getPrix() + " €");
        prix.getStyleClass().add("product-card-price");

        if (produit.getCategorie() != null) {
            Label cat = new Label(produit.getCategorie().getNom());
            cat.getStyleClass().add("category-badge");
            info.getChildren().add(cat);
        }

        info.getChildren().addAll(nom, desc, prix);
        card.getChildren().addAll(imagePlaceholder, info);
        return card;
    }

    private void addPlaceholderIcon(Pane pane) {
        Label imgLabel = new Label("🖼");
        imgLabel.setStyle("-fx-font-size: 40px;");
        imgLabel.setLayoutX(110);
        imgLabel.setLayoutY(65);
        pane.getChildren().add(imgLabel);
    }

    private void filterProduits() {
        String search = searchField.getText().toLowerCase();
        Categorie selectedCat = filterCategorie.getSelectionModel().getSelectedItem();
        List<Produit> filtered = allProduits.stream()
                .filter(p -> p.getNom().toLowerCase().contains(search))
                .filter(p -> selectedCat == null ||
                        (p.getCategorie() != null && p.getCategorie().getId() == selectedCat.getId()))
                .collect(Collectors.toList());
        afficherProduits(filtered);
    }

    // ===== NAVIGATION =====

    @FXML
    public void showMesProduits() {
        pageProduitsView.setVisible(true);
        pageStatutsView.setVisible(false);
        formView.setVisible(false);
        formCategorieView.setVisible(false);
        btnMesProduits.getStyleClass().setAll("navbar-btn-active");
        btnStatuts.getStyleClass().setAll("navbar-btn");
        btnAjouterCategorie.getStyleClass().setAll("navbar-btn");
        loadData();
    }

    @FXML
    public void showStatuts() {
        pageProduitsView.setVisible(false);
        pageStatutsView.setVisible(true);
        formView.setVisible(false);
        formCategorieView.setVisible(false);
        btnStatuts.getStyleClass().setAll("navbar-btn-active");
        btnMesProduits.getStyleClass().setAll("navbar-btn");
        btnAjouterCategorie.getStyleClass().setAll("navbar-btn");
        loadData();
    }

    @FXML
    public void showFormAjouter() {
        produitEnCours = null;
        formTitle.setText("Ajouter un produit");
        nomField.clear();
        descField.clear();
        prixField.clear();
        stockField.clear();
        imageField.clear();
        imagePath = "";
        erreurNom.setText("");
        erreurPrix.setText("");
        erreurStock.setText("");
        erreurCategorie.setText("");
        categorieCombo.getSelectionModel().clearSelection();
        pageProduitsView.setVisible(false);
        pageStatutsView.setVisible(false);
        formView.setVisible(true);
        formCategorieView.setVisible(false);
        btnMesProduits.getStyleClass().setAll("navbar-btn");
        btnStatuts.getStyleClass().setAll("navbar-btn");
        btnAjouterCategorie.getStyleClass().setAll("navbar-btn");
    }

    private void showFormModifier(Produit p) {
        produitEnCours = p;
        formTitle.setText("Modifier le produit");
        nomField.setText(p.getNom());
        descField.setText(p.getDescription() != null ? p.getDescription() : "");
        prixField.setText(String.valueOf(p.getPrix()));
        stockField.setText(String.valueOf(p.getStock()));
        imagePath = p.getImage() != null ? p.getImage() : "";
        imageField.setText(imagePath.isEmpty() ? "" : new File(imagePath).getName());
        erreurNom.setText("");
        erreurPrix.setText("");
        erreurStock.setText("");
        erreurCategorie.setText("");
        if (p.getCategorie() != null) {
            categorieCombo.getItems().stream()
                    .filter(c -> c.getId() == p.getCategorie().getId())
                    .findFirst().ifPresent(categorieCombo::setValue);
        }
        pageProduitsView.setVisible(false);
        pageStatutsView.setVisible(false);
        formView.setVisible(true);
        formCategorieView.setVisible(false);
    }

    @FXML
    public void showFormCategorie() {
        pageProduitsView.setVisible(false);
        pageStatutsView.setVisible(false);
        formView.setVisible(false);
        formCategorieView.setVisible(true);
        btnAjouterCategorie.getStyleClass().setAll("navbar-btn-active");
        btnMesProduits.getStyleClass().setAll("navbar-btn");
        btnStatuts.getStyleClass().setAll("navbar-btn");
        nomCategorieField.clear();
        descCategorieField.clear();
        erreurNomCategorie.setText("");
    }

    // ===== ACTIONS =====

    @FXML
    public void handleChoisirImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );
        Stage stage = (Stage) nomField.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            imagePath = file.getAbsolutePath();
            imageField.setText(file.getName());
        }
    }

    @FXML
    public void handleSaveProduit() {
        boolean valid = true;

        // Contrôle nom
        if (nomField.getText().trim().isEmpty()) {
            erreurNom.setText("⚠ Le nom est obligatoire");
            nomField.setStyle("-fx-border-color: red; -fx-border-radius: 5px;");
            valid = false;
        } else if (nomField.getText().trim().length() < 2) {
            erreurNom.setText("⚠ Le nom doit contenir au moins 2 caractères");
            nomField.setStyle("-fx-border-color: red; -fx-border-radius: 5px;");
            valid = false;
        } else if (produitEnCours == null && produitDAO.existsByNom(nomField.getText().trim())) {
            erreurNom.setText("⚠ Un produit avec ce nom existe déjà");
            nomField.setStyle("-fx-border-color: red; -fx-border-radius: 5px;");
            valid = false;
        } else if (produitEnCours != null && produitDAO.existsByNomExceptId(nomField.getText().trim(), produitEnCours.getId())) {
            erreurNom.setText("⚠ Un produit avec ce nom existe déjà");
            nomField.setStyle("-fx-border-color: red; -fx-border-radius: 5px;");
            valid = false;
        } else {
            erreurNom.setText("");
            nomField.setStyle("");
        }

        // Contrôle prix
        try {
            float prix = Float.parseFloat(prixField.getText().trim());
            if (prix <= 0) {
                erreurPrix.setText("⚠ Le prix doit être supérieur à 0");
                prixField.setStyle("-fx-border-color: red; -fx-border-radius: 5px;");
                valid = false;
            } else {
                erreurPrix.setText("");
                prixField.setStyle("");
            }
        } catch (NumberFormatException e) {
            erreurPrix.setText("⚠ Le prix doit être un nombre valide");
            prixField.setStyle("-fx-border-color: red; -fx-border-radius: 5px;");
            valid = false;
        }

        // Contrôle stock
        try {
            int stock = Integer.parseInt(stockField.getText().trim());
            if (stock < 0) {
                erreurStock.setText("⚠ Le stock ne peut pas être négatif");
                stockField.setStyle("-fx-border-color: red; -fx-border-radius: 5px;");
                valid = false;
            } else {
                erreurStock.setText("");
                stockField.setStyle("");
            }
        } catch (NumberFormatException e) {
            erreurStock.setText("⚠ Le stock doit être un nombre entier");
            stockField.setStyle("-fx-border-color: red; -fx-border-radius: 5px;");
            valid = false;
        }

        // Contrôle catégorie
        if (categorieCombo.getSelectionModel().getSelectedItem() == null) {
            erreurCategorie.setText("⚠ Veuillez choisir une catégorie");
            valid = false;
        } else {
            erreurCategorie.setText("");
        }

        if (!valid) return;

        try {
            if (produitEnCours == null) {
                Produit p = new Produit(
                        nomField.getText().trim(),
                        descField.getText().trim(),
                        Float.parseFloat(prixField.getText().trim()),
                        Integer.parseInt(stockField.getText().trim()),
                        imagePath,
                        "en_attente",
                        categorieCombo.getSelectionModel().getSelectedItem()
                );
                produitDAO.ajouter(p);
            } else {
                produitEnCours.setNom(nomField.getText().trim());
                produitEnCours.setDescription(descField.getText().trim());
                produitEnCours.setPrix(Float.parseFloat(prixField.getText().trim()));
                produitEnCours.setStock(Integer.parseInt(stockField.getText().trim()));
                produitEnCours.setImage(imagePath);
                produitEnCours.setCategorie(categorieCombo.getSelectionModel().getSelectedItem());
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

        Categorie categorie = new Categorie(
                nomCategorieField.getText().trim(),
                descCategorieField.getText().trim()
        );
        categorieDAO.ajouter(categorie);
        showMesProduits();
    }

    @FXML
    public void handleLogout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            Stage stage = (Stage) produitsGrid.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}