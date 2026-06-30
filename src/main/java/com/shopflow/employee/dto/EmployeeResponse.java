package com.shopflow.employee.dto;

import com.shopflow.employee.Employee;

import java.time.LocalDate;

/**
 * DTO per la risposta contenente i dati di un dipendente.
 */
public class EmployeeResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private Double salary;
    private LocalDate hireDate;

    public static EmployeeResponse from(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        response.id = employee.getId();
        response.firstName = employee.getFirstName();
        response.lastName = employee.getLastName();
        response.email = employee.getEmail();
        response.role = employee.getRole();
        response.salary = employee.getSalary();
        response.hireDate = employee.getHireDate();
        return response;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public Double getSalary() { return salary; }
    public LocalDate getHireDate() { return hireDate; }
}
