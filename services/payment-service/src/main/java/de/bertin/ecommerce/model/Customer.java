package de.bertin.ecommerce.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record Customer(
        String customerId,
        @NotNull(message = "Firstname is mandatory")
        String firstname,
        @NotNull(message = "Lastname is mandatory")
        String lastname,
        @NotNull(message = "Email is mandatory")
        @Email(message = "Customer email not well formatted")
        String email
) {
}
