package com.shopflow.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository JPA per la gestione dei clienti.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Cerca un cliente tramite indirizzo email.
     *
     * @param email l'indirizzo email da cercare
     * @return il cliente trovato, oppure empty se non presente
     */
    Optional<Customer> findByEmail(String email);

    /**
     * Verifica se esiste già un cliente con l'email specificata.
     *
     * @param email l'indirizzo email da verificare
     * @return true se l'email è già registrata, false altrimenti
     */
    boolean existsByEmail(String email);
}
