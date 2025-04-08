package com.kata.carrefour.productservice.repository;


import com.kata.carrefour.productservice.model.WishlistItem;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface WishlistItemRepository extends ReactiveCrudRepository<WishlistItem, Long> {
    Flux<WishlistItem> findAllByUserEmail(String userEmail);
}

