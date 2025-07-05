package com.carrefour.kata.discountmvp.infrastructure.persistence.entity;

import com.carrefour.kata.discountmvp.domain.DiscountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "discount_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCodeEntity {
    
    @Id
    private String code;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20)")
    private DiscountType type;
    
    @Column(name = "discount_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal value;
    
    @ElementCollection
    @CollectionTable(name = "discount_code_eligible_products", 
                    joinColumns = @JoinColumn(name = "discount_code"))
    @Column(name = "product_id")
    private Set<String> eligibleProductIds;
    
    @Column(nullable = false)
    private LocalDate expirationDate;
} 