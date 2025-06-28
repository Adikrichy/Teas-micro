package com.teashop.payment.service.impl;

import com.teashop.payment.model.Payment;
import com.teashop.payment.model.PaymentMethod;
import com.teashop.payment.model.PaymentStatus;
import com.teashop.payment.repository.PaymentRepository;
import com.teashop.payment.service.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public Payment createPayment(Long orderId, Long userId, PaymentMethod paymentMethod) {
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setUserId(userId);
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus(PaymentStatus.PENDING);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment processPayment(Long paymentId) {
        Payment payment = getPaymentById(paymentId);
        
        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new IllegalStateException("Payment is not in PENDING state");
        }

        // Simulate payment processing
        try {
            // In a real application, this would integrate with a payment gateway
            payment.setTransactionId(UUID.randomUUID().toString());
            payment.setStatus(PaymentStatus.COMPLETED);
            return paymentRepository.save(payment);
        } catch (Exception e) {
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
            throw new RuntimeException("Payment processing failed", e);
        }
    }

    @Override
    public Payment refundPayment(Long paymentId) {
        Payment payment = getPaymentById(paymentId);
        
        if (payment.getStatus() != PaymentStatus.COMPLETED) {
            throw new IllegalStateException("Payment is not in COMPLETED state");
        }

        payment.setStatus(PaymentStatus.REFUNDED);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment cancelPayment(Long paymentId) {
        Payment payment = getPaymentById(paymentId);
        
        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new IllegalStateException("Payment is not in PENDING state");
        }

        payment.setStatus(PaymentStatus.CANCELLED);
        return paymentRepository.save(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> getPaymentsByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> getPaymentsByStatus(PaymentStatus status) {
        return paymentRepository.findByStatus(status);
    }
} 