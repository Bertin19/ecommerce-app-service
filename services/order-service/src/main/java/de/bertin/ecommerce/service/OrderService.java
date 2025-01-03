package de.bertin.ecommerce.service;

import de.bertin.ecommerce.client.CustomerResponse;
import de.bertin.ecommerce.client.PurchaseResponse;
import de.bertin.ecommerce.controller.OrderRequest;
import de.bertin.ecommerce.client.CustomerClient;
import de.bertin.ecommerce.controller.OrderResponse;
import de.bertin.ecommerce.exception.BusinessException;
import de.bertin.ecommerce.kafka.OrderConfirmation;
import de.bertin.ecommerce.kafka.OrderProducer;
import de.bertin.ecommerce.model.Order;
import de.bertin.ecommerce.orderline.OrderLineRequest;
import de.bertin.ecommerce.client.ProductClient;
import de.bertin.ecommerce.client.PurchaseRequest;
import de.bertin.ecommerce.payment.PaymentClient;
import de.bertin.ecommerce.payment.PaymentRequest;
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
    private final PaymentClient paymentClient;

    public OrderService(
            OrderRepository orderRepository,
            CustomerClient customerClient,
            ProductClient productClient,
            OrderLineService orderLineService,
            OrderProducer orderProducer,
            OrderMapper mapper,
            PaymentClient paymentClient
    ) {
        this.orderRepository = orderRepository;
        this.customerClient = customerClient;
        this.productClient = productClient;
        this.orderLineService = orderLineService;
        this.orderProducer = orderProducer;
        this.mapper = mapper;
        this.paymentClient = paymentClient;
    }

    public String createOrder(OrderRequest request) {
        var customerResponse = validateCustomer(request);
        var purchaseProducts = this.productClient.purchaseProducts(request.products());
        var order = this.orderRepository.save(mapper.toOrder(request));
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
        processPayment(request, order.getId(), customerResponse);
        sendOrderConfirmation(request, customerResponse, purchaseProducts);

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

    private void sendOrderConfirmation(OrderRequest request, CustomerResponse customerResponse, List<PurchaseResponse> purchaseProducts) {
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customerResponse,
                        purchaseProducts
                )
        );
    }

    private void processPayment(OrderRequest request, String orderId, CustomerResponse customerResponse) {
        var paymentrequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                orderId,
                request.reference(),
                customerResponse
        );
        paymentClient.requestOrderPayment(paymentrequest);
    }

    private CustomerResponse validateCustomer(OrderRequest request) {
        return this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order::No existing customer with the provided id"));
    }
}
