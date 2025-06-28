package com.teashop.order.service;

import com.teashop.order.model.Order;
import com.teashop.order.model.OrderStatus;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    Order updateOrder(Long id, Order order);
    void deleteOrder(Long id);
    Order getOrderById(Long id);
    List<Order> getAllOrders();
    List<Order> getOrdersByUserId(Long userId);
    List<Order> getOrdersByStatus(OrderStatus status);
    Order updateOrderStatus(Long id, OrderStatus status);
    void processPayment(Long orderId, String paymentId);
} 