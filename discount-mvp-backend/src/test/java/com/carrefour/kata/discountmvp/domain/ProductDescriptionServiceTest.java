package com.carrefour.kata.discountmvp.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class ProductDescriptionServiceTest {
    
    private ProductDescriptionService descriptionService;
    
    @BeforeEach
    void setUp() {
        descriptionService = new ProductDescriptionService();
    }
    
    @Test
    void generateDescription_shouldReturnOrganicDescription() {
        String description = descriptionService.generateDescription("Organic Bananas");
        assertTrue(description.contains("Organic Carrefour Bio"));
        assertTrue(description.contains("sustainably sourced"));
    }
    
    @Test
    void generateDescription_shouldReturnFreshDescription() {
        String description = descriptionService.generateDescription("Fresh Apples");
        assertTrue(description.contains("Fresh Carrefour product"));
        assertTrue(description.contains("carefully selected"));
    }
    
    @Test
    void generateDescription_shouldReturnPremiumDescription() {
        String description = descriptionService.generateDescription("Premium Coffee");
        assertTrue(description.contains("Premium Carrefour selection"));
        assertTrue(description.contains("exceptional quality"));
    }
    
    @Test
    void generateDescription_shouldReturnLocalDescription() {
        String description = descriptionService.generateDescription("Local Honey");
        assertTrue(description.contains("Local Carrefour product"));
        assertTrue(description.contains("regional producers"));
    }
    
    @Test
    void generateDescription_shouldReturnDefaultDescription() {
        String description = descriptionService.generateDescription("Generic Product");
        assertTrue(description.contains("Quality Carrefour product"));
        assertTrue(description.contains("sustainable sourcing"));
    }
    
    @Test
    void generateDescription_shouldBeCaseInsensitive() {
        String description1 = descriptionService.generateDescription("ORGANIC BANANAS");
        String description2 = descriptionService.generateDescription("organic bananas");
        
        assertEquals(description1, description2);
        assertTrue(description1.contains("Organic Carrefour Bio"));
    }
} 