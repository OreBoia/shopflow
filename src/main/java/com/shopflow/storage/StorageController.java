package com.shopflow.storage;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/storages")
public class StorageController {

    private final StorageService storageService;

    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    public ResponseEntity<List<StorageResponse>> findAll() {
        return ResponseEntity.ok(storageService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StorageResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(storageService.findById(id));
    }

    @PostMapping
    public ResponseEntity<StorageResponse> create(@Valid @RequestBody StorageRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(storageService.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StorageResponse> update(@PathVariable Long id, @Valid @RequestBody StorageRequest request) {
        return ResponseEntity.ok(storageService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        storageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
