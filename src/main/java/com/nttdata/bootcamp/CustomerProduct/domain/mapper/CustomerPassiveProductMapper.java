package com.nttdata.bootcamp.CustomerProduct.domain.mapper;

import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerPassiveProductRequest;
import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerPassiveProductResponse;
import com.nttdata.bootcamp.CustomerProduct.domain.entity.CustomerPassiveProduct;
import com.nttdata.bootcamp.CustomerProduct.infraestructure.ICustomerPassiveProductMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
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
        BeanUtils.copyProperties(request, customerPassiveProduct);
        return customerPassiveProduct;
    }

    @Override
    public CustomerPassiveProductResponse toResponse(@NotNull CustomerPassiveProduct customerPassiveProduct) {
        CustomerPassiveProductResponse customerPassiveProductResponse = new CustomerPassiveProductResponse();
        BeanUtils.copyProperties(customerPassiveProduct, customerPassiveProductResponse);
        customerPassiveProductResponse.setCustomerUrl(customerDomain + customerPath + customerPassiveProduct.getCustomerId());
        customerPassiveProductResponse.setProductUrl(productDomain + productPath + customerPassiveProduct.getProductId());
        return customerPassiveProductResponse;
    }
}
