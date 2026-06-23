package com.shopflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point dell'applicazione ShopFlow.
 * Backend REST per la gestione di un e-commerce:
 * clienti, prodotti, categorie e ordini.
 */
@SpringBootApplication
public class ShopFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopFlowApplication.class, args);
    }
}
