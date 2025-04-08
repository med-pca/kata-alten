package com.kata.carrefour.productservice.repository;

import com.kata.carrefour.productservice.model.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {

}
