package com.nttdata.bootcamp.customerproduct.infraestructure.repository;

import com.nttdata.bootcamp.customerproduct.domain.entity.CustomerPassiveProduct;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz del repositorio CustomerPassiveProductRepository.
 *
 * @author Pedro Manuel Díaz Santa María
 * @version 1.0.0
 */
@Repository
public interface ICustomerPassiveProductRepository extends
      ReactiveMongoRepository<CustomerPassiveProduct, String> {
}
