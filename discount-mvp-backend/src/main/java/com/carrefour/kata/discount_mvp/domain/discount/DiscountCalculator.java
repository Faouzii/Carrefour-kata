package com.carrefour.kata.discount_mvp.domain.discount;

import com.carrefour.kata.discount_mvp.domain.CartItem;
import com.carrefour.kata.discount_mvp.domain.DiscountCode;
import java.math.BigDecimal;

public interface DiscountCalculator {
    boolean supports(DiscountCode discountCode);
    BigDecimal calculateDiscount(CartItem item, DiscountCode discountCode);
} 