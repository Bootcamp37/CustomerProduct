package com.nttdata.bootcamp.CustomerProduct.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customerProductActives")
public class CustomerActiveProduct {
    @Id
    private String id;
    private String customerId;
    private String productId;
    private Double lineOfCredit;
    private Double debt;
}
