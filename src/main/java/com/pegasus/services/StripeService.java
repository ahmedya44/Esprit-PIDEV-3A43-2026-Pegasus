package com.pegasus.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

public class StripeService {

    private static final String SECRET_KEY = "";

    static {
        Stripe.apiKey = SECRET_KEY;
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
}