package com.nttdata.bootcamp.CustomerProduct.domain.service;

import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerActiveProductRequest;
import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerActiveProductResponse;
import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerResponse;
import com.nttdata.bootcamp.CustomerProduct.domain.dto.ProductResponse;
import com.nttdata.bootcamp.CustomerProduct.domain.mapper.ICustomerActiveProductMapper;
import com.nttdata.bootcamp.CustomerProduct.domain.repository.ServiceRepository;
import com.nttdata.bootcamp.CustomerProduct.infraestructure.repository.ICustomerActiveProductRepository;
import com.nttdata.bootcamp.CustomerProduct.infraestructure.service.ICustomerActiveProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerActiveProductService implements ICustomerActiveProductService {
    @Autowired
    private final ICustomerActiveProductRepository repository;
    @Autowired
    private final ICustomerActiveProductMapper mapper;
    @Autowired
    private final ServiceRepository serviceRepository;

    @Override
    public Flux<CustomerActiveProductResponse> getAll() {
        return repository.findAll()
                .map(mapper::toResponse);
    }

    @Override
    public Mono<CustomerActiveProductResponse> getById(String id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .switchIfEmpty(Mono.error(RuntimeException::new));
    }

    @Override
    public Mono<CustomerActiveProductResponse> save(CustomerActiveProductRequest request) {
        Mono<Tuple2<CustomerResponse, ProductResponse>> MonoTuple = serviceRepository.getCustomerProduct(request);
        return MonoTuple.flatMap(f -> Mono.just(request)
                        .map(mapper::toEntity)
                        .flatMap(repository::save)
                        .map(mapper::toResponse)
                        .switchIfEmpty(Mono.error(RuntimeException::new))
                )
                .switchIfEmpty(Mono.error(RuntimeException::new));
    }

    @Override
    public Mono<CustomerActiveProductResponse> update(CustomerActiveProductRequest request, String id) {
        Mono<Tuple2<CustomerResponse, ProductResponse>> MonoTuple = serviceRepository.getCustomerProduct(request);
        return MonoTuple.flatMap(f ->
                        repository.findById(id)
                                .map(element -> mapper.toEntity(request))
                                .doOnNext(e -> e.setId(id))
                                .flatMap(repository::save)
                                .map(mapper::toResponse)
                                .switchIfEmpty(Mono.error(RuntimeException::new))
                )
                .switchIfEmpty(Mono.error(RuntimeException::new));
    }

    @Override
    public Mono<CustomerActiveProductResponse> delete(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(RuntimeException::new))
                .flatMap(deleteCustomer -> repository.delete(deleteCustomer)
                        .then(Mono.just(mapper.toResponse(deleteCustomer))));
    }
}
