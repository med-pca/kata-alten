package com.kata.carrefour.productservice;

import com.kata.carrefour.productservice.model.Product;
import com.kata.carrefour.productservice.model.WishlistItem;
import com.kata.carrefour.productservice.repository.WishlistItemRepository;
import com.kata.carrefour.productservice.service.ProductService;
import com.kata.carrefour.productservice.service.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class WishlistServiceTest {

    private WishlistItemRepository wishlistItemRepository;
    private ProductService productService;
    private WishlistService wishlistService;

    @BeforeEach
    void setUp() {
        wishlistItemRepository = Mockito.mock(WishlistItemRepository.class);
        productService = Mockito.mock(ProductService.class);
        wishlistService = new WishlistService(wishlistItemRepository, productService);
    }

    @Test
    void shouldAddToWishlistWhenProductExists() {
        WishlistItem item = new WishlistItem();
        item.setProductId(1L);

        Product product = new Product();
        product.setId(1L);

        when(productService.getProductById(1L)).thenReturn(Mono.just(product));
        when(wishlistItemRepository.save(any(WishlistItem.class))).thenReturn(Mono.just(item));

        StepVerifier.create(wishlistService.addToWishlist("user@example.com", item))
                .expectNextMatches(saved -> saved.getProductId().equals(1L))
                .verifyComplete();
    }

    @Test
    void shouldFailToAddToWishlistIfProductDoesNotExist() {
        WishlistItem item = new WishlistItem();
        item.setProductId(99L);

        when(productService.getProductById(99L)).thenReturn(Mono.empty());

        StepVerifier.create(wishlistService.addToWishlist("user@example.com", item))
                .expectErrorMatches(e -> e instanceof IllegalArgumentException &&
                        e.getMessage().contains("Product not found with ID"))
                .verify();
    }

    @Test
    void shouldGetWishlistItems() {
        WishlistItem item = new WishlistItem();
        item.setProductId(1L);
        item.setUserEmail("user@example.com");

        when(wishlistItemRepository.findAllByUserEmail("user@example.com")).thenReturn(Flux.just(item));

        StepVerifier.create(wishlistService.getWishlist("user@example.com"))
                .expectNext(item)
                .verifyComplete();
    }

    @Test
    void shouldRemoveFromWishlist() {
        WishlistItem item = new WishlistItem();
        item.setProductId(1L);
        item.setUserEmail("user@example.com");

        when(wishlistItemRepository.findAllByUserEmail("user@example.com")).thenReturn(Flux.just(item));
        when(wishlistItemRepository.delete(item)).thenReturn(Mono.empty());

        StepVerifier.create(wishlistService.removeFromWishlist("user@example.com", 1L))
                .verifyComplete();
    }

    @Test
    void shouldGetWishlistDetails() {
        WishlistItem item = new WishlistItem();
        item.setProductId(1L);
        item.setUserEmail("user@example.com");

        Product product = new Product();
        product.setId(1L);
        product.setName("Wishlist Product");

        when(wishlistItemRepository.findAllByUserEmail("user@example.com")).thenReturn(Flux.just(item));
        when(productService.getProductById(1L)).thenReturn(Mono.just(product));

        StepVerifier.create(wishlistService.getWishlistDetails("user@example.com"))
                .expectNextMatches(dto -> dto.productId().equals(1L) && dto.product().getName().equals("Wishlist Product"))
                .verifyComplete();
    }
}
