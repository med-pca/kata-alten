package com.kata.carrefour.productservice.controller;

import com.kata.carrefour.productservice.model.CartItem;
import com.kata.carrefour.productservice.dto.CartItemDTO;
import com.kata.carrefour.productservice.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public Mono<CartItem> addToCart(@RequestBody CartItem item, @AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("sub");
        return cartService.addToCart(email, item);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public Flux<CartItem> getCart(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("sub");
        return cartService.getCartItems(email);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("isAuthenticated()")
    public Mono<Void> removeFromCart(@PathVariable Long productId, @AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("sub");
        return cartService.removeFromCart(email, productId);
    }


    @GetMapping("/details")
    @PreAuthorize("isAuthenticated()")
    public Flux<CartItemDTO> getCartDetails(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("sub");
        return cartService.getCartDetails(email);
    }
}
