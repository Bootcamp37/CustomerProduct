package com.nttdata.bootcamp.CustomerProduct.domain.mapper;

import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerActiveProductRequest;
import com.nttdata.bootcamp.CustomerProduct.domain.dto.CustomerActiveProductResponse;
import com.nttdata.bootcamp.CustomerProduct.domain.entity.CustomerActiveProduct;
import com.nttdata.bootcamp.CustomerProduct.infraestructure.ICustomerActiveProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
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
        log.info("====> CustomerActiveProductMapper: ToEntity");
        CustomerActiveProduct customerActiveProduct = new CustomerActiveProduct();
        BeanUtils.copyProperties(request, customerActiveProduct);
        return customerActiveProduct;
    }

    @Override
    public CustomerActiveProductResponse toResponse(@NotNull CustomerActiveProduct customerActiveProduct) {
        log.info("====> CustomerActiveProductMapper: ToResponse");
        CustomerActiveProductResponse customerActiveProductResponse = new CustomerActiveProductResponse();
        BeanUtils.copyProperties(customerActiveProduct, customerActiveProductResponse);
        customerActiveProductResponse.setCustomerUrl(customerDomain + customerPath + customerActiveProduct.getCustomerId());
        customerActiveProductResponse.setProductUrl(productDomain + productPath + customerActiveProduct.getProductId());
        return customerActiveProductResponse;
    }
}
