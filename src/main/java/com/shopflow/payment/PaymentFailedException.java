package com.shopflow.payment;

import com.shopflow.shared.exception.BaseAppException;

/**
 * Eccezione lanciata quando il gateway di pagamento rifiuta la transazione.
 * Mappa a una risposta HTTP 422 Unprocessable Entity.
 */
public class PaymentFailedException extends BaseAppException {

    public PaymentFailedException(String reason) {
        super("Pagamento rifiutato dal gateway: " + reason);
    }
}
