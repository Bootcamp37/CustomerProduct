package com.nttdata.bootcamp.customerproduct.infraestructure.service;

import com.nttdata.bootcamp.customerproduct.domain.dto.CustomerPassiveProductRequest;
import com.nttdata.bootcamp.customerproduct.domain.dto.CustomerPassiveProductResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Interfaz del servicio CustomerPassiveProduct.
 *
 * @author Pedro Manuel Díaz Santa María
 * @version 1.0.0
 */
public interface ICustomerPassiveProductService {
  Flux<CustomerPassiveProductResponse> getAll();

  Mono<CustomerPassiveProductResponse> getById(String id);

  Mono<CustomerPassiveProductResponse> save(Mono<CustomerPassiveProductRequest> request);

  Mono<CustomerPassiveProductResponse> update(Mono<CustomerPassiveProductRequest> request,
                                              String id);

  Mono<CustomerPassiveProductResponse> delete(String id);
}
