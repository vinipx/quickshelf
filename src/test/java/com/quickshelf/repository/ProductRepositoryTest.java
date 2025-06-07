package com.quickshelf.repository;

import com.quickshelf.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void whenSaveProduct_thenProductIsPersisted() {
        // Given
        Product product = new Product("Test Product", "Test Description", 10.0f, "Test Category", 5);
        
        // When
        Product savedProduct = productRepository.save(product);
        
        // Then
        assertNotNull(savedProduct.getId());
        assertEquals("Test Product", savedProduct.getName());
    }

    @Test
    void whenFindById_thenReturnProduct() {
        // Given
        Product product = new Product("Test Product", "Test Description", 10.0f, "Test Category", 5);
        entityManager.persist(product);
        entityManager.flush();
        
        // When
        Optional<Product> found = productRepository.findById(product.getId());
        
        // Then
        assertTrue(found.isPresent());
        assertEquals(product.getName(), found.get().getName());
    }

    @Test
    void whenFindAll_thenReturnAllProducts() {
        // Given
        Product product1 = new Product("Product 1", "Description 1", 10.0f, "Category 1", 5);
        Product product2 = new Product("Product 2", "Description 2", 20.0f, "Category 2", 10);
        
        entityManager.persist(product1);
        entityManager.persist(product2);
        entityManager.flush();
        
        // When
        List<Product> products = productRepository.findAll();
        
        // Then
        assertEquals(2, products.size());
    }

    @Test
    void whenDeleteProduct_thenProductIsRemoved() {
        // Given
        Product product = new Product("Test Product", "Test Description", 10.0f, "Test Category", 5);
        entityManager.persist(product);
        entityManager.flush();
        
        // When
        productRepository.deleteById(product.getId());
        Optional<Product> found = productRepository.findById(product.getId());
        
        // Then
        assertFalse(found.isPresent());
    }
}