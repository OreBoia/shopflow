package com.shopflow.invoice;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO per la risposta contenente i dati di una fattura.
 */
public class InvoiceResponse {

    private Long id;
    private String customerName;
    private String customerEmail;
    private BigDecimal amount;
    private String notes;
    private LocalDateTime createdAt;

    public static InvoiceResponse from(Invoice invoice) {
        InvoiceResponse response = new InvoiceResponse();
        response.id = invoice.getId();
        response.customerName = invoice.getCustomerName();
        response.customerEmail = invoice.getCustomerEmail();
        response.amount = invoice.getAmount();
        response.notes = invoice.getNotes();
        response.createdAt = invoice.getCreatedAt();
        return response;
    }

    public Long getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getCustomerEmail() { return customerEmail; }
    public BigDecimal getAmount() { return amount; }
    public String getNotes() { return notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
