package com.carrefour.kata.discountmvp.infrastructure.web;

import com.carrefour.kata.discountmvp.application.CartService;
import com.carrefour.kata.discountmvp.domain.Cart;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Tag(name = "Shopping Cart", description = "Shopping cart management endpoints")
public class CartController {
    private final CartService cartService;

    @GetMapping
    @Operation(
            summary = "Get cart",
            description = "Retrieves the current shopping cart with all items and applied discounts"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved cart",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CartDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<CartDto> getCart() {
        Cart cart = cartService.getCart("default-cart");
        return ResponseEntity.ok(CartDto.from(cart, cartService));
    }

    @PostMapping("/items")
    @Operation(
            summary = "Add item to cart",
            description = "Adds a product to the shopping cart with the specified quantity"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully added item to cart",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CartDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<CartDto> addItem(@Valid @RequestBody AddItemRequest request) {
        Cart cart = cartService.addItem("default-cart", request.productId(), request.quantity());
        return ResponseEntity.ok(CartDto.from(cart, cartService));
    }

    @PutMapping("/items/{productId}")
    @Operation(
            summary = "Update item quantity",
            description = "Updates the quantity of a specific product in the cart"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully updated item quantity",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CartDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found in cart"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<CartDto> updateItem(
            @Parameter(description = "Product ID to update", example = "1")
            @PathVariable String productId,
            @RequestBody UpdateItemRequest request
    ) {
        Cart cart = cartService.updateItem("default-cart", productId, request.quantity());
        return ResponseEntity.ok(CartDto.from(cart, cartService));
    }

    @DeleteMapping("/items/{productId}")
    @Operation(
            summary = "Remove item from cart",
            description = "Removes a specific product from the shopping cart"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully removed item from cart",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CartDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found in cart"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<CartDto> removeItem(
            @Parameter(description = "Product ID to remove", example = "1")
            @PathVariable String productId
    ) {
        Cart cart = cartService.removeItem("default-cart", productId);
        return ResponseEntity.ok(CartDto.from(cart, cartService));
    }

    @PostMapping("/discount")
    @Operation(
            summary = "Apply discount code",
            description = "Applies a discount code to the shopping cart"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully applied discount code",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CartDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid discount code"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Discount code not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<CartDto> applyDiscount(@Valid @RequestBody ApplyDiscountRequest request) {
        Cart cart = cartService.applyDiscountCode("default-cart", request.discountCode());
        return ResponseEntity.ok(CartDto.from(cart, cartService));
    }

    @DeleteMapping
    @Operation(
            summary = "Clear cart",
            description = "Removes all items from the shopping cart"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully cleared cart",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CartDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<CartDto> clearCart() {
        Cart cart = cartService.clearCart("default-cart");
        return ResponseEntity.ok(CartDto.from(cart, cartService));
    }

    @Schema(description = "Request to add an item to the cart")
    public record AddItemRequest(
            @Schema(description = "Product ID to add", example = "1")
            @NotBlank String productId,
            @Schema(description = "Quantity to add", example = "2", minimum = "1")
            @Min(1) int quantity
    ) {}
    
    @Schema(description = "Request to update item quantity")
    public record UpdateItemRequest(
            @Schema(description = "New quantity", example = "3", minimum = "0")
            @Min(0) int quantity
    ) {}
    
    @Schema(description = "Request to apply a discount code")
    public record ApplyDiscountRequest(
            @Schema(description = "Discount code to apply", example = "SAVE10")
            @NotBlank String discountCode
    ) {}
} 