package com.nttdata.bootcamp.customerproduct.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase DTO para el manejo de los CUSTOMERSACTIVEPRODUCT RESPONSE.
 *
 * @author Pedro Manuel Díaz Santa María
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerActiveProductResponse {
  private String id;
  private String customerId;
  private String customerUrl;
  private String productUrl;
  private String productId;
  private Double lineOfCredit;
  private Double debt;
}
