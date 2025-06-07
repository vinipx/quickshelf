package com.quickshelf.service;

import com.quickshelf.exception.ResourceNotFoundException;
import com.quickshelf.model.Product;
import com.quickshelf.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product("Test Product", "Test Description", 10.0f, "Test Category", 5);
        product.setId("test-id");
    }

    @Test
    void whenCreateProduct_thenProductIsSaved() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product created = productService.createProduct(product);

        assertNotNull(created);
        assertEquals("Test Product", created.getName());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void whenGetAllProducts_thenReturnProductList() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product));

        List<Product> products = productService.getAllProducts();

        assertEquals(1, products.size());
        verify(productRepository).findAll();
    }

    @Test
    void whenGetProductById_thenReturnProduct() {
        when(productRepository.findById("test-id")).thenReturn(Optional.of(product));

        Optional<Product> found = productService.getProductById("test-id");

        assertTrue(found.isPresent());
        assertEquals("Test Product", found.get().getName());
        verify(productRepository).findById("test-id");
    }

    @Test
    void whenUpdateProduct_thenReturnUpdatedProduct() {
        Product updatedProduct = new Product("Updated Product", "Updated Description", 20.0f, "Updated Category", 10);
        
        when(productRepository.findById("test-id")).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product result = productService.updateProduct("test-id", updatedProduct);

        assertEquals("Updated Product", result.getName());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(20.0f, result.getPrice());
        assertEquals("Updated Category", result.getCategory());
        assertEquals(10, result.getStockQuantity());
        verify(productRepository).findById("test-id");
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void whenUpdateProductWithInvalidId_thenThrowException() {
        when(productRepository.findById("invalid-id")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            productService.updateProduct("invalid-id", product);
        });
        
        verify(productRepository).findById("invalid-id");
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void whenDeleteProduct_thenReturnTrue() {
        when(productRepository.findById("test-id")).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(any(Product.class));

        boolean result = productService.deleteProduct("test-id");

        assertTrue(result);
        verify(productRepository).findById("test-id");
        verify(productRepository).delete(any(Product.class));
    }

    @Test
    void whenDeleteProductWithInvalidId_thenReturnFalse() {
        when(productRepository.findById("invalid-id")).thenReturn(Optional.empty());

        boolean result = productService.deleteProduct("invalid-id");

        assertFalse(result);
        verify(productRepository).findById("invalid-id");
        verify(productRepository, never()).delete(any(Product.class));
    }
}