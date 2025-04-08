package com.kata.carrefour.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthAccountRequest {
    private String username;
    private String firstname;
    private String email;
    private String password;
}

