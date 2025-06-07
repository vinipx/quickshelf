package com.quickshelf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quickshelf.dto.ProductDto;
import com.quickshelf.exception.ResourceNotFoundException;
import com.quickshelf.model.Product;
import com.quickshelf.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private Product product;
    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        product = new Product("Test Product", "Test Description", 10.0f, "Test Category", 5);
        product.setId("test-id");

        productDto = new ProductDto("test-id", "Test Product", "Test Description", 10.0f, "Test Category", 5);
    }

    @Test
    void whenCreateProduct_thenReturnCreatedProduct() throws Exception {
        when(productService.createProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is("test-id")))
                .andExpect(jsonPath("$.name", is("Test Product")));

        verify(productService).createProduct(any(Product.class));
    }

    @Test
    void whenGetAllProducts_thenReturnProductList() throws Exception {
        when(productService.getAllProducts()).thenReturn(Arrays.asList(product));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("test-id")))
                .andExpect(jsonPath("$[0].name", is("Test Product")));

        verify(productService).getAllProducts();
    }

    @Test
    void whenGetProductById_thenReturnProduct() throws Exception {
        when(productService.getProductById("test-id")).thenReturn(Optional.of(product));

        mockMvc.perform(get("/products/test-id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("test-id")))
                .andExpect(jsonPath("$.name", is("Test Product")));

        verify(productService).getProductById("test-id");
    }

    @Test
    void whenGetProductByInvalidId_thenReturn404() throws Exception {
        when(productService.getProductById("invalid-id")).thenReturn(Optional.empty());

        mockMvc.perform(get("/products/invalid-id"))
                .andExpect(status().isNotFound());

        verify(productService).getProductById("invalid-id");
    }

    @Test
    void whenUpdateProduct_thenReturnUpdatedProduct() throws Exception {
        Product updatedProduct = new Product("Updated Product", "Updated Description", 20.0f, "Updated Category", 10);
        updatedProduct.setId("test-id");

        when(productService.updateProduct(eq("test-id"), any(Product.class))).thenReturn(updatedProduct);

        ProductDto updatedDto = new ProductDto("test-id", "Updated Product", "Updated Description", 20.0f, "Updated Category", 10);

        mockMvc.perform(put("/products/test-id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Product")))
                .andExpect(jsonPath("$.price", is(20.0)));

        verify(productService).updateProduct(eq("test-id"), any(Product.class));
    }

    @Test
    void whenUpdateProductWithInvalidId_thenReturn404() throws Exception {
        when(productService.updateProduct(eq("invalid-id"), any(Product.class)))
                .thenThrow(new ResourceNotFoundException("Product", "id", "invalid-id"));

        mockMvc.perform(put("/products/invalid-id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isNotFound());

        verify(productService).updateProduct(eq("invalid-id"), any(Product.class));
    }

    @Test
    void whenDeleteProduct_thenReturn204() throws Exception {
        when(productService.deleteProduct("test-id")).thenReturn(true);

        mockMvc.perform(delete("/products/test-id"))
                .andExpect(status().isNoContent());

        verify(productService).deleteProduct("test-id");
    }

    @Test
    void whenDeleteProductWithInvalidId_thenReturn404() throws Exception {
        when(productService.deleteProduct("invalid-id")).thenReturn(false);

        mockMvc.perform(delete("/products/invalid-id"))
                .andExpect(status().isNotFound());

        verify(productService).deleteProduct("invalid-id");
    }

    @Test
    void whenCreateInvalidProduct_thenReturn400() throws Exception {
        ProductDto invalidProduct = new ProductDto(null, "", null, -10.0f, "", -5);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidProduct)))
                .andExpect(status().isBadRequest());

        verify(productService, never()).createProduct(any(Product.class));
    }
}