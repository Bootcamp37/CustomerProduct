package com.nttdata.bootcamp.CustomerProduct.domain.mapper;

import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerPassiveProductRequest;
import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerPassiveProductResponse;
import com.nttdata.bootcamp.CustomerProduct.domain.entity.CustomerPassiveProduct;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomerPassiveProductMapper implements ICustomerPassiveProductMapper {
    @Value("${message.path-customerDomain}")
    String customerDomain;
    @Value("${message.path-customerResponse}")
    String customerPath;

    @Value("${message.path-productDomain}")
    String productDomain;
    @Value("${message.path-productResponse}")
    String productPath;

    @Override
    public CustomerPassiveProduct toEntity(@NotNull CustomerPassiveProductRequest request) {
        CustomerPassiveProduct customerPassiveProduct = new CustomerPassiveProduct();
        customerPassiveProduct.setCustomerId(request.getCustomerId());
        customerPassiveProduct.setProductId(request.getProductId());
        customerPassiveProduct.setMaintenance(request.getMaintenance());
        customerPassiveProduct.setMovementLimit(request.getMovementLimit());
        customerPassiveProduct.setMovementDay(request.getMovementDay());
        customerPassiveProduct.setMovementMonth(request.getMovementMonth());
        customerPassiveProduct.setAmount(request.getAmount());
        return customerPassiveProduct;
    }

    @Override
    public CustomerPassiveProductResponse toResponse(@NotNull CustomerPassiveProduct customerPassiveProduct) {
        CustomerPassiveProductResponse customerPassiveProductResponse = new CustomerPassiveProductResponse();
        customerPassiveProductResponse.setId(customerPassiveProduct.getId());
        customerPassiveProductResponse.setCustomerId(customerPassiveProduct.getCustomerId());
        customerPassiveProductResponse.setProductId(customerPassiveProduct.getProductId());
        customerPassiveProductResponse.setMaintenance(customerPassiveProduct.getMaintenance());
        customerPassiveProductResponse.setMovementLimit(customerPassiveProduct.getMovementLimit());
        customerPassiveProductResponse.setMovementDay(customerPassiveProduct.getMovementDay());
        customerPassiveProductResponse.setMovementMonth(customerPassiveProduct.getMovementMonth());
        customerPassiveProductResponse.setCustomerUrl(customerDomain + customerPath + customerPassiveProduct.getCustomerId());
        customerPassiveProductResponse.setProductUrl(productDomain + productPath + customerPassiveProduct.getProductId());
        customerPassiveProductResponse.setAmount(customerPassiveProduct.getAmount());
        return customerPassiveProductResponse;
    }
}
