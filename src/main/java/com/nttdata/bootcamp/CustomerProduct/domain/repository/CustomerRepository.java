package com.nttdata.bootcamp.CustomerProduct.domain.repository;

import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Repository
@Slf4j
public class CustomerRepository {
    public static final String CUSTOMER_SERVICE = "ms-customer";
    @Value("${message.path-customerDomain}")
    public String urlCustomer;
    @Value("${message.path-customerResponse}")
    public String path;
    @Autowired
    ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory;

    public Mono<CustomerResponse> getById(@NotNull String idCustomer) {
        log.info("Ingreso a GetById");
        log.info("Llamada = "+urlCustomer+path+idCustomer);
        WebClient webClientCustomer = WebClient.builder().baseUrl(urlCustomer).build();
        return webClientCustomer.get()
                .uri(path + "{id}", idCustomer)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new Exception("Error 400")))
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new Exception("Error 500")))
                .bodyToMono(CustomerResponse.class)
                .transform(it -> reactiveCircuitBreakerFactory.create(CUSTOMER_SERVICE)
                        .run(it, throwable -> Mono.just(new CustomerResponse()))
                );
    }
}