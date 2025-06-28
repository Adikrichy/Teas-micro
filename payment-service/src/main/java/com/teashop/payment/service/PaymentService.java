package com.teashop.payment.service;

import com.teashop.payment.model.Payment;
import com.teashop.payment.model.PaymentMethod;
import com.teashop.payment.model.PaymentStatus;

import java.util.List;

public interface PaymentService {
    Payment createPayment(Long orderId, Long userId, PaymentMethod paymentMethod);
    Payment processPayment(Long paymentId);
    Payment refundPayment(Long paymentId);
    Payment cancelPayment(Long paymentId);
    Payment getPaymentById(Long id);
    List<Payment> getPaymentsByUserId(Long userId);
    List<Payment> getPaymentsByOrderId(Long orderId);
    List<Payment> getPaymentsByStatus(PaymentStatus status);
} 