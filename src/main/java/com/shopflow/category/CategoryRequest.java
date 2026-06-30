package com.shopflow.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO per creazione e aggiornamento categoria.
 */
public class CategoryRequest {

    @NotBlank(message = "Il nome della categoria e obbligatorio")
    @Size(max = 100, message = "Il nome non puo superare 100 caratteri")
    private String name;

    @Size(max = 500, message = "La descrizione non puo superare 500 caratteri")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
