# Contesto: Modulo Pagamenti

## Classi principali

- `PaymentService`: orchestrazione del flusso di pagamento
- `PaymentGatewayClient`: integrazione con gateway esterno
- `PaymentRepository`: persistenza su DB

## Flusso attuale

1. Ricezione `PaymentRequest` dal controller
2. Validazione importo e dati carta
3. Chiamata al gateway esterno
4. Salvataggio esito su DB
5. Notifica al cliente via `NotificationService`

## Vincoli

- Nessuna logica di business nel controller
- Il gateway risponde in max 3 secondi → gestire timeout
