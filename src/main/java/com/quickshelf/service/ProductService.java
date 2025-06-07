package com.quickshelf.service;

import com.quickshelf.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing products in the QuickShelf inventory system.
 * Defines operations for creating, retrieving, updating, and deleting products.
 */
public interface ProductService {
    
    /**
     * Create a new product in the system.
     * 
     * @param product The product to create
     * @return The created product with generated ID
     */
    Product createProduct(Product product);
    
    /**
     * Get all products from the system.
     * 
     * @return List of all products
     */
    List<Product> getAllProducts();
    
    /**
     * Get a product by its ID.
     * 
     * @param id The product ID
     * @return Optional containing the product if found, empty otherwise
     */
    Optional<Product> getProductById(String id);
    
    /**
     * Update an existing product.
     * 
     * @param id The ID of the product to update
     * @param productDetails The updated product details
     * @return The updated product
     * @throws com.quickshelf.exception.ResourceNotFoundException if product not found
     */
    Product updateProduct(String id, Product productDetails);
    
    /**
     * Delete a product by its ID.
     * 
     * @param id The ID of the product to delete
     * @return true if deleted, false if not found
     */
    boolean deleteProduct(String id);
}