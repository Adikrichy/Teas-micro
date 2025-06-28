package com.teashop.product.service;

import com.teashop.product.model.Product;
import com.teashop.product.model.ProductCategory;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
    Product getProductById(Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(ProductCategory category);
    List<Product> searchProducts(String name);
    void updateStock(Long id, Integer quantity);
} 