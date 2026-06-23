package com.shopflow.customer;

import com.shopflow.shared.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test unitari per CustomerService.
 *
 * NOTA PER IL DOCENTE:
 * Questa classe contiene 3 test su 8 già scritti come riferimento.
 * I test mancanti (segnati con TODO) sono l'esercizio del modulo Testing & Quality:
 * i partecipanti devono generarli con Claude Code usando la skill @create-unit-test.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("CustomerService — Test Unitari")
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private CustomerRequest request;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setFirstName("Mario");
        customer.setLastName("Rossi");
        customer.setEmail("mario.rossi@email.it");
        customer.setPhone("3331234567");

        request = new CustomerRequest();
        request.setFirstName("Mario");
        request.setLastName("Rossi");
        request.setEmail("mario.rossi@email.it");
        request.setPhone("3331234567");
    }

    // ----------------------------------------------------------------
    // TEST GIÀ SCRITTI — modello di riferimento per i partecipanti
    // ----------------------------------------------------------------

    @Test
    @DisplayName("findAll() dovrebbe restituire lista di tutti i clienti")
    void findAll_shouldReturnAllCustomers() {
        when(customerRepository.findAll()).thenReturn(List.of(customer));

        List<CustomerResponse> result = customerService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getEmail()).isEqualTo("mario.rossi@email.it");
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById() dovrebbe restituire il cliente quando esiste")
    void findById_shouldReturnCustomer_whenExists() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        CustomerResponse result = customerService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("Mario");
    }

    @Test
    @DisplayName("findById() dovrebbe lanciare eccezione quando il cliente non esiste")
    void findById_shouldThrowException_whenNotFound() {
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    // ----------------------------------------------------------------
    // TODO (Esercizio Testing & Quality — da generare con Claude Code)
    // ----------------------------------------------------------------

    // TODO: save() dovrebbe creare un nuovo cliente quando l'email non è già registrata

    // TODO: save() dovrebbe lanciare IllegalArgumentException quando l'email è già registrata

    // TODO: update() dovrebbe aggiornare i dati quando il cliente esiste

    // TODO: update() dovrebbe lanciare ResourceNotFoundException quando il cliente non esiste

    // TODO: delete() dovrebbe eliminare il cliente quando esiste

    // TODO: delete() dovrebbe lanciare ResourceNotFoundException quando il cliente non esiste
}
