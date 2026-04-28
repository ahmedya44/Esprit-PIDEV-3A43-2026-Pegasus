package com.pegasus.controllers;

import com.pegasus.dao.*;
import com.pegasus.models.*;
import com.pegasus.services.LikeService;
import com.pegasus.services.TicketPdfService;
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
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DashboardUserController implements Initializable {

    @FXML private VBox      pageCatalogueView;
    @FXML private VBox      pageDetailView;
    @FXML private VBox      pagePanierView;
    @FXML private VBox      pageHistoriqueView;
    @FXML private VBox      pageLikesView;

    @FXML private FlowPane          catalogueGrid;
    @FXML private FlowPane          likesGrid;
    @FXML private TextField         searchField;
    @FXML private ComboBox<Categorie> filterCategorie;

    @FXML private Label     detailNom;
    @FXML private Label     detailDesc;
    @FXML private Label     detailPrix;
    @FXML private Label     detailStock;
    @FXML private Label     detailStatut;
    @FXML private TextField quantiteField;

    @FXML private TableView<LignePanier>            panierTable;
    @FXML private TableColumn<LignePanier, String>  colPanierProduit;
    @FXML private TableColumn<LignePanier, Float>   colPanierPrixUnit;
    @FXML private TableColumn<LignePanier, Integer> colPanierQte;
    @FXML private TableColumn<LignePanier, Float>   colPanierSousTotal;
    @FXML private TableColumn<LignePanier, Void>    colPanierAction;

    @FXML private TableView<Commande>            historiqueTable;
    @FXML private TableColumn<Commande, Integer> colCmdId;
    @FXML private TableColumn<Commande, String>  colCmdDate;
    @FXML private TableColumn<Commande, Float>   colCmdTotal;
    @FXML private TableColumn<Commande, String>  colCmdStatut;
    @FXML private TableColumn<Commande, Void>    colCmdAction;

    private final ProduitDAO   produitDAO   = new ProduitDAO();
    private final CategorieDAO categorieDAO = new CategorieDAO();
    private final CommandeDAO  commandeDAO  = new CommandeDAO();

    private List<Produit>           allProduits  = new ArrayList<>();
    private Produit                 produitSelectionne;
    private final List<LignePanier> lignesPanier = new ArrayList<>();

    // ─────────────────────────────────────────────
    // INITIALIZE
    // ─────────────────────────────────────────────

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupPanierTable();
        setupHistoriqueTable();
        loadData();
        searchField.textProperty().addListener((obs, old, val) -> filterProduits());
        filterCategorie.setOnAction(e -> filterProduits());
    }

    // ─────────────────────────────────────────────
    // SETUP TABLES
    // ─────────────────────────────────────────────

    private void setupPanierTable() {
        colPanierQte     .setCellValueFactory(new PropertyValueFactory<>("quantite"));
        colPanierPrixUnit.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));

        colPanierProduit.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) { setText(null); return; }
                LignePanier lp = getTableView().getItems().get(getIndex());
                setText(lp.getProduit() != null ? lp.getProduit().getNom() : "");
            }
        });

        colPanierSousTotal.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) { setText(null); return; }
                LignePanier lp = getTableView().getItems().get(getIndex());
                setText(String.format("%.2f €", lp.getPrixUnitaire() * lp.getQuantite()));
                setStyle("-fx-text-fill: #f0a500; -fx-font-weight: bold;");
            }
        });

        colPanierAction.setCellFactory(col -> new TableCell<>() {
            final Button btn = new Button("🗑");
            {
                btn.getStyleClass().add("btn-danger");
                btn.setOnAction(e -> {
                    lignesPanier.remove(getIndex());
                    panierTable.setItems(
                            FXCollections.observableArrayList(lignesPanier));
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    private void setupHistoriqueTable() {
        colCmdId.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) { setText(null); return; }
                Commande c = getTableView().getItems().get(getIndex());
                setText("#" + c.getId());
                setStyle("-fx-font-weight: bold;");
            }
        });

        colCmdStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

        colCmdTotal.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) { setText(null); return; }
                Commande c = getTableView().getItems().get(getIndex());
                setText(String.format("%.2f €", c.getTotal()));
                setStyle("-fx-text-fill: #f0a500; -fx-font-weight: bold;");
            }
        });

        colCmdDate.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) { setText(null); return; }
                Commande c = getTableView().getItems().get(getIndex());
                if (c.getDateCommande() != null)
                    setText(c.getDateCommande().format(
                            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            }
        });

        // ── Bouton Ticket PDF ──────────────────────────────────────────
        colCmdAction.setCellFactory(col -> new TableCell<>() {
            final Button btn = new Button("🎫 Ticket PDF");
            {
                btn.setStyle(
                        "-fx-background-color: #1a73e8; -fx-text-fill: white; " +
                                "-fx-font-weight: bold; -fx-cursor: hand;");
                btn.setOnAction(e -> {
                    Commande c = getTableView().getItems().get(getIndex());
                    String path = TicketPdfService.genererTicket(
                            c, lignesPanier, "Client Pegasus");
                    if (path != null) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Ticket généré ✅");
                        alert.setContentText("Ticket sauvegardé ici :\n" + path);
                        alert.showAndWait();
                        try {
                            java.awt.Desktop.getDesktop()
                                    .open(new java.io.File(path));
                        } catch (Exception ex) {
                            System.err.println(
                                    "Impossible d'ouvrir le PDF : " + ex.getMessage());
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setContentText("Impossible de générer le ticket.");
                        alert.showAndWait();
                    }
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    // ─────────────────────────────────────────────
    // CHARGEMENT DONNÉES
    // ─────────────────────────────────────────────

    private void loadData() {
        allProduits = produitDAO.getAll();
        List<Categorie> categories = categorieDAO.getAll();
        filterCategorie.setItems(FXCollections.observableArrayList(categories));
        afficherCatalogue(allProduits);
        loadHistorique();
    }

    private void loadHistorique() {
        historiqueTable.setItems(
                FXCollections.observableArrayList(commandeDAO.getAll()));
    }

    // ─────────────────────────────────────────────
    // CATALOGUE
    // ─────────────────────────────────────────────

    private void afficherCatalogue(List<Produit> produits) {
        catalogueGrid.getChildren().clear();
        for (Produit p : produits)
            catalogueGrid.getChildren().add(createProductCard(p));
    }

    private VBox createProductCard(Produit produit) {
        VBox card = new VBox(0);
        card.getStyleClass().add("product-card");
        card.setPrefWidth(280);

        // ── Image ──────────────────────────────────────────────────────
        Pane imagePlaceholder = new Pane();
        imagePlaceholder.setPrefHeight(180);
        imagePlaceholder.setPrefWidth(280);
        imagePlaceholder.setStyle(
                "-fx-background-color: #f0f0f0; -fx-background-radius: 10px 10px 0 0;");

        if (produit.getImage() != null && !produit.getImage().isEmpty()) {
            try {
                Image img = new Image(
                        new File(produit.getImage()).toURI().toString());
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

        // ── Infos ──────────────────────────────────────────────────────
        VBox info = new VBox(6);
        info.setPadding(new Insets(10, 15, 15, 15));

        Label nom = new Label(produit.getNom());
        nom.getStyleClass().add("product-card-title");

        Label desc = new Label(
                produit.getDescription() != null ? produit.getDescription() : "");
        desc.getStyleClass().add("product-card-desc");
        desc.setWrapText(true);

        if (produit.getCategorie() != null) {
            Label cat = new Label(produit.getCategorie().getNom());
            cat.getStyleClass().add("category-badge");
            info.getChildren().add(cat);
        }

        Label prix = new Label(String.format("%.2f €", produit.getPrix()));
        prix.getStyleClass().add("product-card-price");

        // ── Badge rupture ──────────────────────────────────────────────
        boolean enRupture = "rupture".equals(produit.getStatut());
        if (enRupture) {
            Label rupture = new Label("🚫 Rupture de stock");
            rupture.setStyle(
                    "-fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-font-size: 11px;");
            info.getChildren().addAll(nom, desc, prix, rupture);
        } else {
            info.getChildren().addAll(nom, desc, prix);
        }

        // ── Bouton Like ❤️ uniquement (pas de texte) ───────────────────
        boolean liked = LikeService.isLiked(produit.getId());
        Button btnLike = new Button(liked ? "❤️" : "🤍");
        btnLike.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-font-size: 18px;" +
                        "-fx-cursor: hand;" +
                        "-fx-padding: 2 6 2 6;" +
                        "-fx-border-color: transparent;"
        );
        btnLike.setOnAction(e -> {
            boolean nowLiked = LikeService.toggleLike(produit.getId());
            btnLike.setText(nowLiked ? "❤️" : "🤍");
        });

        // ── Boutons actions ────────────────────────────────────────────
        HBox btns = new HBox(10);
        Button btnVoir   = new Button("👁");
        Button btnPanier = new Button("🛒");
        btnVoir  .getStyleClass().add("btn-primary");
        btnPanier.getStyleClass().add("btn-primary");
        btnVoir  .setOnAction(e -> showDetailProduit(produit));

        // Désactiver panier si rupture
        btnPanier.setDisable(enRupture);
        btnPanier.setOnAction(e -> ajouterAuPanier(produit, 1));

        btns.getChildren().addAll(btnVoir, btnPanier, btnLike);
        info.getChildren().add(btns);
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

    // ─────────────────────────────────────────────
    // PAGE LIKES
    // ─────────────────────────────────────────────

    @FXML
    public void showLikes() {
        hideAllPages();
        pageLikesView.setVisible(true);
        refreshLikes();
    }

    private void refreshLikes() {
        likesGrid.getChildren().clear();
        List<Produit> produits = LikeService.getProduitsLikes();
        if (produits.isEmpty()) {
            Label empty = new Label("💔 Vous n'avez pas encore aimé de produits");
            empty.setStyle(
                    "-fx-font-size: 16px; -fx-text-fill: #999; -fx-padding: 30px;");
            likesGrid.getChildren().add(empty);
            return;
        }
        for (Produit p : produits)
            likesGrid.getChildren().add(createProductCard(p));
    }

    // ─────────────────────────────────────────────
    // DETAIL PRODUIT
    // ─────────────────────────────────────────────

    private void showDetailProduit(Produit p) {
        produitSelectionne = p;
        detailNom  .setText(p.getNom());
        detailDesc .setText(p.getDescription() != null ? p.getDescription() : "");
        detailPrix .setText(String.format("%.2f €", p.getPrix()));
        detailStock.setText("Stock : " + p.getStock());

        String statut = p.getStatut();
        detailStatut.setText("Statut : " + statut);
        switch (statut) {
            case "disponible" -> detailStatut.setStyle(
                    "-fx-text-fill: #27ae60; -fx-font-weight: bold; -fx-font-size: 14px;");
            case "rupture"    -> detailStatut.setStyle(
                    "-fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-font-size: 14px;");
            default           -> detailStatut.setStyle(
                    "-fx-text-fill: #f0a500; -fx-font-weight: bold; -fx-font-size: 14px;");
        }

        hideAllPages();
        pageDetailView.setVisible(true);
    }

    // ─────────────────────────────────────────────
    // PANIER
    // ─────────────────────────────────────────────

    @FXML
    public void handleAjouterAuPanier() {
        if (produitSelectionne == null) return;

        // Bloquer si rupture
        if ("rupture".equals(produitSelectionne.getStatut())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Rupture de stock");
            alert.setContentText("Ce produit est en rupture de stock !");
            alert.showAndWait();
            return;
        }

        int qte = 1;
        try { qte = Integer.parseInt(quantiteField.getText()); }
        catch (Exception ignored) {}

        ajouterAuPanier(produitSelectionne, qte);
        showPanier();
    }

    private void ajouterAuPanier(Produit p, int qte) {
        // Vérifier stock disponible vs déjà dans le panier
        int dejaEnPanier = lignesPanier.stream()
                .filter(lp -> lp.getProduit() != null &&
                        lp.getProduit().getId() == p.getId())
                .mapToInt(LignePanier::getQuantite)
                .sum();

        if (dejaEnPanier + qte > p.getStock()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Stock insuffisant");
            alert.setContentText(
                    "Stock disponible : " + (p.getStock() - dejaEnPanier) + " unité(s)");
            alert.showAndWait();
            return;
        }

        lignesPanier.add(new LignePanier(qte, p.getPrix(), null, p));
        panierTable.setItems(FXCollections.observableArrayList(lignesPanier));
    }

    @FXML
    public void handleViderPanier() {
        lignesPanier.clear();
        panierTable.setItems(FXCollections.observableArrayList(lignesPanier));
    }

    // ─────────────────────────────────────────────
    // COMMANDE + STRIPE + STOCK AUTO
    // ─────────────────────────────────────────────

    @FXML
    public void handlePasserCommande() {
        if (lignesPanier.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Panier vide");
            alert.setContentText("Ajoutez des produits avant de commander.");
            alert.showAndWait();
            return;
        }

        float total = (float) lignesPanier.stream()
                .mapToDouble(l -> l.getPrixUnitaire() * l.getQuantite()).sum();

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/PaymentDialog.fxml"));
            Parent root = loader.load();

            PaymentController paymentCtrl = loader.getController();
            paymentCtrl.init(total, () -> {
                // ✅ Sauvegarder la commande
                Commande commande = new Commande(
                        LocalDateTime.now(), "payee", total);
                commandeDAO.ajouter(commande);

                // ✅ Décrémenter stock + statut auto pour chaque ligne
                for (LignePanier lp : lignesPanier) {
                    Produit p = lp.getProduit();
                    if (p != null) {
                        int newStock = Math.max(0, p.getStock() - lp.getQuantite());
                        p.setStock(newStock);
                        p.setStatut(newStock == 0 ? "rupture" : "disponible");
                        produitDAO.modifier(p);
                    }
                }

                lignesPanier.clear();
                panierTable.setItems(
                        FXCollections.observableArrayList(lignesPanier));
                loadData();
                loadHistorique();
                showHistorique();
            });

            Stage dialog = new Stage();
            dialog.setTitle("Paiement Pegasus");
            dialog.setScene(new Scene(root));
            dialog.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            dialog.setResizable(false);
            dialog.show();

        } catch (Exception e) {
            System.err.println("Erreur ouverture PaymentDialog : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ─────────────────────────────────────────────
    // FILTRES
    // ─────────────────────────────────────────────

    @FXML
    public void filterProduits() {
        String    search      = searchField.getText().toLowerCase();
        Categorie selectedCat = filterCategorie.getSelectionModel().getSelectedItem();
        List<Produit> filtered = allProduits.stream()
                .filter(p -> p.getNom().toLowerCase().contains(search))
                .filter(p -> selectedCat == null ||
                        (p.getCategorie() != null &&
                                p.getCategorie().getId() == selectedCat.getId()))
                .collect(Collectors.toList());
        afficherCatalogue(filtered);
    }

    // ─────────────────────────────────────────────
    // NAVIGATION
    // ─────────────────────────────────────────────

    private void hideAllPages() {
        pageCatalogueView .setVisible(false);
        pageDetailView    .setVisible(false);
        pagePanierView    .setVisible(false);
        pageHistoriqueView.setVisible(false);
        pageLikesView     .setVisible(false);
    }

    @FXML
    public void showCatalogue() {
        hideAllPages();
        pageCatalogueView.setVisible(true);
        loadData();
    }

    @FXML
    public void showPanier() {
        hideAllPages();
        pagePanierView.setVisible(true);
    }

    @FXML
    public void showHistorique() {
        hideAllPages();
        pageHistoriqueView.setVisible(true);
    }

    @FXML
    public void handleLogout() {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/fxml/Login.fxml"));
            Stage stage = (Stage) catalogueGrid.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}