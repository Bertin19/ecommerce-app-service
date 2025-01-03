package de.bertin.ecommerce.service;

import de.bertin.ecommerce.controller.PaymentRequest;
import de.bertin.ecommerce.model.Payment;
import de.bertin.ecommerce.model.PaymentStatus;
import de.bertin.ecommerce.notification.NotificationProducer;
import de.bertin.ecommerce.notification.PaymentNotificationRequest;
import de.bertin.ecommerce.repository.PaymentRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final NotificationProducer notificationProducer;
    private final PaymentMapper mapper;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper mapper, NotificationProducer notificationProducer) {
        this.paymentRepository = paymentRepository;
        this.notificationProducer = notificationProducer;
        this.mapper = mapper;
    }

    @Transactional
    public String createPayment(@Valid PaymentRequest request) {
        Payment payment = mapper.toPayment(request);
        payment.setStatus(PaymentStatus.PENDING);
        Payment savedPayment = paymentRepository.save(payment);
        notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        request.orderReference(),
                        request.amount(),
                        request.paymentMethod(),
                        request.customer().firstname(),
                        request.customer().lastname(),
                        request.customer().email()
                )
        );

        return savedPayment.getId();
    }
}
