package com.nttdata.bootcamp.customerproduct.domain.util;

import com.nttdata.bootcamp.customerproduct.domain.dto.CustomerResponse;
import com.nttdata.bootcamp.customerproduct.domain.dto.ProductResponse;
import lombok.extern.slf4j.Slf4j;
import reactor.util.function.Tuple2;

/**
 * Clase Tool.
 *
 * <p>Es una clase utilitaria.</p>
 *
 * @author Pedro Manuel Diaz Santa María
 * @version 1.0.0
 */
@Slf4j
public class Tool {
  public static Tuple2<CustomerResponse, ProductResponse> printLog(Tuple2<CustomerResponse,
        ProductResponse> tupla) {
    log.info("Resultado del Get = " + tupla.getT1().toString() + " - " + tupla.getT2().toString());
    return tupla;
  }
}
