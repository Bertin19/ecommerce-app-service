package de.bertin.ecommerce.service;

import de.bertin.ecommerce.controller.OrderLineResponse;
import de.bertin.ecommerce.model.Order;
import de.bertin.ecommerce.model.OrderLine;
import de.bertin.ecommerce.orderline.OrderLineRequest;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest request) {
        return OrderLine.builder()
                .id(request.id())
                .quantity(request.quantity())
                .order(
                        Order.builder()
                                .id(request.orderId())
                                .build()
                )
                .productId(request.productId())
                .build();
    }


    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(
                orderLine.getQuantity()
        );
    }
}
