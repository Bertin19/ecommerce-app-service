package de.bertin.ecommerce.repository;

import de.bertin.ecommerce.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {
}
