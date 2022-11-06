package com.nttdata.bootcamp.customerproduct.infraestructure.service;

import com.nttdata.bootcamp.customerproduct.domain.dto.CustomerActiveProductRequest;
import com.nttdata.bootcamp.customerproduct.domain.dto.CustomerActiveProductResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Interfaz del tipo CustomerActiveProductService.
 *
 * @author Pedro Manuel Díaz Santa María
 * @version 1.0.0
 */
public interface ICustomerActiveProductService {
  Flux<CustomerActiveProductResponse> getAll();

  Mono<CustomerActiveProductResponse> getById(String id);

  Mono<CustomerActiveProductResponse> save(Mono<CustomerActiveProductRequest> request);

  Mono<CustomerActiveProductResponse> update(Mono<CustomerActiveProductRequest> request, String id);

  Mono<CustomerActiveProductResponse> delete(String id);
}
