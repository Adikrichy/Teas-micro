package com.teashop.payment.repository;

import com.teashop.payment.model.Payment;
import com.teashop.payment.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUserId(Long userId);
    List<Payment> findByOrderId(Long orderId);
    List<Payment> findByStatus(PaymentStatus status);
    List<Payment> findByUserIdAndStatus(Long userId, PaymentStatus status);
} 