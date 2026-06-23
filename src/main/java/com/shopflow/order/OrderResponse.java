package com.shopflow.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

    private Long id;
    private Long customerId;
    private String customerName;
    private OrderStatus status;
    private BigDecimal total;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;

    public static OrderResponse from(Order order) {
        OrderResponse response = new OrderResponse();
        response.id = order.getId();
        response.customerId = order.getCustomer().getId();
        response.customerName = order.getCustomer().getFirstName()
                + " " + order.getCustomer().getLastName();
        response.status = order.getStatus();
        response.total = order.getTotal();
        response.createdAt = order.getCreatedAt();
        response.items = order.getItems().stream()
                .map(OrderItemResponse::from)
                .toList();
        return response;
    }

    public Long getId() { return id; }
    public Long getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public OrderStatus getStatus() { return status; }
    public BigDecimal getTotal() { return total; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<OrderItemResponse> getItems() { return items; }

    public static class OrderItemResponse {
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal subtotal;

        public static OrderItemResponse from(OrderItem item) {
            OrderItemResponse r = new OrderItemResponse();
            r.productId = item.getProduct().getId();
            r.productName = item.getProduct().getName();
            r.quantity = item.getQuantity();
            r.unitPrice = item.getUnitPrice();
            r.subtotal = item.getSubtotal();
            return r;
        }

        public Long getProductId() { return productId; }
        public String getProductName() { return productName; }
        public Integer getQuantity() { return quantity; }
        public BigDecimal getUnitPrice() { return unitPrice; }
        public BigDecimal getSubtotal() { return subtotal; }
    }
}
