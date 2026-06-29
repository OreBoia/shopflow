package com.shopflow.invoice;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * DTO per la creazione di una nuova fattura.
 */
public class InvoiceRequest {

    @NotBlank(message = "Il nome cliente è obbligatorio")
    @Size(max = 200, message = "Il nome cliente non può superare 200 caratteri")
    private String customerName;

    @NotBlank(message = "L'email cliente è obbligatoria")
    @Email(message = "Formato email non valido")
    private String customerEmail;

    @NotNull(message = "L'importo è obbligatorio")
    @DecimalMin(value = "0.01", message = "L'importo deve essere maggiore di zero")
    private BigDecimal amount;

    @Size(max = 500, message = "Le note non possono superare 500 caratteri")
    private String notes;

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
