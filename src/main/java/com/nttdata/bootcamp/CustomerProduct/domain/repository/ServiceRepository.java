package com.nttdata.bootcamp.CustomerProduct.domain.repository;

import com.nttdata.bootcamp.CustomerProduct.domain.dto.*;
import com.nttdata.bootcamp.CustomerProduct.domain.entity.CustomerType;
import com.nttdata.bootcamp.CustomerProduct.domain.entity.ProductType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Repository
@RequiredArgsConstructor
public class ServiceRepository {

    @Autowired
    private final CustomerRepository customerRepository;
    @Autowired
    private final ProductRepository productRepository;

    public Mono<Tuple2<CustomerResponse, ProductResponse>> get(CustomerProductRequest request, ProductType productType) {
        return customerRepository.getById(request.getCustomerId())
                .zipWith(productRepository.getById(request.getProductId()))
                .filter(tupla -> tupla.getT2().getProductType().equals(productType));
    }

    public Mono<Tuple2<CustomerResponse, ProductResponse>> getCustomerProduct(CustomerActiveProductRequest request) {
        return get(request, ProductType.ACTIVE)
                // Filtra que el customer cuadre con el producto
                .filter(tupla -> tupla.getT2().getCustomerType().equals(tupla.getT1().getCustomerType()))
                .switchIfEmpty(Mono.error(RuntimeException::new));
    }

    public Mono<Tuple2<CustomerResponse, ProductResponse>> getCustomerProduct(CustomerPassiveProductRequest request) {
        return get(request, ProductType.PASSIVE)
                // Filtra que el customer cuadre con el producto
                .filter(tupla ->
                        tupla.getT2().getCustomerType().equals(CustomerType.BOTH) ||
                                tupla.getT2().getCustomerType().equals(tupla.getT1().getCustomerType())
                )
                .switchIfEmpty(Mono.error(RuntimeException::new));
    }
}
