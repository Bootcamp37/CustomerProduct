package com.nttdata.bootcamp.CustomerProduct.infraestructure.repository;

import com.nttdata.bootcamp.CustomerProduct.domain.entity.CustomerPassiveProduct;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerPassiveProductRepository extends ReactiveMongoRepository<CustomerPassiveProduct, String> {
}
