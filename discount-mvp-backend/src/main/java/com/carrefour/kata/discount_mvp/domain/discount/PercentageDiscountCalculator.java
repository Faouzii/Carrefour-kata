package com.carrefour.kata.discount_mvp.domain.discount;

import com.carrefour.kata.discount_mvp.domain.CartItem;
import com.carrefour.kata.discount_mvp.domain.DiscountCode;
import com.carrefour.kata.discount_mvp.domain.DiscountType;
import java.math.BigDecimal;

public class PercentageDiscountCalculator implements DiscountCalculator {
    @Override
    public boolean supports(DiscountCode discountCode) {
        return discountCode.getType() == DiscountType.PERCENTAGE;
    }

    @Override
    public BigDecimal calculateDiscount(CartItem item, DiscountCode discountCode) {
        return item.product().price()
                .multiply(discountCode.getValue())
                .divide(BigDecimal.valueOf(100));
    }
} 