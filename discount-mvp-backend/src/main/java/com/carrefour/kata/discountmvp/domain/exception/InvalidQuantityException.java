package com.carrefour.kata.discountmvp.domain.exception;

public class InvalidQuantityException extends BusinessException {
    
    public InvalidQuantityException(int quantity) {
        super(String.format("Invalid quantity: %d. Quantity must be greater than 0", quantity));
    }
    
    @Override
    public String getErrorCode() {
        return "INVALID_QUANTITY";
    }
} 