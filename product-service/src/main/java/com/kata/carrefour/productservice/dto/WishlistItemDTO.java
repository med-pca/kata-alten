package com.kata.carrefour.productservice.dto;

import com.kata.carrefour.productservice.model.Product;

public record WishlistItemDTO(
        Long productId,
        Product product
) {}

