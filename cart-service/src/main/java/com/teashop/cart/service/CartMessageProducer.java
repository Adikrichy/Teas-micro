package com.teashop.cart.service;

import com.teashop.cart.config.RabbitMQConfig;
import com.teashop.cart.model.message.CartUpdateMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CartMessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendCartUpdate(Long userId, Long productId, Integer quantity, String operation) {
        CartUpdateMessage message = new CartUpdateMessage(
                userId,
                productId,
                quantity,
                operation,
                LocalDateTime.now()
        );
        rabbitTemplate.convertAndSend(RabbitMQConfig.CART_EXCHANGE, "product.update", message);
    }
} 