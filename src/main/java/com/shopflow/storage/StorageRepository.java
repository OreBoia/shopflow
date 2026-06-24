package com.shopflow.storage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<Storage, Long> {

    /**
     * Restituisce i record di storage associati a uno specifico prodotto.
     *
     * @param productId identificativo del prodotto
     * @return lista dei record storage collegati al prodotto
     */
    List<Storage> findByProductId(Long productId);

    /**
     * Restituisce i record storage che risultano disponibili.
     *
     * @param available stato disponibilita da filtrare
     * @return lista dei record con stato richiesto
     */
    List<Storage> findByAvailable(boolean available);
}
