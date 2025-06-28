package com.teashop.order.repository;

import com.teashop.order.model.Order;
import com.teashop.order.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByUserIdAndStatus(Long userId, OrderStatus status);
    List<Order> findByStatus(OrderStatus status);
} 