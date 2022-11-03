package com.nttdata.bootcamp.CustomerProduct.domain.service;

import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerPassiveProductRequest;
import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerPassiveProductResponse;
import com.nttdata.bootcamp.CustomerProduct.domain.entity.CustomerSubType;
import com.nttdata.bootcamp.CustomerProduct.domain.entity.CustomerType;
import com.nttdata.bootcamp.CustomerProduct.domain.repository.ServiceRepository;
import com.nttdata.bootcamp.CustomerProduct.infraestructure.ICustomerPassiveProductMapper;
import com.nttdata.bootcamp.CustomerProduct.infraestructure.repository.ICustomerPassiveProductRepository;
import com.nttdata.bootcamp.CustomerProduct.infraestructure.service.ICustomerPassiveProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerPassiveProductService implements ICustomerPassiveProductService {
    @Autowired
    private final ICustomerPassiveProductRepository repository;
    @Autowired
    private final ICustomerPassiveProductMapper mapper;
    @Autowired
    private final ServiceRepository serviceRepository;

    @Override
    public Flux<CustomerPassiveProductResponse> getAll() {
        return repository.findAll()
                .map(mapper::toResponse);
    }

    @Override
    public Mono<CustomerPassiveProductResponse> getById(String id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .switchIfEmpty(Mono.error(RuntimeException::new));
    }

    @Override
    public Mono<CustomerPassiveProductResponse> save(Mono<CustomerPassiveProductRequest> request) {
        // Devuelve si existe el Customer y el Product
        return request.flatMap(serviceRepository::getCustomerProduct)
                .flatMap(f -> {
                    // Cliente Empresarial
                    if (f.getT1().getCustomerType().equals(CustomerType.BUSINESS)) {
                        if(f.getT1().getSubType().equals(CustomerSubType.PYME))
                        {
                            // Vaidar que tenga una tarjeta de credito con el banco
                            // Cuenta corriente sin comisión de mantenimiento
                        }else{
                        log.info("Entramos con un producto pasivo y cliente empresarial");
                        return request.map(mapper::toEntity)
                                .flatMap(repository::save)
                                .map(mapper::toResponse);
                        }
                    }
                    // Cliente Personal
                    if (f.getT1().getCustomerType().equals(CustomerType.PERSONAL)) {
                        if(f.getT1().getSubType().equals(CustomerSubType.VIP)){
                            // Debe tener una tarjeta de credito
                            // cuenta de ahorro con monto minimo de promedio cada mes
                        }else{
                            log.info("Entramos con un producto pasivo y cliente personal");
                            // Recupera si el cliente ya esta registrado con ese tipo de producto
                            return request.flatMap(
                                    e -> {
                                        return repository.findAll()
                                                // Filtra las cuentas que tengan al cliente
                                                .filter(response -> response.getCustomerId().equals(e.getCustomerId()))
                                                // Filtra las cuentas que tengan el producto
                                                .filter(response -> response.getProductId().equals(e.getProductId()))
                                                // Devolvemos cuanto queda
                                                .count()
                                                .flatMap(count -> {
                                                    if (count.compareTo(0L) == 0) {
                                                        return repository.save(mapper.toEntity(e))
                                                                .map(mapper::toResponse);
                                                    }
                                                    log.info("Devolvemos count == " + count);
                                                    return Mono.error(RuntimeException::new);
                                                });
                                    }
                            );
                        }

                    }
                    return Mono.error(RuntimeException::new);
                })
                .switchIfEmpty(Mono.error(RuntimeException::new));
    }

    @Override
    public Mono<CustomerPassiveProductResponse> update(Mono<CustomerPassiveProductRequest> request, String id) {
        return request.map(serviceRepository::getCustomerProduct)
                .flatMap(f ->
                        repository.findById(id)
                                .flatMap(element ->
                                        request.map(mapper::toEntity)
                                                .doOnNext(e -> e.setId(id))
                                                .flatMap(repository::save)
                                                .map(mapper::toResponse)
                                                .switchIfEmpty(Mono.error(RuntimeException::new))
                                ))
                .switchIfEmpty(Mono.error(RuntimeException::new));
    }

    @Override
    public Mono<CustomerPassiveProductResponse> delete(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(RuntimeException::new))
                .flatMap(deleteCustomerPassiveProduct -> repository.delete(deleteCustomerPassiveProduct)
                        .then(Mono.just(mapper.toResponse(deleteCustomerPassiveProduct))));
    }
}
