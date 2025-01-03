package de.bertin.ecommerce.payment;

import de.bertin.ecommerce.client.CustomerResponse;
import de.bertin.ecommerce.model.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String orderId,
        String orderReference,
        CustomerResponse customer
) {
}
