package com.kata.carrefour.productservice.controller;

import com.kata.carrefour.productservice.config.AdminOnly;
import com.kata.carrefour.productservice.model.Product;
import com.kata.carrefour.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private static final String ADMIN_EMAIL = "admin@admin.com";

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @AdminOnly
    public Mono<Product> createProduct(@RequestBody Product product,
                                 @AuthenticationPrincipal Jwt jwt) {
        return productService.createProduct(product);
    }

    @PostMapping("/batch")
    @PreAuthorize("isAuthenticated()")
    @AdminOnly
    public Flux<Product> createProductBatch(@RequestBody List<Product> products,
                                            @AuthenticationPrincipal Jwt jwt) {
        return productService.createProductsBatch(products);
    }



    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public Flux<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Mono<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @AdminOnly
    public Mono<Product> updateProduct(@PathVariable Long id,
                                 @RequestBody Product updatedProduct,
                                 @AuthenticationPrincipal Jwt jwt) {
        return productService.updateProduct(id, updatedProduct);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Mono<?> deleteProduct(@PathVariable Long id,
                                 @AuthenticationPrincipal Jwt jwt) {
        if (!isAdmin(jwt)) {
            return Mono.just("Access denied: only admin can delete products.");
        }
        return productService.deleteProduct(id);
    }

    private boolean isAdmin(Jwt jwt) {
        String email = jwt.getClaimAsString("sub"); // ou "email" selon ton token
        return ADMIN_EMAIL.equalsIgnoreCase(email);
    }
}
