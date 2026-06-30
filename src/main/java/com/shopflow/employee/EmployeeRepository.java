package com.shopflow.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository JPA per la gestione dei dipendenti.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Cerca un dipendente tramite indirizzo email.
     *
     * @param email l'indirizzo email da cercare
     * @return il dipendente trovato, oppure empty se non presente
     */
    Optional<Employee> findByEmail(String email);

    /**
     * Verifica se esiste già un dipendente con l'email specificata.
     *
     * @param email l'indirizzo email da verificare
     * @return true se l'email è già registrata, false altrimenti
     */
    boolean existsByEmail(String email);
}
