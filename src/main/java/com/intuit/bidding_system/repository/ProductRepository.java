package com.intuit.bidding_system.repository;

import com.intuit.bidding_system.entity.Category;
import com.intuit.bidding_system.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByIsActive(Boolean isActive);

    List<Product> findByCategory(Category category);

    Optional<Product> findByProductId(Long productId);
}
