package com.kata.carrefour.productservice.repository;

import com.kata.carrefour.productservice.model.CartItem;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CartItemRepository extends ReactiveCrudRepository<CartItem, Long> {
    Flux<CartItem> findAllByUserEmail(String userEmail);
}
