package com.nttdata.bootcamp.customerproduct.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase DTO para el manejo de los CUSTOMERSPASSIVEPRODUCT RESPONSE.
 *
 * @author Pedro Manuel Díaz Santa María
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPassiveProductResponse {
  private String id;
  private String customerId;
  private String customerUrl;
  private String productId;
  private String productUrl;
  private Double maintenance;
  private Integer movementLimit;
  private Integer movementDay;
  private Double amount;
  private int maxMovementFree;
  private Double commission;
  private Double minAmount;
}