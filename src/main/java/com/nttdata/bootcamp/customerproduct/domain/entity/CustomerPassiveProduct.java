package com.nttdata.bootcamp.customerproduct.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase ENTITY para el manejo de los CustomerPassiveProduct en BD.
 *
 * @author Pedro Manuel Diaz Santa Maria
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customerPassiveProduct")
public class CustomerPassiveProduct {
  @Id
  private String id;
  private String customerId;
  private String productId;
  private Double maintenance;
  private Integer movementLimit;
  private Integer movementDay;
  private Double amount;
  private int maxMovementFree;
  private Double commission;
  private Double minAmount;
}
