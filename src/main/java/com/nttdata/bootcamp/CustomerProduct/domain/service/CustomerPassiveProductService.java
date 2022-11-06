package com.nttdata.bootcamp.CustomerProduct.domain.service;

import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerPassiveProductRequest;
import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerPassiveProductResponse;
import com.nttdata.bootcamp.CustomerProduct.domain.dto.PassiveProductFactory;
import com.nttdata.bootcamp.CustomerProduct.domain.entity.CustomerSubType;
import com.nttdata.bootcamp.CustomerProduct.domain.entity.CustomerType;
import com.nttdata.bootcamp.CustomerProduct.domain.entity.ProductSubType;
import com.nttdata.bootcamp.CustomerProduct.domain.repository.ProductRepository;
import com.nttdata.bootcamp.CustomerProduct.domain.repository.ServiceRepository;
import com.nttdata.bootcamp.CustomerProduct.infraestructure.ICustomerPassiveProductMapper;
import com.nttdata.bootcamp.CustomerProduct.infraestructure.repository.ICustomerActiveProductRepository;
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
    private final ICustomerActiveProductRepository activeRepository;
    @Autowired
    private final ICustomerPassiveProductMapper mapper;
    @Autowired
    private final ServiceRepository serviceRepository;
    @Autowired
    private final ProductRepository productRepository;

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
                // Retornamos la tupla Customer|Product
                .flatMap(serviceRepository::getCustomerProduct)
                .flatMap(f -> {
                    // SI CUSTOMER ES BUSINESS
                    if (f.getT1().getCustomerType().equals(CustomerType.BUSINESS)) {
                        // SI NO SOLICITA UNA CUENTA CORRIENTE
                        if (!f.getT2().getProductSubType().equals(ProductSubType.CURRENT)) {
                            return Mono.error(RuntimeException::new);
                        }
                        // SI SOLICITA UNA CUENTA CORRIENTE
                        // SI CUSTOMER NO ES PYME
                        if (Objects.isNull(f.getT1().getSubType())) {
                            log.info("Entramos con un producto pasivo y cliente empresarial no pyme");
                            return request.map(e -> PassiveProductFactory.build(e, f.getT2().getProductSubType()))
                                    .flatMap(repository::save)
                                    .map(mapper::toResponse);
                        }
                        // SINO, SI EL CUSTOMER ES PYME
                        else if (f.getT1().getSubType().equals(CustomerSubType.PYME)) {
                            // TIENE YA UNA TARJETA DE CREDITO?
                            return this.validatedHaveCreditCard(f.getT1().getId())
                                    .flatMap(
                                            count ->
                                                    request.flatMap(e -> {
                                                        if (e.getMinAmount().equals(0.0)) {
                                                            return repository.save(PassiveProductFactory.build(e, f.getT2().getProductSubType()))
                                                                    .map(mapper::toResponse);
                                                        }
                                                        return Mono.error(RuntimeException::new);
                                                    })
                                    );
                            // Cuenta corriente sin comisión de mantenimiento
                        }
                        return Mono.error(RuntimeException::new);
                    }
                    // SI CUSTOMER ES PERSONAL
                    if (f.getT1().getCustomerType().equals(CustomerType.PERSONAL)) {
                        // SI NO ES VIP
                        if (Objects.isNull(f.getT1().getSubType())) {
                            log.info("Entramos con un producto pasivo y cliente personal");
                            return request.flatMap(e -> savePersonalPassiveProduct(e, f.getT2().getProductSubType()));
                        }
                        // SINO, SI ES VIP
                        else if (f.getT1().getSubType().equals(CustomerSubType.VIP)) {
                            //  SOLICITA UNA CUENTA DE AHORRO
                            if (f.getT2().getProductSubType().equals(ProductSubType.SAVING)) {
                                // DEBE TENER MINIMO UNA TARJETA DE CREDITO
                                return this.validatedHaveCreditCard(f.getT1().getId())
                                        .flatMap(
                                                count ->
                                                        request.flatMap(e -> {
                                                            if (e.getMinAmount().equals(0.0)) {
                                                                return Mono.error(RuntimeException::new);
                                                            }
                                                            return this.savePersonalPassiveProduct(e, f.getT2().getProductSubType());
                                                        })
                                        )
                                        .switchIfEmpty(Mono.error(RuntimeException::new));
                            }
                            // SINO , SI NO SOLICITA CUENTA DE AHORRO
                            return request.flatMap(e -> this.savePersonalPassiveProduct(e, f.getT2().getProductSubType()));
                        }
                        return Mono.error(RuntimeException::new);
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

    public Mono<CustomerPassiveProductResponse> savePersonalPassiveProduct(CustomerPassiveProductRequest request, ProductSubType productSubType) {
        return repository.findAll()
                // Filtra las cuentas que tengan al cliente
                .filter(response -> response.getCustomerId().equals(request.getCustomerId()))
                // Filtra las cuentas que tengan el producto
                .filter(response -> response.getProductId().equals(request.getProductId()))
                // Devolvemos cuanto queda
                .count()
                .map(e -> {
                    log.info(" 4 ===> " + e.toString());
                    return e;
                })
                .filter(e -> e.compareTo(0L) == 0)
                .flatMap(count -> repository.save(PassiveProductFactory.build(request, productSubType))
                        .flatMap(repository::save)
                        .map(mapper::toResponse)
                );
    }

    public Mono<Long> validatedHaveCreditCard(String customerId) {
        return activeRepository.findAll()
                // FILTRA LAS CUENTAS ACTIVAS DEL CUSTOMER
                .filter(response -> response.getCustomerId().equals(customerId))
                // TRAE LOS PRODUCTOS DEL CUSTOMER
                .flatMap(response -> productRepository.getById(response.getProductId()))
                // FILTRA LOS PRODUCTOS QUE SON TARJETAS
                .filter(response -> response.getProductSubType().equals(ProductSubType.CREDITCARD))
                // CUENTA CUANTAS TARJETAS TIENE
                .count()
                // FILTRA SI TIENE MAS DE UNA TARJETA
                .filter(e -> e.compareTo(1L) >= 0);
    }
}
