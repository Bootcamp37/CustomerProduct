package com.nttdata.bootcamp.CustomerProduct.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPassiveProductRequest extends CustomerProductRequest {
    private Double maintenance;
    private Integer movementLimit;
    private Integer movementDay;
    private Integer movementMonth;
    private Double amount;
    private int maxMovementFree;
    private Double commission;
}
