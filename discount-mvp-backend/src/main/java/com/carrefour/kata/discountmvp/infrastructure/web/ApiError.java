package com.carrefour.kata.discountmvp.infrastructure.web;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "API error response")
public record ApiError(
        @Schema(description = "Error message", example = "Product not found")
        String error,
        @Schema(description = "Error code", example = "PRODUCT_NOT_FOUND")
        String errorCode
) {
    
    public ApiError(String error) {
        this(error, null);
    }
    
    public static ApiError fromBusinessException(com.carrefour.kata.discountmvp.domain.exception.BusinessException ex) {
        return new ApiError(ex.getMessage(), ex.getErrorCode());
    }
} 