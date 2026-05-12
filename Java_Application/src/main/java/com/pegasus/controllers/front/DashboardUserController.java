package com.pegasus.controllers.front;


import com.pegasus.controllers.SceneNavigator;
import com.pegasus.controllers.EventsRoleRouter;
import com.pegasus.dao.*;
import com.pegasus.entities.*;
import com.pegasus.services.LikeService;
import com.pegasus.services.TicketPdfService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
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
    @FXML private VBox      pageHistoriqueView;
    @FXML private VBox      pageLikesView;

    @FXML private VBox      productWorkspace;
    @FXML private StackPane cartOverlay;
    @FXML private VBox      cartItemsBox;
    @FXML private Label     cartCountLabel;
    @FXML private Label     cartTotalLabel;
    @FXML private VBox      orderCardsBox;
    @FXML private Label     orderCountLabel;
    @FXML private Label     orderTotalLabel;
    @FXML private Label     productPageTitle;
    @FXML private Button    btnAccueil;
    @FXML private Button    btnPanier;
    @FXML private Button    btnHistorique;
    @FXML private Button    btnLikes;
    @FXML private Button    navProductsButton;
    @FXML private Button    navBackofficeButton;
    @FXML private MenuButton navAccountMenu;

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

    private final ProduitDAO   produitDAO   = new ProduitDAO();
    private final CategorieDAO categorieDAO = new CategorieDAO();
    private final CommandeDAO  commandeDAO  = new CommandeDAO();
    private final LigneCommandeDAO ligneCommandeDAO = new LigneCommandeDAO();

    private List<Produit>           allProduits  = new ArrayList<>();
    private Produit                 produitSelectionne;
    private final List<LignePanier> lignesPanier = new ArrayList<>();

    // ─────────────────────────────────────────────
    // INITIALIZE
    // ─────────────────────────────────────────────

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        setupNavbar();
        setActiveAppNav();
        setActiveProductNav(btnAccueil);
        if (cartOverlay != null) {
            cartOverlay.setOnMouseClicked(event -> {
                if (event.getTarget() == cartOverlay) {
                    hidePanierModal();
                }
            });
        }
        searchField.textProperty().addListener((obs, old, val) -> filterProduits());
        filterCategorie.setOnAction(e -> filterProduits());
    }

    private void setupNavbar() {
        User currentUser = SceneNavigator.getCurrentUser();
        if (navAccountMenu != null) {
            navAccountMenu.setText("\uD83D\uDC64");
            navAccountMenu.setTooltip(new Tooltip(currentUser == null || currentUser.getUsername() == null || currentUser.getUsername().isBlank()
                    ? "Account"
                    : currentUser.getUsername().trim()));
        }
        if (navBackofficeButton != null) {
            boolean isAdmin = currentUser != null && "admin".equalsIgnoreCase(currentUser.getDtype());
            navBackofficeButton.setVisible(isAdmin);
            navBackofficeButton.setManaged(isAdmin);
        }
    }

    private void setActiveAppNav() {
        if (navProductsButton != null && !navProductsButton.getStyleClass().contains("pegasus-nav-button-active")) {
            navProductsButton.getStyleClass().add("pegasus-nav-button-active");
        }
    }

    // ─────────────────────────────────────────────
    // CHARGEMENT DONNÉES
    // ─────────────────────────────────────────────

    private void loadData() {
        allProduits = produitDAO.getAll();
        List<Categorie> categories = categorieDAO.getAll();
        filterCategorie.getItems().setAll(categories);
        afficherCatalogue(allProduits);
        loadHistorique();
    }

    private void loadHistorique() {
        List<Commande> commandes = commandeDAO.getAll();
        renderOrderCards(commandes);
    }

    private void renderOrderCards(List<Commande> commandes) {
        if (orderCardsBox == null) {
            return;
        }
        orderCardsBox.getChildren().clear();
        if (commandes == null || commandes.isEmpty()) {
            VBox emptyState = new VBox(8);
            emptyState.setAlignment(Pos.CENTER);
            emptyState.getStyleClass().add("orders-empty-state");
            Label title = new Label("No orders yet");
            title.getStyleClass().add("orders-empty-title");
            Label copy = new Label("When you complete a checkout, your purchases will appear here.");
            copy.getStyleClass().add("orders-empty-copy");
            emptyState.getChildren().addAll(title, copy);
            orderCardsBox.getChildren().add(emptyState);
            updateOrderSummary(List.of());
            return;
        }
        for (Commande commande : commandes) {
            orderCardsBox.getChildren().add(createOrderCard(commande));
        }
        updateOrderSummary(commandes);
    }

    private HBox createOrderCard(Commande commande) {
        VBox identity = createOrderMetric("Order", "#" + commande.getId(), "orders-card-main");
        VBox date = createOrderMetric("Date", formatOrderDate(commande), null);
        VBox status = createOrderMetric("Status", formatStatus(commande.getStatut()), statusClass(commande.getStatut()));
        VBox total = createOrderMetric("Total", String.format("%.2f EUR", commande.getTotal()), "orders-card-total");

        Button ticketButton = new Button("Ticket PDF");
        ticketButton.getStyleClass().add("orders-ticket-button");
        ticketButton.setOnAction(event -> exportOrderTicket(commande));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox card = new HBox(18, identity, date, status, total, spacer, ticketButton);
        card.setAlignment(Pos.CENTER_LEFT);
        card.getStyleClass().add("orders-card");
        return card;
    }

    private VBox createOrderMetric(String labelText, String valueText, String valueClass) {
        Label label = new Label(labelText);
        label.getStyleClass().add("orders-card-label");
        Label value = new Label(valueText);
        value.getStyleClass().add("orders-card-value");
        if (valueClass != null && !valueClass.isBlank()) {
            value.getStyleClass().add(valueClass);
        }
        VBox box = new VBox(5, label, value);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setMinWidth(120);
        return box;
    }

    private void updateOrderSummary(List<Commande> commandes) {
        int count = commandes == null ? 0 : commandes.size();
        double total = commandes == null ? 0 : commandes.stream().mapToDouble(Commande::getTotal).sum();
        if (orderCountLabel != null) {
            orderCountLabel.setText(count + (count == 1 ? " order" : " orders"));
        }
        if (orderTotalLabel != null) {
            orderTotalLabel.setText(String.format("%.2f EUR", total));
        }
    }

    private void exportOrderTicket(Commande commande) {
        String path = TicketPdfService.genererTicket(
                commande, ligneCommandeDAO.getByCommandeId(commande.getId()), "Pegasus Client");
        if (path != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ticket generated");
            alert.setContentText("Ticket saved here:\n" + path);
            alert.showAndWait();
            try {
                java.awt.Desktop.getDesktop().open(new java.io.File(path));
            } catch (Exception ex) {
                System.err.println("Could not open PDF: " + ex.getMessage());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ticket error");
            alert.setContentText("Could not generate the ticket.");
            alert.showAndWait();
        }
    }

    private String formatOrderDate(Commande commande) {
        return commande.getDateCommande() == null
                ? "-"
                : commande.getDateCommande().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    private String formatStatus(String status) {
        if (status == null || status.isBlank()) {
            return "Unknown";
        }
        return switch (status.trim().toLowerCase()) {
            case "payee", "paid" -> "Paid";
            case "en_attente", "pending" -> "Pending";
            case "annulee", "cancelled", "canceled" -> "Cancelled";
            default -> status.replace('_', ' ');
        };
    }

    private String statusClass(String status) {
        if (status == null) {
            return "orders-status-neutral";
        }
        return switch (status.trim().toLowerCase()) {
            case "payee", "paid" -> "orders-status-paid";
            case "en_attente", "pending" -> "orders-status-pending";
            case "annulee", "cancelled", "canceled" -> "orders-status-cancelled";
            default -> "orders-status-neutral";
        };
    }

    private String formatProductStatus(String status) {
        if (status == null || status.isBlank()) {
            return "Unknown";
        }
        return switch (status.trim().toLowerCase()) {
            case "disponible", "available" -> "Available";
            case "rupture", "out_of_stock" -> "Out of stock";
            case "en_attente", "pending" -> "Pending";
            case "refuse", "rejected" -> "Rejected";
            case "archive", "archived" -> "Archived";
            default -> status.replace('_', ' ');
        };
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

        Label prix = new Label(String.format("%.2f EUR", produit.getPrix()));
        prix.getStyleClass().add("product-card-price");

        // ── Badge rupture ──────────────────────────────────────────────
        boolean enRupture = "rupture".equals(produit.getStatut());
        if (enRupture) {
            Label rupture = new Label("Out of stock");
            rupture.setStyle(
                    "-fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-font-size: 11px;");
            info.getChildren().addAll(nom, desc, prix, rupture);
        } else {
            info.getChildren().addAll(nom, desc, prix);
        }

        // Like button
        boolean liked = LikeService.isLiked(produit.getId());
        Button btnLike = new Button(liked ? "Liked" : "Like");
        btnLike.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-font-size: 18px;" +
                        "-fx-cursor: hand;" +
                        "-fx-padding: 2 6 2 6;" +
                        "-fx-border-color: transparent;"
        );
        btnLike.setOnAction(e -> {
            boolean nowLiked = LikeService.toggleLike(produit.getId());
            btnLike.setText(nowLiked ? "Liked" : "Like");
        });

        // ── Boutons actions ────────────────────────────────────────────
        HBox btns = new HBox(10);
        Button btnVoir   = new Button("View");
        Button btnPanier = new Button("Cart");
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
        Label imgLabel = new Label("Image");
        imgLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #636e72;");
        imgLabel.setLayoutX(105);
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
        setPageTitle("Liked Products");
        setActiveProductNav(btnLikes);
        refreshLikes();
    }

    private void refreshLikes() {
        likesGrid.getChildren().clear();
        List<Produit> produits = LikeService.getProduitsLikes();
        if (produits.isEmpty()) {
            Label empty = new Label("You have not liked any products yet.");
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
        detailPrix .setText(String.format("%.2f EUR", p.getPrix()));
        detailStock.setText("Stock: " + p.getStock());

        String statut = p.getStatut();
        detailStatut.setText("Status: " + formatProductStatus(statut));
        switch (statut) {
            case "disponible" -> detailStatut.setStyle(
                    "-fx-text-fill: #27ae60; -fx-font-weight: bold; -fx-font-size: 14px;");
            case "rupture"    -> detailStatut.setStyle(
                    "-fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-font-size: 14px;");
            default           -> detailStatut.setStyle(
                    "-fx-text-fill: #f0a500; -fx-font-weight: bold; -fx-font-size: 14px;");
        }

        hideAllPages();
        setPageTitle("Product Detail");
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
            alert.setTitle("Out of stock");
            alert.setContentText("This product is out of stock.");
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
            alert.setTitle("Insufficient stock");
            alert.setContentText(
                    "Available stock: " + (p.getStock() - dejaEnPanier) + " unit(s)");
            alert.showAndWait();
            return;
        }

        lignesPanier.add(new LignePanier(qte, p.getPrix(), null, p));
        refreshCartModal();
    }

    @FXML
    public void handleViderPanier() {
        lignesPanier.clear();
        refreshCartModal();
    }

    @FXML
    public void hidePanierModal() {
        if (cartOverlay != null) {
            cartOverlay.setVisible(false);
            cartOverlay.setManaged(false);
        }
        if (productWorkspace != null) {
            productWorkspace.setEffect(null);
            productWorkspace.setCache(false);
        }
        setActiveProductNav(btnAccueil);
        setPageTitle("Products");
    }

    private void showPanierModal() {
        refreshCartModal();
        if (productWorkspace != null) {
            productWorkspace.setCache(true);
            productWorkspace.setCacheHint(javafx.scene.CacheHint.SPEED);
            productWorkspace.setEffect(new GaussianBlur(8));
        }
        if (cartOverlay != null) {
            cartOverlay.setVisible(true);
            cartOverlay.setManaged(true);
        }
        setPageTitle("Your Cart");
        setActiveProductNav(btnPanier);
    }

    private void refreshCartModal() {
        if (cartItemsBox == null) {
            return;
        }
        cartItemsBox.getChildren().clear();
        if (lignesPanier.isEmpty()) {
            VBox emptyState = new VBox(8);
            emptyState.setAlignment(Pos.CENTER);
            emptyState.getStyleClass().add("cart-empty-state");
            Label title = new Label("Your cart is empty");
            title.getStyleClass().add("cart-empty-title");
            Label copy = new Label("Add products from the catalog and they will appear here.");
            copy.getStyleClass().add("cart-empty-copy");
            emptyState.getChildren().addAll(title, copy);
            cartItemsBox.getChildren().add(emptyState);
        } else {
            for (LignePanier ligne : lignesPanier) {
                cartItemsBox.getChildren().add(createCartItemCard(ligne));
            }
        }
        updateCartSummary();
    }

    private HBox createCartItemCard(LignePanier ligne) {
        Produit produit = ligne.getProduit();
        Label title = new Label(produit == null ? "Product" : produit.getNom());
        title.getStyleClass().add("cart-item-title");
        Label meta = new Label(String.format("%.2f EUR / unit", ligne.getPrixUnitaire()));
        meta.getStyleClass().add("cart-item-meta");
        VBox copy = new VBox(4, title, meta);
        copy.setAlignment(Pos.CENTER_LEFT);

        Label quantity = new Label("x" + ligne.getQuantite());
        quantity.getStyleClass().add("cart-quantity-pill");

        Label subtotal = new Label(String.format("%.2f EUR", ligne.getPrixUnitaire() * ligne.getQuantite()));
        subtotal.getStyleClass().add("cart-item-total");

        Button removeButton = new Button("Remove");
        removeButton.getStyleClass().add("cart-remove-button");
        removeButton.setOnAction(event -> {
            lignesPanier.remove(ligne);
            refreshCartModal();
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox card = new HBox(14, copy, spacer, quantity, subtotal, removeButton);
        card.setAlignment(Pos.CENTER_LEFT);
        card.getStyleClass().add("cart-item-card");
        return card;
    }

    private void updateCartSummary() {
        int items = lignesPanier.stream().mapToInt(LignePanier::getQuantite).sum();
        double total = lignesPanier.stream()
                .mapToDouble(ligne -> ligne.getPrixUnitaire() * ligne.getQuantite())
                .sum();
        if (cartCountLabel != null) {
            cartCountLabel.setText(items + " item(s)");
        }
        if (cartTotalLabel != null) {
            cartTotalLabel.setText(String.format("%.2f EUR", total));
        }
    }

    // ─────────────────────────────────────────────
    // COMMANDE + STRIPE + STOCK AUTO
    // ─────────────────────────────────────────────

    @FXML
    public void handlePasserCommande() {
        if (lignesPanier.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty cart");
            alert.setContentText("Add products before checking out.");
            alert.showAndWait();
            return;
        }

        float total = (float) lignesPanier.stream()
                .mapToDouble(l -> l.getPrixUnitaire() * l.getQuantite()).sum();

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/front/PaymentDialog.fxml"));
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
                        ligneCommandeDAO.ajouter(new LigneCommande(
                                lp.getQuantite(), lp.getPrixUnitaire(), commande, p));
                        int newStock = Math.max(0, p.getStock() - lp.getQuantite());
                        p.setStock(newStock);
                        p.setStatut(newStock == 0 ? "rupture" : "disponible");
                        produitDAO.modifier(p);
                    }
                }

                lignesPanier.clear();
                refreshCartModal();
                hidePanierModal();
                loadData();
                loadHistorique();
                showHistorique();
            });

            Stage dialog = new Stage();
            dialog.setTitle("Pegasus Payment");
            dialog.setScene(new Scene(root));
            dialog.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            dialog.setResizable(false);
            dialog.show();

        } catch (Exception e) {
            System.err.println("Payment dialog error: " + e.getMessage());
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
        pageHistoriqueView.setVisible(false);
        pageLikesView     .setVisible(false);
    }

    @FXML
    public void showCatalogue() {
        hideAllPages();
        pageCatalogueView.setVisible(true);
        setPageTitle("Products");
        setActiveProductNav(btnAccueil);
        loadData();
    }

    @FXML
    public void showPanier() {
        showPanierModal();
    }

    @FXML
    public void showHistorique() {
        hideAllPages();
        pageHistoriqueView.setVisible(true);
        setPageTitle("Order History");
        setActiveProductNav(btnHistorique);
    }

    private void setPageTitle(String title) {
        if (productPageTitle != null) {
            productPageTitle.setText(title);
        }
    }

    private void setActiveProductNav(Button activeButton) {
        Button[] buttons = {btnAccueil, btnPanier, btnHistorique, btnLikes};
        for (Button button : buttons) {
            if (button == null) {
                continue;
            }
            button.getStyleClass().remove("product-subnav-button-active");
            if (!button.getStyleClass().contains("product-subnav-button")) {
                button.getStyleClass().add("product-subnav-button");
            }
        }
        if (activeButton != null && !activeButton.getStyleClass().contains("product-subnav-button-active")) {
            activeButton.getStyleClass().add("product-subnav-button-active");
        }
    }

    @FXML
    public void handleLogout() {
        SceneNavigator.logoutToFrontHome();
    }

    @FXML
    public void goHome() {
        try {
            SceneNavigator.goTo("/views/front/home-view.fxml");
        } catch (Exception e) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/views/front/home-view.fxml"));
                Stage stage = (Stage) catalogueGrid.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    @FXML
    public void goGallery() {
        navigateTo("/views/front/menu-view.fxml");
    }

    @FXML
    public void goCourses() {
        navigateTo("/views/front/FrontLayout.fxml");
    }

    @FXML
    public void goEvents() {
        navigateTo(EventsRoleRouter.resolveEventsEntryFxml());
    }

    @FXML
    public void goForum() {
        if (SceneNavigator.getCurrentUser() == null) {
            navigateTo("/views/front/signin-view.fxml");
            return;
        }
        ForumModuleLauncher.openForumWindow();
    }

    @FXML
    public void goProfile() {
        navigateTo("/views/front/profile-view.fxml");
    }

    @FXML
    public void goBackoffice() {
        User currentUser = SceneNavigator.getCurrentUser();
        if (currentUser == null || !"admin".equalsIgnoreCase(currentUser.getDtype())) {
            navigateTo("/views/front/home-view.fxml");
            return;
        }
        navigateTo("/views/back/AdminLayout.fxml");
    }

    @FXML
    public void handleBackHome() {
        goHome();
    }

    private void navigateTo(String fxmlPath) {
        try {
            SceneNavigator.goTo(fxmlPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
