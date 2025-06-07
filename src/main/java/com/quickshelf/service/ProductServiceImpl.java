package com.quickshelf.service;

import com.quickshelf.exception.ResourceNotFoundException;
import com.quickshelf.model.Product;
import com.quickshelf.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the ProductService interface.
 * Provides business logic for product management operations.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    /**
     * Constructor for dependency injection.
     *
     * @param productRepository the repository for product data access
     */
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * {@inheritDoc}
     * Saves a new product to the database.
     */
    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * {@inheritDoc}
     * Retrieves all products from the database.
     */
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * {@inheritDoc}
     * Retrieves a product by its ID.
     */
    @Override
    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     * Updates an existing product with new details.
     * Throws ResourceNotFoundException if the product with the given ID is not found.
     */
    @Override
    public Product updateProduct(String id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setCategory(productDetails.getCategory());
        product.setStockQuantity(productDetails.getStockQuantity());

        return productRepository.save(product);
    }

    /**
     * {@inheritDoc}
     * Deletes a product by its ID.
     * Returns true if the product was found and deleted, false otherwise.
     */
    @Override
    public boolean deleteProduct(String id) {
        return productRepository.findById(id)
                .map(product -> {
                    productRepository.delete(product);
                    return true;
                })
                .orElse(false);
    }
}