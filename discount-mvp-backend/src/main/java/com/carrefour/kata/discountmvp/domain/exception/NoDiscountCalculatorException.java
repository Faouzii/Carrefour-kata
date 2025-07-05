package com.carrefour.kata.discountmvp.domain.exception;

import com.carrefour.kata.discountmvp.domain.DiscountType;

public class NoDiscountCalculatorException extends BusinessException {
    
    public NoDiscountCalculatorException(DiscountType discountType) {
        super(String.format("No discount calculator available for discount type: %s", discountType));
    }
    
    @Override
    public String getErrorCode() {
        return "NO_DISCOUNT_CALCULATOR";
    }
} 