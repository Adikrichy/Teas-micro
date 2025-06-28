package com.teashop.cart.config;

import com.teashop.cart.model.Cart;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Cart> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Cart> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        
        // Set key serializer
        template.setKeySerializer(new StringRedisSerializer());
        
        // Set value serializer
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Cart.class));
        
        // Set hash key serializer
        template.setHashKeySerializer(new StringRedisSerializer());
        
        // Set hash value serializer
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Cart.class));
        
        template.afterPropertiesSet();
        return template;
    }
} 