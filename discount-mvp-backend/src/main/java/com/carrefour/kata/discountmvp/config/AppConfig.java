package com.carrefour.kata.discountmvp.config;

import com.carrefour.kata.discountmvp.application.CartService;
import com.carrefour.kata.discountmvp.domain.discount.DiscountCalculator;
import com.carrefour.kata.discountmvp.domain.discount.FixedAmountDiscountCalculator;
import com.carrefour.kata.discountmvp.domain.discount.PercentageDiscountCalculator;
import com.carrefour.kata.discountmvp.domain.port.CartRepository;
import com.carrefour.kata.discountmvp.domain.port.DiscountCodeRepository;
import com.carrefour.kata.discountmvp.domain.port.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public DiscountCalculator percentageDiscountCalculator() {
        return new PercentageDiscountCalculator();
    }

    @Bean
    public DiscountCalculator fixedAmountDiscountCalculator() {
        return new FixedAmountDiscountCalculator();
    }

    @Bean
    public CartService cartService(CartRepository cartRepository,
                                   ProductRepository productRepository,
                                   DiscountCodeRepository discountCodeRepository,
                                   List<DiscountCalculator> discountCalculators) {
        return new CartService(cartRepository, productRepository, discountCodeRepository, discountCalculators);
    }
} 