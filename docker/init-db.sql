-- ShopFlow bootstrap SQL
-- Crea schema applicativo e inserisce dati di test.

BEGIN;

-- =====================================================================
-- Drop tabelle (ordine inverso alle foreign key)
-- =====================================================================
DROP TABLE IF EXISTS payments CASCADE;
DROP TABLE IF EXISTS storages CASCADE;
DROP TABLE IF EXISTS order_items CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS invoices CASCADE;
DROP TABLE IF EXISTS employees CASCADE;
DROP TABLE IF EXISTS customers CASCADE;
DROP TABLE IF EXISTS categories CASCADE;

-- =====================================================================
-- Tabelle base
-- =====================================================================
CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(500)
);

CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE employees (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    role VARCHAR(100) NOT NULL,
    salary DOUBLE PRECISION NOT NULL,
    hire_date DATE NOT NULL
);

CREATE TABLE invoices (
    id BIGSERIAL PRIMARY KEY,
    customer_name VARCHAR(200) NOT NULL,
    customer_email VARCHAR(255) NOT NULL,
    amount NUMERIC(10,2) NOT NULL,
    notes VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================================
-- Tabelle dipendenti
-- =====================================================================
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    price NUMERIC(10,2) NOT NULL,
    stock INTEGER NOT NULL,
    category_id BIGINT REFERENCES categories(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL REFERENCES customers(id),
    status VARCHAR(50) NOT NULL CHECK (status IN ('PENDING', 'CONFIRMED', 'SHIPPED', 'DELIVERED', 'CANCELLED')),
    total NUMERIC(10,2),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    product_id BIGINT NOT NULL REFERENCES products(id),
    quantity INTEGER NOT NULL,
    unit_price NUMERIC(10,2) NOT NULL
);

CREATE TABLE storages (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL REFERENCES products(id),
    location VARCHAR(120) NOT NULL,
    quantity INTEGER NOT NULL,
    available BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE payments (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES orders(id),
    amount NUMERIC(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'SUCCESS', 'FAILED', 'TIMEOUT', 'REFUNDED')),
    gateway_transaction_id VARCHAR(255),
    failure_reason TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    processed_at TIMESTAMP,
    refunded_at TIMESTAMP,
    refund_gateway_id VARCHAR(255)
);

-- =====================================================================
-- Indici utili
-- =====================================================================
CREATE INDEX idx_products_category_id ON products(category_id);
CREATE INDEX idx_orders_customer_id ON orders(customer_id);
CREATE INDEX idx_order_items_order_id ON order_items(order_id);
CREATE INDEX idx_order_items_product_id ON order_items(product_id);
CREATE INDEX idx_storages_product_id ON storages(product_id);
CREATE INDEX idx_payments_order_id ON payments(order_id);
CREATE INDEX idx_payments_status ON payments(status);

-- =====================================================================
-- Seed dati di test
-- =====================================================================
INSERT INTO categories (id, name, description) VALUES
(1, 'Notebook', 'Categoria per notebook e ultrabook'),
(2, 'Monitor', 'Monitor da ufficio e gaming'),
(3, 'Accessori', 'Periferiche e accessori vari')
ON CONFLICT (name) DO NOTHING;

INSERT INTO customers (id, first_name, last_name, email, phone, created_at) VALUES
(1, 'Mario', 'Rossi', 'mario.rossi@email.it', '3331234567', NOW() - INTERVAL '10 days'),
(2, 'Giulia', 'Bianchi', 'giulia.bianchi@email.it', '3349876543', NOW() - INTERVAL '7 days'),
(3, 'Luca', 'Verdi', 'luca.verdi@email.it', '3351112233', NOW() - INTERVAL '3 days')
ON CONFLICT (email) DO NOTHING;

INSERT INTO employees (id, first_name, last_name, email, role, salary, hire_date) VALUES
(1, 'Anna', 'Neri', 'anna.neri@shopflow.it', 'WAREHOUSE_MANAGER', 36000, DATE '2022-04-11'),
(2, 'Paolo', 'Ricci', 'paolo.ricci@shopflow.it', 'CUSTOMER_SUPPORT', 30000, DATE '2023-01-09')
ON CONFLICT (email) DO NOTHING;

INSERT INTO users (id, username, email, created_at) VALUES
(1, 'admin', 'admin@shopflow.it', NOW() - INTERVAL '30 days'),
(2, 'demo_user', 'demo@shopflow.it', NOW() - INTERVAL '5 days')
ON CONFLICT (email) DO NOTHING;

INSERT INTO products (id, name, description, price, stock, category_id, created_at) VALUES
(1, 'Ultrabook Pro 14', 'Notebook 14 pollici, 16GB RAM, 512GB SSD', 1299.90, 12, 1, NOW() - INTERVAL '8 days'),
(2, 'Monitor 27 QHD', 'Monitor IPS 27 pollici 2560x1440', 299.90, 25, 2, NOW() - INTERVAL '6 days'),
(3, 'Mouse Wireless', 'Mouse ergonomico con sensore ottico', 39.90, 60, 3, NOW() - INTERVAL '4 days')
ON CONFLICT DO NOTHING;

INSERT INTO orders (id, customer_id, status, total, created_at) VALUES
(1, 1, 'PENDING', 1339.80, NOW() - INTERVAL '2 days'),
(2, 2, 'CONFIRMED', 299.90, NOW() - INTERVAL '1 day')
ON CONFLICT DO NOTHING;

INSERT INTO order_items (id, order_id, product_id, quantity, unit_price) VALUES
(1, 1, 1, 1, 1299.90),
(2, 1, 3, 1, 39.90),
(3, 2, 2, 1, 299.90)
ON CONFLICT DO NOTHING;

INSERT INTO storages (id, product_id, location, quantity, available, created_at) VALUES
(1, 1, 'WH-A1', 12, TRUE, NOW() - INTERVAL '8 days'),
(2, 2, 'WH-B3', 25, TRUE, NOW() - INTERVAL '6 days'),
(3, 3, 'WH-C2', 60, TRUE, NOW() - INTERVAL '4 days')
ON CONFLICT DO NOTHING;

INSERT INTO payments (id, order_id, amount, status, gateway_transaction_id, failure_reason, created_at, processed_at, refunded_at, refund_gateway_id) VALUES
(1, 1, 1339.80, 'PENDING', NULL, NULL, NOW() - INTERVAL '2 days', NULL, NULL, NULL),
(2, 2, 299.90, 'SUCCESS', 'gw_tx_202406300001', NULL, NOW() - INTERVAL '1 day', NOW() - INTERVAL '1 day' + INTERVAL '5 minutes', NULL, NULL)
ON CONFLICT DO NOTHING;

INSERT INTO invoices (id, customer_name, customer_email, amount, notes, created_at) VALUES
(1, 'Mario Rossi', 'mario.rossi@email.it', 1339.80, 'Fattura ordine #1', NOW() - INTERVAL '2 days'),
(2, 'Giulia Bianchi', 'giulia.bianchi@email.it', 299.90, 'Fattura ordine #2', NOW() - INTERVAL '1 day')
ON CONFLICT DO NOTHING;

-- Allinea le sequenze dopo insert con ID espliciti
SELECT setval(pg_get_serial_sequence('categories', 'id'), COALESCE((SELECT MAX(id) FROM categories), 1), true);
SELECT setval(pg_get_serial_sequence('customers', 'id'), COALESCE((SELECT MAX(id) FROM customers), 1), true);
SELECT setval(pg_get_serial_sequence('employees', 'id'), COALESCE((SELECT MAX(id) FROM employees), 1), true);
SELECT setval(pg_get_serial_sequence('invoices', 'id'), COALESCE((SELECT MAX(id) FROM invoices), 1), true);
SELECT setval(pg_get_serial_sequence('users', 'id'), COALESCE((SELECT MAX(id) FROM users), 1), true);
SELECT setval(pg_get_serial_sequence('products', 'id'), COALESCE((SELECT MAX(id) FROM products), 1), true);
SELECT setval(pg_get_serial_sequence('orders', 'id'), COALESCE((SELECT MAX(id) FROM orders), 1), true);
SELECT setval(pg_get_serial_sequence('order_items', 'id'), COALESCE((SELECT MAX(id) FROM order_items), 1), true);
SELECT setval(pg_get_serial_sequence('storages', 'id'), COALESCE((SELECT MAX(id) FROM storages), 1), true);
SELECT setval(pg_get_serial_sequence('payments', 'id'), COALESCE((SELECT MAX(id) FROM payments), 1), true);

COMMIT;
