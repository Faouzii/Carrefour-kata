package com.carrefour.kata.discountmvp.infrastructure.web;

import com.carrefour.kata.discountmvp.domain.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    @Test
    void handleDiscountCodeExpired_shouldReturnBadRequest() {
        var exception = new DiscountCodeExpiredException("EXPIRED");
        
        ResponseEntity<ApiError> response = exceptionHandler.handleDiscountCodeExpired(exception);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("DISCOUNT_CODE_EXPIRED", response.getBody().errorCode());
        assertTrue(response.getBody().error().contains("EXPIRED"));
    }

    @Test
    void handleDiscountCodeNotApplicable_shouldReturnBadRequest() {
        var exception = new DiscountCodeNotApplicableException("NA");
        
        ResponseEntity<ApiError> response = exceptionHandler.handleDiscountCodeNotApplicable(exception);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("DISCOUNT_CODE_NOT_APPLICABLE", response.getBody().errorCode());
        assertTrue(response.getBody().error().contains("NA"));
    }

    @Test
    void handleDiscountCodeNotFound_shouldReturnNotFound() {
        var exception = new DiscountCodeNotFoundException("INVALID");
        
        ResponseEntity<ApiError> response = exceptionHandler.handleDiscountCodeNotFound(exception);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("DISCOUNT_CODE_NOT_FOUND", response.getBody().errorCode());
        assertTrue(response.getBody().error().contains("INVALID"));
    }

    @Test
    void handleProductNotFound_shouldReturnNotFound() {
        var exception = new ProductNotFoundException("999");
        
        ResponseEntity<ApiError> response = exceptionHandler.handleProductNotFound(exception);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("PRODUCT_NOT_FOUND", response.getBody().errorCode());
        assertTrue(response.getBody().error().contains("999"));
    }

    @Test
    void handleCartNotFound_shouldReturnNotFound() {
        var exception = new CartNotFoundException("nonexistent");
        
        ResponseEntity<ApiError> response = exceptionHandler.handleCartNotFound(exception);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("CART_NOT_FOUND", response.getBody().errorCode());
        assertTrue(response.getBody().error().contains("nonexistent"));
    }

    @Test
    void handleInvalidQuantity_shouldReturnBadRequest() {
        var exception = new InvalidQuantityException(0);
        
        ResponseEntity<ApiError> response = exceptionHandler.handleInvalidQuantity(exception);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("INVALID_QUANTITY", response.getBody().errorCode());
        assertTrue(response.getBody().error().contains("0"));
    }

    @Test
    void handleNoDiscountCalculator_shouldReturnInternalServerError() {
        var exception = new NoDiscountCalculatorException(com.carrefour.kata.discountmvp.domain.DiscountType.PERCENTAGE);
        
        ResponseEntity<ApiError> response = exceptionHandler.handleNoDiscountCalculator(exception);
        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("NO_DISCOUNT_CALCULATOR", response.getBody().errorCode());
        assertTrue(response.getBody().error().contains("PERCENTAGE"));
    }

    @Test
    void handleBusinessException_shouldReturnBadRequest() {
        var exception = new BusinessException("Test business error") {
            @Override
            public String getErrorCode() {
                return "TEST_ERROR";
            }
        };
        
        ResponseEntity<ApiError> response = exceptionHandler.handleBusinessException(exception);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("TEST_ERROR", response.getBody().errorCode());
        assertEquals("Test business error", response.getBody().error());
    }
} 