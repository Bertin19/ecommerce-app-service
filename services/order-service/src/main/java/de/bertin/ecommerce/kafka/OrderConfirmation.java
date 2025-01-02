package de.bertin.ecommerce.kafka;

import de.bertin.ecommerce.client.CustomerResponse;
import de.bertin.ecommerce.client.PurchaseResponse;
import de.bertin.ecommerce.model.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
