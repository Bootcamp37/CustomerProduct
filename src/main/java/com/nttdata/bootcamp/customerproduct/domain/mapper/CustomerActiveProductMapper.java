package com.nttdata.bootcamp.customerproduct.domain.mapper;

import com.nttdata.bootcamp.customerproduct.domain.dto.CustomerActiveProductRequest;
import com.nttdata.bootcamp.customerproduct.domain.dto.CustomerActiveProductResponse;
import com.nttdata.bootcamp.customerproduct.domain.entity.CustomerActiveProduct;
import com.nttdata.bootcamp.customerproduct.infraestructure.ICustomerActiveProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Clase MAPPER para la conversión de REQUEST a ENTITY y de ENTITY A RESPONSE
 * para la clase CUSTOMERACTIVEPRODUCTR.
 *
 * @author Pedro Manuel Diaz Santa Maria
 * @version 1.0.0
 */
@Component
@Slf4j
public class CustomerActiveProductMapper implements ICustomerActiveProductMapper {
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
   * @param request No Null - Variable del tipo CUSTOMERACTIVEPRODUCT REQUEST
   * @return Retorna un objeto ENTITY del CUSTOMERACTIVEPRODUCT REQUEST
   * @author Pedro Manuel Diaz Santa Maria
   * @version 1.0.0
   */
  @Override
  public CustomerActiveProduct toEntity(CustomerActiveProductRequest request) {
    log.info("====> CustomerActiveProductMapper: ToEntity");
    CustomerActiveProduct customerActiveProduct = new CustomerActiveProduct();
    BeanUtils.copyProperties(request, customerActiveProduct);
    return customerActiveProduct;
  }

  /**
   * Método ToEntity.
   * Este metodo convierte un ENTITY a RESPONSE.
   *
   * @param customerActiveProduct No Null - Variable del tipo CUSTOMERACTIVEPRODUCT ENTITY
   * @return Retorna un objeto RESPONSE del CUSTOMERACTIVEPRODUCT ENTITY
   * @author Pedro Manuel Diaz Santa Maria
   * @version 1.0.0
   */
  @Override
  public CustomerActiveProductResponse toResponse(
        CustomerActiveProduct customerActiveProduct) {
    log.info("====> CustomerActiveProductMapper: ToResponse");
    CustomerActiveProductResponse customerActiveProductResponse
          = new CustomerActiveProductResponse();
    BeanUtils.copyProperties(customerActiveProduct, customerActiveProductResponse);
    customerActiveProductResponse.setCustomerUrl(
          customerDomain + customerPath + customerActiveProduct.getCustomerId());
    customerActiveProductResponse.setProductUrl(
          productDomain + productPath + customerActiveProduct.getProductId());
    return customerActiveProductResponse;
  }
}
