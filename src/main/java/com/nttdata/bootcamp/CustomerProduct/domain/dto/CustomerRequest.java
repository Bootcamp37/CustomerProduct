package com.nttdata.bootcamp.CustomerProduct.domain.dto;

import com.nttdata.bootcamp.CustomerProduct.domain.entity.CustomerSubType;
import com.nttdata.bootcamp.CustomerProduct.domain.entity.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    private String firstname;
    private String lastname;
    private CustomerType customerType;
    private CustomerSubType subType;
}
