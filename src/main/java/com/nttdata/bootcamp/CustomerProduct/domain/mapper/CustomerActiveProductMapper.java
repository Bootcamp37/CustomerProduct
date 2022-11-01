package com.nttdata.bootcamp.CustomerProduct.domain.mapper;

import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerActiveProductRequest;
import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerActiveProductResponse;
import com.nttdata.bootcamp.CustomerProduct.domain.entity.CustomerActiveProduct;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomerActiveProductMapper implements ICustomerActiveProductMapper {
    @Value("${message.path-customerDomain}")
    String customerDomain;
    @Value("${message.path-customerResponse}")
    String customerPath;

    @Value("${message.path-productDomain}")
    String productDomain;
    @Value("${message.path-productResponse}")
    String productPath;

    @Override
    public CustomerActiveProduct toEntity(@NotNull CustomerActiveProductRequest request) {
        CustomerActiveProduct customerActiveProduct = new CustomerActiveProduct();
        customerActiveProduct.setCustomerId(request.getCustomerId());
        customerActiveProduct.setProductId(request.getProductId());
        customerActiveProduct.setLineOfCredit(request.getLineOfCredit());
        customerActiveProduct.setDebt(request.getDebt());
        return customerActiveProduct;
    }

    @Override
    public CustomerActiveProductResponse toResponse(@NotNull CustomerActiveProduct customerActiveProduct) {
        CustomerActiveProductResponse customerActiveProductResponse = new CustomerActiveProductResponse();
        customerActiveProductResponse.setId(customerActiveProduct.getId());
        customerActiveProductResponse.setCustomerId(customerActiveProduct.getCustomerId());
        customerActiveProductResponse.setProductId(customerActiveProduct.getProductId());
        customerActiveProductResponse.setLineOfCredit(customerActiveProduct.getLineOfCredit());
        customerActiveProductResponse.setDebt(customerActiveProduct.getDebt());
        customerActiveProductResponse.setCustomerUrl(customerDomain + customerPath + customerActiveProduct.getCustomerId());
        customerActiveProductResponse.setProductUrl(productDomain + productPath + customerActiveProduct.getProductId());
        return customerActiveProductResponse;
    }
}
