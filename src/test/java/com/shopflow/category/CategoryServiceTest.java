package com.shopflow.category;

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

import com.shopflow.shared.exception.ResourceNotFoundException;

/**
 * Starter test per l'esercizio CategoryService.
 *
 * Nota: copiare questo file in src/test/java/com/shopflow/category/
 * quando il service sara implementato in src/main.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("CategoryService - Test Unitari")
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private CategoryRequest request;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        category = new Category("Notebook", "Categoria per notebook e ultrabook");
        ReflectionTestUtils.setField(category, "id", 1L);

        request = new CategoryRequest();
        request.setName("Notebook");
        request.setDescription("Categoria per notebook e ultrabook");
    }

    @Test
    @DisplayName("findAll() dovrebbe restituire tutte le categorie")
    void findAll_shouldReturnAllCategories() {
        when(categoryRepository.findAll()).thenReturn(List.of(category));

        List<CategoryResponse> result = categoryService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Notebook");
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById() dovrebbe restituire la categoria quando esiste")
    void findById_shouldReturnCategory_whenExists() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        CategoryResponse result = categoryService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Notebook");
    }

    @Test
    @DisplayName("findById() dovrebbe lanciare eccezione quando non trova la categoria")
    void findById_shouldThrowException_whenNotFound() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("save() dovrebbe creare una categoria quando il nome non esiste")
    void save_shouldCreateCategory_whenNameIsUnique() {
        when(categoryRepository.existsByName("Notebook")).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> {
            Category toSave = invocation.getArgument(0);
            ReflectionTestUtils.setField(toSave, "id", 2L);
            return toSave;
        });

        CategoryResponse result = categoryService.save(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(2L);
        assertThat(result.getName()).isEqualTo("Notebook");
    }

    @Test
    @DisplayName("save() dovrebbe lanciare IllegalArgumentException quando il nome esiste")
    void save_shouldThrowException_whenNameAlreadyExists() {
        when(categoryRepository.existsByName("Notebook")).thenReturn(true);

        assertThatThrownBy(() -> categoryService.save(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Notebook");

        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    @DisplayName("update() dovrebbe aggiornare categoria quando esiste")
    void update_shouldUpdateCategory_whenExists() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        request.setName("Notebook Premium");

        CategoryResponse result = categoryService.update(1L, request);

        assertThat(result.getName()).isEqualTo("Notebook Premium");
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("update() dovrebbe lanciare eccezione quando categoria non esiste")
    void update_shouldThrowException_whenNotFound() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.update(99L, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");

        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    @DisplayName("delete() dovrebbe eliminare categoria quando esiste")
    void delete_shouldRemoveCategory_whenExists() {
        when(categoryRepository.existsById(1L)).thenReturn(true);

        categoryService.delete(1L);

        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("delete() dovrebbe lanciare eccezione quando categoria non esiste")
    void delete_shouldThrowException_whenNotFound() {
        when(categoryRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> categoryService.delete(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");

        verify(categoryRepository, never()).deleteById(any());
    }
}
