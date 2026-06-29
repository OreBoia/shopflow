CREATE TABLE payments (
    id                    BIGSERIAL PRIMARY KEY,
    order_id              BIGINT        NOT NULL REFERENCES orders(id),
    amount                NUMERIC(10,2) NOT NULL,
    status                VARCHAR(20)   NOT NULL,
    gateway_transaction_id VARCHAR(255),
    failure_reason        TEXT,
    created_at            TIMESTAMP     NOT NULL,
    processed_at          TIMESTAMP
);

CREATE INDEX idx_payments_order_id ON payments(order_id);
CREATE INDEX idx_payments_status    ON payments(status);
