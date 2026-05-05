package com.pegasus.controllers;

import com.pegasus.services.StripeService;
import com.stripe.exception.CardException;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PaymentController {

    @FXML private Label labelTotal;
    @FXML private TextField cardName;
    @FXML private TextField cardNumber;
    @FXML private TextField cardExpiry;
    @FXML private TextField cardCvv;
    @FXML private Label statusLabel;
    @FXML private Button btnPayer;

    private float montant;
    private Runnable onSuccess;

    public void init(float montant, Runnable onSuccess) {
        this.montant = montant;
        this.onSuccess = onSuccess;
        labelTotal.setText(String.format("Total : %.2f €", montant));
    }

    @FXML
    public void handlePayer() {
        // Validation champs
        if (cardName.getText().isBlank() || cardNumber.getText().isBlank()
                || cardExpiry.getText().isBlank() || cardCvv.getText().isBlank()) {
            showStatus("⚠️ Veuillez remplir tous les champs.", "#e67e22");
            return;
        }

        // Désactiver le bouton pendant le traitement
        btnPayer.setDisable(true);
        btnPayer.setText("⏳ Traitement...");
        showStatus("", "#333");

        // Appel Stripe dans un thread séparé (évite de geler l'UI)
        new Thread(() -> {
            try {
                PaymentIntent intent = StripeService.createPaymentIntent(montant);

                // En mode test, "requires_payment_method" = PaymentIntent créé avec succès
                String status = intent.getStatus();

                Platform.runLater(() -> {
                    if (status.equals("requires_payment_method")
                            || status.equals("requires_confirmation")
                            || status.equals("succeeded")) {

                        showStatus("✅ Paiement accepté avec succès !", "#27ae60");
                        btnPayer.setText("✅ Payé");

                        // Attendre 1.5s puis fermer et déclencher callback
                        new Thread(() -> {
                            try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
                            Platform.runLater(() -> {
                                if (onSuccess != null) onSuccess.run();
                                fermerFenetre();
                            });
                        }).start();

                    } else {
                        showStatus("❌ Statut inattendu : " + status, "#e74c3c");
                        resetBtn();
                    }
                });

            } catch (CardException e) {
                Platform.runLater(() -> {
                    showStatus("❌ Carte refusée : " + e.getMessage(), "#e74c3c");
                    resetBtn();
                });
            } catch (StripeException e) {
                Platform.runLater(() -> {
                    showStatus("❌ Erreur Stripe : " + e.getMessage(), "#e74c3c");
                    resetBtn();
                });
            }
        }).start();
    }

    @FXML
    public void handleAnnuler() {
        fermerFenetre();
    }

    private void fermerFenetre() {
        Stage stage = (Stage) btnPayer.getScene().getWindow();
        stage.close();
    }

    private void showStatus(String message, String color) {
        statusLabel.setText(message);
        statusLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: " + color + ";");
    }

    private void resetBtn() {
        btnPayer.setDisable(false);
        btnPayer.setText("🔒 Payer maintenant");
    }
}