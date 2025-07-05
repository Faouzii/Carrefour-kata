package com.carrefour.kata.discountmvp.domain.exception;

public class CartNotFoundException extends BusinessException {
    
    public CartNotFoundException(String cartId) {
        super(String.format("Cart with id '%s' not found", cartId));
    }
    
    @Override
    public String getErrorCode() {
        return "CART_NOT_FOUND";
    }
} 