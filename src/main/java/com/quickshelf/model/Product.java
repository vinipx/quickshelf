package com.quickshelf.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Entity class representing a product in the QuickShelf inventory system.
 * Each product has a unique identifier, name, description, price, category, and stock quantity.
 */
@Entity
public class Product {

    /**
     * Unique identifier for the product, automatically generated as a UUID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * Name of the product. Cannot be blank.
     */
    @NotBlank(message = "Product name is required")
    @Column(nullable = false)
    private String name;

    /**
     * Description of the product. Optional field with maximum length of 1000 characters.
     */
    @Column(length = 1000)
    private String description;

    /**
     * Price of the product. Must be non-negative.
     */
    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price must be non-negative")
    @Column(nullable = false)
    private Float price;

    /**
     * Category of the product. Cannot be blank.
     */
    @NotBlank(message = "Category is required")
    @Column(nullable = false)
    private String category;

    /**
     * Current stock quantity of the product. Must be non-negative.
     */
    @NotNull(message = "Stock quantity is required")
    @PositiveOrZero(message = "Stock quantity must be non-negative")
    @Column(nullable = false)
    private Integer stockQuantity;

    /**
     * Default constructor required by JPA.
     */
    public Product() {
    }

    /**
     * Constructor with all fields except ID (which is auto-generated).
     *
     * @param name          the name of the product
     * @param description   the description of the product
     * @param price         the price of the product
     * @param category      the category of the product
     * @param stockQuantity the stock quantity of the product
     */
    public Product(String name, String description, Float price, String category, Integer stockQuantity) {
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