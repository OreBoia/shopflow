package com.shopflow.order;

import com.shopflow.customer.Customer;
import com.shopflow.customer.CustomerRepository;
import com.shopflow.product.Product;
import com.shopflow.product.ProductRepository;
import com.shopflow.shared.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service per la gestione degli ordini.
 *
 * NOTA PER IL DOCENTE:
 * Questo service è intenzionalmente incompleto per gli esercizi del corso.
 * Mancano due funzionalità da implementare con il supporto dell'AI:
 *
 * TODO (Esercizio Coding - Senior):
 *   1. Aggiungere la verifica e scalatura dello stock in createOrder()
 *      dopo la creazione di ogni OrderItem
 *   2. Aggiungere il calcolo del totale dell'ordine prima del salvataggio
 *      sommando i subtotali di tutti gli OrderItem
 *
 * Suggerimento AI: usare @generate-javadoc e l'agent @new-feature-agent
 */
@Service
@Transactional
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                        CustomerRepository customerRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(OrderResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderResponse findById(Long id) {
        return orderRepository.findById(id)
                .map(OrderResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId)
                .stream()
                .map(OrderResponse::from)
                .toList();
    }

    public OrderResponse createOrder(OrderRequest request) {
        log.info("Creazione ordine per cliente id: {}", request.getCustomerId());

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", request.getCustomerId()));

        Order order = new Order();
        order.setCustomer(customer);

        for (OrderRequest.OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", itemRequest.getProductId()));

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemRequest.getQuantity());
            item.setUnitPrice(product.getPrice());
            order.getItems().add(item);

            // TODO: verificare che product.getStock() >= itemRequest.getQuantity()
            //       e lanciare InsufficientStockException se non disponibile
            // TODO: scalare lo stock del prodotto dopo l'aggiunta dell'item
        }

        // TODO: calcolare il totale sommando item.getSubtotal() per ogni item
        //       e impostarlo con order.setTotal(...)

        Order saved = orderRepository.save(order);
        log.info("Ordine creato con id: {}", saved.getId());
        return OrderResponse.from(saved);
    }

    public OrderResponse updateStatus(Long id, OrderStatus newStatus) {
        log.info("Aggiornamento stato ordine {} -> {}", id, newStatus);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));
        order.setStatus(newStatus);
        return OrderResponse.from(orderRepository.save(order));
    }
}
