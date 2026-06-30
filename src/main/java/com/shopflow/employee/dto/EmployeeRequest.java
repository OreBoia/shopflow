package com.shopflow.employee.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * DTO per la creazione e l'aggiornamento di un dipendente.
 */
public class EmployeeRequest {

    @NotBlank(message = "Il nome è obbligatorio")
    @Size(max = 100, message = "Il nome non può superare 100 caratteri")
    private String firstName;

    @NotBlank(message = "Il cognome è obbligatorio")
    @Size(max = 100, message = "Il cognome non può superare 100 caratteri")
    private String lastName;

    @NotBlank(message = "L'email è obbligatoria")
    @Email(message = "Formato email non valido")
    private String email;

    @NotBlank(message = "Il ruolo è obbligatorio")
    @Size(max = 100, message = "Il ruolo non può superare 100 caratteri")
    private String role;

    @NotNull(message = "Lo stipendio è obbligatorio")
    @Positive(message = "Lo stipendio deve essere un valore positivo")
    private Double salary;

    @NotNull(message = "La data di assunzione è obbligatoria")
    private LocalDate hireDate;

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }
}
