package com.shopflow.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository JPA per la gestione degli utenti.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Cerca un utente tramite indirizzo email.
     *
     * @param email l'indirizzo email da cercare
     * @return l'utente trovato, oppure empty se non presente
     */
    Optional<User> findByEmail(String email);

    /**
     * Verifica se esiste già un utente con l'email specificata.
     *
     * @param email l'indirizzo email da verificare
     * @return true se l'email è già registrata, false altrimenti
     */
    boolean existsByEmail(String email);
}
