-- Schema definition for QuickShelf application

-- Drop table if exists
DROP TABLE IF EXISTS product;

-- Create product table
CREATE TABLE product (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    price FLOAT NOT NULL,
    category VARCHAR(255) NOT NULL,
    stock_quantity INT NOT NULL
);