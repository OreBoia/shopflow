package com.shopflow.shared.exception;

/**
 * Eccezione lanciata quando una risorsa richiesta non viene trovata nel sistema.
 * Mappa a una risposta HTTP 404 Not Found.
 */
public class ResourceNotFoundException extends BaseAppException {

    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("%s con id %d non trovato", resourceName, id));
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
