package com.kata.carrefour.authservice.repository;

import com.kata.carrefour.authservice.model.Role;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface RoleRepository extends ReactiveCrudRepository<Role, Long> {
    Mono<Role> findByName(String name);
}
