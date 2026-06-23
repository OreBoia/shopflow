package com.shopflow.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO per la creazione e l'aggiornamento di un cliente.
 */
public class CustomerRequest {

    @NotBlank(message = "Il nome è obbligatorio")
    @Size(max = 100, message = "Il nome non può superare 100 caratteri")
    private String firstName;

    @NotBlank(message = "Il cognome è obbligatorio")
    @Size(max = 100, message = "Il cognome non può superare 100 caratteri")
    private String lastName;

    @NotBlank(message = "L'email è obbligatoria")
    @Email(message = "Formato email non valido")
    private String email;

    @Size(max = 20, message = "Il telefono non può superare 20 caratteri")
    private String phone;

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
