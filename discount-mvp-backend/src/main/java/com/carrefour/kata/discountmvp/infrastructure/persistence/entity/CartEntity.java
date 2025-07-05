package com.carrefour.kata.discountmvp.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartEntity {
    
    @Id
    private String id;
    
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemEntity> items;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applied_discount_code")
    private DiscountCodeEntity appliedDiscountCode;
} 