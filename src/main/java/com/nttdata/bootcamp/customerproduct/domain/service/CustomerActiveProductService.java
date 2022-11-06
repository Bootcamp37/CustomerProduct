package com.nttdata.bootcamp.customerproduct.domain.service;

import com.nttdata.bootcamp.customerproduct.domain.dto.CustomerActiveProductRequest;
import com.nttdata.bootcamp.customerproduct.domain.dto.CustomerActiveProductResponse;
import com.nttdata.bootcamp.customerproduct.domain.repository.ServiceRepository;
import com.nttdata.bootcamp.customerproduct.domain.util.Tool;
import com.nttdata.bootcamp.customerproduct.infraestructure.ICustomerActiveProductMapper;
import com.nttdata.bootcamp.customerproduct.infraestructure.repository.ICustomerActiveProductRepository;
import com.nttdata.bootcamp.customerproduct.infraestructure.service.ICustomerActiveProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Clase Service - CustomerActiveProductService.
 *
 * @author Pedro Manuel Díaz Santa María
 * @version 1.0.0
 */
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

  /**
   * Método GetAll.
   *
   * @return Retorna un objeto Flux < CustomerActiveProductResponse >
   * @author Pedro Manuel Diaz Santa Maria
   * @version 1.0.0
   */
  @Override
  public Flux<CustomerActiveProductResponse> getAll() {
    log.info("====> CustomerActiveProductService: GetAll");
    return repository.findAll()
          .map(mapper::toResponse);
  }

  /**
   * Método GetById.
   *
   * @param id Variable del tipo String
   * @return Retorna un objeto RESPONSE del Mono < CustomerActiveProductResponse >
   * @author Pedro Manuel Diaz Santa Maria
   * @version 1.0.0
   */
  @Override
  public Mono<CustomerActiveProductResponse> getById(String id) {
    log.info("====> CustomerActiveProductService: GetById");
    return repository.findById(id)
          .map(mapper::toResponse)
          .switchIfEmpty(Mono.error(RuntimeException::new));
  }

  /**
   * Método Save.
   *
   * @param request Variable del tipo Mono < CustomerActiveProductRequest >
   * @return Retorna un objeto Mono < CustomerActiveProductResponse >
   * @author Pedro Manuel Diaz Santa Maria
   * @version 1.0.0
   */
  @Override
  public Mono<CustomerActiveProductResponse> save(Mono<CustomerActiveProductRequest> request) {
    log.info("====> CustomerActiveProductService: Save");
    return request.map(this::printDebug)
          .flatMap(serviceRepository::getCustomerProduct)
          .map(Tool::printLog)
          .flatMap(e ->
                repository.findAll()
                      .filter(response -> response.getCustomerId().equals(e.getT1().getId()))
                      .filter(response -> response.getProductId().equals(e.getT2().getId()))
                      .count()
          )
          .flatMap(count -> {
            if (count.compareTo(0L) == 0) {
              return request;
            }
            return Mono.error(RuntimeException::new);
          })
          .map(mapper::toEntity)
          .flatMap(repository::save)
          .map(mapper::toResponse)
          .switchIfEmpty(Mono.error(RuntimeException::new));
  }

  /**
   * Método Update.
   *
   * @param request Variable del tipo Mono < CustomerActiveProductRequest >
   * @param id      Variable del tipo String
   * @return Retorna un objeto Mono < CustomerActiveProductResponse >
   * @author Pedro Manuel Diaz Santa Maria
   * @version 1.0.0
   */
  @Override
  public Mono<CustomerActiveProductResponse> update(
        Mono<CustomerActiveProductRequest> request, String id) {
    log.info("====> CustomerActiveProductService: Update");
    return request.map(this::printDebug)
          .flatMap(serviceRepository::getCustomerProduct)
          .flatMap(item -> repository.findById(id)
                .switchIfEmpty(Mono.error(RuntimeException::new))
                .map(e -> item)
          )
          .flatMap(f -> request)
          .map(mapper::toEntity)
          .doOnNext(e -> e.setId(id))
          .flatMap(repository::save)
          .map(mapper::toResponse)
          .switchIfEmpty(Mono.error(RuntimeException::new));
  }

  /**
   * Método Delete.
   *
   * @param id Variable del tipo String
   * @return Retorna un objeto Mono < CustomerActiveProductResponse >
   * @author Pedro Manuel Diaz Santa Maria
   * @version 1.0.0
   */
  @Override
  public Mono<CustomerActiveProductResponse> delete(String id) {
    log.info("====> CustomerActiveProductService: Delete");
    return repository.findById(id)
          .switchIfEmpty(Mono.error(RuntimeException::new))
          .flatMap(deleteCustomer -> repository.delete(deleteCustomer)
                .then(Mono.just(mapper.toResponse(deleteCustomer))));
  }

  /**
   * Método PrintDebug.
   *
   * @param request Variable del tipo CustomerActiveProductRequest
   * @return Retorna un objeto CustomerActiveProductRequest
   * @author Pedro Manuel Diaz Santa Maria
   * @version 1.0.0
   */
  public CustomerActiveProductRequest printDebug(CustomerActiveProductRequest request) {
    log.info("====> CustomerActiveProductService: printDebug");
    log.info("====> CustomerActiveProductService: Request ==> " + request.toString());
    return request;
  }
}
