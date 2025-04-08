package com.kata.carrefour.productservice.dto;

import com.kata.carrefour.productservice.model.Product;

public record CartItemDTO(
        Long productId,
        int quantity,
        Product product
) {}

