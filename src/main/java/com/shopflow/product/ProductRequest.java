package com.shopflow.product;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ProductRequest {

    @NotBlank(message = "Il nome è obbligatorio")
    @Size(max = 200)
    private String name;

    private String description;

    @NotNull(message = "Il prezzo è obbligatorio")
    @DecimalMin(value = "0.01", message = "Il prezzo deve essere maggiore di zero")
    private BigDecimal price;

    @NotNull(message = "Lo stock è obbligatorio")
    @Min(value = 0, message = "Lo stock non può essere negativo")
    private Integer stock;

    private Long categoryId;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
}
