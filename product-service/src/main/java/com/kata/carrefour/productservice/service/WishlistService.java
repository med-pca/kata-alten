package com.kata.carrefour.productservice.service;


import com.kata.carrefour.productservice.dto.WishlistItemDTO;
import com.kata.carrefour.productservice.model.WishlistItem;
import com.kata.carrefour.productservice.repository.WishlistItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistItemRepository wishlistItemRepository;
    private final ProductService productService;

    public Mono<WishlistItem> addToWishlist(String userEmail, WishlistItem item) {
        return productService.getProductById(item.getProductId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product not found with ID: " + item.getProductId())))
                .flatMap(product -> {
                    item.setUserEmail(userEmail);
                    return wishlistItemRepository.save(item);
                });
    }

    public Flux<WishlistItem> getWishlist(String userEmail) {
        return wishlistItemRepository.findAllByUserEmail(userEmail);
    }

    public Mono<Void> removeFromWishlist(String userEmail, Long productId) {
        return wishlistItemRepository.findAllByUserEmail(userEmail)
                .filter(item -> item.getProductId().equals(productId))
                .flatMap(wishlistItemRepository::delete)
                .then();
    }

    public Flux<WishlistItemDTO> getWishlistDetails(String userEmail) {
        return wishlistItemRepository.findAllByUserEmail(userEmail)
                .flatMap(item -> productService.getProductById(item.getProductId())
                        .map(product -> new WishlistItemDTO(item.getProductId(), product))
                );
    }

}
