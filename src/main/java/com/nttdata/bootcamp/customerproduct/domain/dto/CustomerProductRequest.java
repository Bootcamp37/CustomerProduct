package com.nttdata.bootcamp.customerproduct.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase DTO para el manejo de los CUSTOMERSRODUCT REQUEST.
 *
 * @author Pedro Manuel Díaz Santa María
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerProductRequest {
  private String customerId;
  private String productId;
}
