package com.shopflow.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class OrderRequest {

    @NotNull(message = "Il cliente è obbligatorio")
    private Long customerId;

    @NotEmpty(message = "L'ordine deve contenere almeno un prodotto")
    private List<OrderItemRequest> items;

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public List<OrderItemRequest> getItems() { return items; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }

    public static class OrderItemRequest {

        @NotNull(message = "Il prodotto è obbligatorio")
        private Long productId;

        @NotNull
        @Min(value = 1, message = "La quantità deve essere almeno 1")
        private Integer quantity;

        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }

        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }
}
