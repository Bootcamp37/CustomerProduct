package com.nttdata.bootcamp.customerproduct.domain.mapper;

import com.nttdata.bootcamp.customerproduct.domain.dto.CustomerActiveProductRequest;
import com.nttdata.bootcamp.customerproduct.domain.dto.CustomerPassiveProductRequest;
import com.nttdata.bootcamp.customerproduct.domain.dto.CustomerPassiveProductResponse;
import com.nttdata.bootcamp.customerproduct.domain.entity.CustomerPassiveProduct;
import com.nttdata.bootcamp.customerproduct.infraestructure.ICustomerPassiveProductMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.jupiter.api.Assertions.*;

class CustomerPassiveProductMapperTest {

  private ICustomerPassiveProductMapper mapper = new CustomerPassiveProductMapper();
  @Value("${message.path-customerDomain}")
  private String urlCustomer;
  @Value("${message.path-customerResponse}")
  private String pathCustomer;
  @Value("${message.path-productDomain}")
  private String urlProduct;
  @Value("${message.path-productResponse}")
  private String pathProduct;
  @Test
  void toEntity() {
    var customerPassiveProductRequest = new CustomerPassiveProductRequest(15.0, 15, 0, 500.0, 5, 0.15, 0.0);
    customerPassiveProductRequest.setCustomerId("1a1");
    customerPassiveProductRequest.setProductId("1b1");
    var customerPassiveProduct = new CustomerPassiveProduct(null, "1a1", "1b1",15.0, 15, 0, 500.0, 5, 0.15, 0.0);
    assertEquals(mapper.toEntity(customerPassiveProductRequest), customerPassiveProduct);
  }

  @Test
  void toResponse() {
    var customerPassiveProduct = new CustomerPassiveProduct("111", "1a1", "1b1",15.0, 15, 0, 500.0, 5, 0.15, 0.0);
    var customerPassiveProductResponse = new CustomerPassiveProductResponse();
    customerPassiveProductResponse.setId("111");
    customerPassiveProductResponse.setCustomerId("1a1");
    customerPassiveProductResponse.setCustomerUrl(urlCustomer+pathCustomer+"1a1");
    customerPassiveProductResponse.setProductId("1b1");
    customerPassiveProductResponse.setProductUrl(urlProduct+pathProduct+"1b1");
    customerPassiveProductResponse.setMaintenance(15.0);
    customerPassiveProductResponse.setMovementLimit(15);
    customerPassiveProductResponse.setMovementDay(0);
    customerPassiveProductResponse.setAmount(500.0);
    customerPassiveProductResponse.setMaxMovementFree(5);
    customerPassiveProductResponse.setCommission(0.15);
    customerPassiveProductResponse.setMinAmount(0.0);
    assertEquals(mapper.toResponse(customerPassiveProduct), customerPassiveProductResponse);
  }
}