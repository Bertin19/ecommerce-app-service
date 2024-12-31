package de.bertin.ecommerce.controller;

import jakarta.validation.constraints.NotNull;

public record ProductPurchaseRequest(
        @NotNull(message = "Product id is mandatory")
        String productId,
        @NotNull(message = "Quantity id is mandatory")
        double quantity
) {
}
