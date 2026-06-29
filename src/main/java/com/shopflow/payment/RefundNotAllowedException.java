package com.shopflow.payment;

import com.shopflow.shared.exception.BaseAppException;

/**
 * Eccezione lanciata quando si tenta un rimborso su un pagamento il cui stato
 * non lo consente (es. già rimborsato, in timeout, o non ancora completato).
 * Mappa a una risposta HTTP 409 Conflict.
 */
public class RefundNotAllowedException extends BaseAppException {

    public RefundNotAllowedException(Long paymentId, PaymentStatus currentStatus) {
        super("Rimborso non consentito per il pagamento id " + paymentId
                + ": stato corrente '" + currentStatus + "' non rimborsabile");
    }
}
