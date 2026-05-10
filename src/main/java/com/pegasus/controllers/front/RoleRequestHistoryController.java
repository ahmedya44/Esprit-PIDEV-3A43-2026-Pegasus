package com.pegasus.controllers.front;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pegasus.controllers.SceneNavigator;
import com.pegasus.entities.RoleRequest;
import com.pegasus.entities.User;
import com.pegasus.services.RoleRequestService;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RoleRequestHistoryController {
    @FXML private VBox historyContainer;
    @FXML private Label statusLabel;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final RoleRequestService roleRequestService = new RoleRequestService();

    @FXML
    public void initialize() {
        User currentUser = SceneNavigator.getCurrentUser();
        if (currentUser == null) {
            SceneNavigator.showSnackbar("Session", "You need to sign in first.", true);
            onGoSignIn();
            return;
        }
        loadHistory(currentUser);
    }

    private void loadHistory(User currentUser) {
        List<RoleRequest> requests = roleRequestService.findRequestsByUserId(currentUser.getId());
        historyContainer.getChildren().clear();
        if (requests.isEmpty()) {
            Label empty = new Label("No role requests yet.");
            empty.getStyleClass().add("subtitle");
            historyContainer.getChildren().add(empty);
            statusLabel.setText("0 request(s)");
            return;
        }

        for (RoleRequest request : requests) {
            historyContainer.getChildren().add(buildRequestCard(request));
        }
        statusLabel.setText(requests.size() + " request(s)");
    }

    private VBox buildRequestCard(RoleRequest request) {
        VBox card = new VBox(10);
        card.getStyleClass().add("section-card");
        card.setPadding(new Insets(14));

        HBox topRow = new HBox(10);
        Label role = new Label("Requested role: " + safe(request.getRequestedRole()).toUpperCase());
        role.getStyleClass().add("section-title");
        Label status = new Label("Status: " + safe(request.getStatus()));
        status.getStyleClass().add("subtitle");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topRow.getChildren().addAll(role, spacer, status);

        Label createdAt = new Label("Created at: " + formatDate(request.getCreatedAt()));
        createdAt.getStyleClass().add("light-text");

        Label reviewedAt = new Label("Reviewed at: " + formatDate(request.getReviewedAt()));
        reviewedAt.getStyleClass().add("light-text");

        Label details = new Label("Submitted info: " + compactDetails(request.getRequestDataJson()));
        details.setWrapText(true);
        details.getStyleClass().add("subtitle");

        card.getChildren().addAll(topRow, createdAt, reviewedAt, details);
        if (RoleRequestService.STATUS_REJECTED.equalsIgnoreCase(request.getStatus())) {
            Label reason = new Label("Rejection reason: " + safe(request.getRejectionReason(), "No reason provided"));
            reason.setWrapText(true);
            reason.getStyleClass().add("error-label");
            card.getChildren().add(reason);
        }
        return card;
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
            return builder.isEmpty() ? "-" : builder.toString();
        } catch (Exception e) {
            return json;
        }
    }

    private String formatDate(java.time.LocalDateTime dateTime) {
        return dateTime == null ? "-" : dateTime.format(DATE_FORMAT);
    }

    private String safe(String value) {
        return safe(value, "-");
    }

    private String safe(String value, String fallback) {
        if (value == null || value.isBlank()) {
            return fallback;
        }
        return value.trim();
    }

    @FXML
    public void onBackToProfile() {
        try {
            SceneNavigator.goTo("/views/front/profile-view.fxml");
        } catch (IOException e) {
            SceneNavigator.showSnackbar("Navigation Error", "Could not open profile page.", true);
        }
    }

    @FXML
    public void onGoSignIn() {
        try {
            SceneNavigator.goTo("/views/front/signin-view.fxml");
        } catch (IOException e) {
            SceneNavigator.showSnackbar("Navigation Error", "Could not open sign-in page.", true);
        }
    }
}
