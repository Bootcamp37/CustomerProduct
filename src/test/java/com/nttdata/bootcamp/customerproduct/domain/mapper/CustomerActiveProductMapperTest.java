package com.nttdata.bootcamp.customerproduct.domain.mapper;

import com.nttdata.bootcamp.customerproduct.domain.dto.CustomerActiveProductRequest;
import com.nttdata.bootcamp.customerproduct.domain.dto.CustomerActiveProductResponse;
import com.nttdata.bootcamp.customerproduct.domain.entity.CustomerActiveProduct;
import com.nttdata.bootcamp.customerproduct.infraestructure.ICustomerActiveProductMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerActiveProductMapperTest {

  private ICustomerActiveProductMapper mapper = new CustomerActiveProductMapper();
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
    var customerActiveProductRequest = new CustomerActiveProductRequest(900.0, 9.0);
    customerActiveProductRequest.setCustomerId("1a1");
    customerActiveProductRequest.setProductId("1b1");
    var customerActiveProduct = new CustomerActiveProduct(null, "1a1", "1b1", 900.0, 9.0);
    assertEquals(mapper.toEntity(customerActiveProductRequest), customerActiveProduct);
  }

  @Test
  void toResponse() {
    var customerActiveProduct = new CustomerActiveProduct("111", "1a1", "1b1", 900.0, 9.0);
    var customerActiveProductResponse = new CustomerActiveProductResponse();
    customerActiveProductResponse.setId("111");
    customerActiveProductResponse.setCustomerId("1a1");
    customerActiveProductResponse.setCustomerUrl(urlCustomer + pathCustomer + "1a1");
    customerActiveProductResponse.setProductUrl(urlProduct + pathProduct + "1b1");
    customerActiveProductResponse.setProductId("1b1");
    customerActiveProductResponse.setLineOfCredit(900.0);
    customerActiveProductResponse.setDebt(9.0);
    assertEquals(mapper.toResponse(customerActiveProduct), customerActiveProductResponse);
  }
}