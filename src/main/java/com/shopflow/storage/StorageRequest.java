package com.shopflow.storage;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class StorageRequest {

    @NotNull(message = "Il prodotto e obbligatorio")
    private Long productId;

    @NotBlank(message = "La posizione e obbligatoria")
    @Size(max = 120, message = "La posizione non puo superare 120 caratteri")
    private String location;

    @NotNull(message = "La quantita e obbligatoria")
    @Min(value = 0, message = "La quantita non puo essere negativa")
    private Integer quantity;

    @NotNull(message = "La disponibilita e obbligatoria")
    private Boolean available;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
