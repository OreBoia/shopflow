package com.shopflow.category;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Starter per esercizio: implementare gli endpoint REST del modulo Category.
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // TODO: aggiungere endpoint GET /api/categories
    // TODO: aggiungere endpoint GET /api/categories/{id}
    // TODO: aggiungere endpoint POST /api/categories
    // TODO: aggiungere endpoint PUT /api/categories/{id}
    // TODO: aggiungere endpoint DELETE /api/categories/{id}
}