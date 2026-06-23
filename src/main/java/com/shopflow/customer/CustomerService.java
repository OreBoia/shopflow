package com.shopflow.customer;

import com.shopflow.shared.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service per la gestione dei clienti.
 * Contiene tutta la logica di business relativa ai clienti di ShopFlow:
 * creazione, lettura, aggiornamento ed eliminazione.
 */
@Service
@Transactional
public class CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Restituisce la lista di tutti i clienti registrati.
     *
     * @return lista di tutti i clienti
     */
    @Transactional(readOnly = true)
    public List<CustomerResponse> findAll() {
        log.info("Recupero lista clienti");
        return customerRepository.findAll()
                .stream()
                .map(CustomerResponse::from)
                .toList();
    }

    /**
     * Cerca un cliente per ID.
     *
     * @param id l'identificativo del cliente
     * @return il cliente trovato
     * @throws ResourceNotFoundException se il cliente non esiste
     */
    @Transactional(readOnly = true)
    public CustomerResponse findById(Long id) {
        log.info("Ricerca cliente con id: {}", id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", id));
        return CustomerResponse.from(customer);
    }

    /**
     * Crea un nuovo cliente nel sistema.
     *
     * @param request i dati del cliente da creare
     * @return il cliente creato
     * @throws IllegalArgumentException se l'email è già registrata
     */
    public CustomerResponse save(CustomerRequest request) {
        log.info("Creazione nuovo cliente con email: {}", request.getEmail());
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                "Email già registrata: " + request.getEmail()
            );
        }
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        return CustomerResponse.from(customerRepository.save(customer));
    }

    /**
     * Aggiorna i dati di un cliente esistente.
     *
     * @param id      l'identificativo del cliente da aggiornare
     * @param request i nuovi dati del cliente
     * @return il cliente aggiornato
     * @throws ResourceNotFoundException se il cliente non esiste
     */
    public CustomerResponse update(Long id, CustomerRequest request) {
        log.info("Aggiornamento cliente con id: {}", id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", id));
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setPhone(request.getPhone());
        return CustomerResponse.from(customerRepository.save(customer));
    }

    /**
     * Elimina un cliente dal sistema.
     *
     * @param id l'identificativo del cliente da eliminare
     * @throws ResourceNotFoundException se il cliente non esiste
     */
    public void delete(Long id) {
        log.info("Eliminazione cliente con id: {}", id);
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer", id);
        }
        customerRepository.deleteById(id);
    }
}
