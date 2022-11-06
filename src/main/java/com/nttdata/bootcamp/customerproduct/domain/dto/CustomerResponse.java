package com.nttdata.bootcamp.customerproduct.domain.dto;

import com.nttdata.bootcamp.customerproduct.domain.entity.CustomerSubType;
import com.nttdata.bootcamp.customerproduct.domain.entity.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase DTO para el manejo de los CUSTOMERS RESPONSE.
 *
 * @author Pedro Manuel Díaz Santa María
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
  private String id;
  private String firstname;
  private String lastname;
  private CustomerType customerType;
  private CustomerSubType subType;
}
