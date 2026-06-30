package com.shopflow.payment;

import java.math.BigDecimal;
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

import com.shopflow.order.Order;
import com.shopflow.order.OrderRepository;

/**
 * Esercizi mirati per il modulo pagamento su timeout/failure gateway.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("PaymentService - Timeout e failure")
class PaymentServiceTimeoutTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PaymentGatewayClient gatewayClient;

    @InjectMocks
    private PaymentService paymentService;

    private Order order;
    private PaymentRequest request;

    @BeforeEach
    void setUp() {
        order = new Order();
        ReflectionTestUtils.setField(order, "id", 77L);

        request = new PaymentRequest();
        request.setOrderId(77L);
        request.setAmount(new BigDecimal("149.99"));
        request.setCardNumber("4111111111111111");
        request.setExpiryDate("12/30");
        request.setCvv("123");
    }

    @Test
    @DisplayName("processPayment() dovrebbe salvare stato TIMEOUT e rilanciare eccezione")
    void processPayment_shouldPersistTimeoutAndRethrow_whenGatewayTimeout() {
        when(orderRepository.findById(77L)).thenReturn(Optional.of(order));
        when(gatewayClient.charge("4111111111111111", "12/30", "123", new BigDecimal("149.99")))
                .thenThrow(new PaymentGatewayTimeoutException("Timeout gateway"));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        assertThatThrownBy(() -> paymentService.processPayment(request))
                .isInstanceOf(PaymentGatewayTimeoutException.class)
                .hasMessageContaining("Timeout");

        ArgumentCaptor<Payment> captor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository, times(1)).save(captor.capture());
        assertThat(captor.getValue().getStatus()).isEqualTo(PaymentStatus.TIMEOUT);
        assertThat(captor.getValue().getFailureReason()).isEqualTo("Timeout del gateway");
    }

    @Test
    @DisplayName("processPayment() dovrebbe salvare stato FAILED e rilanciare eccezione")
    void processPayment_shouldPersistFailedAndRethrow_whenGatewayRejects() {
        when(orderRepository.findById(77L)).thenReturn(Optional.of(order));
        when(gatewayClient.charge("4111111111111111", "12/30", "123", new BigDecimal("149.99")))
                .thenThrow(new PaymentFailedException("Carta rifiutata"));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        assertThatThrownBy(() -> paymentService.processPayment(request))
                .isInstanceOf(PaymentFailedException.class)
                .hasMessageContaining("Carta rifiutata");

        ArgumentCaptor<Payment> captor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository, times(1)).save(captor.capture());
        assertThat(captor.getValue().getStatus()).isEqualTo(PaymentStatus.FAILED);
        assertThat(captor.getValue().getFailureReason()).isEqualTo("Carta rifiutata");
    }
}
