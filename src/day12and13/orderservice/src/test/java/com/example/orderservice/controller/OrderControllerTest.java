package com.example.orderservice.controller;

import com.example.orderservice.inDTO.OrderInDTO;
import com.example.orderservice.inDTO.OrderUpdateInDTO;
import com.example.orderservice.outDTO.OrderOutDTO;
import com.example.orderservice.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderOutDTO sampleOrderOutDTO;

    @BeforeEach
    void setup() {
        sampleOrderOutDTO = new OrderOutDTO();
        sampleOrderOutDTO.setId(1L);
        sampleOrderOutDTO.setCustomerEmail("test@gmail.com");
        sampleOrderOutDTO.setProduct("Product A");
        sampleOrderOutDTO.setQuantity(2);
        sampleOrderOutDTO.setStatus("Ordered");
    }

    @Test
    void createOrder_shouldReturnCreatedOrder() throws Exception {
        OrderInDTO dto = new OrderInDTO();
        dto.setCustomerEmail("test@gmail.com");
        dto.setProduct("Product A");
        dto.setQuantity(2);

        when(orderService.createOrder(ArgumentMatchers.any(OrderInDTO.class))).thenReturn(sampleOrderOutDTO);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerEmail").value("test@gmail.com"))
                .andExpect(jsonPath("$.product").value("Product A"))
                .andExpect(jsonPath("$.quantity").value(2))
                .andExpect(jsonPath("$.status").value("Ordered"));

        verify(orderService, times(1)).createOrder(any(OrderInDTO.class));
    }

    @Test
    void deleteOrder_shouldReturnNoContent() throws Exception {
        doNothing().when(orderService).deleteOrder(1L);

        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isNoContent());

        verify(orderService, times(1)).deleteOrder(1L);
    }

    @Test
    void getOrderById_shouldReturnOrder() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(sampleOrderOutDTO);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerEmail").value("test@gmail.com"));

        verify(orderService, times(1)).getOrderById(1L);
    }

    @Test
    void getAllOrders_shouldReturnList() throws Exception {
        when(orderService.getAllOrders()).thenReturn(Arrays.asList(sampleOrderOutDTO));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].customerEmail").value("test@gmail.com"));

        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    void updateOrderStatus_shouldReturnUpdatedOrder() throws Exception {
        OrderUpdateInDTO updateDTO = new OrderUpdateInDTO();
        updateDTO.setStatus("Processing");

        OrderOutDTO updatedOrder = new OrderOutDTO();
        updatedOrder.setId(1L);
        updatedOrder.setCustomerEmail("test@gmail.com");
        updatedOrder.setProduct("Product A");
        updatedOrder.setQuantity(2);
        updatedOrder.setStatus("Processing");

        when(orderService.updateOrderStatus(eq(1L), any(OrderUpdateInDTO.class))).thenReturn(updatedOrder);

        mockMvc.perform(put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("Processing"));

        verify(orderService, times(1)).updateOrderStatus(eq(1L), any(OrderUpdateInDTO.class));
    }
}

