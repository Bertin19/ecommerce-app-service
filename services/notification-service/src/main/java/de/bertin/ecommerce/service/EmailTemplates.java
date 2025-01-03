package de.bertin.ecommerce.service;

import lombok.Getter;

public enum EmailTemplates {
    PAYMENT_CONFIRMATION(
            "payment-confirmation.html",
            "Payment successfully processed"
    ),
    ORDER_CONFIRMATION(
            "order-confirmation.html",
            "Order confirmation"
    );

    @Getter
    private String template;
    @Getter
    private String subject;

    EmailTemplates(String subject, String template) {
        this.subject = subject;
        this.template = template;
    }
}
