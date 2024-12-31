package de.bertin.ecommerce.controller;

import de.bertin.ecommerce.model.Address;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email,
        Address address
) {
}
