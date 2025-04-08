package com.kata.carrefour.authservice.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("tokens")
public class Token {
    @Id
    private Long id;
    private String token;
    private boolean revoked;
    private Long userId;
}
