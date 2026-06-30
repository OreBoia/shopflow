package com.shopflow.employee;

import com.shopflow.employee.dto.EmployeeRequest;
import com.shopflow.employee.dto.EmployeeResponse;
import com.shopflow.shared.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service per la gestione dei dipendenti.
 * Contiene tutta la logica di business relativa ai dipendenti di ShopFlow:
 * creazione, lettura, aggiornamento ed eliminazione.
 */
@Service
@Transactional
public class EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Restituisce la lista di tutti i dipendenti registrati.
     *
     * @return lista di tutti i dipendenti
     */
    @Transactional(readOnly = true)
    public List<EmployeeResponse> findAll() {
        log.info("Recupero lista dipendenti");
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeResponse::from)
                .toList();
    }

    /**
     * Cerca un dipendente per ID.
     *
     * @param id l'identificativo del dipendente
     * @return il dipendente trovato
     * @throws ResourceNotFoundException se il dipendente non esiste
     */
    @Transactional(readOnly = true)
    public EmployeeResponse findById(Long id) {
        log.info("Ricerca dipendente con id: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));
        return EmployeeResponse.from(employee);
    }

    /**
     * Crea un nuovo dipendente nel sistema.
     *
     * @param request i dati del dipendente da creare
     * @return il dipendente creato
     * @throws IllegalArgumentException se l'email è già registrata
     */
    public EmployeeResponse save(EmployeeRequest request) {
        log.info("Creazione nuovo dipendente con email: {}", request.getEmail());
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                "Email già registrata: " + request.getEmail()
            );
        }
        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setRole(request.getRole());
        employee.setSalary(request.getSalary());
        employee.setHireDate(request.getHireDate());
        return EmployeeResponse.from(employeeRepository.save(employee));
    }

    /**
     * Aggiorna i dati di un dipendente esistente.
     *
     * @param id      l'identificativo del dipendente da aggiornare
     * @param request i nuovi dati del dipendente
     * @return il dipendente aggiornato
     * @throws ResourceNotFoundException se il dipendente non esiste
     */
    public EmployeeResponse update(Long id, EmployeeRequest request) {
        log.info("Aggiornamento dipendente con id: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setRole(request.getRole());
        employee.setSalary(request.getSalary());
        employee.setHireDate(request.getHireDate());
        return EmployeeResponse.from(employeeRepository.save(employee));
    }

    /**
     * Elimina un dipendente dal sistema.
     *
     * @param id l'identificativo del dipendente da eliminare
     * @throws ResourceNotFoundException se il dipendente non esiste
     */
    public void delete(Long id) {
        log.info("Eliminazione dipendente con id: {}", id);
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee", id);
        }
        employeeRepository.deleteById(id);
    }
}
