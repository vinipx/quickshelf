package com.quickshelf.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Data Transfer Object (DTO) for Product entities.
 * Used for transferring product data between the client and the server.
 * Contains validation constraints for input validation.
 */
public class ProductDto {
    
    /**
     * Unique identifier for the product.
     */
    private String id;
    
    /**
     * Name of the product. Cannot be blank.
     */
    @NotBlank(message = "Product name is required")
    private String name;
    
    /**
     * Description of the product. Optional field.
     */
    private String description;
    
    /**
     * Price of the product. Must be non-negative.
     */
    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price must be non-negative")
    private Float price;
    
    /**
     * Category of the product. Cannot be blank.
     */
    @NotBlank(message = "Category is required")
    private String category;
    
    /**
     * Current stock quantity of the product. Must be non-negative.
     */
    @NotNull(message = "Stock quantity is required")
    @PositiveOrZero(message = "Stock quantity must be non-negative")
    private Integer stockQuantity;
    
    /**
     * Default constructor.
     */
    public ProductDto() {
    }
    
    /**
     * Constructor with all fields.
     *
     * @param id            the product ID
     * @param name          the product name
     * @param description   the product description
     * @param price         the product price
     * @param category      the product category
     * @param stockQuantity the product stock quantity
     */
    public ProductDto(String id, String name, String description, Float price, String category, Integer stockQuantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
    }
    
    /**
     * Gets the product ID.
     *
     * @return the product ID
     */
    public String getId() {
        return id;
    }
    
    /**
     * Sets the product ID.
     *
     * @param id the product ID to set
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Gets the product name.
     *
     * @return the product name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the product name.
     *
     * @param name the product name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets the product description.
     *
     * @return the product description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Sets the product description.
     *
     * @param description the product description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Gets the product price.
     *
     * @return the product price
     */
    public Float getPrice() {
        return price;
    }
    
    /**
     * Sets the product price.
     *
     * @param price the product price to set
     */
    public void setPrice(Float price) {
        this.price = price;
    }
    
    /**
     * Gets the product category.
     *
     * @return the product category
     */
    public String getCategory() {
        return category;
    }
    
    /**
     * Sets the product category.
     *
     * @param category the product category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }
    
    /**
     * Gets the product stock quantity.
     *
     * @return the product stock quantity
     */
    public Integer getStockQuantity() {
        return stockQuantity;
    }
    
    /**
     * Sets the product stock quantity.
     *
     * @param stockQuantity the product stock quantity to set
     */
    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}