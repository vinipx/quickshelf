package com.quickshelf.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quickshelf.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LiveServerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api";
    }

    @Test
    void testCreateAndGetProduct() {
        // Create a product
        ProductDto newProduct = new ProductDto(null, "Live Test Product", "Test Description", 25.0f, "Test Category", 20);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProductDto> request = new HttpEntity<>(newProduct, headers);
        
        ResponseEntity<ProductDto> createResponse = restTemplate.postForEntity(
                getBaseUrl() + "/products", request, ProductDto.class);
        
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        assertNotNull(createResponse.getBody().getId());
        assertEquals("Live Test Product", createResponse.getBody().getName());
        
        // Get the created product
        String productId = createResponse.getBody().getId();
        ResponseEntity<ProductDto> getResponse = restTemplate.getForEntity(
                getBaseUrl() + "/products/" + productId, ProductDto.class);
        
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals(productId, getResponse.getBody().getId());
        assertEquals("Live Test Product", getResponse.getBody().getName());
    }

    @Test
    void testGetAllProducts() {
        // Create a product first
        ProductDto newProduct = new ProductDto(null, "Another Test Product", "Test Description", 30.0f, "Test Category", 15);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProductDto> request = new HttpEntity<>(newProduct, headers);
        
        restTemplate.postForEntity(getBaseUrl() + "/products", request, ProductDto.class);
        
        // Get all products
        ResponseEntity<ProductDto[]> response = restTemplate.getForEntity(
                getBaseUrl() + "/products", ProductDto[].class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    void testUpdateProduct() {
        // Create a product
        ProductDto newProduct = new ProductDto(null, "Product To Update", "Original Description", 40.0f, "Original Category", 25);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProductDto> createRequest = new HttpEntity<>(newProduct, headers);
        
        ResponseEntity<ProductDto> createResponse = restTemplate.postForEntity(
                getBaseUrl() + "/products", createRequest, ProductDto.class);
        
        String productId = createResponse.getBody().getId();
        
        // Update the product
        ProductDto updatedProduct = new ProductDto(
                productId, "Updated Product", "Updated Description", 45.0f, "Updated Category", 30);
        
        HttpEntity<ProductDto> updateRequest = new HttpEntity<>(updatedProduct, headers);
        
        restTemplate.put(getBaseUrl() + "/products/" + productId, updateRequest);
        
        // Get the updated product
        ResponseEntity<ProductDto> getResponse = restTemplate.getForEntity(
                getBaseUrl() + "/products/" + productId, ProductDto.class);
        
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals("Updated Product", getResponse.getBody().getName());
        assertEquals("Updated Description", getResponse.getBody().getDescription());
        assertEquals(45.0f, getResponse.getBody().getPrice());
    }

    @Test
    void testDeleteProduct() {
        // Create a product
        ProductDto newProduct = new ProductDto(null, "Product To Delete", "Test Description", 50.0f, "Test Category", 10);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProductDto> request = new HttpEntity<>(newProduct, headers);
        
        ResponseEntity<ProductDto> createResponse = restTemplate.postForEntity(
                getBaseUrl() + "/products", request, ProductDto.class);
        
        String productId = createResponse.getBody().getId();
        
        // Delete the product
        restTemplate.delete(getBaseUrl() + "/products/" + productId);
        
        // Try to get the deleted product
        ResponseEntity<String> getResponse = restTemplate.getForEntity(
                getBaseUrl() + "/products/" + productId, String.class);
        
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }

    @Test
    void testInvalidProductCreation() {
        // Try to create an invalid product
        ProductDto invalidProduct = new ProductDto(null, "", null, -10.0f, "", -5);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProductDto> request = new HttpEntity<>(invalidProduct, headers);
        
        ResponseEntity<String> response = restTemplate.postForEntity(
                getBaseUrl() + "/products", request, String.class);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Validation error"));
    }
}