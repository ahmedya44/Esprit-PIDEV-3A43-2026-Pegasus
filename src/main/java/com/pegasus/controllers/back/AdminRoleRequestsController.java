package com.pegasus.controllers.back;

import com.pegasus.controllers.SceneNavigator;
import com.pegasus.entities.RoleRequest;
import com.pegasus.entities.User;
import com.pegasus.services.RoleRequestService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;

import java.util.Optional;
import java.time.format.DateTimeFormatter;

public class AdminRoleRequestsController {
    private static final String ALL = "All";
    @FXML private TableView<RoleRequest> requestsTable;
    @FXML private ComboBox<String> statusFilterBox;
    @FXML private TableColumn<RoleRequest, String> colUser;
    @FXML private TableColumn<RoleRequest, String> colEmail;
    @FXML private TableColumn<RoleRequest, String> colCurrentRole;
    @FXML private TableColumn<RoleRequest, String> colRequestedRole;
    @FXML private TableColumn<RoleRequest, String> colStatus;
    @FXML private TableColumn<RoleRequest, String> colCreatedAt;
    @FXML private TableColumn<RoleRequest, String> colDetails;
    @FXML private TableColumn<RoleRequest, Void> colActions;
    @FXML private Label statusLabel;

    private final RoleRequestService roleRequestService = new RoleRequestService();
    private final ObservableList<RoleRequest> requests = FXCollections.observableArrayList();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @FXML
    public void initialize() {
        colUser.setCellValueFactory(cell -> new SimpleStringProperty(safe(cell.getValue().getUsername())));
        colEmail.setCellValueFactory(cell -> new SimpleStringProperty(safe(cell.getValue().getEmail())));
        colCurrentRole.setCellValueFactory(cell -> new SimpleStringProperty(safe(cell.getValue().getCurrentRole())));
        colRequestedRole.setCellValueFactory(cell -> new SimpleStringProperty(safe(cell.getValue().getRequestedRole())));
        colStatus.setCellValueFactory(cell -> new SimpleStringProperty(safe(cell.getValue().getStatus())));
        colCreatedAt.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getCreatedAt() == null ? "-" : cell.getValue().getCreatedAt().format(DATE_FORMAT)
        ));
        colDetails.setCellValueFactory(cell -> new SimpleStringProperty(compactDetails(cell.getValue().getRequestDataJson())));
        colActions.setCellFactory(column -> new TableCell<>() {
            private final Button approveButton = new Button("Approve");
            private final Button rejectButton = new Button("Reject");
            private final HBox box = new HBox(8, approveButton, rejectButton);

            {
                approveButton.getStyleClass().add("admin-table-success-button");
                rejectButton.getStyleClass().add("admin-table-danger-button");
                approveButton.setOnAction(event -> handleApprove(getCurrentRequest()));
                rejectButton.setOnAction(event -> handleReject(getCurrentRequest()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }
                RoleRequest request = getCurrentRequest();
                if (request == null) {
                    setGraphic(null);
                    return;
                }
                boolean pending = RoleRequestService.STATUS_PENDING.equalsIgnoreCase(request.getStatus());
                approveButton.setDisable(!pending);
                rejectButton.setDisable(!pending);
                setGraphic(box);
            }

            private RoleRequest getCurrentRequest() {
                if (getTableRow() == null || !(getTableRow().getItem() instanceof RoleRequest)) {
                    return null;
                }
                return (RoleRequest) getTableRow().getItem();
            }
        });

        statusFilterBox.setItems(FXCollections.observableArrayList(
                ALL,
                RoleRequestService.STATUS_PENDING,
                RoleRequestService.STATUS_APPROVED,
                RoleRequestService.STATUS_REJECTED
        ));
        statusFilterBox.getSelectionModel().select(ALL);
        requestsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        refreshRequests();
    }

    @FXML
    public void refreshRequests() {
        requests.setAll(roleRequestService.findAllRequests());
        applyFilters();
    }

    @FXML
    public void onFilterChanged() {
        applyFilters();
    }

    private void handleApprove(RoleRequest request) {
        if (request == null) {
            return;
        }
        if (!confirm("Approve request for " + request.getUsername() + " to become " + request.getRequestedRole() + "?")) {
            return;
        }
        User admin = SceneNavigator.getCurrentUser();
        boolean ok = roleRequestService.approveRequest(request, admin);
        if (!ok) {
            notifyError(roleRequestService.getLastError());
            return;
        }
        SceneNavigator.showSnackbar("Role Requests", "Request approved successfully.", false);
        refreshRequests();
    }

    private void handleReject(RoleRequest request) {
        if (request == null) {
            return;
        }
        if (!confirm("Reject request for " + request.getUsername() + "?")) {
            return;
        }
        String reason = promptRejectionReason();
        if (reason == null) {
            return;
        }
        User admin = SceneNavigator.getCurrentUser();
        boolean ok = roleRequestService.rejectRequest(request, admin, reason);
        if (!ok) {
            notifyError(roleRequestService.getLastError());
            return;
        }
        SceneNavigator.showSnackbar("Role Requests", "Request rejected.", false);
        refreshRequests();
    }

    private boolean confirm(String message) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Confirm Action");
        dialog.setHeaderText(null);
        dialog.setContentText(message);
        dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        String theme = AdminRoleRequestsController.class.getResource("/styles/theme.css").toExternalForm();
        dialog.getDialogPane().getStylesheets().add(theme);
        dialog.getDialogPane().getStyleClass().add("profile-dialog");
        ButtonType result = dialog.showAndWait().orElse(ButtonType.CANCEL);
        return result == ButtonType.OK;
    }

    private String promptRejectionReason() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Reject Request");
        dialog.setHeaderText(null);
        dialog.setContentText("Reason (optional):");
        String theme = AdminRoleRequestsController.class.getResource("/styles/theme.css").toExternalForm();
        dialog.getDialogPane().getStylesheets().add(theme);
        dialog.getDialogPane().getStyleClass().add("profile-dialog");
        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty()) {
            return null;
        }
        String reason = result.get();
        return reason == null || reason.isBlank() ? "Rejected by admin" : reason.trim();
    }

    private void notifyError(String message) {
        String clean = safe(message);
        statusLabel.setText(clean.isBlank() ? "Operation failed." : clean);
        SceneNavigator.showSnackbar("Role Requests", clean.isBlank() ? "Operation failed." : clean, true);
    }

    private String compactDetails(String json) {
        if (json == null || json.isBlank()) {
            return "-";
        }
        try {
            JsonObject object = JsonParser.parseString(json).getAsJsonObject();
            StringBuilder builder = new StringBuilder();
            for (String key : object.keySet()) {
                String value = object.get(key).isJsonNull() ? "" : object.get(key).getAsString();
                if (value == null || value.isBlank()) {
                    continue;
                }
                if (builder.length() > 0) {
                    builder.append(" | ");
                }
                builder.append(key).append(": ").append(value);
            }
            String text = builder.toString();
            return text.length() > 240 ? text.substring(0, 237) + "..." : text;
        } catch (Exception e) {
            return json.length() > 240 ? json.substring(0, 237) + "..." : json;
        }
    }

    private String safe(String value) {
        return value == null ? "" : value.trim();
    }

    private void applyFilters() {
        String selected = statusFilterBox == null ? ALL : statusFilterBox.getValue();
        ObservableList<RoleRequest> filtered = requests.filtered(request -> {
            if (selected == null || ALL.equals(selected)) {
                return true;
            }
            return selected.equalsIgnoreCase(safe(request.getStatus()));
        });
        requestsTable.setItems(filtered);
        statusLabel.setText(filtered.size() + " request(s) shown / " + requests.size() + " total");
    }
}
