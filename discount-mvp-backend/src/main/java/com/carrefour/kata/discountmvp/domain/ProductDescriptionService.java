package com.carrefour.kata.discountmvp.domain;

import org.springframework.stereotype.Service;

@Service
public class ProductDescriptionService {
    
    public String generateDescription(String productName) {
        var lowerName = productName.toLowerCase();
        
        if (lowerName.contains("organic") || lowerName.contains("bio")) {
            return "Organic Carrefour Bio product, sustainably sourced and certified organic.";
        } else if (lowerName.contains("fresh") || lowerName.contains("fruit") || lowerName.contains("vegetable")) {
            return "Fresh Carrefour product, carefully selected for quality and taste.";
        } else if (lowerName.contains("premium") || lowerName.contains("gourmet")) {
            return "Premium Carrefour selection, offering exceptional quality and flavor.";
        } else if (lowerName.contains("local") || lowerName.contains("regional")) {
            return "Local Carrefour product, supporting regional producers and sustainability.";
        } else {
            return "Quality Carrefour product with sustainable sourcing and excellent value.";
        }
    }
} 