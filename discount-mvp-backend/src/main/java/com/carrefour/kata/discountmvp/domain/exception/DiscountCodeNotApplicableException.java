package com.carrefour.kata.discountmvp.domain.exception;

public class DiscountCodeNotApplicableException extends BusinessException {
    
    public DiscountCodeNotApplicableException(String discountCode) {
        super(String.format("Discount code '%s' is not applicable to any product in the cart", discountCode));
    }
    
    @Override
    public String getErrorCode() {
        return "DISCOUNT_CODE_NOT_APPLICABLE";
    }
} 