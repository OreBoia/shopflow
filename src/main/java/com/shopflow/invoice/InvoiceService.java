package com.shopflow.invoice;

import com.shopflow.shared.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service per la gestione delle fatture.
 * Contiene la logica di business per la creazione e il recupero delle fatture in ShopFlow.
 */
@Service
@Transactional
public class InvoiceService {

    private static final Logger log = LoggerFactory.getLogger(InvoiceService.class);

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * Restituisce la lista di tutte le fatture registrate.
     *
     * @return lista di tutte le fatture
     */
    @Transactional(readOnly = true)
    public List<InvoiceResponse> findAll() {
        log.info("Recupero lista fatture");
        return invoiceRepository.findAll()
                .stream()
                .map(InvoiceResponse::from)
                .toList();
    }

    /**
     * Cerca una fattura per ID.
     *
     * @param id l'identificativo della fattura
     * @return la fattura trovata
     * @throws ResourceNotFoundException se la fattura non esiste
     */
    @Transactional(readOnly = true)
    public InvoiceResponse findById(Long id) {
        log.info("Ricerca fattura con id: {}", id);
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", id));
        return InvoiceResponse.from(invoice);
    }

    /**
     * Crea una nuova fattura nel sistema.
     *
     * @param request i dati della fattura da creare
     * @return la fattura creata con id e timestamp assegnati
     */
    public InvoiceResponse save(InvoiceRequest request) {
        log.info("Creazione nuova fattura per cliente: {}", request.getCustomerEmail());
        Invoice invoice = new Invoice();
        invoice.setCustomerName(request.getCustomerName());
        invoice.setCustomerEmail(request.getCustomerEmail());
        invoice.setAmount(request.getAmount());
        invoice.setNotes(request.getNotes());
        return InvoiceResponse.from(invoiceRepository.save(invoice));
    }

    /**
     * Elimina una fattura dal sistema.
     *
     * @param id l'identificativo della fattura da eliminare
     * @throws ResourceNotFoundException se la fattura non esiste
     */
    public void delete(Long id) {
        log.info("Eliminazione fattura con id: {}", id);
        if (!invoiceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Invoice", id);
        }
        invoiceRepository.deleteById(id);
    }
}
