package com.pegasus.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import java.io.InputStream;
import java.util.Properties;

public class StripeService {

    private static final String STRIPE_CONFIG_PATH = "/stripe.properties";
    private static final String STRIPE_KEY_PROPERTY = "stripe.secret.key";

    static {
        Stripe.apiKey = loadSecretKey();
    }

    public static PaymentIntent createPaymentIntent(float montantEnEuros) throws StripeException {
        long montantEnCentimes = (long) (montantEnEuros * 100);

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(montantEnCentimes)
                .setCurrency("eur")
                .setDescription("Commande Pegasus")
                .addPaymentMethodType("card")
                .build();

        return PaymentIntent.create(params);
    }

    private static String loadSecretKey() {
        Properties properties = new Properties();
        try (InputStream inputStream = StripeService.class.getResourceAsStream(STRIPE_CONFIG_PATH)) {
            if (inputStream == null) {
                throw new IllegalStateException("Missing stripe.properties file in src/main/resources.");
            }
            properties.load(inputStream);
            String key = properties.getProperty(STRIPE_KEY_PROPERTY);
            if (key == null || key.isBlank()) {
                throw new IllegalStateException("stripe.secret.key is missing in stripe.properties.");
            }
            return key.trim();
        } catch (Exception e) {
            throw new IllegalStateException("Stripe configuration error: " + e.getMessage(), e);
        }
    }
}
