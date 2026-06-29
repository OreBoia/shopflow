package com.shopflow.user;

import java.time.LocalDateTime;

/**
 * DTO per la restituzione dei dati di un utente al client.
 */
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;

    private UserResponse() {}

    /**
     * Crea un {@code UserResponse} a partire dall'entità {@link User}.
     *
     * @param user l'entità utente
     * @return il DTO di risposta
     */
    public static UserResponse from(User user) {
        UserResponse response = new UserResponse();
        response.id = user.getId();
        response.username = user.getUsername();
        response.email = user.getEmail();
        response.createdAt = user.getCreatedAt();
        return response;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
