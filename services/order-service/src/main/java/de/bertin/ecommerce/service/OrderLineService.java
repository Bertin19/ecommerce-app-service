package de.bertin.ecommerce.service;

import de.bertin.ecommerce.controller.OrderLineResponse;
import de.bertin.ecommerce.orderline.OrderLineRequest;
import de.bertin.ecommerce.repository.OrderLineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderLineService {
    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper mapper;

    public OrderLineService(OrderLineRepository orderLineRepository,
                            OrderLineMapper mapper) {
        this.orderLineRepository = orderLineRepository;
        this.mapper = mapper;
    }

    public String saveOrderLine(OrderLineRequest request) {
        var order = mapper.toOrderLine(request);
        return orderLineRepository.save(order).getId();
    }

    public List<OrderLineResponse> findAllByOrderId(String orderId) {
            return orderLineRepository.findAllByOrderId(orderId)
                    .stream()
                    .map(mapper::toOrderLineResponse)
                    .collect(Collectors.toList());
    }
}
