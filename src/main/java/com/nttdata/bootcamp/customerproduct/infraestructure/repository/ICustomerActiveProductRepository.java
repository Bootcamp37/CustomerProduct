package com.nttdata.bootcamp.customerproduct.infraestructure.repository;

import com.nttdata.bootcamp.customerproduct.domain.entity.CustomerActiveProduct;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz del tipo CustomerActiveProductRepository.
 *
 * @author Pedro Manuel Díaz Santa María
 * @version 1.0.0
 */
@Repository
public interface ICustomerActiveProductRepository extends
      ReactiveMongoRepository<CustomerActiveProduct, String> {
}
