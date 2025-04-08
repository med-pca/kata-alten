package com.kata.carrefour.productservice.service;

import com.kata.carrefour.productservice.model.Product;
import com.kata.carrefour.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Mono<Product> createProduct(Product product) {

         return productRepository.save(product);
    }

    public Flux<Product> createProductsBatch(List<Product> products) {
        return productRepository.saveAll(products);
    }

    public Mono<Product> getProductById(Long id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")));
    }

    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }


    public Mono<Product> updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id)
                .flatMap(existingProduct -> {
                    existingProduct.setCode(updatedProduct.getCode());
                    existingProduct.setName(updatedProduct.getName());
                    existingProduct.setDescription(updatedProduct.getDescription());
                    existingProduct.setImage(updatedProduct.getImage());
                    existingProduct.setCategory(updatedProduct.getCategory());
                    existingProduct.setPrice(updatedProduct.getPrice());
                    existingProduct.setQuantity(updatedProduct.getQuantity());
                    existingProduct.setInternalReference(updatedProduct.getInternalReference());
                    existingProduct.setShellId(updatedProduct.getShellId());
                    existingProduct.setInventoryStatus(updatedProduct.getInventoryStatus());
                    existingProduct.setRating(updatedProduct.getRating());
                    existingProduct.setUpdatedAt(System.currentTimeMillis()); // mise à jour du timestamp
                    return productRepository.save(existingProduct);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")));
    }


    public Mono<Void> deleteProduct(Long id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")))
                .flatMap(product -> productRepository.deleteById(id));
    }
}
