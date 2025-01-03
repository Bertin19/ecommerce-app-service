package de.bertin.ecommerce.service;

import de.bertin.ecommerce.controller.PaymentRequest;
import de.bertin.ecommerce.model.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {
    public Payment toPayment(PaymentRequest request) {
        return Payment.builder()
                .orderId(request.orderId())
                .paymentMethod(request.paymentMethod())
                .amount(request.amount())
                .build();
    }
}
