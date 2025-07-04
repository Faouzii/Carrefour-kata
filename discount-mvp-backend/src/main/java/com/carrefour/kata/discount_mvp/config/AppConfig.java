package com.carrefour.kata.discount_mvp.config;

import com.carrefour.kata.discount_mvp.application.CartService;
import com.carrefour.kata.discount_mvp.domain.discount.DiscountCalculator;
import com.carrefour.kata.discount_mvp.domain.discount.FixedAmountDiscountCalculator;
import com.carrefour.kata.discount_mvp.domain.discount.PercentageDiscountCalculator;
import com.carrefour.kata.discount_mvp.domain.port.CartRepository;
import com.carrefour.kata.discount_mvp.domain.port.DiscountCodeRepository;
import com.carrefour.kata.discount_mvp.domain.port.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

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