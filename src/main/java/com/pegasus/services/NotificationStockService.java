package com.pegasus.services;

import com.pegasus.entities.Produit;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Window;

public class NotificationStockService {

    private static final int SEUIL_CRITIQUE = 1; // notification si stock <= 1
    private static Label  badgeLabel;
    private static Window ownerWindow;

    public static void setBadge(Label badge, Window window) {
        badgeLabel  = badge;
        ownerWindow = window;
    }

    public static void verifierStock(Produit p) {
        if (p.getStock() <= SEUIL_CRITIQUE) {
            Platform.runLater(() -> {
                // ── Badge navbar ──────────────────────────────
                if (badgeLabel != null) {
                    badgeLabel.setVisible(true);
                    badgeLabel.setText(p.getStock() == 0 ? "🚫" : "⚠️");
                    badgeLabel.setStyle(
                            "-fx-background-color: #e74c3c;" +
                                    "-fx-text-fill: white;" +
                                    "-fx-background-radius: 10;" +
                                    "-fx-padding: 2 5 2 5;" +
                                    "-fx-font-size: 10px;" +
                                    "-fx-font-weight: bold;"
                    );
                }
                // ── Toast notification ────────────────────────
                showToast(p);
            });
        } else {
            Platform.runLater(() -> {
                if (badgeLabel != null) badgeLabel.setVisible(false);
            });
        }
    }

    private static void showToast(Produit p) {
        if (ownerWindow == null) return;

        Popup popup = new Popup();
        popup.setAutoHide(true);

        // ── Contenu toast ─────────────────────────────────────
        HBox toast = new HBox(12);
        toast.setAlignment(Pos.CENTER_LEFT);
        toast.setStyle(
                "-fx-background-color: #1a1a2e;" +
                        "-fx-background-radius: 12;" +
                        "-fx-padding: 14 20 14 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.45), 15, 0, 0, 5);"
        );

        // Icône
        Label icon = new Label(p.getStock() == 0 ? "🚫" : "⚠️");
        icon.setStyle("-fx-font-size: 22px;");

        // Barre colorée à gauche (simulation via border)
        Label bar = new Label();
        bar.setStyle(
                "-fx-background-color: " + (p.getStock() == 0 ? "#e74c3c" : "#f0a500") + ";" +
                        "-fx-pref-width: 4px;" +
                        "-fx-pref-height: 50px;" +
                        "-fx-background-radius: 4px;"
        );

        VBox text = new VBox(4);
        Label titre = new Label(p.getStock() == 0 ? "Rupture de stock !" : "Stock critique !");
        titre.setStyle(
                "-fx-text-fill: " + (p.getStock() == 0 ? "#e74c3c" : "#f0a500") + ";" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14px;"
        );

        Label msg = new Label(
                "\"" + p.getNom() + "\" — " +
                        (p.getStock() == 0 ? "Plus aucune unité disponible" : "Dernière unité restante")
        );
        msg.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 12px;");

        Label hint = new Label("Pensez à réapprovisionner");
        hint.setStyle("-fx-text-fill: #888888; -fx-font-size: 11px;");

        text.getChildren().addAll(titre, msg, hint);
        toast.getChildren().addAll(bar, icon, text);
        popup.getContent().add(toast);

        // ── Position coin bas-droite ──────────────────────────
        double x = ownerWindow.getX() + ownerWindow.getWidth()  - 380;
        double y = ownerWindow.getY() + ownerWindow.getHeight() - 130;
        popup.show(ownerWindow, x, y);

        // ── Disparaît après 4 secondes ────────────────────────
        new Thread(() -> {
            try { Thread.sleep(4000); } catch (InterruptedException ignored) {}
            Platform.runLater(popup::hide);
        }).start();
    }
}
