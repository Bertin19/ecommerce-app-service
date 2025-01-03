package de.bertin.ecommerce.kafka.order;

public record Customer(
        String customerId,
        String firstname,
        String lastname,
        String email
) {
}
