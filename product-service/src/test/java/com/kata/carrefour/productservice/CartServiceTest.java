package com.kata.carrefour.productservice;

import com.kata.carrefour.productservice.model.CartItem;
import com.kata.carrefour.productservice.model.Product;
import com.kata.carrefour.productservice.repository.CartItemRepository;
import com.kata.carrefour.productservice.service.CartService;
import com.kata.carrefour.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CartServiceTest {

    private CartItemRepository cartItemRepository;
    private ProductService productService;
    private CartService cartService;

    @BeforeEach
    void setUp() {
        cartItemRepository = Mockito.mock(CartItemRepository.class);
        productService = Mockito.mock(ProductService.class);
        cartService = new CartService(cartItemRepository, productService);
    }

    @Test
    void shouldAddToCart() {
        CartItem item = new CartItem();
        item.setProductId(1L);
        item.setQuantity(2);

        when(cartItemRepository.save(any(CartItem.class))).thenReturn(Mono.just(item));

        StepVerifier.create(cartService.addToCart("user@example.com", item))
                .expectNextMatches(saved -> saved.getProductId().equals(1L))
                .verifyComplete();
    }

    @Test
    void shouldGetCartItems() {
        CartItem item1 = new CartItem();
        item1.setProductId(1L);
        item1.setUserEmail("user@example.com");

        when(cartItemRepository.findAllByUserEmail("user@example.com")).thenReturn(Flux.just(item1));

        StepVerifier.create(cartService.getCartItems("user@example.com"))
                .expectNext(item1)
                .verifyComplete();
    }

    @Test
    void shouldRemoveFromCart() {
        CartItem item = new CartItem();
        item.setProductId(1L);
        item.setUserEmail("user@example.com");

        when(cartItemRepository.findAllByUserEmail("user@example.com")).thenReturn(Flux.just(item));
        when(cartItemRepository.delete(item)).thenReturn(Mono.empty());

        StepVerifier.create(cartService.removeFromCart("user@example.com", 1L))
                .verifyComplete();
    }

    @Test
    void shouldGetCartDetails() {
        CartItem item = new CartItem();
        item.setProductId(1L);
        item.setQuantity(2);
        item.setUserEmail("user@example.com");

        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        when(cartItemRepository.findAllByUserEmail("user@example.com")).thenReturn(Flux.just(item));
        when(productService.getProductById(1L)).thenReturn(Mono.just(product));

        StepVerifier.create(cartService.getCartDetails("user@example.com"))
                .expectNextMatches(dto -> dto.productId().equals(1L) && dto.product().getName().equals("Test Product"))
                .verifyComplete();
    }
}
