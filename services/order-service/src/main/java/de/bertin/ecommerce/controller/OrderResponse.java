package de.bertin.ecommerce.controller;

import de.bertin.ecommerce.model.PaymentMethod;

import java.math.BigDecimal;

public record OrderResponse(
        String reference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerId
) {
}
