package com.carrefour.kata.discountmvp.domain.exception;

public class ProductNotFoundException extends BusinessException {
    
    public ProductNotFoundException(String productId) {
        super(String.format("Product with id '%s' not found", productId));
    }
    
    @Override
    public String getErrorCode() {
        return "PRODUCT_NOT_FOUND";
    }
} 