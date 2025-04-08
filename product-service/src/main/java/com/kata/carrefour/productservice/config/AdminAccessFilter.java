package com.kata.carrefour.productservice.config;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Order(1)
public class AdminAccessFilter implements WebFilter {

    private static final String ADMIN_EMAIL = "admin@admin.com";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        HttpMethod method = exchange.getRequest().getMethod();

        boolean isSensitiveRoute =
                path.startsWith("/api/products") &&
                        (HttpMethod.POST.equals(method) ||
                                HttpMethod.PATCH.equals(method) ||
                                HttpMethod.DELETE.equals(method));

        if (!isSensitiveRoute) {
            return chain.filter(exchange);
        }

        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> (Jwt) ctx.getAuthentication().getPrincipal())
                .map(jwt -> jwt.getClaimAsString("sub")) // ou "email" si c’est le claim utilisé
                .flatMap(email -> {
                    if (ADMIN_EMAIL.equalsIgnoreCase(email)) {
                        return chain.filter(exchange);
                    } else {
                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                        return exchange.getResponse().setComplete();
                    }
                });
    }
}

