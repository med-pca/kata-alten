package com.kata.carrefour.productservice.service;

import com.kata.carrefour.productservice.dto.CartItemDTO;
import com.kata.carrefour.productservice.model.CartItem;
import com.kata.carrefour.productservice.model.Product;
import com.kata.carrefour.productservice.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductService productService;

    public Mono<CartItem> addToCart(String userEmail, CartItem item) {
        item.setUserEmail(userEmail);
        return cartItemRepository.save(item);
    }

    public Flux<CartItem> getCartItems(String userEmail) {
        return cartItemRepository.findAllByUserEmail(userEmail);
    }

    public Mono<Void> removeFromCart(String userEmail, Long productId) {
        return cartItemRepository.findAllByUserEmail(userEmail)
                .filter(item -> item.getProductId().equals(productId))
                .flatMap(cartItemRepository::delete)
                .then();
    }

    public Flux<CartItemDTO> getCartDetails(String userEmail) {
        return cartItemRepository.findAllByUserEmail(userEmail)
                .flatMap(item -> productService.getProductById(item.getProductId())
                        .map(product -> new CartItemDTO(item.getProductId(), item.getQuantity(), product))
                );
    }
}
