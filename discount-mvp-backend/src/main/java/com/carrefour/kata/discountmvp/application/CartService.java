package com.carrefour.kata.discountmvp.application;

import com.carrefour.kata.discountmvp.domain.*;
import com.carrefour.kata.discountmvp.domain.discount.DiscountCalculator;
import com.carrefour.kata.discountmvp.domain.exception.*;
import com.carrefour.kata.discountmvp.domain.port.CartRepository;
import com.carrefour.kata.discountmvp.domain.port.DiscountCodeRepository;
import com.carrefour.kata.discountmvp.domain.port.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final DiscountCodeRepository discountCodeRepository;
    private final List<DiscountCalculator> discountCalculators;

    public Cart addItem(String cartId, String productId, int quantity) {
        log.info("Adding {} units of product {} to cart {}", quantity, productId, cartId);
        
        if (quantity <= 0) {
            throw new InvalidQuantityException(quantity);
        }
        
        var cart = cartRepository.findById(cartId).orElseGet(() -> new Cart(cartId, new ArrayList<>(), null));
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        var items = new ArrayList<>(cart.items());
        var existing = items.stream().filter(i -> i.product().id().equals(productId)).findFirst();
        if (existing.isPresent()) {
            items.remove(existing.get());
            items.add(new CartItem(product, existing.get().quantity() + quantity));
            log.debug("Updated existing item quantity for product {} in cart {}", productId, cartId);
        } else {
            items.add(new CartItem(product, quantity));
            log.debug("Added new item for product {} to cart {}", productId, cartId);
        }
        var updated = new Cart(cart.id(), items, cart.getAppliedDiscountCode().orElse(null));
        return cartRepository.save(updated);
    }

    public Cart applyDiscountCode(String cartId, String code) {
        log.info("Applying discount code {} to cart {}", code, cartId);
        var cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(cartId));
        var discountCode = discountCodeRepository.findByCode(code)
                .orElseThrow(() -> new DiscountCodeNotFoundException(code));
        if (discountCode.getExpirationDate().isBefore(LocalDate.now())) {
            log.warn("Attempted to use expired discount code {} for cart {}", code, cartId);
            throw new DiscountCodeExpiredException(code);
        }
        var applicable = cart.items().stream()
                .anyMatch(item -> discountCode.getEligibleProductIds().contains(item.product().id()));
        if (!applicable) {
            log.warn("Discount code {} not applicable to any product in cart {}", code, cartId);
            throw new DiscountCodeNotApplicableException(code);
        }
        log.info("Successfully applied discount code {} to cart {}", code, cartId);
        var updated = new Cart(cart.id(), cart.items(), discountCode);
        return cartRepository.save(updated);
    }

    public Cart getCart(String cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(cartId));
    }

    public BigDecimal calculateTotal(String cartId) {
        var cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(cartId));
        var total = BigDecimal.ZERO;
        var discountCodeOpt = cart.getAppliedDiscountCode();
        
        for (var item : cart.items()) {
            var itemTotal = item.product().price().multiply(BigDecimal.valueOf(item.quantity()));
            
            if (discountCodeOpt.isPresent()) {
                var discountCode = discountCodeOpt.get();
                if (discountCode.getEligibleProductIds().contains(item.product().id())) {
                    var calculator = discountCalculators.stream()
                            .filter(c -> c.supports(discountCode))
                            .findFirst()
                            .orElseThrow(() -> new NoDiscountCalculatorException(discountCode.getType()));
                    var discount = calculator.calculateDiscount(item, discountCode).multiply(BigDecimal.valueOf(item.quantity()));
                    itemTotal = itemTotal.subtract(discount);
                }
            }
            total = total.add(itemTotal);
        }
        return total;
    }

    public Cart updateItem(String cartId, String productId, int quantity) {
        if (quantity <= 0) {
            return removeItem(cartId, productId);
        }
        return addItem(cartId, productId, quantity);
    }

    public Cart removeItem(String cartId, String productId) {
        var cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(cartId));
        var items = cart.items().stream()
                .filter(item -> !item.product().id().equals(productId))
                .collect(Collectors.toList());
        var updatedCart = new Cart(cart.id(), items, cart.getAppliedDiscountCode().orElse(null));
        return cartRepository.save(updatedCart);
    }

    public Cart clearCart(String cartId) {
        var emptyCart = new Cart(cartId, new ArrayList<>(), null);
        return cartRepository.save(emptyCart);
    }
} 