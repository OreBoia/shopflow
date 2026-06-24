package com.shopflow.storage;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopflow.product.Product;
import com.shopflow.product.ProductRepository;
import com.shopflow.shared.exception.ResourceNotFoundException;

@Service
@Transactional
public class StorageService {

    private static final Logger log = LoggerFactory.getLogger(StorageService.class);

    private final StorageRepository storageRepository;
    private final ProductRepository productRepository;

    public StorageService(StorageRepository storageRepository, ProductRepository productRepository) {
        this.storageRepository = storageRepository;
        this.productRepository = productRepository;
    }

    /**
     * Restituisce tutti i record di storage disponibili nel sistema.
     *
     * @return lista completa dei record storage
     */
    @Transactional(readOnly = true)
    public List<StorageResponse> findAll() {
        log.info("Recupero lista storage");
        return storageRepository.findAll()
                .stream()
                .map(StorageResponse::from)
                .toList();
    }

    /**
     * Cerca un record storage tramite identificativo univoco.
     *
     * @param id identificativo del record storage
     * @return record storage trovato
     * @throws ResourceNotFoundException se il record non esiste
     */
    @Transactional(readOnly = true)
    public StorageResponse findById(Long id) {
        log.info("Ricerca storage con id: {}", id);
        Storage storage = storageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Storage", id));
        return StorageResponse.from(storage);
    }

    /**
     * Crea un nuovo record storage associato a un prodotto esistente.
     *
     * @param request dati necessari per la creazione del record
     * @return record storage creato
     * @throws ResourceNotFoundException se il prodotto associato non esiste
     */
    public StorageResponse save(StorageRequest request) {
        log.info("Creazione storage per prodotto id: {}", request.getProductId());
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", request.getProductId()));

        Storage storage = new Storage();
        storage.setProduct(product);
        storage.setLocation(request.getLocation());
        storage.setQuantity(request.getQuantity());
        storage.setAvailable(request.getAvailable());

        return StorageResponse.from(storageRepository.save(storage));
    }

    /**
     * Aggiorna un record storage esistente.
     *
     * @param id identificativo del record da aggiornare
     * @param request nuovi dati del record
     * @return record storage aggiornato
     * @throws ResourceNotFoundException se il record o il prodotto non esistono
     */
    public StorageResponse update(Long id, StorageRequest request) {
        log.info("Aggiornamento storage con id: {}", id);
        Storage storage = storageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Storage", id));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", request.getProductId()));

        storage.setProduct(product);
        storage.setLocation(request.getLocation());
        storage.setQuantity(request.getQuantity());
        storage.setAvailable(request.getAvailable());

        return StorageResponse.from(storageRepository.save(storage));
    }

    /**
     * Elimina un record storage dal sistema.
     *
     * @param id identificativo del record da eliminare
     * @throws ResourceNotFoundException se il record non esiste
     */
    public void delete(Long id) {
        log.info("Eliminazione storage con id: {}", id);
        if (!storageRepository.existsById(id)) {
            throw new ResourceNotFoundException("Storage", id);
        }
        storageRepository.deleteById(id);
    }
}
