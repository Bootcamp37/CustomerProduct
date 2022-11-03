package com.nttdata.bootcamp.CustomerProduct.domain.repository;

import com.nttdata.bootcamp.CustomerProduct.domain.dto.*;
import com.nttdata.bootcamp.CustomerProduct.domain.entity.CustomerType;
import com.nttdata.bootcamp.CustomerProduct.domain.entity.ProductType;
import com.nttdata.bootcamp.CustomerProduct.domain.util.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ServiceRepository {

    @Autowired
    private final CustomerRepository customerRepository;
    @Autowired
    private final ProductRepository productRepository;

    public Mono<Tuple2<CustomerResponse, ProductResponse>> get(CustomerProductRequest request, ProductType productType) {
        log.info("Ingreso a Get");
        return customerRepository.getById(request.getCustomerId())
                .zipWith(productRepository.getById(request.getProductId()))
                .filter(tupla -> tupla.getT2().getProductType().equals(productType));
    }

    public Mono<Tuple2<CustomerResponse, ProductResponse>> getCustomerProduct(CustomerActiveProductRequest request) {
        log.info("Ingreso a GetCustomerProduct(CustomerActiveProductRequest)");
        return get(request, ProductType.ACTIVE)
                .map(Tool::printLog)
                // Filtra que el customer cuadre con el producto
                .filter(tupla -> tupla.getT2().getCustomerType().equals(tupla.getT1().getCustomerType()))
                .switchIfEmpty(Mono.error(RuntimeException::new));
    }

    public Mono<Tuple2<CustomerResponse, ProductResponse>> getCustomerProduct(CustomerPassiveProductRequest request) {
        log.info("Ingreso a GetCustomerProduct(CustomerPassiveProductRequest)");
        return get(request, ProductType.PASSIVE)
                .map(Tool::printLog)
                // Filtra que el customer cuadre con el producto
                .filter(tupla ->
                        tupla.getT2().getCustomerType().equals(CustomerType.BOTH) ||
                                tupla.getT2().getCustomerType().equals(tupla.getT1().getCustomerType())
                )
                .switchIfEmpty(Mono.error(RuntimeException::new));
    }
}
