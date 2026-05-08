package com.pegasus.controllers.back;

import com.pegasus.dao.ProduitDAO;
import com.pegasus.entities.Produit;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.Locale;

public class AdminProductsController {
    @FXML private TextField searchField;
    @FXML private ComboBox<String> statusFilter;
    @FXML private TableView<Produit> productsTable;
    @FXML private TableColumn<Produit, String> colName;
    @FXML private TableColumn<Produit, String> colCategory;
    @FXML private TableColumn<Produit, Float> colPrice;
    @FXML private TableColumn<Produit, Integer> colStock;
    @FXML private TableColumn<Produit, String> colStatus;
    @FXML private TableColumn<Produit, Void> colActions;
    @FXML private Label statusLabel;

    private final ProduitDAO produitDAO = new ProduitDAO();
    private final ObservableList<Produit> allProducts = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        statusFilter.setItems(FXCollections.observableArrayList(
                "All", "disponible", "en_attente", "rupture", "refuse", "archive"
        ));
        statusFilter.getSelectionModel().select("All");

        colName.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colCategory.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getCategorie() == null ? "Uncategorized" : cell.getValue().getCategorie().getNom()
        ));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("statut"));
        colActions.setCellFactory(column -> new TableCell<>() {
            private final Button approveButton = new Button("Approve");
            private final Button rejectButton = new Button("Reject");
            private final Button deleteButton = new Button("Delete");
            private final HBox actions = new HBox(8, approveButton, rejectButton, deleteButton);

            {
                approveButton.getStyleClass().add("admin-table-success-button");
                rejectButton.getStyleClass().add("admin-table-warning-button");
                deleteButton.getStyleClass().add("admin-table-danger-button");

                approveButton.setOnAction(event -> updateStatus(getTableView().getItems().get(getIndex()), "disponible"));
                rejectButton.setOnAction(event -> updateStatus(getTableView().getItems().get(getIndex()), "refuse"));
                deleteButton.setOnAction(event -> deleteProduct(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : actions);
            }
        });

        productsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        searchField.textProperty().addListener((obs, oldValue, newValue) -> applyFilters());
        statusFilter.setOnAction(event -> applyFilters());
        refreshProducts();
    }

    @FXML
    public void refreshProducts() {
        List<Produit> products = produitDAO.getAll();
        allProducts.setAll(products);
        applyFilters();
    }

    private void applyFilters() {
        String query = normalize(searchField.getText());
        String status = statusFilter.getValue();
        ObservableList<Produit> filtered = allProducts.filtered(product -> {
            boolean matchesQuery = query.isEmpty()
                    || normalize(product.getNom()).contains(query)
                    || normalize(product.getDescription()).contains(query)
                    || (product.getCategorie() != null && normalize(product.getCategorie().getNom()).contains(query));
            boolean matchesStatus = status == null
                    || "All".equals(status)
                    || normalize(product.getStatut()).equals(normalize(status));
            return matchesQuery && matchesStatus;
        });
        productsTable.setItems(filtered);
        statusLabel.setText(filtered.size() + " product(s) shown");
    }

    private void updateStatus(Produit product, String status) {
        if (product == null) {
            return;
        }
        product.setStatut(status);
        produitDAO.modifier(product);
        refreshProducts();
    }

    private void deleteProduct(Produit product) {
        if (product == null) {
            return;
        }
        produitDAO.supprimer(product.getId());
        refreshProducts();
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
    }
}
