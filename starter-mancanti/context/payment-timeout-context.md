# Payment Timeout Context

## Obiettivo

- Implementare gestione robusta timeout/failure nel flusso pagamento.
- Verificare persistenza dello stato pagamento anche in caso di errore gateway.

## Classi principali coinvolte

- PaymentService: orchestration del flusso di pagamento e rimborso.
- PaymentGatewayClient: integrazione esterna, possibile timeout.
- PaymentRepository: persistenza stato pagamento.
- GlobalExceptionHandler: mapping eccezioni in risposta REST consistente.

## Flusso applicativo atteso

1. Controller riceve PaymentRequest validato.
2. Service recupera Order e crea Payment in stato iniziale.
3. Service invoca gatewayClient.charge(...).
4. Se timeout: stato TIMEOUT + failureReason + rilancio eccezione.
5. Se failure: stato FAILED + failureReason + rilancio eccezione.
6. Handler converte eccezioni in HTTP 422/504 senza stacktrace.

## Vincoli tecnici

- Nessun ritorno null da metodi pubblici.
- Nessun System.out o printStackTrace.
- Eccezioni custom che estendono BaseAppException.
- Tutte le risposte errore passano da GlobalExceptionHandler.

## Prompt suggerito

"Usando questo contesto, implementa i test unitari di PaymentService per timeout e failure gateway e i test WebMvc di GlobalExceptionHandler, senza cambiare comportamento del codice applicativo." 
