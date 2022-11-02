package com.nttdata.bootcamp.CustomerProduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CustomerProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerProductApplication.class, args);
    }

}
