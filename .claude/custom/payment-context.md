# Contesto: Modulo Pagamenti

## Panoramica

Modulo per l'elaborazione dei pagamenti e dei rimborsi di un ordine,
con integrazione verso un gateway di pagamento esterno. Stato: completo
(Entity, Repository, Service, Controller, DTO, enum, eccezioni custom).

## Classi principali

- `PaymentService` — orchestrazione del flusso di pagamento e rimborso (`@Transactional`)
- `PaymentGatewayClient` — integrazione con gateway esterno (`@Component`, **attualmente stub**)
- `PaymentRepository` — persistenza JPA; espone `findByOrderId(Long)`
- `PaymentController` — endpoint REST sotto `/api/payments`
- `Payment` — entità JPA (tabella `payments`), associata a `Order` (`@ManyToOne` LAZY)
- `PaymentStatus` — enum: `PENDING`, `SUCCESS`, `FAILED`, `TIMEOUT`, `REFUNDED`
- `PaymentRequest` / `PaymentResponse` — DTO input/output

## Endpoint REST

| Metodo | Path                            | Descrizione                      |
|--------|---------------------------------|----------------------------------|
| GET    | `/api/payments`                 | Tutti i pagamenti                |
| GET    | `/api/payments/{id}`            | Pagamento per id                 |
| GET    | `/api/payments/order/{orderId}` | Pagamenti di un ordine           |
| POST   | `/api/payments`                 | Elabora un nuovo pagamento (201) |
| POST   | `/api/payments/{id}/refund`     | Rimborsa un pagamento            |

## Flusso pagamento (`processPayment`)

1. Ricezione `PaymentRequest` dal controller (validazione Bean Validation)
2. Lookup dell'`Order` → `ResourceNotFoundException` se assente
3. Creazione `Payment` (stato iniziale `PENDING` via `@PrePersist`)
4. Chiamata a `gatewayClient.charge(...)`
5. Esito:
   - successo → stato `SUCCESS`, salva `gatewayTransactionId` e `processedAt`
   - `PaymentFailedException` → stato `FAILED`, salva `failureReason`, **rilancia**
   - `PaymentGatewayTimeoutException` → stato `TIMEOUT`, salva motivo, **rilancia**
6. Salvataggio su DB e ritorno di `PaymentResponse`

## Flusso rimborso (`processRefund`)

1. Lookup del `Payment` → `ResourceNotFoundException` se assente
2. Verifica stato: deve essere `SUCCESS`, altrimenti `RefundNotAllowedException`
3. Chiamata a `gatewayClient.refund(transactionId, amount)`
4. Stato → `REFUNDED`, salva `refundGatewayId` e `refundedAt`
5. Ritorno di `PaymentResponse`

## Eccezioni custom (estendono `BaseAppException`)

- `PaymentFailedException` → HTTP 422 (gateway rifiuta la transazione)
- `PaymentGatewayTimeoutException` → HTTP 504 (gateway non risponde entro 3s)
- `RefundNotAllowedException` → HTTP 409 (stato non rimborsabile)

## Validazione `PaymentRequest`

- `orderId` obbligatorio
- `amount` obbligatorio, ≥ 0.01
- `cardNumber` 16 cifre
- `expiryDate` formato `MM/AA`
- `cvv` 3 o 4 cifre

## Vincoli

- Nessuna logica di business nel controller
- Il gateway deve rispondere in max 3 secondi → gestire timeout (costante
  `GATEWAY_TIMEOUT_MS = 3000` in `PaymentGatewayClient`)
- Eccezioni custom estendono `BaseAppException`, mappate in `GlobalExceptionHandler`
