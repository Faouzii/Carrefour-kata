package com.carrefour.kata.discount_mvp.application;

import com.carrefour.kata.discount_mvp.domain.*;
import com.carrefour.kata.discount_mvp.domain.discount.DiscountCalculator;
import com.carrefour.kata.discount_mvp.domain.port.CartRepository;
import com.carrefour.kata.discount_mvp.domain.port.DiscountCodeRepository;
import com.carrefour.kata.discount_mvp.domain.port.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final DiscountCodeRepository discountCodeRepository;
    private final List<DiscountCalculator> discountCalculators;

    public CartService(CartRepository cartRepository,
                      ProductRepository productRepository,
                      DiscountCodeRepository discountCodeRepository,
                      List<DiscountCalculator> discountCalculators) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.discountCodeRepository = discountCodeRepository;
        this.discountCalculators = discountCalculators;
    }

    public Cart addItem(String cartId, String productId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseGet(() -> new Cart(cartId, new ArrayList<>(), null));
        Product product = productRepository.findById(productId).orElseThrow();
        List<CartItem> items = new ArrayList<>(cart.items());
        Optional<CartItem> existing = items.stream().filter(i -> i.product().id().equals(productId)).findFirst();
        if (existing.isPresent()) {
            items.remove(existing.get());
            items.add(new CartItem(product, existing.get().quantity() + quantity));
        } else {
            items.add(new CartItem(product, quantity));
        }
        Cart updated = new Cart(cart.id(), items, cart.getAppliedDiscountCode().orElse(null));
        return cartRepository.save(updated);
    }

    public Cart applyDiscountCode(String cartId, String code) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        DiscountCode discountCode = discountCodeRepository.findByCode(code).orElseThrow();
        if (discountCode.getExpirationDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Discount code expired");
        }
        boolean applicable = cart.items().stream()
                .anyMatch(item -> discountCode.getApplicableProductIds().contains(item.product().id()));
        if (!applicable) {
            throw new IllegalArgumentException("Discount code not applicable to any product in cart");
        }
        Cart updated = new Cart(cart.id(), cart.items(), discountCode);
        return cartRepository.save(updated);
    }

    public Cart getCart(String cartId) {
        return cartRepository.findById(cartId).orElseThrow();
    }

    public BigDecimal calculateTotal(String cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cart.items()) {
            BigDecimal itemTotal = item.product().price().multiply(BigDecimal.valueOf(item.quantity()));
            if (cart.getAppliedDiscountCode().isPresent() &&
                cart.getAppliedDiscountCode().get().getApplicableProductIds().contains(item.product().id())) {
                DiscountCode discountCode = cart.getAppliedDiscountCode().get();
                DiscountCalculator calculator = discountCalculators.stream()
                        .filter(c -> c.supports(discountCode))
                        .findFirst()
                        .orElseThrow();
                BigDecimal discount = calculator.calculateDiscount(item, discountCode).multiply(BigDecimal.valueOf(item.quantity()));
                itemTotal = itemTotal.subtract(discount);
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
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        List<CartItem> items = cart.items().stream()
                .filter(item -> !item.product().id().equals(productId))
                .collect(Collectors.toList());
        Cart updatedCart = new Cart(cart.id(), items, cart.getAppliedDiscountCode().orElse(null));
        return cartRepository.save(updatedCart);
    }

    public Cart clearCart(String cartId) {
        Cart emptyCart = new Cart(cartId, new ArrayList<>(), null);
        return cartRepository.save(emptyCart);
    }
} 