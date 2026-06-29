package com.shopflow.payment;

import com.shopflow.shared.exception.BaseAppException;

/**
 * Eccezione lanciata quando il gateway di pagamento non risponde entro il timeout di 3 secondi.
 * Mappa a una risposta HTTP 504 Gateway Timeout.
 */
public class PaymentGatewayTimeoutException extends BaseAppException {

    public PaymentGatewayTimeoutException(String message) {
        super(message);
    }
}
