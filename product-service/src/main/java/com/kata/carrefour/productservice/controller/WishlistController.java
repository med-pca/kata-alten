package com.kata.carrefour.productservice.controller;


import com.kata.carrefour.productservice.dto.WishlistItemDTO;
import com.kata.carrefour.productservice.model.WishlistItem;
import com.kata.carrefour.productservice.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public Mono<WishlistItem> addToWishlist(@RequestBody WishlistItem item, @AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("sub");
        return wishlistService.addToWishlist(email, item);
    }


    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public Flux<WishlistItem> getWishlist(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("sub");
        return wishlistService.getWishlist(email);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("isAuthenticated()")
    public Mono<Void> removeFromWishlist(@PathVariable Long productId, @AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("sub");
        return wishlistService.removeFromWishlist(email, productId);
    }

    @GetMapping("/details")
    @PreAuthorize("isAuthenticated()")
    public Flux<WishlistItemDTO> getWishlistDetails(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("sub");
        return wishlistService.getWishlistDetails(email);
    }

}

