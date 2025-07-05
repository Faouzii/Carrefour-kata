package com.carrefour.kata.discountmvp.infrastructure.web;

import com.carrefour.kata.discountmvp.application.CartService;
import com.carrefour.kata.discountmvp.domain.Cart;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "Shopping cart data transfer object")
public record CartDto(
        @Schema(description = "Cart identifier", example = "default-cart")
        String id, 
        @Schema(description = "List of items in the cart")
        List<CartItemDto> items, 
        @Schema(description = "Applied discount code", example = "SAVE10")
        String appliedDiscountCode,
        @Schema(description = "Total amount before discount", example = "25.50")
        BigDecimal totalAmount,
        @Schema(description = "Discount amount", example = "2.55")
        BigDecimal discountAmount,
        @Schema(description = "Final amount after discount", example = "22.95")
        BigDecimal finalAmount
) {
    public static CartDto from(Cart cart, CartService cartService) {
        var items = cart.items().stream()
                .map(item -> new CartItemDto(
                        item.product().id(),
                        item.quantity(),
                        new ProductDto(
                                item.product().id(),
                                item.product().name(),
                                item.product().price(),
                                item.product().description()
                        )
                ))
                .collect(Collectors.toList());
        
        var appliedDiscountCode = cart.getAppliedDiscountCode().map(d -> d.getCode()).orElse(null);
        
        var totalAmount = cart.items().stream()
                .map(item -> item.product().price().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        var discountAmount = BigDecimal.ZERO;
        var finalAmount = totalAmount;
        
        if (cart.getAppliedDiscountCode().isPresent()) {
            try {
                var originalTotal = cartService.calculateTotal(cart.id());
                discountAmount = totalAmount.subtract(originalTotal);
                finalAmount = originalTotal;
            } catch (Exception e) {
                // If calculation fails, keep original amounts
                discountAmount = BigDecimal.ZERO;
                finalAmount = totalAmount;
            }
        }
        
        return new CartDto(cart.id(), items, appliedDiscountCode, totalAmount, discountAmount, finalAmount);
    }
    
    @Schema(description = "Cart item data transfer object")
    public record CartItemDto(
            @Schema(description = "Product identifier", example = "1")
            String productId, 
            @Schema(description = "Quantity of the product", example = "2")
            int quantity, 
            @Schema(description = "Product details")
            ProductDto product
    ) {}
    
    @Schema(description = "Product data transfer object")
    public record ProductDto(
            @Schema(description = "Product identifier", example = "1")
            String id, 
            @Schema(description = "Product name", example = "Fresh Apples")
            String name, 
            @Schema(description = "Product price", example = "2.50")
            BigDecimal price, 
            @Schema(description = "Product description", example = "Fresh, crisp apples from local orchards")
            String description
    ) {}
} 