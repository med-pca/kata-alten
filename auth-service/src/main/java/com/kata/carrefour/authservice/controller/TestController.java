package com.kata.carrefour.authservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping // 🔥 NE PAS METTRE `@RequestMapping("/private")` pour éviter un double préfixe
public class TestController {

    @GetMapping("/private")
    public Map<String, String> privateEndpoint() {
        return Map.of("message", "Bienvenue sur une route sécurisée !");
    }

    @GetMapping("/public/hello")
    public Map<String, String> publicEndpoint() {
        return Map.of("message", "Bienvenue sur l'API publique !");
    }
}
