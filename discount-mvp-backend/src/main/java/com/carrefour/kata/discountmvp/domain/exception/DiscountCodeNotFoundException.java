package com.carrefour.kata.discountmvp.domain.exception;

public class DiscountCodeNotFoundException extends BusinessException {
    
    public DiscountCodeNotFoundException(String discountCode) {
        super(String.format("Discount code '%s' not found", discountCode));
    }
    
    @Override
    public String getErrorCode() {
        return "DISCOUNT_CODE_NOT_FOUND";
    }
} 