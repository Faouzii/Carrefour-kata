package com.carrefour.kata.discountmvp.infrastructure.web;

import com.carrefour.kata.discountmvp.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    // Business Exceptions
    @ExceptionHandler(DiscountCodeExpiredException.class)
    public ResponseEntity<ApiError> handleDiscountCodeExpired(DiscountCodeExpiredException ex) {
        return ResponseEntity.badRequest().body(ApiError.fromBusinessException(ex));
    }
    
    @ExceptionHandler(DiscountCodeNotApplicableException.class)
    public ResponseEntity<ApiError> handleDiscountCodeNotApplicable(DiscountCodeNotApplicableException ex) {
        return ResponseEntity.badRequest().body(ApiError.fromBusinessException(ex));
    }
    
    @ExceptionHandler(DiscountCodeNotFoundException.class)
    public ResponseEntity<ApiError> handleDiscountCodeNotFound(DiscountCodeNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError.fromBusinessException(ex));
    }
    
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiError> handleProductNotFound(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError.fromBusinessException(ex));
    }
    
    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ApiError> handleCartNotFound(CartNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError.fromBusinessException(ex));
    }
    
    @ExceptionHandler(InvalidQuantityException.class)
    public ResponseEntity<ApiError> handleInvalidQuantity(InvalidQuantityException ex) {
        return ResponseEntity.badRequest().body(ApiError.fromBusinessException(ex));
    }
    
    @ExceptionHandler(NoDiscountCalculatorException.class)
    public ResponseEntity<ApiError> handleNoDiscountCalculator(NoDiscountCalculatorException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiError.fromBusinessException(ex));
    }
    
    // Generic Business Exception handler
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusinessException(BusinessException ex) {
        return ResponseEntity.badRequest().body(ApiError.fromBusinessException(ex));
    }
    
    // Legacy exception handlers for backward compatibility
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ApiError(ex.getMessage()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> handleNotFound(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.badRequest().body(new ApiError("Invalid parameter: " + ex.getName()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");
        return ResponseEntity.badRequest().body(new ApiError(message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleOther(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiError("Unexpected error"));
    }
} 