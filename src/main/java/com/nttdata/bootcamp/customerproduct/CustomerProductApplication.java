package com.nttdata.bootcamp.customerproduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Programa principal.
 *
 * @author Pedro Manuel Díaz Santa María
 * @version 1.0.0
 */
@SpringBootApplication
@EnableEurekaClient
public class CustomerProductApplication {

  public static void main(String[] args) {
    SpringApplication.run(CustomerProductApplication.class, args);
  }

}
