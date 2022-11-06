package com.nttdata.bootcamp.customerproduct.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase DTO para el manejo de los CUSTOMERSACTIVEPRODUCT REQUEST.
 *
 * @author Pedro Manuel Díaz Santa María
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerActiveProductRequest extends CustomerProductRequest {
  private Double lineOfCredit;
  private Double debt;
}
