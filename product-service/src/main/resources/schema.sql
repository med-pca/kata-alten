CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(100) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    image VARCHAR(500),
    category VARCHAR(100),
    price DOUBLE,
    quantity INT,
    internal_reference VARCHAR(255),
    shell_id BIGINT,
    inventory_status VARCHAR(20), -- Expecting values: 'INSTOCK', 'LOWSTOCK', 'OUTOFSTOCK'
    rating DOUBLE,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE TABLE cart_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_email VARCHAR(255) NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL
);

CREATE TABLE IF NOT EXISTS wishlist_items (
    id IDENTITY PRIMARY KEY,
    user_email VARCHAR(255) NOT NULL,
    product_id BIGINT NOT NULL
);


