package com.nttdata.bootcamp.CustomerProduct.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerActiveProductRequest extends CustomerProductRequest {
    private Double lineOfCredit;
    private Double debt;
}
