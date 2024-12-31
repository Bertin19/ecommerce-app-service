package de.bertin.ecommerce.repository;

import de.bertin.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findAllByIdInOrderById(List<String> productIds);
}
