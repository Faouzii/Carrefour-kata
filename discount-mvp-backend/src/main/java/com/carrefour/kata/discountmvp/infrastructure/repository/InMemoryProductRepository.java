package com.carrefour.kata.discountmvp.infrastructure.repository;

import com.carrefour.kata.discountmvp.domain.Product;
import com.carrefour.kata.discountmvp.domain.ProductDescriptionService;
import com.carrefour.kata.discountmvp.domain.port.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@ConditionalOnProperty(name = "app.database.type", havingValue = "memory", matchIfMissing = true)
public class InMemoryProductRepository implements ProductRepository {
    private final ProductDescriptionService descriptionService;
    private final Map<String, Product> products = new HashMap<>();

    public InMemoryProductRepository(ProductDescriptionService descriptionService) {
        this.descriptionService = descriptionService;
        initializeProducts();
    }
    
    private void initializeProducts() {
        products.put("1", new Product("1", "Fresh Apples", BigDecimal.valueOf(2.50), 
                descriptionService.generateDescription("Fresh Apples")));
        products.put("2", new Product("2", "Organic Bananas", BigDecimal.valueOf(1.80), 
                descriptionService.generateDescription("Organic Bananas")));
        products.put("3", new Product("3", "Sweet Oranges", BigDecimal.valueOf(3.20), 
                descriptionService.generateDescription("Sweet Oranges")));
        products.put("4", new Product("4", "Fresh Milk", BigDecimal.valueOf(1.50), 
                descriptionService.generateDescription("Fresh Milk")));
        products.put("5", new Product("5", "Whole Grain Bread", BigDecimal.valueOf(2.00), 
                descriptionService.generateDescription("Whole Grain Bread")));
        
        products.put("6", new Product("6", "Smartphone", BigDecimal.valueOf(299.99), 
                descriptionService.generateDescription("Smartphone")));
        products.put("7", new Product("7", "Laptop", BigDecimal.valueOf(899.99), 
                descriptionService.generateDescription("Laptop")));
        products.put("8", new Product("8", "Wireless Headphones", BigDecimal.valueOf(89.99), 
                descriptionService.generateDescription("Wireless Headphones")));
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