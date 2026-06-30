package com.shopflow.category;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopflow.shared.exception.ResourceNotFoundException;

/**
 * Service per la gestione delle categorie.
 * Contiene tutta la logica di business relativa alle categorie di ShopFlow:
 * creazione, lettura, aggiornamento ed eliminazione.
 */
@Service
@Transactional
public class CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Restituisce la lista di tutte le categorie registrate.
     *
     * @return lista di tutte le categorie
     */
    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll() {
        log.info("Recupero lista categorie");
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::from)
                .toList();
    }

    /**
     * Cerca una categoria per ID.
     *
     * @param id l'identificativo della categoria
     * @return la categoria trovata
     * @throws ResourceNotFoundException se la categoria non esiste
     */
    @Transactional(readOnly = true)
    public CategoryResponse findById(Long id) {
        log.info("Ricerca categoria con id: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
        return CategoryResponse.from(category);
    }

    /**
     * Crea una nuova categoria nel sistema.
     *
     * @param request i dati della categoria da creare
     * @return la categoria creata
     * @throws IllegalArgumentException se il nome categoria esiste gia
     */
    public CategoryResponse save(CategoryRequest request) {
        log.info("Creazione nuova categoria con nome: {}", request.getName());

        if (categoryRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException(
                    "Esiste gia una categoria con nome: " + request.getName());
        }

        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return CategoryResponse.from(categoryRepository.save(category));
    }

    /**
     * Aggiorna i dati di una categoria esistente.
     *
     * @param id      l'identificativo della categoria da aggiornare
     * @param request i nuovi dati della categoria
     * @return la categoria aggiornata
     * @throws ResourceNotFoundException se la categoria non esiste
     */
    public CategoryResponse update(Long id, CategoryRequest request) {
        log.info("Aggiornamento categoria con id: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return CategoryResponse.from(categoryRepository.save(category));
    }

    /**
     * Elimina una categoria dal sistema.
     *
     * @param id l'identificativo della categoria da eliminare
     * @throws ResourceNotFoundException se la categoria non esiste
     */
    public void delete(Long id) {
        log.info("Eliminazione categoria con id: {}", id);
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category", id);
        }
        categoryRepository.deleteById(id);
    }
}