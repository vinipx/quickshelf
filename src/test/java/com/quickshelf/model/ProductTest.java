package com.quickshelf.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsAreValid_thenNoViolations() {
        Product product = new Product("Test Product", "Test Description", 10.0f, "Test Category", 5);
        
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenNameIsBlank_thenViolationOccurs() {
        Product product = new Product("", "Test Description", 10.0f, "Test Category", 5);
        
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        
        assertEquals(1, violations.size());
        assertEquals("Product name is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenPriceIsNegative_thenViolationOccurs() {
        Product product = new Product("Test Product", "Test Description", -10.0f, "Test Category", 5);
        
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        
        assertEquals(1, violations.size());
        assertEquals("Price must be non-negative", violations.iterator().next().getMessage());
    }

    @Test
    void whenStockQuantityIsNegative_thenViolationOccurs() {
        Product product = new Product("Test Product", "Test Description", 10.0f, "Test Category", -5);
        
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        
        assertEquals(1, violations.size());
        assertEquals("Stock quantity must be non-negative", violations.iterator().next().getMessage());
    }

    @Test
    void whenCategoryIsBlank_thenViolationOccurs() {
        Product product = new Product("Test Product", "Test Description", 10.0f, "", 5);
        
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        
        assertEquals(1, violations.size());
        assertEquals("Category is required", violations.iterator().next().getMessage());
    }
}