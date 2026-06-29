package com.shopflow.payment;

import com.shopflow.order.Order;
import com.shopflow.order.OrderRepository;
import com.shopflow.shared.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service per l'orchestrazione del flusso di pagamento.
 * Coordina validazione, chiamata al gateway esterno, persistenza ed esito.
 */
@Service
@Transactional
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentGatewayClient gatewayClient;

    public PaymentService(PaymentRepository paymentRepository,
                          OrderRepository orderRepository,
                          PaymentGatewayClient gatewayClient) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.gatewayClient = gatewayClient;
    }

    /**
     * Recupera tutti i pagamenti presenti nel sistema.
     *
     * @return lista di {@link PaymentResponse}
     */
    @Transactional(readOnly = true)
    public List<PaymentResponse> findAll() {
        return paymentRepository.findAll()
                .stream()
                .map(PaymentResponse::from)
                .toList();
    }

    /**
     * Recupera un pagamento per id.
     *
     * @param id identificativo del pagamento
     * @return il {@link PaymentResponse} corrispondente
     * @throws ResourceNotFoundException se il pagamento non esiste
     */
    @Transactional(readOnly = true)
    public PaymentResponse findById(Long id) {
        return paymentRepository.findById(id)
                .map(PaymentResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", id));
    }

    /**
     * Recupera tutti i pagamenti associati a un ordine.
     *
     * @param orderId identificativo dell'ordine
     * @return lista di {@link PaymentResponse}
     */
    @Transactional(readOnly = true)
    public List<PaymentResponse> findByOrder(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .stream()
                .map(PaymentResponse::from)
                .toList();
    }

    /**
     * Elabora un nuovo pagamento seguendo il flusso:
     * validazione → gateway esterno → persistenza esito.
     *
     * @param request dati della richiesta di pagamento
     * @return il {@link PaymentResponse} con l'esito della transazione
     * @throws ResourceNotFoundException se l'ordine non esiste
     * @throws PaymentFailedException se il gateway rifiuta il pagamento
     * @throws PaymentGatewayTimeoutException se il gateway non risponde in tempo
     */
    public PaymentResponse processPayment(PaymentRequest request) {
        log.info("Avvio elaborazione pagamento per ordine id: {}", request.getOrderId());

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order", request.getOrderId()));

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(request.getAmount());

        try {
            String transactionId = gatewayClient.charge(
                    request.getCardNumber(),
                    request.getExpiryDate(),
                    request.getCvv(),
                    request.getAmount()
            );

            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setGatewayTransactionId(transactionId);
            payment.setProcessedAt(LocalDateTime.now());
            log.info("Pagamento approvato, transactionId: {}", transactionId);

        } catch (PaymentGatewayTimeoutException e) {
            payment.setStatus(PaymentStatus.TIMEOUT);
            payment.setFailureReason("Timeout del gateway");
            paymentRepository.save(payment);
            log.warn("Timeout gateway per ordine id: {}", request.getOrderId());
            throw e;

        } catch (PaymentFailedException e) {
            payment.setStatus(PaymentStatus.FAILED);
            payment.setFailureReason(e.getMessage());
            paymentRepository.save(payment);
            log.warn("Pagamento rifiutato per ordine id: {}", request.getOrderId());
            throw e;
        }

        Payment saved = paymentRepository.save(payment);
        log.info("Pagamento salvato con id: {}", saved.getId());
        return PaymentResponse.from(saved);
    }

    /**
     * Elabora il rimborso di un pagamento già completato con successo.
     * Il flusso è: verifica stato → chiamata gateway per storno → aggiornamento persistenza.
     *
     * @param paymentId identificativo del pagamento da rimborsare
     * @return il {@link PaymentResponse} aggiornato con stato {@code REFUNDED}
     * @throws ResourceNotFoundException se il pagamento non esiste
     * @throws RefundNotAllowedException se il pagamento non è in stato {@code SUCCESS}
     * @throws PaymentFailedException se il gateway rifiuta lo storno
     * @throws PaymentGatewayTimeoutException se il gateway non risponde in tempo
     */
    public PaymentResponse processRefund(Long paymentId) {
        log.info("Avvio rimborso per pagamento id: {}", paymentId);

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", paymentId));

        if (payment.getStatus() != PaymentStatus.SUCCESS) {
            throw new RefundNotAllowedException(paymentId, payment.getStatus());
        }

        String refundId = gatewayClient.refund(payment.getGatewayTransactionId(), payment.getAmount());

        payment.setStatus(PaymentStatus.REFUNDED);
        payment.setRefundGatewayId(refundId);
        payment.setRefundedAt(LocalDateTime.now());

        Payment saved = paymentRepository.save(payment);
        log.info("Rimborso completato per pagamento id: {}, refundId: {}", paymentId, refundId);
        return PaymentResponse.from(saved);
    }
}
