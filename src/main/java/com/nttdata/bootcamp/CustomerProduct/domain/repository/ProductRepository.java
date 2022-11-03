package com.nttdata.bootcamp.CustomerProduct.domain.repository;

import com.nttdata.bootcamp.CustomerProduct.domain.dto.ProductResponse;
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

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductRepository {
    public static final String PRODUCT_SERVICE = "ms-product";
    @Value("${message.path-productDomain}")
    public String urlProduct;
    @Value("${message.path-productResponse}")
    public String path;
    @Autowired
    ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory;

    public Mono<ProductResponse> getById(@NotNull String idProduct) {
        log.debug("====> ProductRepository: GetById");
        log.debug("====> Llamada: " + urlProduct + path + idProduct);
        WebClient webClientProduct = WebClient.builder().baseUrl(urlProduct).build();
        return webClientProduct.get()
                .uri(path + "{id}", idProduct)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new Exception("Error 400")))
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new Exception("Error 500")))
                .bodyToMono(ProductResponse.class)
                .transform(it -> reactiveCircuitBreakerFactory.create(PRODUCT_SERVICE)
                        .run(it, throwable -> Mono.just(new ProductResponse()))
                );
    }
}