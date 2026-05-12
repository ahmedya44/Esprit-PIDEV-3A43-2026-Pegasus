package com.pegasus.controllers.back;

import com.pegasus.controllers.SceneNavigator;
import com.pegasus.entities.User;
import com.pegasus.services.ServiceUser;
import com.pegasus.services.VoiceSearchService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
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
    @FXML private Button voiceSearchButton;
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
    private VoiceSearchService voiceSearchService;
    private Task<String> voiceSearchTask;
    private volatile boolean stopVoiceRequested;

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

                activateButton.setOnAction(event -> updateStatus(getCurrentRowUser(), ServiceUser.STATUS_ACTIVE));
                pendingButton.setOnAction(event -> updateStatus(getCurrentRowUser(), ServiceUser.STATUS_PENDING_VERIFICATION));
                suspendButton.setOnAction(event -> updateStatus(getCurrentRowUser(), STATUS_SUSPENDED));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }
                User target = getCurrentRowUser();
                if (target == null) {
                    setGraphic(null);
                    return;
                }
                boolean allowed = canChangeStatus(target);
                activateButton.setDisable(!allowed);
                pendingButton.setDisable(!allowed);
                suspendButton.setDisable(!allowed);
                setGraphic(actions);
            }

            private User getCurrentRowUser() {
                if (getTableRow() == null) {
                    return null;
                }
                Object rowItem = getTableRow().getItem();
                if (!(rowItem instanceof User)) {
                    return null;
                }
                return (User) rowItem;
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
                    || normalize(user.getPhone()).contains(query)
                    || normalize(user.getDtype()).contains(query)
                    || normalize(user.getStatus()).contains(query)
                    || normalize(user.getProvider()).contains(query)
                    || normalize(user.getCreatedAt() == null ? "" : user.getCreatedAt().format(DATE_FORMAT)).contains(query);
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
        if (!canChangeStatus(user)) {
            if (isAdmin(user)) {
                notifyStatus("You cannot change another admin's status.", true);
            } else {
                notifyStatus("You cannot change your own admin status from here.", true);
            }
            return;
        }
        if (!confirmStatusChange(user, status)) {
            notifyStatus("Status update cancelled.", false);
            return;
        }
        user.setStatus(status);
        serviceUser.modifier(user);
        if (serviceUser.getLastError() != null) {
            notifyStatus(serviceUser.getLastError(), true);
            return;
        }
        notifyStatus(user.getUsername() + " updated to " + status + ".", false);
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

    @FXML
    public void onVoiceSearchUsers() {
        if (voiceSearchTask != null && voiceSearchTask.isRunning()) {
            stopVoiceRequested = true;
            statusLabel.setText("Stopping voice capture...");
            if (voiceSearchButton != null) {
                voiceSearchButton.setText("Stopping...");
                voiceSearchButton.setDisable(true);
            }
            return;
        }

        stopVoiceRequested = false;
        if (voiceSearchButton != null) {
            voiceSearchButton.setDisable(false);
            voiceSearchButton.setText("Stop Listening");
        }
        statusLabel.setText("Listening for voice input...");

        voiceSearchTask = new Task<>() {
            @Override
            protected String call() {
                if (voiceSearchService == null) {
                    voiceSearchService = new VoiceSearchService();
                }
                return voiceSearchService.recognizeOnce(() -> stopVoiceRequested);
            }
        };

        voiceSearchTask.setOnSucceeded(event -> {
            if (voiceSearchButton != null) {
                voiceSearchButton.setDisable(false);
                voiceSearchButton.setText("Voice Search");
            }
            String text = voiceSearchTask.getValue();
            if (text == null || text.isBlank()) {
                statusLabel.setText("No speech recognized. Please try again.");
                SceneNavigator.showSnackbar("Voice Search", "No speech recognized. Please try again.", true);
                voiceSearchTask = null;
                return;
            }
            searchField.setText(text);
            applyFilters();
            statusLabel.setText("Voice search applied: \"" + text + "\"");
            SceneNavigator.showSnackbar("Voice Search", "Search updated from voice input.", false);
            voiceSearchTask = null;
        });

        voiceSearchTask.setOnFailed(event -> {
            if (voiceSearchButton != null) {
                voiceSearchButton.setDisable(false);
                voiceSearchButton.setText("Voice Search");
            }
            String message = voiceSearchTask.getException() == null ? "Voice search failed." : voiceSearchTask.getException().getMessage();
            statusLabel.setText(message);
            SceneNavigator.showSnackbar("Voice Search", message, true);
            voiceSearchTask = null;
        });

        Thread worker = new Thread(voiceSearchTask, "admin-users-voice-search");
        worker.setDaemon(true);
        worker.start();
    }

    private boolean isAdmin(User user) {
        return user != null && "admin".equalsIgnoreCase(normalize(user.getDtype()));
    }

    private boolean canChangeStatus(User targetUser) {
        if (targetUser == null) {
            return false;
        }
        User currentUser = SceneNavigator.getCurrentUser();
        if (currentUser != null && targetUser.getId() != null && targetUser.getId().equals(currentUser.getId())) {
            return false;
        }
        return !isAdmin(targetUser);
    }

    private boolean confirmStatusChange(User user, String status) {
        Dialog<ButtonType> confirmDialog = new Dialog<>();
        confirmDialog.setTitle("Update User Status");
        confirmDialog.setHeaderText(null);
        confirmDialog.setContentText("Change status for " + user.getUsername() + " to " + status + "?");
        confirmDialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        styleDialog(confirmDialog);
        ButtonType result = confirmDialog.showAndWait().orElse(ButtonType.CANCEL);
        return result == ButtonType.OK;
    }

    private void styleDialog(Dialog<?> dialog) {
        String theme = AdminUsersController.class.getResource("/styles/theme.css").toExternalForm();
        dialog.getDialogPane().getStylesheets().add(theme);
        dialog.getDialogPane().getStyleClass().add("profile-dialog");
    }

    private void notifyStatus(String message, boolean isError) {
        statusLabel.setText(message);
        SceneNavigator.showSnackbar(isError ? "Users Dashboard" : "Status Updated", message, isError);
    }
}
