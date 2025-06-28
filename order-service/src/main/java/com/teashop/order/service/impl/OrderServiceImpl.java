package com.teashop.order.service.impl;

import com.teashop.order.client.ProductServiceClient;
import com.teashop.order.model.Order;
import com.teashop.order.model.OrderItem;
import com.teashop.order.model.OrderStatus;
import com.teashop.order.repository.OrderRepository;
import com.teashop.order.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductServiceClient productServiceClient;

    @Override
    public Order createOrder(Order order) {
        order.calculateTotalAmount();
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Long id, Order order) {
        Order existingOrder = getOrderById(id);
        order.setId(id);
        order.calculateTotalAmount();
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = getOrderById(id);
        orderRepository.delete(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    public Order updateOrderStatus(Long id, OrderStatus status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Override
    public void processPayment(Long orderId, String paymentId) {
        Order order = getOrderById(orderId);
        order.setPaymentId(paymentId);
        order.setStatus(OrderStatus.PAID);
        
        // Update product stock
        for (OrderItem item : order.getItems()) {
            productServiceClient.updateStock(item.getProductId(), item.getQuantity());
        }
        
        orderRepository.save(order);
    }
} 