package com.shopflow.shared.exception;

/**
 * Eccezione base per tutte le eccezioni applicative di ShopFlow.
 * Tutte le eccezioni custom del progetto devono estendere questa classe.
 */
public class BaseAppException extends RuntimeException {

    public BaseAppException(String message) {
        super(message);
    }

    public BaseAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
