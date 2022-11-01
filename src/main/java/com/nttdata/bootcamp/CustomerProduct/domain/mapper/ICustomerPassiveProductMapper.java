package com.nttdata.bootcamp.CustomerProduct.domain.mapper;

import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerPassiveProductRequest;
import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerPassiveProductResponse;
import com.nttdata.bootcamp.CustomerProduct.domain.entity.CustomerPassiveProduct;

public interface ICustomerPassiveProductMapper {
    CustomerPassiveProduct toEntity(CustomerPassiveProductRequest request);

    CustomerPassiveProductResponse toResponse(CustomerPassiveProduct customerPassiveProduct);
}
