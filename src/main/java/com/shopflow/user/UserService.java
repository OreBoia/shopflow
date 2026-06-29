package com.shopflow.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service per la gestione degli utenti.
 * Contiene tutta la logica di business relativa agli utenti di ShopFlow:
 * creazione, lettura, aggiornamento ed eliminazione.
 */
@Service
@Transactional
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Restituisce la lista di tutti gli utenti registrati.
     *
     * @return lista di tutti gli utenti
     */
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        log.info("Recupero lista utenti");
        return userRepository.findAll()
                .stream()
                .map(UserResponse::from)
                .toList();
    }

    /**
     * Cerca un utente per ID.
     *
     * @param id l'identificativo dell'utente
     * @return l'utente trovato
     * @throws UserNotFoundException se l'utente non esiste
     */
    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        log.info("Ricerca utente con id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return UserResponse.from(user);
    }

    /**
     * Crea un nuovo utente nel sistema.
     *
     * @param request i dati dell'utente da creare
     * @return l'utente creato
     * @throws IllegalArgumentException se l'email è già registrata
     */
    public UserResponse save(UserRequest request) {
        log.info("Creazione nuovo utente con email: {}", request.getEmail());
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                "Email già registrata: " + request.getEmail()
            );
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        return UserResponse.from(userRepository.save(user));
    }

    /**
     * Elimina un utente dal sistema.
     *
     * @param id l'identificativo dell'utente da eliminare
     * @throws UserNotFoundException se l'utente non esiste
     */
    public void delete(Long id) {
        log.info("Eliminazione utente con id: {}", id);
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
