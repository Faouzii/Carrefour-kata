package com.carrefour.kata.discountmvp.infrastructure.repository;

import com.carrefour.kata.discountmvp.domain.DiscountCode;
import com.carrefour.kata.discountmvp.domain.DiscountType;
import com.carrefour.kata.discountmvp.domain.port.DiscountCodeRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Repository
@ConditionalOnProperty(name = "app.database.type", havingValue = "memory", matchIfMissing = true)
public class InMemoryDiscountCodeRepository implements DiscountCodeRepository {
    private final Map<String, DiscountCode> codes = new HashMap<>();

    public InMemoryDiscountCodeRepository() {
        codes.put("PERC10", new DiscountCode(
                "PERC10",
                DiscountType.PERCENTAGE,
                BigDecimal.valueOf(10),
                Set.of("1", "2", "3", "4", "5"),
                LocalDate.now().plusDays(30)));
        codes.put("FIXED5", new DiscountCode(
                "FIXED5",
                DiscountType.FIXED_AMOUNT,
                BigDecimal.valueOf(0.5),
                Set.of("1", "2", "3", "4", "5"),
                LocalDate.now().plusDays(15)));
    }

    @Override
    public Optional<DiscountCode> findByCode(String code) {
        return Optional.ofNullable(codes.get(code));
    }

    @Override
    public List<DiscountCode> findAll() {
        return new ArrayList<>(codes.values());
    }
} 