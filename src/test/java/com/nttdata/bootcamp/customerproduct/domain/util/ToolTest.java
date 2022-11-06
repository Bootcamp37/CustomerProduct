package com.nttdata.bootcamp.customerproduct.domain.util;

import com.nttdata.bootcamp.customerproduct.domain.dto.CustomerResponse;
import com.nttdata.bootcamp.customerproduct.domain.dto.ProductResponse;
import com.nttdata.bootcamp.customerproduct.domain.entity.CustomerSubType;
import com.nttdata.bootcamp.customerproduct.domain.entity.CustomerType;
import com.nttdata.bootcamp.customerproduct.domain.entity.ProductSubType;
import com.nttdata.bootcamp.customerproduct.domain.entity.ProductType;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToolTest {

  @Test
  void printLog() {
    var customerResponse = new CustomerResponse("1", "Nombre", "Apellido", CustomerType.PERSONAL, CustomerSubType.VIP);
    var productResponse = new ProductResponse("1", "Producto", "", ProductType.ACTIVE, ProductSubType.CREDITCARD, CustomerType.PERSONAL);
    var tuple2 = Mono.just(customerResponse).zipWith(Mono.just(productResponse)).block();
    assertEquals(Tool.printLog(tuple2), tuple2);
  }
}