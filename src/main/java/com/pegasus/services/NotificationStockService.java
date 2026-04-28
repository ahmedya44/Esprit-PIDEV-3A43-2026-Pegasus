package com.pegasus.services;

import com.pegasus.models.Produit;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class NotificationStockService {

    private static final int SEUIL_CRITIQUE = 5; // ← stock critique en dessous de 5
    private static Label badgeLabel; // référence au badge navbar

    // Injecter le badge depuis le contrôleur artiste
    public static void setBadge(Label badge) {
        badgeLabel = badge;
    }

    public static void verifierStock(Produit p) {
        if (p.getStock() <= SEUIL_CRITIQUE) {
            Platform.runLater(() -> {
                // ── 1. Popup Alert ──────────────────────────
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("⚠️ Stock faible !");
                alert.setHeaderText("Stock critique pour : " + p.getNom());
                alert.setContentText(
                        "Il reste seulement " + p.getStock() + " unité(s) en stock.\n" +
                                "Pensez à réapprovisionner !");
                alert.show(); // show() = non-bloquant

                // ── 2. Badge navbar ─────────────────────────
                if (badgeLabel != null) {
                    badgeLabel.setVisible(true);
                    badgeLabel.setText("⚠️");
                    badgeLabel.setStyle(
                            "-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                                    "-fx-background-radius: 10; -fx-padding: 2 6 2 6; " +
                                    "-fx-font-size: 11px; -fx-font-weight: bold;");
                }
            });
        } else {
            // Stock OK → cacher le badge
            if (badgeLabel != null) {
                Platform.runLater(() -> badgeLabel.setVisible(false));
            }
        }
    }
}