package com.teashop.cart.service;

import com.teashop.cart.model.Cart;
import com.teashop.cart.model.CartItem;

public interface CartService {
    Cart getCart(Long userId);
    Cart addItemToCart(Long userId, Long productId, Integer quantity);
    Cart updateItemQuantity(Long userId, Long productId, Integer quantity);
    Cart removeItemFromCart(Long userId, Long productId);
    void clearCart(Long userId);
} 