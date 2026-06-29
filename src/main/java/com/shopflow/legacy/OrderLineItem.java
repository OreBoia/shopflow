package com.shopflow.legacy;

public record OrderLineItem(double price, int quantity) {

    public OrderLineItem {
        if (price < 0) throw new IllegalArgumentException("Il prezzo non può essere negativo");
        if (quantity <= 0) throw new IllegalArgumentException("La quantità deve essere positiva");
    }

    public double subtotal() {
        return price * quantity;
    }
}
