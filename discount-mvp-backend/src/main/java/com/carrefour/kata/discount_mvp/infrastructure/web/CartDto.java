package com.carrefour.kata.discount_mvp.infrastructure.web;

import com.carrefour.kata.discount_mvp.application.CartService;
import com.carrefour.kata.discount_mvp.domain.Cart;
import com.carrefour.kata.discount_mvp.domain.CartItem;
import com.carrefour.kata.discount_mvp.domain.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public record CartDto(
        String id, 
        List<CartItemDto> items, 
        String appliedDiscountCode,
        BigDecimal totalAmount,
        BigDecimal discountAmount,
        BigDecimal finalAmount
) {
    public static CartDto from(Cart cart, CartService cartService) {
        List<CartItemDto> items = cart.items().stream()
                .map(item -> new CartItemDto(
                        item.product().id(),
                        item.quantity(),
                        new ProductDto(
                                item.product().id(),
                                item.product().name(),
                                item.product().price(),
                                "Product description"
                        )
                ))
                .collect(Collectors.toList());
        
        String appliedDiscountCode = cart.getAppliedDiscountCode().map(d -> d.getCode()).orElse(null);
        
        BigDecimal totalAmount = cart.items().stream()
                .map(item -> item.product().price().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal discountAmount = BigDecimal.ZERO;
        BigDecimal finalAmount = totalAmount;
        
        if (cart.getAppliedDiscountCode().isPresent()) {
            try {
                BigDecimal originalTotal = cartService.calculateTotal(cart.id());
                discountAmount = totalAmount.subtract(originalTotal);
                finalAmount = originalTotal;
            } catch (Exception e) {
            }
        }
        
        return new CartDto(cart.id(), items, appliedDiscountCode, totalAmount, discountAmount, finalAmount);
    }
    
    public record CartItemDto(
            String productId, 
            int quantity, 
            ProductDto product
    ) {}
    
    public record ProductDto(
            String id, 
            String name, 
            BigDecimal price, 
            String description
    ) {}
} 