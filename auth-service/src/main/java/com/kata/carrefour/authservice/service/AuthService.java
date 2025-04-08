package com.kata.carrefour.authservice.service;

import com.kata.carrefour.authservice.dto.AuthAccountRequest;
import com.kata.carrefour.authservice.dto.AuthRequest;
import com.kata.carrefour.authservice.model.User;
import com.kata.carrefour.authservice.repository.UserRepository;
import com.kata.carrefour.authservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Mono<Object> registerAccount(AuthAccountRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .flatMap(existingUser -> Mono.error(new RuntimeException("User already exists")))
                .switchIfEmpty(Mono.defer(() -> {
                    User newUser = new User(request.getUsername(),request.getFirstname(),request.getEmail(), passwordEncoder.encode(request.getPassword()));
                    return userRepository.save(newUser).map(savedUser -> jwtUtil.generateToken(savedUser.getEmail()));
                }));
    }

    public Mono<String> login(AuthRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .flatMap(user -> passwordEncoder.matches(request.getPassword(), user.getPassword())
                        ? Mono.just(jwtUtil.generateToken(user.getEmail()))
                        : Mono.error(new RuntimeException("Invalid credentials"))
                );
    }
}
