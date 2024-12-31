package de.bertin.ecommerce.controller;

import java.math.BigDecimal;

public record ProductPurchaseResponse(
        String name,
        String description,
        BigDecimal price,
        double quantity
) {
}
