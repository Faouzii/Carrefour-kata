package com.carrefour.kata.discountmvp.infrastructure.persistence;

import com.carrefour.kata.discountmvp.domain.DiscountType;
import com.carrefour.kata.discountmvp.domain.ProductDescriptionService;
import com.carrefour.kata.discountmvp.infrastructure.persistence.entity.DiscountCodeEntity;
import com.carrefour.kata.discountmvp.infrastructure.persistence.entity.ProductEntity;
import com.carrefour.kata.discountmvp.infrastructure.persistence.repository.JpaDiscountCodeRepository;
import com.carrefour.kata.discountmvp.infrastructure.persistence.repository.JpaProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Component
@ConditionalOnProperty(name = "app.database.type", havingValue = "jpa", matchIfMissing = false)
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {
    
    private final JpaProductRepository productRepository;
    private final JpaDiscountCodeRepository discountCodeRepository;
    private final ProductDescriptionService descriptionService;
    
    @EventListener(ApplicationReadyEvent.class)
    public void initializeData() {
        log.info("Initializing JPA database with sample data");
        
        if (productRepository.count() == 0) {
            initializeProducts();
        }
        
        if (discountCodeRepository.count() == 0) {
            initializeDiscountCodes();
        }
        
        log.info("JPA database initialization completed");
    }
    
    private void initializeProducts() {
        log.debug("Initializing products");
        
        var products = Set.of(
                new ProductEntity("1", "Fresh Apples", BigDecimal.valueOf(2.50), 
                        descriptionService.generateDescription("Fresh Apples")),
                new ProductEntity("2", "Organic Bananas", BigDecimal.valueOf(1.80), 
                        descriptionService.generateDescription("Organic Bananas")),
                new ProductEntity("3", "Sweet Oranges", BigDecimal.valueOf(3.20), 
                        descriptionService.generateDescription("Sweet Oranges")),
                new ProductEntity("4", "Fresh Milk", BigDecimal.valueOf(1.50), 
                        descriptionService.generateDescription("Fresh Milk")),
                new ProductEntity("5", "Whole Grain Bread", BigDecimal.valueOf(2.00), 
                        descriptionService.generateDescription("Whole Grain Bread")),
                new ProductEntity("6", "Smartphone", BigDecimal.valueOf(299.99), 
                        descriptionService.generateDescription("Smartphone")),
                new ProductEntity("7", "Laptop", BigDecimal.valueOf(899.99), 
                        descriptionService.generateDescription("Laptop")),
                new ProductEntity("8", "Wireless Headphones", BigDecimal.valueOf(89.99), 
                        descriptionService.generateDescription("Wireless Headphones"))
        );
        
        productRepository.saveAll(products);
        log.debug("Initialized {} products", products.size());
    }
    
    private void initializeDiscountCodes() {
        log.debug("Initializing discount codes");
        
        var discountCodes = Set.of(
                new DiscountCodeEntity("PERC10", DiscountType.PERCENTAGE, BigDecimal.valueOf(10), 
                        Set.of("1", "2", "3", "4", "5"), LocalDate.now().plusDays(30)),
                new DiscountCodeEntity("FIXED5", DiscountType.FIXED_AMOUNT, BigDecimal.valueOf(5), 
                        Set.of("6", "7", "8"), LocalDate.now().plusDays(60)),
                new DiscountCodeEntity("EXPIRED", DiscountType.PERCENTAGE, BigDecimal.valueOf(15), 
                        Set.of("1", "2"), LocalDate.now().minusDays(1)),
                new DiscountCodeEntity("NA", DiscountType.PERCENTAGE, BigDecimal.valueOf(20), 
                        Set.of("999"), LocalDate.now().plusDays(30))
        );
        
        discountCodeRepository.saveAll(discountCodes);
        log.debug("Initialized {} discount codes", discountCodes.size());
    }
} 