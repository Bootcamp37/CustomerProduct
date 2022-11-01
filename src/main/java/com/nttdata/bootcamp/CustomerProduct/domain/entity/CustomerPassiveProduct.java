package com.nttdata.bootcamp.CustomerProduct.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private Integer movementMonth;
    private Double amount;
}
