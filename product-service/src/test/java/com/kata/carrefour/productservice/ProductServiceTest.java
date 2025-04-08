package com.kata.carrefour.productservice;

import com.kata.carrefour.productservice.model.Product;
import com.kata.carrefour.productservice.repository.ProductRepository;
import com.kata.carrefour.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    void shouldCreateProduct() {
        Product product = new Product();
        product.setName("Test Product");

        when(productRepository.save(product)).thenReturn(Mono.just(product));

        StepVerifier.create(productService.createProduct(product))
                .expectNextMatches(p -> p.getName().equals("Test Product"))
                .verifyComplete();
    }

    @Test
    void shouldReturnAllProducts() {
        Product p1 = new Product(); p1.setId(1L);
        Product p2 = new Product(); p2.setId(2L);

        when(productRepository.findAll()).thenReturn(Flux.just(p1, p2));

        StepVerifier.create(productService.getAllProducts())
                .expectNext(p1)
                .expectNext(p2)
                .verifyComplete();
    }

    @Test
    void shouldUpdateExistingProduct() {
        Product existing = new Product();
        existing.setId(1L);
        existing.setName("Old");

        Product updated = new Product();
        updated.setName("New");

        when(productRepository.findById(1L)).thenReturn(Mono.just(existing));
        when(productRepository.save(any(Product.class))).thenReturn(Mono.just(updated));

        StepVerifier.create(productService.updateProduct(1L, updated))
                .expectNextMatches(p -> p.getName().equals("New"))
                .verifyComplete();
    }

    @Test
    void shouldDeleteProduct() {
        Product product = new Product();
        product.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Mono.just(product));
        when(productRepository.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(productService.deleteProduct(1L))
                .verifyComplete();
    }
}

