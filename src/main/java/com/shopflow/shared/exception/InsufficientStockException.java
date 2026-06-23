package com.shopflow.shared.exception;

/**
 * Eccezione lanciata quando la quantità richiesta supera lo stock disponibile.
 * Mappa a una risposta HTTP 422 Unprocessable Entity.
 */
public class InsufficientStockException extends BaseAppException {

    public InsufficientStockException(String productName, int requested, int available) {
        super(String.format(
            "Stock insufficiente per '%s': richiesti %d, disponibili %d",
            productName, requested, available
        ));
    }
}
