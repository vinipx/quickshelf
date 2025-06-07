package com.quickshelf.repository;

import com.quickshelf.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Product entity.
 * Extends JpaRepository to inherit basic CRUD operations and pagination support.
 * The generic types specify the entity type (Product) and the type of its primary key (String).
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    // Spring Data JPA provides basic CRUD operations by default
    // Additional custom query methods can be added here if needed
}