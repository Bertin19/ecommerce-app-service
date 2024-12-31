package de.bertin.ecommerce.controller;

import java.math.BigDecimal;

public record ProductResponse(
        String name,
        String description,
        double availableQuantity,
        BigDecimal price,
        String categoryId,
        String categoryName,
        String categoryDescription
) {
}
