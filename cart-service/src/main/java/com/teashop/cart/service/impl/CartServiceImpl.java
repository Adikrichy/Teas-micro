package com.teashop.cart.service.impl;

import com.teashop.cart.client.ProductResponse;
import com.teashop.cart.client.ProductServiceClient;
import com.teashop.cart.model.Cart;
import com.teashop.cart.model.CartItem;
import com.teashop.cart.service.CartService;
import com.teashop.cart.service.CartMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final RedisTemplate<String, Cart> redisTemplate;
    private final ProductServiceClient productServiceClient;
    private final CartMessageProducer cartMessageProducer;
    private static final String CART_KEY_PREFIX = "cart:";
    private static final long CART_EXPIRATION_DAYS = 30;

    @Override
    public Cart getCart(Long userId) {
        String cartKey = getCartKey(userId);
        Cart cart = redisTemplate.opsForValue().get(cartKey);
        return cart != null ? cart : new Cart(userId);
    }

    @Override
    public Cart addItemToCart(Long userId, Long productId, Integer quantity) {
        Cart cart = getCart(userId);
        ProductResponse product = productServiceClient.getProductById(productId);

        if (!product.isActive() || product.getStockQuantity() < quantity) {
            throw new RuntimeException("Product is not available or insufficient stock");
        }

        CartItem cartItem = new CartItem(
                product.getId(),
                product.getName(),
                product.getPrice(),
                quantity,
                product.getImageUrl()
        );

        cart.addItem(cartItem);
        saveCart(userId, cart);
        
        // Send message to RabbitMQ
        cartMessageProducer.sendCartUpdate(userId, productId, quantity, "ADD");
        
        return cart;
    }

    @Override
    public Cart updateItemQuantity(Long userId, Long productId, Integer quantity) {
        Cart cart = getCart(userId);
        ProductResponse product = productServiceClient.getProductById(productId);

        if (!product.isActive() || product.getStockQuantity() < quantity) {
            throw new RuntimeException("Product is not available or insufficient stock");
        }

        cart.updateItemQuantity(productId, quantity);
        saveCart(userId, cart);
        
        // Send message to RabbitMQ
        cartMessageProducer.sendCartUpdate(userId, productId, quantity, "UPDATE");
        
        return cart;
    }

    @Override
    public Cart removeItemFromCart(Long userId, Long productId) {
        Cart cart = getCart(userId);
        cart.removeItem(productId);
        saveCart(userId, cart);
        
        // Send message to RabbitMQ
        cartMessageProducer.sendCartUpdate(userId, productId, 0, "REMOVE");
        
        return cart;
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = getCart(userId);
        cart.clear();
        saveCart(userId, cart);
    }

    private String getCartKey(Long userId) {
        return CART_KEY_PREFIX + userId;
    }

    private void saveCart(Long userId, Cart cart) {
        String cartKey = getCartKey(userId);
        redisTemplate.opsForValue().set(cartKey, cart, CART_EXPIRATION_DAYS, TimeUnit.DAYS);
    }
} 