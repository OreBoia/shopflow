package com.shopflow.user;

/**
 * DTO per la creazione o aggiornamento di un utente.
 */
public class UserRequest {

    private String username;
    private String email;

    public UserRequest() {}

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
