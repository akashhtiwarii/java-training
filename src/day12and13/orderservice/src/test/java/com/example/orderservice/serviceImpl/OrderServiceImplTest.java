package com.example.orderservice.serviceImpl;

import com.example.orderservice.entity.Order;
import com.example.orderservice.exception.ResourceInvalidException;
import com.example.orderservice.exception.ResourceNotFoundException;
import com.example.orderservice.inDTO.OrderInDTO;
import com.example.orderservice.inDTO.OrderUpdateInDTO;
import com.example.orderservice.kafka.OrderKafkaProducer;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.outDTO.OrderOutDTO;
import com.example.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderKafkaProducer orderKafkaProducer;

    private Order order;
    private OrderInDTO orderInDTO;
    private OrderOutDTO orderOutDTO;

    @BeforeEach
    void setup() {
        order = new Order();
        order.setId(1L);
        order.setCustomerEmail("test@example.com");
        order.setProduct("Laptop");
        order.setQuantity(1);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("Ordered");

        orderInDTO = new OrderInDTO();
        orderInDTO.setCustomerEmail("test@example.com");
        orderInDTO.setProduct("Laptop");
        orderInDTO.setQuantity(1);

        orderOutDTO = new OrderOutDTO();
        orderOutDTO.setId(1L);
        orderOutDTO.setCustomerEmail("test@example.com");
        orderOutDTO.setProduct("Laptop");
        orderOutDTO.setQuantity(1);
        orderOutDTO.setOrderDate(order.getOrderDate());
        orderOutDTO.setStatus("Ordered");
    }

    @Test
    void createOrder_shouldSaveAndSendEvent() {
        try (MockedStatic<OrderMapper> mockedStatic = mockStatic(OrderMapper.class)) {
            mockedStatic.when(() -> OrderMapper.toEntity(orderInDTO)).thenReturn(order);
            when(orderRepository.save(order)).thenReturn(order);
            mockedStatic.when(() -> OrderMapper.toDTO(order)).thenReturn(orderOutDTO);

            OrderOutDTO result = orderService.createOrder(orderInDTO);

            assertEquals(orderOutDTO, result);
            verify(orderKafkaProducer).sendOrderCreatedEvent(orderOutDTO);
        }
    }

    @Test
    void deleteOrder_withValidId_shouldDelete() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        orderService.deleteOrder(1L);
        verify(orderRepository).deleteById(1L);
    }

    @Test
    void deleteOrder_withInvalidId_shouldThrowInvalidException() {
        assertThrows(ResourceInvalidException.class, () -> orderService.deleteOrder(-1L));
    }

    @Test
    void deleteOrder_notFound_shouldThrowNotFoundException() {
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> orderService.deleteOrder(99L));
    }

    @Test
    void getOrderById_validId_shouldReturnDTO() {
        try (MockedStatic<OrderMapper> mockedStatic = mockStatic(OrderMapper.class)) {
            when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
            mockedStatic.when(() -> OrderMapper.toDTO(order)).thenReturn(orderOutDTO);

            OrderOutDTO result = orderService.getOrderById(1L);

            assertEquals(orderOutDTO, result);
        }
    }

    @Test
    void getOrderById_invalidId_shouldThrowInvalidException() {
        assertThrows(ResourceInvalidException.class, () -> orderService.getOrderById(0L));
    }

    @Test
    void getOrderById_notFound_shouldThrowNotFoundException() {
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(99L));
    }

    @Test
    void getAllOrders_shouldReturnListOfDTOs() {
        try (MockedStatic<OrderMapper> mockedStatic = mockStatic(OrderMapper.class)) {
            when(orderRepository.findAll()).thenReturn(List.of(order));
            mockedStatic.when(() -> OrderMapper.toDTO(order)).thenReturn(orderOutDTO);

            List<OrderOutDTO> result = orderService.getAllOrders();

            assertEquals(1, result.size());
            assertEquals(orderOutDTO, result.get(0));
        }
    }

    @Test
    void updateOrderStatus_validTransition_shouldUpdate() {
        try (MockedStatic<OrderMapper> mockedStatic = mockStatic(OrderMapper.class)) {
            OrderUpdateInDTO updateDTO = new OrderUpdateInDTO();
            updateDTO.setStatus("Processing");

            when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
            when(orderRepository.save(any(Order.class))).thenReturn(order);
            mockedStatic.when(() -> OrderMapper.toDTO(order)).thenReturn(orderOutDTO);

            OrderOutDTO result = orderService.updateOrderStatus(1L, updateDTO);

            assertEquals(orderOutDTO, result);
        }
    }

    @Test
    void updateOrderStatus_invalidTransition_shouldThrow() {
        OrderUpdateInDTO updateDTO = new OrderUpdateInDTO();
        updateDTO.setStatus("Ordered");

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertThrows(ResourceInvalidException.class, () -> orderService.updateOrderStatus(1L, updateDTO));
    }

    @Test
    void updateOrderStatus_notFound_shouldThrow() {
        OrderUpdateInDTO updateDTO = new OrderUpdateInDTO();
        updateDTO.setStatus("Processing");

        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.updateOrderStatus(99L, updateDTO));
    }
}
