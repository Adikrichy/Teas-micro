package com.teashop.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service")
public interface ProductServiceClient {
    @PutMapping("/api/products/{id}/stock")
    void updateStock(@PathVariable("id") Long productId, @RequestParam("quantity") Integer quantity);
} 