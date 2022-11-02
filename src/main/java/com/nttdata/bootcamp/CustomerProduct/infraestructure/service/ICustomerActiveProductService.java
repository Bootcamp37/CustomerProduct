package com.nttdata.bootcamp.CustomerProduct.infraestructure.service;

import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerActiveProductRequest;
import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerActiveProductResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomerActiveProductService {
    Flux<CustomerActiveProductResponse> getAll();

    Mono<CustomerActiveProductResponse> getById(String id);

    Mono<CustomerActiveProductResponse> save(Mono<CustomerActiveProductRequest> request);

    Mono<CustomerActiveProductResponse> update(Mono<CustomerActiveProductRequest> request, String id);

    Mono<CustomerActiveProductResponse> delete(String id);
}
