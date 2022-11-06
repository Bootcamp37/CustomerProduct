package com.nttdata.bootcamp.customerproduct.infraestructure;

import com.nttdata.bootcamp.customerproduct.domain.dto.CustomerActiveProductRequest;
import com.nttdata.bootcamp.customerproduct.domain.dto.CustomerActiveProductResponse;
import com.nttdata.bootcamp.customerproduct.domain.entity.CustomerActiveProduct;

/**
 * Interfaz del tipo CustomerActiveProductMapper.
 *
 * @author Pedro Manuel Díaz Santa María
 * @version 1.0.0
 */
public interface ICustomerActiveProductMapper {
  CustomerActiveProduct toEntity(CustomerActiveProductRequest request);

  CustomerActiveProductResponse toResponse(CustomerActiveProduct customerActiveProduct);
}
