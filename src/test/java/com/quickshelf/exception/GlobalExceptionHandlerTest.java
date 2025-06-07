package com.quickshelf.exception;

import com.quickshelf.controller.ProductController;
import com.quickshelf.dto.ProductDto;
import com.quickshelf.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductController.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenResourceNotFound_thenReturnNotFoundStatus() throws Exception {
        when(productService.getProductById("non-existent-id")).thenThrow(
                new ResourceNotFoundException("Product", "id", "non-existent-id"));

        mockMvc.perform(get("/products/non-existent-id"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Product not found with id: 'non-existent-id'")));
    }

    @Test
    void whenValidationFails_thenReturnBadRequestStatus() throws Exception {
        ProductDto invalidProduct = new ProductDto();
        // Not setting required fields to trigger validation errors

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidProduct)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", is("Validation error")))
                .andExpect(jsonPath("$.errors.name", is("Product name is required")))
                .andExpect(jsonPath("$.errors.price", is("Price is required")))
                .andExpect(jsonPath("$.errors.category", is("Category is required")))
                .andExpect(jsonPath("$.errors.stockQuantity", is("Stock quantity is required")));
    }

    @Test
    void whenUpdateWithInvalidId_thenReturnNotFoundStatus() throws Exception {
        ProductDto productDto = new ProductDto(null, "Test Product", "Test Description", 10.0f, "Test Category", 5);

        when(productService.updateProduct(eq("non-existent-id"), any()))
                .thenThrow(new ResourceNotFoundException("Product", "id", "non-existent-id"));

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                .put("/products/non-existent-id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Product not found with id: 'non-existent-id'")));
    }
}