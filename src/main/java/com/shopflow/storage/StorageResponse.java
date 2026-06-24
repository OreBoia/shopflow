package com.shopflow.storage;

import java.time.LocalDateTime;

public class StorageResponse {

    private Long id;
    private Long productId;
    private String productName;
    private String location;
    private Integer quantity;
    private boolean available;
    private LocalDateTime createdAt;

    public static StorageResponse from(Storage storage) {
        StorageResponse response = new StorageResponse();
        response.id = storage.getId();
        response.productId = storage.getProduct().getId();
        response.productName = storage.getProduct().getName();
        response.location = storage.getLocation();
        response.quantity = storage.getQuantity();
        response.available = storage.isAvailable();
        response.createdAt = storage.getCreatedAt();
        return response;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getLocation() {
        return location;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public boolean isAvailable() {
        return available;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
