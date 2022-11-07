package com.nttdata.bootcamp.customerproduct.domain.repository;

import com.nttdata.bootcamp.customerproduct.domain.dto.*;
import com.nttdata.bootcamp.customerproduct.domain.entity.CustomerType;
import com.nttdata.bootcamp.customerproduct.domain.entity.ProductType;
import com.nttdata.bootcamp.customerproduct.domain.util.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

/**
 * Clase Repository.
 *
 * @author Pedro Manuel Díaz Santa María
 * @version 1.0.0
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class ServiceRepository {

  @Autowired
  private final CustomerRepository customerRepository;
  @Autowired
  private final ProductRepository productRepository;

  /**
   * Método Get.
   *
   * @param request     Variable del tipo CustomerProductRequest
   * @param productType variable del tipo ProductType
   * @return Retorna Mono < Tuple2 < CustomerResponse, ProductResponse > >
   */
  public Mono<Tuple2<CustomerResponse, ProductResponse>> get(
        CustomerProductRequest request,
        ProductType productType) {
    log.info("====> ProductRepository: Get");
    return customerRepository.getById(request.getCustomerId())
          .map(e -> {
            log.info(" ====> e1 = " + e.toString());
            return e;
          })
          .zipWith(productRepository.getById(request.getProductId())
                .map(e -> {
                  log.info(" ====> e2 = " + e.toString());
                  return e;
                }))
          .filter(tupla -> tupla.getT2().getProductType().equals(productType));
  }

  /**
   * Método GetCustomerProduct.
   *
   * @param request Variable del tipo CustomerActiveProductRequest
   * @return Retorna Mono < Tuple2 < CustomerResponse, ProductResponse > >
   */
  public Mono<Tuple2<CustomerResponse, ProductResponse>> getCustomerProduct(
        CustomerActiveProductRequest request) {
    log.info("====> ProductRepository: GetCustomerProduct 1");
    return get(request, ProductType.ACTIVE)
          .map(Tool::printLog)
          // Filtra que el customer cuadre con el producto
          .filter(tupla -> tupla.getT2().getCustomerType().equals(tupla.getT1().getCustomerType()))
          .switchIfEmpty(Mono.error(RuntimeException::new));
  }

  /**
   * Método GetCustomerProduct.
   *
   * @param request Variable del tipo CustomerPassiveProductRequest
   * @return Retorna un Mono < Tuple2 < CustomerResponse > >
   */
  public Mono<Tuple2<CustomerResponse, ProductResponse>> getCustomerProduct(
        CustomerPassiveProductRequest request) {
    log.info("====> ProductRepository: GetCustomerProduct 2");
    return get(request, ProductType.PASSIVE)
          .map(Tool::printLog)
          .map(e -> {
            log.info("tupla.getT2().getCustomerType() ===>" + e.getT2().getCustomerType());
            log.info("tupla.getT1().getCustomerType() ===>" + e.getT1().getCustomerType());
            return e;
          })
          // Filtra que el customer cuadre con el producto
          .filter(tupla ->
                tupla.getT2().getCustomerType().equals(CustomerType.BOTH)
                      || tupla.getT2().getCustomerType().equals(tupla.getT1().getCustomerType())
          )
          .switchIfEmpty(Mono.error(RuntimeException::new));
  }
}
