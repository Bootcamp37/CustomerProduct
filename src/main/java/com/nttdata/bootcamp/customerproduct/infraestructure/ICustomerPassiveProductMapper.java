package com.nttdata.bootcamp.customerproduct.infraestructure;

import com.nttdata.bootcamp.customerproduct.domain.dto.CustomerPassiveProductRequest;
import com.nttdata.bootcamp.customerproduct.domain.dto.CustomerPassiveProductResponse;
import com.nttdata.bootcamp.customerproduct.domain.entity.CustomerPassiveProduct;

/**
 * Interfaz del tipo CustomerPassiveProductMapper.
 *
 * @author Pedro Manuel Díaz Santa María
 * @version 1.0.0
 */
public interface ICustomerPassiveProductMapper {
  CustomerPassiveProduct toEntity(CustomerPassiveProductRequest request);

  CustomerPassiveProductResponse toResponse(CustomerPassiveProduct customerPassiveProduct);
}
