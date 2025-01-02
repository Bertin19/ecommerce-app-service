package de.bertin.ecommerce.service;

import de.bertin.ecommerce.controller.OrderLineResponse;
import de.bertin.ecommerce.controller.OrderRequest;
import de.bertin.ecommerce.controller.OrderResponse;
import de.bertin.ecommerce.model.Order;
import de.bertin.ecommerce.model.OrderLine;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
    public Order toOrder(OrderRequest request) {
        return Order.builder()
                .customerId(request.customerId())
                .reference(request.reference())
                .totalAmount(request.amount())
                .paymentMethod(request.paymentMethod())
                .build();
    }

    public OrderResponse fromOrder(Order order) {
        return new OrderResponse(
                // order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomerId()
        );
    }
}
