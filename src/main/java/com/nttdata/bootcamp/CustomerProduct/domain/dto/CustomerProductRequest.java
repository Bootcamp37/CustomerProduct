package com.nttdata.bootcamp.CustomerProduct.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerProductRequest {
    private String customerId;
    private String productId;
}
