package com.carrefour.kata.discountmvp.domain.exception;

public class DiscountCodeExpiredException extends BusinessException {
    
    public DiscountCodeExpiredException(String discountCode) {
        super(String.format("Discount code '%s' has expired", discountCode));
    }
    
    @Override
    public String getErrorCode() {
        return "DISCOUNT_CODE_EXPIRED";
    }
} 