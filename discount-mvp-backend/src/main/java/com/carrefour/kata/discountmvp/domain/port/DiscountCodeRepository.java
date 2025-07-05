package com.carrefour.kata.discountmvp.domain.port;

import com.carrefour.kata.discountmvp.domain.DiscountCode;
import java.util.List;
import java.util.Optional;

public interface DiscountCodeRepository {
    Optional<DiscountCode> findByCode(String code);
    List<DiscountCode> findAll();
} 