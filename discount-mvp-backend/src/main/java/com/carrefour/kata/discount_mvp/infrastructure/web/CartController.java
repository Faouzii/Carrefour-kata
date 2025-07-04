package com.carrefour.kata.discount_mvp.infrastructure.web;

import com.carrefour.kata.discount_mvp.application.CartService;
import com.carrefour.kata.discount_mvp.domain.Cart;
import com.carrefour.kata.discount_mvp.infrastructure.web.CartDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartDto> getCart() {
        Cart cart = cartService.getCart("default-cart");
        return ResponseEntity.ok(CartDto.from(cart, cartService));
    }

    @PostMapping("/items")
    public ResponseEntity<CartDto> addItem(@Valid @RequestBody AddItemRequest request) {
        Cart cart = cartService.addItem("default-cart", request.productId(), request.quantity());
        return ResponseEntity.ok(CartDto.from(cart, cartService));
    }

    @PutMapping("/items/{productId}")
    public ResponseEntity<CartDto> updateItem(@PathVariable String productId, @RequestBody UpdateItemRequest request) {
        Cart cart = cartService.updateItem("default-cart", productId, request.quantity());
        return ResponseEntity.ok(CartDto.from(cart, cartService));
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<CartDto> removeItem(@PathVariable String productId) {
        Cart cart = cartService.removeItem("default-cart", productId);
        return ResponseEntity.ok(CartDto.from(cart, cartService));
    }

    @PostMapping("/discount")
    public ResponseEntity<CartDto> applyDiscount(@Valid @RequestBody ApplyDiscountRequest request) {
        Cart cart = cartService.applyDiscountCode("default-cart", request.discountCode());
        return ResponseEntity.ok(CartDto.from(cart, cartService));
    }

    @DeleteMapping
    public ResponseEntity<CartDto> clearCart() {
        Cart cart = cartService.clearCart("default-cart");
        return ResponseEntity.ok(CartDto.from(cart, cartService));
    }

    public record AddItemRequest(
            @NotBlank String productId,
            @Min(1) int quantity
    ) {}
    
    public record UpdateItemRequest(
            @Min(0) int quantity
    ) {}
    
    public record ApplyDiscountRequest(
            @NotBlank String discountCode
    ) {}
} 