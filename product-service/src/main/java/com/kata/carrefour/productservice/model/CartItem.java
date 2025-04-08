package com.kata.carrefour.productservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("cart_items")
@Data
public class CartItem {
    @Id
    private Long id;

    private String userEmail;
    private Long productId;
    private int quantity;
}
