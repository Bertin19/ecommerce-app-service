package de.bertin.ecommerce.orderline;

public record OrderLineRequest(
        String id,
        String orderId,
        String productId,
        double quantity) {
}
