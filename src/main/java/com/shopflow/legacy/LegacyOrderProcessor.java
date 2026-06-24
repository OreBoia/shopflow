package com.shopflow.legacy;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * ATTENZIONE — MODULO LEGACY
 *
 * Questa classe è un esempio di codice legacy con code smell intenzionali.
 *
 * Esercizio (Modulo Coding + Refactoring):
 *   Usa Claude Code per identificare tutti i problemi presenti
 *   e proporre una versione refactored senza cambiare il comportamento.
 */
@Component
public class LegacyOrderProcessor {

    // magic numbers: cosa significano 1, 2, 3?
    public String getStatus(int s) {
        if (s == 1) return "ok";
        else if (s == 2) return "wait";
        else if (s == 3) return "done";
        else return "err";
    }

    // metodo troppo lungo, fa troppe cose, nessuna separazione responsabilità
    public double process(List<Map<String, Object>> items, String type, boolean flag) {
        double tot = 0;
        double disc = 0;
        for (int i = 0; i < items.size(); i++) {
            Map<String, Object> item = items.get(i);
            double p = (double) item.get("price");
            int q = (int) item.get("qty");
            double sub = p * q;
            if (type.equals("VIP")) {
                disc = sub * 0.20;
                sub = sub - disc;
            } else if (type.equals("MEMBER")) {
                disc = sub * 0.10;
                sub = sub - disc;
            } else {
                disc = 0;
            }
            if (flag) {
                sub = sub * 1.22;
            }
            tot = tot + sub;
            System.out.println("item " + i + " processed: " + sub);
        }
        if (tot > 1000) {
            tot = tot - (tot * 0.05);
            System.out.println("bonus sconto applicato");
        }
        return tot;
    }

    // nessuna validazione, ritorna null, non usa Optional
    public Map<String, Object> findCustomer(List<Map<String, Object>> customers, String email) {
        for (Map<String, Object> c : customers) {
            if (c.get("email").equals(email)) {
                return c;
            }
        }
        return null;
    }

    // duplicazione: stessa logica di process() ma con tassa diversa
    public double processExport(List<Map<String, Object>> items, String type) {
        double tot = 0;
        for (int i = 0; i < items.size(); i++) {
            Map<String, Object> item = items.get(i);
            double p = (double) item.get("price");
            int q = (int) item.get("qty");
            double sub = p * q;
            if (type.equals("VIP")) {
                sub = sub - (sub * 0.20);
            } else if (type.equals("MEMBER")) {
                sub = sub - (sub * 0.10);
            }
            sub = sub * 1.10;
            tot = tot + sub;
        }
        return tot;
    }
}
