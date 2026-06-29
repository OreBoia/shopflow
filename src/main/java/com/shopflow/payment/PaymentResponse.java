package com.shopflow.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentResponse {

    private Long id;
    private Long orderId;
    private BigDecimal amount;
    private PaymentStatus status;
    private String gatewayTransactionId;
    private String failureReason;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
    private LocalDateTime refundedAt;
    private String refundGatewayId;

    public static PaymentResponse from(Payment payment) {
        PaymentResponse response = new PaymentResponse();
        response.id = payment.getId();
        response.orderId = payment.getOrder().getId();
        response.amount = payment.getAmount();
        response.status = payment.getStatus();
        response.gatewayTransactionId = payment.getGatewayTransactionId();
        response.failureReason = payment.getFailureReason();
        response.createdAt = payment.getCreatedAt();
        response.processedAt = payment.getProcessedAt();
        response.refundedAt = payment.getRefundedAt();
        response.refundGatewayId = payment.getRefundGatewayId();
        return response;
    }

    public Long getId() { return id; }
    public Long getOrderId() { return orderId; }
    public BigDecimal getAmount() { return amount; }
    public PaymentStatus getStatus() { return status; }
    public String getGatewayTransactionId() { return gatewayTransactionId; }
    public String getFailureReason() { return failureReason; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getProcessedAt() { return processedAt; }
    public LocalDateTime getRefundedAt() { return refundedAt; }
    public String getRefundGatewayId() { return refundGatewayId; }
}
