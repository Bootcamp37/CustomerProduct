package com.nttdata.bootcamp.CustomerProduct.domain.repository;

import com.nttdata.bootcamp.CustomerProduct.domain.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ProductRepository {
    public Mono<ProductResponse> getById(@NotNull String idProduct) {
        String urlProduct = "http://localhost:8081";
        WebClient webClientProduct = WebClient.builder().baseUrl(urlProduct).build();
        return webClientProduct.get()
                .uri("/api/v1/products/{id}", idProduct)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ProductResponse.class);
    }
}