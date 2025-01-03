package de.bertin.ecommerce.controller;

import de.bertin.ecommerce.model.Customer;
import de.bertin.ecommerce.model.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String orderId,
        String orderReference,
        Customer customer
) {
}
