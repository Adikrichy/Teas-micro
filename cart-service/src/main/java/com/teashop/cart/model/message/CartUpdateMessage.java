package com.teashop.cart.model.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartUpdateMessage {
    private Long userId;
    private Long productId;
    private Integer quantity;
    private String operation; // ADD, REMOVE, UPDATE
    private LocalDateTime timestamp;
} 