package com.nttdata.bootcamp.customerproduct.domain.dto;

import com.nttdata.bootcamp.customerproduct.domain.entity.CustomerType;
import com.nttdata.bootcamp.customerproduct.domain.entity.ProductSubType;
import com.nttdata.bootcamp.customerproduct.domain.entity.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase DTO para el manejo de los PRODUCT RESPONSE.
 *
 * @author Pedro Manuel Díaz Santa María
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
  private String id;
  private String name;
  private String description;
  private ProductType productType;
  private ProductSubType productSubType;
  private CustomerType customerType;
}
