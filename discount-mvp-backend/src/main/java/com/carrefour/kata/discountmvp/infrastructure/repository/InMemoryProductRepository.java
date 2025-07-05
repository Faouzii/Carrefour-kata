package com.carrefour.kata.discountmvp.infrastructure.repository;

import com.carrefour.kata.discountmvp.domain.Product;
import com.carrefour.kata.discountmvp.domain.port.ProductRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public class InMemoryProductRepository implements ProductRepository {
    private final Map<String, Product> products = new HashMap<>();

    public InMemoryProductRepository() {
        products.put("1", new Product("1", "Fresh Apples", BigDecimal.valueOf(2.50)));
        products.put("2", new Product("2", "Organic Bananas", BigDecimal.valueOf(1.80)));
        products.put("3", new Product("3", "Sweet Oranges", BigDecimal.valueOf(3.20)));
        products.put("4", new Product("4", "Fresh Milk", BigDecimal.valueOf(1.50)));
        products.put("5", new Product("5", "Whole Grain Bread", BigDecimal.valueOf(2.00)));
        
        products.put("6", new Product("6", "Smartphone", BigDecimal.valueOf(299.99)));
        products.put("7", new Product("7", "Laptop", BigDecimal.valueOf(899.99)));
        products.put("8", new Product("8", "Wireless Headphones", BigDecimal.valueOf(89.99)));
    }

    @Override
    public Optional<Product> findById(String id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }
} 