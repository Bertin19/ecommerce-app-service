package de.bertin.ecommerce.service;

import de.bertin.ecommerce.controller.OrderRequest;
import de.bertin.ecommerce.client.CustomerClient;
import de.bertin.ecommerce.controller.OrderResponse;
import de.bertin.ecommerce.exception.BusinessException;
import de.bertin.ecommerce.kafka.OrderConfirmation;
import de.bertin.ecommerce.kafka.OrderProducer;
import de.bertin.ecommerce.orderline.OrderLineRequest;
import de.bertin.ecommerce.client.ProductClient;
import de.bertin.ecommerce.client.PurchaseRequest;
import de.bertin.ecommerce.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {


    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final OrderMapper mapper;

    public OrderService(CustomerClient customerClient,
                        ProductClient productClient,
                        OrderRepository orderRepository,
                        OrderLineService orderLineService,
                        OrderProducer orderProducer,
                        OrderMapper mapper
    ) {
        this.customerClient = customerClient;
        this.productClient = productClient;
        this.orderRepository = orderRepository;
        this.orderLineService = orderLineService;
        this.orderProducer = orderProducer;
        this.mapper = mapper;
    }

    public String createOrder(OrderRequest request) {
        // check the customer
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order::No existing customer with the provided id"));

        // Purchase the products --> using product-service by calling the rest template
        var purchaseProducts = this.productClient.purchaseProducts(request.products());
        // persist order
        var order = this.orderRepository.save(mapper.toOrder(request));
        // persist order lines
        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        // TODO: Start the payment process

        // send the order confirmation --> notification-service (kafka maybe)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchaseProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(String orderId) {
        return orderRepository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException("User with given Id does not exist"));
    }
}
