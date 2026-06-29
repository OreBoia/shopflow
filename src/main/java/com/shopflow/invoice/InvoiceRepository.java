package com.shopflow.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository JPA per la gestione delle fatture.
 */
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    /**
     * Cerca tutte le fatture associate a un indirizzo email cliente.
     *
     * @param customerEmail l'indirizzo email del cliente
     * @return lista delle fatture trovate
     */
    List<Invoice> findByCustomerEmail(String customerEmail);
}
