package com.carrefour.kata.discountmvp.infrastructure.web;

public record ApiError(String error, String errorCode) {
    
    public ApiError(String error) {
        this(error, null);
    }
    
    public static ApiError fromBusinessException(com.carrefour.kata.discountmvp.domain.exception.BusinessException ex) {
        return new ApiError(ex.getMessage(), ex.getErrorCode());
    }
} 