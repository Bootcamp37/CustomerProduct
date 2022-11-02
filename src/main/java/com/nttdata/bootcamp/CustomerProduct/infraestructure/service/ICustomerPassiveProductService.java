package com.nttdata.bootcamp.CustomerProduct.infraestructure.service;

import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerPassiveProductRequest;
import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerPassiveProductResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomerPassiveProductService {
    Flux<CustomerPassiveProductResponse> getAll();

    Mono<CustomerPassiveProductResponse> getById(String id);

    Mono<CustomerPassiveProductResponse> save(Mono<CustomerPassiveProductRequest> request);

    Mono<CustomerPassiveProductResponse> update(Mono<CustomerPassiveProductRequest> request, String id);

    Mono<CustomerPassiveProductResponse> delete(String id);
}
