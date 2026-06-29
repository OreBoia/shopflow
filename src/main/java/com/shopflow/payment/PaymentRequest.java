package com.shopflow.payment;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public class PaymentRequest {

    @NotNull(message = "L'id dell'ordine è obbligatorio")
    private Long orderId;

    @NotNull(message = "L'importo è obbligatorio")
    @DecimalMin(value = "0.01", message = "L'importo deve essere maggiore di zero")
    private BigDecimal amount;

    @NotBlank(message = "Il numero di carta è obbligatorio")
    @Pattern(regexp = "\\d{16}", message = "Il numero di carta deve essere di 16 cifre")
    private String cardNumber;

    @NotBlank(message = "La data di scadenza è obbligatoria")
    @Pattern(regexp = "\\d{2}/\\d{2}", message = "La scadenza deve essere nel formato MM/AA")
    private String expiryDate;

    @NotBlank(message = "Il CVV è obbligatorio")
    @Pattern(regexp = "\\d{3,4}", message = "Il CVV deve essere di 3 o 4 cifre")
    private String cvv;

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getExpiryDate() { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }

    public String getCvv() { return cvv; }
    public void setCvv(String cvv) { this.cvv = cvv; }
}
