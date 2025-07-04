package com.carrefour.kata.discount_mvp.domain;

import java.util.List;
import java.util.Optional;

public record Cart(String id, List<CartItem> items, DiscountCode appliedDiscountCode) {
    public Optional<DiscountCode> getAppliedDiscountCode() {
        return Optional.ofNullable(appliedDiscountCode);
    }
} 