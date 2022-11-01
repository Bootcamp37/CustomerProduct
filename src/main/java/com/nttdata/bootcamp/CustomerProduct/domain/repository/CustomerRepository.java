package com.nttdata.bootcamp.CustomerProduct.domain.repository;

import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class CustomerRepository {
    public Mono<CustomerResponse> getById(@NotNull String idCustomer) {
        String urlCustomer = "http://localhost:8080";
        WebClient webClientCustomer = WebClient.builder().baseUrl(urlCustomer).build();
        return webClientCustomer.get()
                .uri("/api/v1/customers/{id}", idCustomer)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(CustomerResponse.class);
    }
}