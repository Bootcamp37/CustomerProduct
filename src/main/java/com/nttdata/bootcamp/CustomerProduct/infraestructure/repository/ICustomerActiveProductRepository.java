package com.nttdata.bootcamp.CustomerProduct.infraestructure.repository;

import com.nttdata.bootcamp.CustomerProduct.domain.entity.CustomerActiveProduct;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerActiveProductRepository extends ReactiveMongoRepository<CustomerActiveProduct, String> {
}
