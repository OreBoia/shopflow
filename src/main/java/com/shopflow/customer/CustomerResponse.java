package com.shopflow.customer;

import java.time.LocalDateTime;

/**
 * DTO per la risposta contenente i dati di un cliente.
 */
public class CustomerResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDateTime createdAt;

    public static CustomerResponse from(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.id = customer.getId();
        response.firstName = customer.getFirstName();
        response.lastName = customer.getLastName();
        response.email = customer.getEmail();
        response.phone = customer.getPhone();
        response.createdAt = customer.getCreatedAt();
        return response;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
