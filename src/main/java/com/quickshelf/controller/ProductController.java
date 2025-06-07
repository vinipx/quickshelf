package com.quickshelf.controller;

import com.quickshelf.dto.ProductDto;
import com.quickshelf.exception.ResourceNotFoundException;
import com.quickshelf.model.Product;
import com.quickshelf.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing products.
 * Provides endpoints for CRUD operations on products.
 */
@RestController
@RequestMapping("/products")
@Tag(name = "Product", description = "Product management APIs")
public class ProductController {

    private final ProductService productService;

    /**
     * Constructor for dependency injection.
     *
     * @param productService the service for product operations
     */
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Creates a new product.
     *
     * @param productDto the product data transfer object
     * @return the created product with HTTP status 201 (Created)
     */
    @PostMapping
    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        Product product = convertToEntity(productDto);
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(convertToDto(createdProduct), HttpStatus.CREATED);
    }

    /**
     * Retrieves all products.
     *
     * @return list of all products with HTTP status 200 (OK)
     */
    @GetMapping
    @Operation(summary = "Get all products")
    @ApiResponse(responseCode = "200", description = "List of products retrieved successfully")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> productDtos = products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDtos);
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the product ID
     * @return the product if found with HTTP status 200 (OK)
     * @throws ResourceNotFoundException if the product is not found
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDto> getProductById(
            @Parameter(description = "Product ID", required = true)
            @PathVariable String id) {
        return productService.getProductById(id)
                .map(product -> ResponseEntity.ok(convertToDto(product)))
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    /**
     * Updates an existing product.
     *
     * @param id the product ID
     * @param productDto the updated product data
     * @return the updated product with HTTP status 200 (OK)
     * @throws ResourceNotFoundException if the product is not found
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDto> updateProduct(
            @Parameter(description = "Product ID", required = true)
            @PathVariable String id,
            @Valid @RequestBody ProductDto productDto) {
        Product product = convertToEntity(productDto);
        Product updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(convertToDto(updatedProduct));
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the product ID
     * @return HTTP status 204 (No Content) if deleted successfully
     * @throws ResourceNotFoundException if the product is not found
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Product ID", required = true)
            @PathVariable String id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            throw new ResourceNotFoundException("Product", "id", id);
        }
    }

    /**
     * Converts a Product entity to a ProductDto.
     *
     * @param product the product entity
     * @return the product data transfer object
     */
    private ProductDto convertToDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory(),
                product.getStockQuantity()
        );
    }

    /**
     * Converts a ProductDto to a Product entity.
     *
     * @param productDto the product data transfer object
     * @return the product entity
     */
    private Product convertToEntity(ProductDto productDto) {
        Product product = new Product(
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice(),
                productDto.getCategory(),
                productDto.getStockQuantity()
        );
        if (productDto.getId() != null) {
            product.setId(productDto.getId());
        }
        return product;
    }
}