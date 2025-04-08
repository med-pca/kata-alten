package com.kata.carrefour.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class User {

    @Id
    private Long id;
    private String username;
    private String firstname;
    private String email;
    private String password;


    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String username, String firstname, String email, String password) {
        this.username = username;
        this.firstname = firstname;
        this.email = email;
        this.password = password;
    }
}
