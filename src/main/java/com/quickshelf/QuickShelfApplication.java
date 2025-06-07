package com.quickshelf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the QuickShelf API.
 * This class serves as the entry point for the Spring Boot application.
 * 
 * <p>QuickShelf is a RESTful API server that allows users to perform CRUD operations
 * on product entries. Each product has attributes such as ID, name, description,
 * price, category, and stock quantity.</p>
 */
@SpringBootApplication
public class QuickShelfApplication {

    /**
     * The main method that starts the Spring Boot application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(QuickShelfApplication.class, args);
    }
}