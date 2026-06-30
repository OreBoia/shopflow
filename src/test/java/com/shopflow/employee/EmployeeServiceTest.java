package com.shopflow.employee;

import com.shopflow.employee.dto.EmployeeRequest;
import com.shopflow.employee.dto.EmployeeResponse;
import com.shopflow.shared.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EmployeeService - Test Unitari")
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;
    private EmployeeRequest request;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        ReflectionTestUtils.setField(employee, "id", 1L);
        employee.setFirstName("Mario");
        employee.setLastName("Rossi");
        employee.setEmail("mario.rossi@shopflow.com");
        employee.setRole("DEVELOPER");
        employee.setSalary(45000.0);
        employee.setHireDate(LocalDate.of(2023, 1, 15));

        request = new EmployeeRequest();
        request.setFirstName("Mario");
        request.setLastName("Rossi");
        request.setEmail("mario.rossi@shopflow.com");
        request.setRole("DEVELOPER");
        request.setSalary(45000.0);
        request.setHireDate(LocalDate.of(2023, 1, 15));
    }

    @Test
    @DisplayName("findAll() dovrebbe restituire tutti i dipendenti")
    void findAll_shouldReturnAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee));

        List<EmployeeResponse> result = employeeService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFirstName()).isEqualTo("Mario");
        assertThat(result.get(0).getEmail()).isEqualTo("mario.rossi@shopflow.com");
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll() dovrebbe restituire lista vuota quando non ci sono dipendenti")
    void findAll_shouldReturnEmptyList_whenNoEmployees() {
        when(employeeRepository.findAll()).thenReturn(List.of());

        List<EmployeeResponse> result = employeeService.findAll();

        assertThat(result).isEmpty();
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById() dovrebbe restituire il dipendente quando esiste")
    void findById_shouldReturnEmployee_whenExists() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        EmployeeResponse result = employeeService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getLastName()).isEqualTo("Rossi");
        assertThat(result.getRole()).isEqualTo("DEVELOPER");
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById() dovrebbe lanciare eccezione quando il dipendente non esiste")
    void findById_shouldThrowException_whenNotFound() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");

        verify(employeeRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("save() dovrebbe creare un nuovo dipendente")
    void save_shouldCreateEmployee() {
        when(employeeRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> {
            Employee toSave = invocation.getArgument(0);
            ReflectionTestUtils.setField(toSave, "id", 2L);
            return toSave;
        });

        EmployeeResponse result = employeeService.save(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(2L);
        assertThat(result.getEmail()).isEqualTo("mario.rossi@shopflow.com");
        assertThat(result.getSalary()).isEqualTo(45000.0);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    @DisplayName("save() dovrebbe lanciare eccezione se l'email è già registrata")
    void save_shouldThrowException_whenEmailAlreadyExists() {
        when(employeeRepository.existsByEmail(request.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> employeeService.save(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("mario.rossi@shopflow.com");

        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("update() dovrebbe aggiornare il dipendente quando esiste")
    void update_shouldUpdateEmployee_whenExists() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        request.setRole("SENIOR_DEVELOPER");
        request.setSalary(55000.0);

        EmployeeResponse result = employeeService.update(1L, request);

        assertThat(result.getRole()).isEqualTo("SENIOR_DEVELOPER");
        assertThat(result.getSalary()).isEqualTo(55000.0);
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    @DisplayName("update() dovrebbe lanciare eccezione quando il dipendente non esiste")
    void update_shouldThrowException_whenNotFound() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.update(99L, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");

        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("delete() dovrebbe eliminare il dipendente quando esiste")
    void delete_shouldRemoveEmployee_whenExists() {
        when(employeeRepository.existsById(1L)).thenReturn(true);

        employeeService.delete(1L);

        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("delete() dovrebbe lanciare eccezione quando il dipendente non esiste")
    void delete_shouldThrowException_whenNotFound() {
        when(employeeRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> employeeService.delete(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");

        verify(employeeRepository, never()).deleteById(any());
    }
}
