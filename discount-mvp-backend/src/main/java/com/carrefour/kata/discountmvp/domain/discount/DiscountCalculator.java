package com.carrefour.kata.discountmvp.domain.discount;

import com.carrefour.kata.discountmvp.domain.CartItem;
import com.carrefour.kata.discountmvp.domain.DiscountCode;
import java.math.BigDecimal;

public interface DiscountCalculator {
    boolean supports(DiscountCode discountCode);
    BigDecimal calculateDiscount(CartItem item, DiscountCode discountCode);
} 