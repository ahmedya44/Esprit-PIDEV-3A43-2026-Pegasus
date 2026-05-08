package com.pegasus.controllers.back;

import com.pegasus.controllers.SceneNavigator;
import com.pegasus.entities.User;
import com.pegasus.services.ServiceUser;
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

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class AdminUsersController {
    @FXML private TextField searchField;
    @FXML private ComboBox<String> roleFilter;
    @FXML private ComboBox<String> statusFilter;
    @FXML private Label summaryLabel;
    @FXML private TableView<User> usersTable;
    @FXML private TableColumn<User, String> colUsername;
    @FXML private TableColumn<User, String> colEmail;
    @FXML private TableColumn<User, String> colRole;
    @FXML private TableColumn<User, String> colStatus;
    @FXML private TableColumn<User, String> colProvider;
    @FXML private TableColumn<User, String> colCreated;
    @FXML private TableColumn<User, Void> colActions;
    @FXML private Label statusLabel;

    private static final String ALL = "All";
    private static final String STATUS_SUSPENDED = "SUSPENDED";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final ServiceUser serviceUser = new ServiceUser();
    private final ObservableList<User> allUsers = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        roleFilter.setItems(FXCollections.observableArrayList(ALL, "admin", "artiste", "normal_user", "user"));
        statusFilter.setItems(FXCollections.observableArrayList(
                ALL,
                ServiceUser.STATUS_ACTIVE,
                ServiceUser.STATUS_PENDING_VERIFICATION,
                STATUS_SUSPENDED,
                "BANNED"
        ));
        roleFilter.getSelectionModel().select(ALL);
        statusFilter.getSelectionModel().select(ALL);

        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("dtype"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colProvider.setCellValueFactory(new PropertyValueFactory<>("provider"));
        colCreated.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getCreatedAt() == null ? "-" : cell.getValue().getCreatedAt().format(DATE_FORMAT)
        ));
        colActions.setCellFactory(column -> new TableCell<>() {
            private final Button activateButton = new Button("Activate");
            private final Button pendingButton = new Button("Pending");
            private final Button suspendButton = new Button("Suspend");
            private final HBox actions = new HBox(8, activateButton, pendingButton, suspendButton);

            {
                activateButton.getStyleClass().add("admin-table-success-button");
                pendingButton.getStyleClass().add("admin-table-warning-button");
                suspendButton.getStyleClass().add("admin-table-danger-button");

                activateButton.setOnAction(event -> updateStatus(getTableView().getItems().get(getIndex()), ServiceUser.STATUS_ACTIVE));
                pendingButton.setOnAction(event -> updateStatus(getTableView().getItems().get(getIndex()), ServiceUser.STATUS_PENDING_VERIFICATION));
                suspendButton.setOnAction(event -> updateStatus(getTableView().getItems().get(getIndex()), STATUS_SUSPENDED));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : actions);
            }
        });

        usersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        searchField.textProperty().addListener((obs, oldValue, newValue) -> applyFilters());
        roleFilter.setOnAction(event -> applyFilters());
        statusFilter.setOnAction(event -> applyFilters());
        refreshUsers();
    }

    @FXML
    public void refreshUsers() {
        List<User> users = serviceUser.findAllUsers();
        allUsers.setAll(users);
        applyFilters();
    }

    private void applyFilters() {
        String query = normalize(searchField.getText());
        String role = roleFilter.getValue();
        String status = statusFilter.getValue();
        ObservableList<User> filtered = allUsers.filtered(user -> {
            boolean matchesQuery = query.isEmpty()
                    || normalize(user.getUsername()).contains(query)
                    || normalize(user.getEmail()).contains(query)
                    || normalize(user.getPhone()).contains(query);
            boolean matchesRole = role == null || ALL.equals(role) || normalize(user.getDtype()).equals(normalize(role));
            boolean matchesStatus = status == null || ALL.equals(status) || normalize(user.getStatus()).equals(normalize(status));
            return matchesQuery && matchesRole && matchesStatus;
        });
        usersTable.setItems(filtered);
        updateSummary(filtered);
    }

    private void updateStatus(User user, String status) {
        if (user == null) {
            return;
        }
        User currentUser = SceneNavigator.getCurrentUser();
        if (currentUser != null && user.getId() != null && user.getId().equals(currentUser.getId())) {
            statusLabel.setText("You cannot change your own admin status from here.");
            return;
        }
        user.setStatus(status);
        serviceUser.modifier(user);
        if (serviceUser.getLastError() != null) {
            statusLabel.setText(serviceUser.getLastError());
            return;
        }
        statusLabel.setText(user.getUsername() + " updated to " + status + ".");
        refreshUsers();
    }

    private void updateSummary(ObservableList<User> filtered) {
        long active = allUsers.stream()
                .filter(user -> ServiceUser.STATUS_ACTIVE.equalsIgnoreCase(user.getStatus()))
                .count();
        long suspended = allUsers.stream()
                .filter(user -> STATUS_SUSPENDED.equalsIgnoreCase(user.getStatus()) || "BANNED".equalsIgnoreCase(user.getStatus()))
                .count();
        summaryLabel.setText(filtered.size() + " shown / " + allUsers.size() + " users");
        statusLabel.setText(active + " active, " + suspended + " suspended or banned");
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
    }
}
