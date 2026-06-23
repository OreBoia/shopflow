package com.shopflow.category;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Starter per esercizio: implementare il service CRUD delle categorie.
 */
@Service
@Transactional
public class CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // TODO: aggiungere metodi pubblici findAll, findById, save, update, delete
    // TODO: aggiungere Javadoc a tutti i metodi pubblici
    // TODO: usare ResourceNotFoundException quando la categoria non esiste
}