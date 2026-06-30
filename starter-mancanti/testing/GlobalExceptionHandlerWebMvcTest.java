package com.shopflow.shared.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

/**
 * Esercizio WebMvc sul mapping errori centralizzato.
 */
@WebMvcTest(controllers = GlobalExceptionHandlerWebMvcTest.DummyController.class)
@Import(GlobalExceptionHandler.class)
@DisplayName("GlobalExceptionHandler - WebMvc test")
class GlobalExceptionHandlerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("dovrebbe rispondere 404 per ResourceNotFoundException")
    void shouldReturn404_whenResourceNotFound() throws Exception {
        mockMvc.perform(get("/dummy/not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Product con id 99 non trovato"));
    }

    @Test
    @DisplayName("dovrebbe rispondere 422 per InsufficientStockException")
    void shouldReturn422_whenInsufficientStock() throws Exception {
        mockMvc.perform(get("/dummy/stock"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status").value(422))
                .andExpect(jsonPath("$.message").value("Stock insufficiente per 'Monitor': richiesti 8, disponibili 2"));
    }

    @Test
    @DisplayName("dovrebbe rispondere 400 per errore validazione")
    void shouldReturn400_whenValidationFails() throws Exception {
        mockMvc.perform(post("/dummy/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("name: must not be blank"));
    }

    @RestController
    @RequestMapping("/dummy")
    static class DummyController {

        @GetMapping("/not-found")
        public void notFound() {
            throw new ResourceNotFoundException("Product", 99L);
        }

        @GetMapping("/stock")
        public void stock() {
            throw new InsufficientStockException("Monitor", 8, 2);
        }

        @PostMapping("/validate")
        public void validate(@Valid @RequestBody DummyRequest request) {
            // no-op
        }
    }

    static class DummyRequest {
        @NotBlank
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
