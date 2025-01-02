package de.bertin.ecommerce.client;

public record CustomerResponse(
        String Id,
        String firstname,
        String lastname,
        String email
) {
}
