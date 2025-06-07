package com.quickshelf.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quickshelf.dto.ProductDto;
import com.quickshelf.model.Product;
import com.quickshelf.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        // Create a test product
        testProduct = new Product("Integration Test Product", "Test Description", 15.0f, "Test Category", 10);
        testProduct = productRepository.save(testProduct);
    }

    @AfterEach
    void tearDown() {
        // Clean up the database after each test
        productRepository.deleteAll();
    }

    @Test
    void shouldCreateProduct() throws Exception {
        ProductDto newProduct = new ProductDto(null, "New Product", "New Description", 25.0f, "New Category", 20);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProduct)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("New Product")))
                .andExpect(jsonPath("$.price", is(25.0)))
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    void shouldGetAllProducts() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].id", is(testProduct.getId())));
    }

    @Test
    void shouldGetProductById() throws Exception {
        mockMvc.perform(get("/products/{id}", testProduct.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testProduct.getId())))
                .andExpect(jsonPath("$.name", is("Integration Test Product")));
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        ProductDto updatedProduct = new ProductDto(
                testProduct.getId(),
                "Updated Product",
                "Updated Description",
                30.0f,
                "Updated Category",
                25
        );

        mockMvc.perform(put("/products/{id}", testProduct.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Product")))
                .andExpect(jsonPath("$.price", is(30.0)));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        mockMvc.perform(delete("/products/{id}", testProduct.getId()))
                .andExpect(status().isNoContent());

        // Verify the product is deleted
        mockMvc.perform(get("/products/{id}", testProduct.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn404WhenProductNotFound() throws Exception {
        mockMvc.perform(get("/products/non-existent-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400WhenInvalidProductData() throws Exception {
        ProductDto invalidProduct = new ProductDto(null, "", null, -10.0f, "", -5);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidProduct)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is("Validation error")));
    }
}