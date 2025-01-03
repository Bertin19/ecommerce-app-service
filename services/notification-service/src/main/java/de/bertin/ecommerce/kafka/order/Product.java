package de.bertin.ecommerce.kafka.order;

import java.math.BigDecimal;

public record Product(
        String productId,
        String productName,
        String description,
        BigDecimal price,
        double quantity
) {
}
