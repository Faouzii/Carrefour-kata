package com.carrefour.kata.discountmvp.domain;

import lombok.Value;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Value
public class DiscountCode {
    String code;
    DiscountType type;
    BigDecimal value;
    Set<String> applicableProductIds;
    LocalDate expirationDate;
} 