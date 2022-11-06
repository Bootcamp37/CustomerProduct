package com.nttdata.bootcamp.customerproduct.domain.mapper;

import com.nttdata.bootcamp.customerproduct.domain.dto.CustomerPassiveProductRequest;
import com.nttdata.bootcamp.customerproduct.domain.dto.CustomerPassiveProductResponse;
import com.nttdata.bootcamp.customerproduct.domain.entity.CustomerPassiveProduct;
import com.nttdata.bootcamp.customerproduct.infraestructure.ICustomerPassiveProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Clase MAPPER para la conversión de REQUEST a ENTITY y de ENTITY A RESPONSE
 * para la clase CUSTOMERPASSIVEPRODUCTR.
 *
 * @author Pedro Manuel Diaz Santa Maria
 * @version 1.0.0
 */
@Component
@Slf4j
public class CustomerPassiveProductMapper implements ICustomerPassiveProductMapper {
  @Value("${message.path-customerDomain}")
  String customerDomain;
  @Value("${message.path-customerResponse}")
  String customerPath;

  @Value("${message.path-productDomain}")
  String productDomain;
  @Value("${message.path-productResponse}")
  String productPath;

  /**
   * Método ToEntity.
   * Este metodo convierte un RESPONSE a ENTITY.
   *
   * @param request No Null - Variable del tipo CUSTOMERPASSIVEPRODUCT REQUEST
   * @return Retorna un objeto ENTITY del CUSTOMERPASSIVEPRODUCT REQUEST
   * @author Pedro Manuel Diaz Santa Maria
   * @version 1.0.0
   */
  @Override
  public CustomerPassiveProduct toEntity(@NotNull CustomerPassiveProductRequest request) {
    log.info("====> CustomerPassiveProductMapper: ToEntity");
    CustomerPassiveProduct customerPassiveProduct = new CustomerPassiveProduct();
    BeanUtils.copyProperties(request, customerPassiveProduct);
    return customerPassiveProduct;
  }

  /**
   * Método ToEntity.
   * Este metodo convierte un ENTITY a RESPONSE.
   *
   * @param customerPassiveProduct No Null - Variable del tipo CUSTOMERPASSIVEPRODUCT ENTITY
   * @return Retorna un objeto RESPONSE del CUSTOMERPASSIVEPRODUCT ENTITY
   * @author Pedro Manuel Diaz Santa Maria
   * @version 1.0.0
   */
  @Override
  public CustomerPassiveProductResponse toResponse(CustomerPassiveProduct customerPassiveProduct) {
    log.info("====> CustomerPassiveProductMapper: ToResponse");
    CustomerPassiveProductResponse customerPassiveProductResponse
          = new CustomerPassiveProductResponse();
    BeanUtils.copyProperties(customerPassiveProduct, customerPassiveProductResponse);
    customerPassiveProductResponse.setCustomerUrl(
          customerDomain + customerPath + customerPassiveProduct.getCustomerId());
    customerPassiveProductResponse.setProductUrl(
          productDomain + productPath + customerPassiveProduct.getProductId());
    return customerPassiveProductResponse;
  }
}
