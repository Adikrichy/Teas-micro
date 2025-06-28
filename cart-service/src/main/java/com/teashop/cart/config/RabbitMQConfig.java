package com.teashop.cart.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String CART_EXCHANGE = "cart.exchange";
    public static final String CART_PRODUCT_UPDATE_QUEUE = "cart.product.update";
    public static final String CART_ORDER_UPDATE_QUEUE = "cart.order.update";

    @Bean
    public DirectExchange cartExchange() {
        return new DirectExchange(CART_EXCHANGE);
    }

    @Bean
    public Queue cartProductUpdateQueue() {
        return new Queue(CART_PRODUCT_UPDATE_QUEUE);
    }

    @Bean
    public Queue cartOrderUpdateQueue() {
        return new Queue(CART_ORDER_UPDATE_QUEUE);
    }

    @Bean
    public Binding cartProductUpdateBinding() {
        return BindingBuilder.bind(cartProductUpdateQueue())
                .to(cartExchange())
                .with("product.update");
    }

    @Bean
    public Binding cartOrderUpdateBinding() {
        return BindingBuilder.bind(cartOrderUpdateQueue())
                .to(cartExchange())
                .with("order.update");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
} 