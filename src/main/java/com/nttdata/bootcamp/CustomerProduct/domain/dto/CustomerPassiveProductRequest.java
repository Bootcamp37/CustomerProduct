package com.nttdata.bootcamp.CustomerProduct.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
