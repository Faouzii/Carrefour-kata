package com.carrefour.kata.discountmvp.infrastructure.web;

import com.carrefour.kata.discountmvp.domain.Product;
import com.carrefour.kata.discountmvp.domain.port.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Product management endpoints")
public class ProductController {
    private final ProductRepository productRepository;

    @GetMapping
    @Operation(
            summary = "Get all products",
            description = "Retrieves a list of all available products in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved products",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        var products = productRepository.findAll();
        var productDtos = products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDtos);
    }

    private ProductDto toDto(Product product) {
        return new ProductDto(
                product.id(),
                product.name(),
                product.price(),
                product.description()
        );
    }

    @Schema(description = "Product data transfer object")
    public record ProductDto(
            @Schema(description = "Unique product identifier", example = "1")
            String id,
            @Schema(description = "Product name", example = "Fresh Apples")
            String name,
            @Schema(description = "Product price", example = "2.50")
            java.math.BigDecimal price,
            @Schema(description = "Product description", example = "Fresh, crisp apples from local orchards")
            String description
    ) {}
} 