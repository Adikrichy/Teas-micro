package com.teashop.cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private Long userId;
    private List<CartItem> items = new ArrayList<>();
    private BigDecimal totalAmount;

    // Add this constructor
    public Cart(Long userId) {
        this.userId = userId;
        this.items = new ArrayList<>();
        this.totalAmount = BigDecimal.ZERO;
    }

    public void calculateTotalAmount() {
        this.totalAmount = items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addItem(CartItem item) {
        items.add(item);
        calculateTotalAmount();
    }

    public void removeItem(Long productId) {
        items.removeIf(item -> item.getProductId().equals(productId));
        calculateTotalAmount();
    }

    public void updateItemQuantity(Long productId, Integer quantity) {
        items.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    calculateTotalAmount();
                });
    }

    public void clear() {
        items.clear();
        totalAmount = BigDecimal.ZERO;
    }
}