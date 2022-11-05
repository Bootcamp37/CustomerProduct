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

import java.util.Objects;

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
        log.info("====> CustomerPassiveProductService: GetAll");
        return repository.findAll()
                .map(mapper::toResponse);
    }

    @Override
    public Mono<CustomerPassiveProductResponse> getById(String id) {
        log.info("====> CustomerPassiveProductService: GetById");
        return repository.findById(id)
                .map(mapper::toResponse)
                .switchIfEmpty(Mono.error(RuntimeException::new));
    }

    @Override
    public Mono<CustomerPassiveProductResponse> save(Mono<CustomerPassiveProductRequest> request) {
        log.info("====> CustomerPassiveProductService: Save");
        return request.map(this::printDebug)
                .flatMap(serviceRepository::getCustomerProduct)
                .flatMap(f -> {
                    // Cliente Empresarial
                    if (f.getT1().getCustomerType().equals(CustomerType.BUSINESS)) {
                        if (Objects.isNull(f.getT1().getSubType())) {
                            log.info("Entramos con un producto pasivo y cliente empresarial");
                            return request.map(mapper::toEntity)
                                    .flatMap(repository::save)
                                    .map(mapper::toResponse);
                        }
                        else if (f.getT1().getSubType().equals(CustomerSubType.PYME)) {
                            // Vaidar que tenga una tarjeta de credito con el banco
                            // Cuenta corriente sin comisión de mantenimiento
                        }

                    }
                    // Cliente Personal
                    if (f.getT1().getCustomerType().equals(CustomerType.PERSONAL)) {
                        if(Objects.isNull(f.getT1().getSubType())) {
                            log.info("Entramos con un producto pasivo y cliente personal");
                            return request.flatMap(
                                    e -> {
                                        var a1 = repository.findAll();
                                        var f1 = a1.map(ff1 -> {
                                            log.info("ff1 ===> " + ff1.toString());
                                            return ff1;
                                        });
                                        // Filtra las cuentas que tengan al cliente
                                        var b1 = f1.filter(response -> response.getCustomerId().equals(e.getCustomerId()));
                                        var f2 = b1.map(ff2 -> {
                                            log.info("ff2 ===> " + ff2.toString());
                                            return ff2;
                                        });
                                        // Filtra las cuentas que tengan el producto
                                        var c1 = f2.filter(response -> response.getProductId().equals(e.getProductId()));
                                        var f3 = c1.map(ff3 -> {
                                            log.info("ff3 ===> " + ff3.toString());
                                            return ff3;
                                        });
                                        // Devolvemos cuanto queda
                                        var d1 = f3.count();
                                        var f4 = d1.map(ff4 -> {
                                            log.info("ff4 ===> " + ff4.toString());
                                            return ff4;
                                        });
                                        var e1 = f4.flatMap(count -> {
                                                    if (count.compareTo(0L) == 0) {
                                                        return repository.save(mapper.toEntity(e))
                                                                .map(mapper::toResponse);
                                                    }
                                                    log.info("Devolvemos count == " + count);
                                                    return Mono.error(RuntimeException::new);
                                                });
                                        return e1;
                                    }
                            );
                        }
                        else if (f.getT1().getSubType().equals(CustomerSubType.VIP)) {
                            // Debe tener una tarjeta de credito
                            // cuenta de ahorro con monto minimo de promedio cada mes
                        }

                    }
                    return Mono.error(RuntimeException::new);
                })
                .switchIfEmpty(Mono.error(RuntimeException::new));
    }

    @Override
    public Mono<CustomerPassiveProductResponse> update(Mono<CustomerPassiveProductRequest> request, String id) {
        log.info("====> CustomerPassiveProductService: Update");
        return request.map(this::printDebug)
                .map(serviceRepository::getCustomerProduct)
                .flatMap(item ->
                        repository.findById(id)
                                .switchIfEmpty(Mono.error(RuntimeException::new))
                                .map(e -> item)
                )
                .flatMap(element -> request)
                .map(mapper::toEntity)
                .doOnNext(e -> e.setId(id))
                .flatMap(repository::save)
                .map(mapper::toResponse)
                .switchIfEmpty(Mono.error(RuntimeException::new));
    }

    @Override
    public Mono<CustomerPassiveProductResponse> delete(String id) {
        log.info("====> CustomerPassiveProductService: Delete");
        return repository.findById(id)
                .switchIfEmpty(Mono.error(RuntimeException::new))
                .flatMap(deleteCustomerPassiveProduct -> repository.delete(deleteCustomerPassiveProduct)
                        .then(Mono.just(mapper.toResponse(deleteCustomerPassiveProduct))));
    }

    public CustomerPassiveProductRequest printDebug(CustomerPassiveProductRequest request) {
        log.info("====> CustomerPassiveProductService: printDebug");
        log.info("====> CustomerPassiveProductService: Request ==> " + request.toString());
        return request;
    }
}
