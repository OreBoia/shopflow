package com.shopflow.legacy;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.shopflow.customer.Customer;
import com.shopflow.customer.CustomerRepository;

/**
 * Elabora ordini applicando sconti per tipo cliente, IVA e sconti volume.
 *
 * <p>Refactoring del modulo legacy originale — comportamento invariato,
 * code smell rimossi (magic numbers, duplicazione, null return, System.out).
 */
@Component
public class LegacyOrderProcessor {

    private static final Logger log = LoggerFactory.getLogger(LegacyOrderProcessor.class);

    private static final double VIP_DISCOUNT    = 0.20;
    private static final double MEMBER_DISCOUNT = 0.10;
    private static final double VAT_RATE        = 0.22;
    private static final double EXPORT_TAX_RATE = 0.10;
    private static final double BULK_THRESHOLD  = 1000.0;
    private static final double BULK_DISCOUNT   = 0.05;

    private final CustomerRepository customerRepository;

    public LegacyOrderProcessor(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Traduce un codice numerico di stato ordine nella stringa corrispondente.
     *
     * @param statusCode codice stato: 1=confermato, 2=in attesa, 3=completato
     * @return stringa di stato, oppure "err" se il codice non è riconosciuto
     */
    public String getStatus(int statusCode) {
        return switch (statusCode) {
            case 1 -> "ok";
            case 2 -> "wait";
            case 3 -> "done";
            default -> "err";
        };
    }

    /**
     * Calcola il totale di un ordine domestico.
     *
     * <p>Applica in sequenza: sconto per tipo cliente, IVA opzionale,
     * sconto volume se il totale supera {@value #BULK_THRESHOLD} €.
     *
     * @param items       articoli dell'ordine
     * @param customerType tipo cliente per determinare lo sconto applicabile
     * @param applyVat    {@code true} per applicare l'IVA al 22%
     * @return totale finale dell'ordine
     */
    public double process(List<OrderLineItem> items, CustomerType customerType, boolean applyVat) {
        double total = items.stream()
                .mapToDouble(item -> calculateItemTotal(item, customerType, applyVat))
                .sum();

        if (total > BULK_THRESHOLD) {
            total = applyBulkDiscount(total);
        }

        return total;
    }


    /**
     * Calcola il totale di un ordine export con tassa fissa al 10%.
     *
     * Applica lo sconto per tipo cliente e poi la tassa export.
     * Non prevede sconto volume né IVA.
     *
     * @param items        articoli dell'ordine
     * @param customerType tipo cliente per determinare lo sconto applicabile
     * @return totale finale dell'ordine export
     */
    public double processExport(List<OrderLineItem> items, CustomerType customerType) {
        return items.stream()
                .mapToDouble(item -> applyTax(applyDiscount(item.subtotal(), customerType), EXPORT_TAX_RATE))
                .sum();
    }

    /**
     * Cerca un cliente per indirizzo email.
     *
     * @param email indirizzo email da cercare
     * @return {@link Optional} con il cliente trovato, vuoto se non esiste
     * @throws IllegalArgumentException se l'email è null o vuota
     */
    public Optional<Customer> findCustomerByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("L'email non può essere null o vuota");
        }
        return customerRepository.findByEmail(email);
    }

    private double calculateItemTotal(OrderLineItem item, CustomerType customerType, boolean applyVat) {
        double subtotal = applyDiscount(item.subtotal(), customerType);
        if (applyVat) {
            subtotal = applyTax(subtotal, VAT_RATE);
        }
        log.debug("Articolo elaborato: subtotale={}", subtotal);
        return subtotal;
    }

    private double applyDiscount(double amount, CustomerType customerType) {
        return switch (customerType) {
            case VIP    -> amount * (1 - VIP_DISCOUNT);
            case MEMBER -> amount * (1 - MEMBER_DISCOUNT);
            case STANDARD -> amount;
        };
    }

    private double applyTax(double amount, double taxRate) {
        return amount * (1 + taxRate);
    }

    private double applyBulkDiscount(double total) {
        double discounted = total * (1 - BULK_DISCOUNT);
        log.debug("Sconto volume applicato: {} -> {}", total, discounted);
        return discounted;
    }
}
