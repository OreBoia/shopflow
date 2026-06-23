package com.shopflow.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String categoryName;
    private LocalDateTime createdAt;

    public static ProductResponse from(Product product) {
        ProductResponse response = new ProductResponse();
        response.id = product.getId();
        response.name = product.getName();
        response.description = product.getDescription();
        response.price = product.getPrice();
        response.stock = product.getStock();
        response.categoryName = product.getCategory() != null
                ? product.getCategory().getName()
                : null;
        response.createdAt = product.getCreatedAt();
        return response;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public Integer getStock() { return stock; }
    public String getCategoryName() { return categoryName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
