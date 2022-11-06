package com.nttdata.bootcamp.customerproduct.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase DTO para el manejo de los CUSTOMERSPASSIVEPRODUCT REQUEST.
 *
 * @author Pedro Manuel Díaz Santa María
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPassiveProductRequest extends CustomerProductRequest {
  private Double maintenance = 0.0;
  private Integer movementLimit = 0;
  private Integer movementDay = 0;
  private Double amount = 0.0;
  private int maxMovementFree = 0;
  private Double commission = 0.0;
  private Double minAmount = 0.0;
}
