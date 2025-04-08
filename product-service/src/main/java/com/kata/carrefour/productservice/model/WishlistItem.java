package com.kata.carrefour.productservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("wishlist_items")
@Data
public class WishlistItem {
    @Id
    private Long id;

    private String userEmail;
    private Long productId;
}
