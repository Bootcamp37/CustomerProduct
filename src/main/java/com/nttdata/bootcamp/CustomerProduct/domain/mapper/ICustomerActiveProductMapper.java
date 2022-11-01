package com.nttdata.bootcamp.CustomerProduct.domain.mapper;

import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerActiveProductRequest;
import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerActiveProductResponse;
import com.nttdata.bootcamp.CustomerProduct.domain.entity.CustomerActiveProduct;

public interface ICustomerActiveProductMapper {
    CustomerActiveProduct toEntity(CustomerActiveProductRequest request);

    CustomerActiveProductResponse toResponse(CustomerActiveProduct customerActiveProduct);
}
