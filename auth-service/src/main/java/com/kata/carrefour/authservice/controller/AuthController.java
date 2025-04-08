package com.kata.carrefour.authservice.controller;

import com.kata.carrefour.authservice.dto.AuthAccountRequest;
import com.kata.carrefour.authservice.dto.AuthRequest;
import com.kata.carrefour.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public Mono<Object> register(@RequestBody AuthRequest request) {
        return authService.register(request);
    }

    @PostMapping("/account")
    public Mono<Object> account(@RequestBody AuthAccountRequest request) {
        return authService.registerAccount(request);
    }

    @PostMapping("/token")
    public Mono<String> loginToken(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @PostMapping("/login")
    public Mono<String> login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }
}
