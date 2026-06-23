-- ShopFlow — Schema iniziale del database
-- V1__init_schema.sql

CREATE TABLE categories (
    id      BIGSERIAL PRIMARY KEY,
    name    VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(500)
);

CREATE TABLE customers (
    id          BIGSERIAL PRIMARY KEY,
    first_name  VARCHAR(100) NOT NULL,
    last_name   VARCHAR(100) NOT NULL,
    email       VARCHAR(255) NOT NULL UNIQUE,
    phone       VARCHAR(20),
    created_at  TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE products (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(200) NOT NULL,
    description TEXT,
    price       NUMERIC(10,2) NOT NULL,
    stock       INTEGER NOT NULL DEFAULT 0,
    category_id BIGINT REFERENCES categories(id),
    created_at  TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE orders (
    id          BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL REFERENCES customers(id),
    status      VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    total       NUMERIC(10,2),
    created_at  TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE order_items (
    id          BIGSERIAL PRIMARY KEY,
    order_id    BIGINT NOT NULL REFERENCES orders(id),
    product_id  BIGINT NOT NULL REFERENCES products(id),
    quantity    INTEGER NOT NULL,
    unit_price  NUMERIC(10,2) NOT NULL
);

-- Dati di esempio
INSERT INTO categories (name, description) VALUES
    ('Elettronica', 'Dispositivi elettronici e accessori'),
    ('Abbigliamento', 'Capi di abbigliamento e accessori moda'),
    ('Libri', 'Libri, ebook e materiale didattico');

INSERT INTO customers (first_name, last_name, email, phone) VALUES
    ('Mario', 'Rossi', 'mario.rossi@email.it', '3331234567'),
    ('Giulia', 'Bianchi', 'giulia.bianchi@email.it', '3479876543');

INSERT INTO products (name, description, price, stock, category_id) VALUES
    ('Laptop Pro 15', 'Laptop ad alte prestazioni per sviluppatori', 1299.99, 10, 1),
    ('Mouse Wireless', 'Mouse ergonomico senza fili', 39.99, 50, 1),
    ('T-Shirt Dev', 'T-shirt con stampa "I love Java"', 19.99, 100, 2),
    ('Clean Code', 'Il libro di Robert C. Martin', 34.99, 30, 3);
