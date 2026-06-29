package com.shopflow.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Client per l'integrazione con il gateway di pagamento esterno.
 * Gestisce il timeout di 3 secondi imposto dal gateway.
 *
 * Nota: implementazione stub — sostituire con la chiamata HTTP reale al gateway.
 */
@Component
public class PaymentGatewayClient {

    private static final Logger log = LoggerFactory.getLogger(PaymentGatewayClient.class);
    private static final int GATEWAY_TIMEOUT_MS = 3000;

    /**
     * Invia la richiesta di pagamento al gateway esterno.
     *
     * @param cardNumber numero della carta
     * @param expiryDate data di scadenza nel formato MM/AA
     * @param cvv codice di sicurezza
     * @param amount importo da addebitare
     * @return il transaction ID restituito dal gateway
     * @throws PaymentGatewayTimeoutException se il gateway non risponde entro 3 secondi
     * @throws com.shopflow.payment.PaymentFailedException se il gateway rifiuta il pagamento
     */
    public String charge(String cardNumber, String expiryDate, String cvv, BigDecimal amount) {
        log.info("Invio richiesta al gateway per importo: {}", amount);

        try {
            // Stub: simula latenza gateway — sostituire con chiamata HTTP reale
            simulateGatewayCall();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new PaymentGatewayTimeoutException("Gateway interrotto durante la chiamata");
        }

        String transactionId = UUID.randomUUID().toString();
        log.info("Gateway ha approvato il pagamento, transactionId: {}", transactionId);
        return transactionId;
    }

    /**
     * Richiede il rimborso di una transazione già addebitata al gateway esterno.
     *
     * @param transactionId il transaction ID della transazione originale
     * @param amount importo da rimborsare
     * @return il refund ID restituito dal gateway
     * @throws PaymentGatewayTimeoutException se il gateway non risponde entro 3 secondi
     * @throws PaymentFailedException se il gateway rifiuta il rimborso
     */
    public String refund(String transactionId, BigDecimal amount) {
        log.info("Invio richiesta rimborso al gateway per transactionId: {}, importo: {}", transactionId, amount);

        try {
            // Stub: simula latenza gateway — sostituire con chiamata HTTP reale
            simulateGatewayCall();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new PaymentGatewayTimeoutException("Gateway interrotto durante il rimborso");
        }

        String refundId = UUID.randomUUID().toString();
        log.info("Gateway ha approvato il rimborso, refundId: {}", refundId);
        return refundId;
    }

    private void simulateGatewayCall() throws InterruptedException {
        // Stub: nessuna latenza reale — rimuovere in produzione
    }
}
