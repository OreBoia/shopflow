package com.shopflow.employee;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Entità che rappresenta un dipendente dell'azienda nel sistema ShopFlow.
 * Mantiene l'anagrafica del dipendente, il ruolo, la retribuzione e la data di assunzione.
 * Versione aggiornata per test hook pre-commit.
 */
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 100)
    private String role;

    @Column(nullable = false)
    private Double salary;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    public Employee() {}

    // Getters e Setters

    public Long getId() { return id; }

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
