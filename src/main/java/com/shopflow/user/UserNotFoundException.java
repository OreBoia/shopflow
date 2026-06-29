package com.shopflow.user;

import com.shopflow.shared.exception.BaseAppException;

/**
 * Eccezione lanciata quando un utente richiesto non viene trovato nel sistema.
 * Mappa a una risposta HTTP 404 Not Found.
 */
public class UserNotFoundException extends BaseAppException {

    public UserNotFoundException(Long id) {
        super(String.format("User con id %d non trovato", id));
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
