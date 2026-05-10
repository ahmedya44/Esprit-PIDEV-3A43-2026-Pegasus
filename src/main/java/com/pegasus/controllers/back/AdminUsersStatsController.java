package com.pegasus.controllers.back;

import com.pegasus.entities.User;
import com.pegasus.services.ServiceUser;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AdminUsersStatsController {
    @FXML private Label statusLabel;
    @FXML private Label totalUsersLabel;
    @FXML private Label activeUsersLabel;
    @FXML private Label pendingUsersLabel;
    @FXML private Label suspendedUsersLabel;
    @FXML private PieChart rolesPieChart;
    @FXML private PieChart statusPieChart;
    @FXML private BarChart<String, Number> registrationsChart;

    private final ServiceUser serviceUser = new ServiceUser();
    private static final DateTimeFormatter DAY_LABEL = DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH);

    @FXML
    public void initialize() {
        refreshStats();
    }

    @FXML
    public void refreshStats() {
        try {
            List<User> users = serviceUser.findAllUsers();
            renderKpis(users);
            renderRoleChart(users);
            renderStatusChart(users);
            renderRegistrationsChart(users);
            statusLabel.setText("User stats refreshed for " + users.size() + " user(s).");
        } catch (Exception e) {
            statusLabel.setText("Failed to load user stats: " + e.getMessage());
        }
    }

    private void renderKpis(List<User> users) {
        int total = users.size();
        int active = (int) users.stream().filter(u -> "ACTIVE".equalsIgnoreCase(safe(u.getStatus()))).count();
        int pending = (int) users.stream().filter(u -> "PENDING_VERIFICATION".equalsIgnoreCase(safe(u.getStatus()))).count();
        int suspended = (int) users.stream().filter(u -> {
            String status = safe(u.getStatus());
            return "SUSPENDED".equalsIgnoreCase(status) || "BANNED".equalsIgnoreCase(status);
        }).count();

        totalUsersLabel.setText(String.valueOf(total));
        activeUsersLabel.setText(String.valueOf(active));
        pendingUsersLabel.setText(String.valueOf(pending));
        suspendedUsersLabel.setText(String.valueOf(suspended));
    }

    private void renderRoleChart(List<User> users) {
        Map<String, Integer> byRole = new LinkedHashMap<>();
        byRole.put("Admin", 0);
        byRole.put("Normal User", 0);
        byRole.put("Artiste", 0);
        byRole.put("Sponsor", 0);
        byRole.put("Other", 0);

        for (User user : users) {
            String dtype = safe(user.getDtype()).toLowerCase();
            if (dtype.equals("admin")) {
                byRole.put("Admin", byRole.get("Admin") + 1);
            } else if (dtype.equals("normal_user")) {
                byRole.put("Normal User", byRole.get("Normal User") + 1);
            } else if (dtype.equals("artiste")) {
                byRole.put("Artiste", byRole.get("Artiste") + 1);
            } else if (dtype.equals("sponsor")) {
                byRole.put("Sponsor", byRole.get("Sponsor") + 1);
            } else {
                byRole.put("Other", byRole.get("Other") + 1);
            }
        }

        rolesPieChart.setData(FXCollections.observableArrayList(
                byRole.entrySet().stream()
                        .filter(entry -> entry.getValue() > 0)
                        .map(entry -> new PieChart.Data(entry.getKey(), entry.getValue()))
                        .toList()
        ));
    }

    private void renderStatusChart(List<User> users) {
        Map<String, Integer> byStatus = new LinkedHashMap<>();
        for (User user : users) {
            String status = safe(user.getStatus()).toUpperCase(Locale.ROOT);
            if (status.isBlank()) {
                status = "UNKNOWN";
            }
            byStatus.put(status, byStatus.getOrDefault(status, 0) + 1);
        }

        statusPieChart.setData(FXCollections.observableArrayList(
                byStatus.entrySet().stream()
                        .map(entry -> new PieChart.Data(entry.getKey(), entry.getValue()))
                        .toList()
        ));
    }

    private void renderRegistrationsChart(List<User> users) {
        LocalDate today = LocalDate.now();
        Map<LocalDate, Integer> days = new LinkedHashMap<>();
        for (int i = 6; i >= 0; i--) {
            days.put(today.minusDays(i), 0);
        }

        for (User user : users) {
            if (user.getCreatedAt() == null) {
                continue;
            }
            LocalDate creationDate = user.getCreatedAt().toLocalDate();
            if (days.containsKey(creationDate)) {
                days.put(creationDate, days.get(creationDate) + 1);
            }
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<LocalDate, Integer> entry : days.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey().format(DAY_LABEL), entry.getValue()));
        }
        registrationsChart.getData().setAll(series);
    }

    private String safe(String value) {
        return value == null ? "" : value.trim();
    }
}
