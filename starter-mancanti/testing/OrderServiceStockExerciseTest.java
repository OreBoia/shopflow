package com.shopflow.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.shopflow.customer.Customer;
import com.shopflow.customer.CustomerRepository;
import com.shopflow.product.Product;
import com.shopflow.product.ProductRepository;
import com.shopflow.shared.exception.InsufficientStockException;

/**
 * Esercizio guidato per i TODO di OrderService:
 * verifica stock, scalatura stock e calcolo totale ordine.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("OrderService - Esercizi stock e totale")
class OrderServiceStockExerciseTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    private Customer customer;
    private Product product;
    private OrderRequest request;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        ReflectionTestUtils.setField(customer, "id", 10L);
        customer.setFirstName("Giulia");
        customer.setLastName("Bianchi");

        product = new Product();
        ReflectionTestUtils.setField(product, "id", 20L);
        product.setName("Monitor 27");
        product.setPrice(new BigDecimal("299.90"));
        product.setStock(5);

        OrderRequest.OrderItemRequest item = new OrderRequest.OrderItemRequest();
        item.setProductId(20L);
        item.setQuantity(2);

        request = new OrderRequest();
        request.setCustomerId(10L);
        request.setItems(List.of(item));
    }

    @Test
    @DisplayName("createOrder() dovrebbe scalare stock e valorizzare totale quando stock disponibile")
    void createOrder_shouldScaleStockAndSetTotal_whenStockAvailable() {
        when(customerRepository.findById(10L)).thenReturn(Optional.of(customer));
        when(productRepository.findById(20L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order toSave = invocation.getArgument(0);
            ReflectionTestUtils.setField(toSave, "id", 100L);
            return toSave;
        });

        OrderResponse result = orderService.createOrder(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(100L);
        assertThat(result.getTotal()).isEqualByComparingTo("599.80");
        assertThat(product.getStock()).isEqualTo(3);

        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository, times(1)).save(captor.capture());
        assertThat(captor.getValue().getItems()).hasSize(1);
    }

    @Test
    @DisplayName("createOrder() dovrebbe lanciare InsufficientStockException quando stock insufficiente")
    void createOrder_shouldThrowInsufficientStock_whenStockNotEnough() {
        request.getItems().get(0).setQuantity(6);

        when(customerRepository.findById(10L)).thenReturn(Optional.of(customer));
        when(productRepository.findById(20L)).thenReturn(Optional.of(product));

        assertThatThrownBy(() -> orderService.createOrder(request))
                .isInstanceOf(InsufficientStockException.class)
                .hasMessageContaining("Monitor 27");
    }
}
