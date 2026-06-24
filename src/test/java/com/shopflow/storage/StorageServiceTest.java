package com.shopflow.storage;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.shopflow.product.Product;
import com.shopflow.product.ProductRepository;
import com.shopflow.shared.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("StorageService - Test Unitari")
class StorageServiceTest {

    @Mock
    private StorageRepository storageRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private StorageService storageService;

    private Product product;
    private Storage storage;
    private StorageRequest request;

    @BeforeEach
    void setUp() {
        product = new Product();
        ReflectionTestUtils.setField(product, "id", 10L);
        product.setName("Laptop");
        product.setStock(25);

        storage = new Storage();
        ReflectionTestUtils.setField(storage, "id", 1L);
        storage.setProduct(product);
        storage.setLocation("Magazzino A1");
        storage.setQuantity(15);
        storage.setAvailable(true);

        request = new StorageRequest();
        request.setProductId(10L);
        request.setLocation("Magazzino A1");
        request.setQuantity(15);
        request.setAvailable(true);
    }

    @Test
    @DisplayName("findAll() dovrebbe restituire tutti i record storage")
    void findAll_shouldReturnAllStorages() {
        when(storageRepository.findAll()).thenReturn(List.of(storage));

        List<StorageResponse> result = storageService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getProductName()).isEqualTo("Laptop");
        verify(storageRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll() dovrebbe restituire lista vuota quando non ci sono record")
    void findAll_shouldReturnEmptyList_whenNoStorages() {
        when(storageRepository.findAll()).thenReturn(List.of());

        List<StorageResponse> result = storageService.findAll();

        assertThat(result).isEmpty();
        verify(storageRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById() dovrebbe restituire il record quando esiste")
    void findById_shouldReturnStorage_whenExists() {
        when(storageRepository.findById(1L)).thenReturn(Optional.of(storage));

        StorageResponse result = storageService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getLocation()).isEqualTo("Magazzino A1");
        assertThat(result.getProductId()).isEqualTo(10L);
    }

    @Test
    @DisplayName("findById() dovrebbe lanciare eccezione quando il record non esiste")
    void findById_shouldThrowException_whenNotFound() {
        when(storageRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> storageService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("save() dovrebbe creare un nuovo storage quando il prodotto esiste")
    void save_shouldCreateStorage_whenProductExists() {
        when(productRepository.findById(10L)).thenReturn(Optional.of(product));
        when(storageRepository.save(any(Storage.class))).thenAnswer(invocation -> {
            Storage toSave = invocation.getArgument(0);
            ReflectionTestUtils.setField(toSave, "id", 2L);
            return toSave;
        });

        StorageResponse result = storageService.save(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(2L);
        assertThat(result.getProductId()).isEqualTo(10L);
        assertThat(result.getQuantity()).isEqualTo(15);
        verify(storageRepository, times(1)).save(any(Storage.class));
    }

    @Test
    @DisplayName("save() dovrebbe lanciare eccezione quando il prodotto non esiste")
    void save_shouldThrowException_whenProductNotFound() {
        when(productRepository.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> storageService.save(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Product")
                .hasMessageContaining("10");

        verify(storageRepository, never()).save(any(Storage.class));
    }

    @Test
    @DisplayName("update() dovrebbe aggiornare il record quando esiste")
    void update_shouldUpdateStorage_whenExists() {
        when(storageRepository.findById(1L)).thenReturn(Optional.of(storage));
        when(productRepository.findById(10L)).thenReturn(Optional.of(product));
        when(storageRepository.save(any(Storage.class))).thenAnswer(invocation -> invocation.getArgument(0));

        request.setLocation("Magazzino B2");
        request.setQuantity(8);
        request.setAvailable(false);

        StorageResponse result = storageService.update(1L, request);

        assertThat(result.getLocation()).isEqualTo("Magazzino B2");
        assertThat(result.getQuantity()).isEqualTo(8);
        assertThat(result.isAvailable()).isFalse();
    }

    @Test
    @DisplayName("update() dovrebbe lanciare eccezione quando il record non esiste")
    void update_shouldThrowException_whenStorageNotFound() {
        when(storageRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> storageService.update(1L, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Storage")
                .hasMessageContaining("1");

        verify(productRepository, never()).findById(any());
        verify(storageRepository, never()).save(any(Storage.class));
    }

    @Test
    @DisplayName("update() dovrebbe lanciare eccezione quando il prodotto non esiste")
    void update_shouldThrowException_whenProductNotFound() {
        when(storageRepository.findById(1L)).thenReturn(Optional.of(storage));
        when(productRepository.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> storageService.update(1L, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Product")
                .hasMessageContaining("10");

        verify(storageRepository, never()).save(any(Storage.class));
    }

    @Test
    @DisplayName("delete() dovrebbe eliminare il record quando esiste")
    void delete_shouldRemoveStorage_whenExists() {
        when(storageRepository.existsById(1L)).thenReturn(true);

        storageService.delete(1L);

        verify(storageRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("delete() dovrebbe lanciare eccezione quando il record non esiste")
    void delete_shouldThrowException_whenNotFound() {
        when(storageRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> storageService.delete(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");

        verify(storageRepository, never()).deleteById(any());
    }
}
