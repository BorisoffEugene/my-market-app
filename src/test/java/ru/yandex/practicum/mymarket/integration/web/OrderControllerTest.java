package ru.yandex.practicum.mymarket.integration.web;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.mymarket.controller.OrderController;
import ru.yandex.practicum.mymarket.domain.Order;
import ru.yandex.practicum.mymarket.domain.OrderItem;
import ru.yandex.practicum.mymarket.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Интеграционное (WEB) тестирование заказов")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;
/*
    @Test
    @DisplayName("Получение списка заказов (заказы есть)")
    void testFindAll_Success() throws Exception {
        List<Order> orders = List.of(
                new Order(List.of(new OrderItem("Название 11", 1, 1_000L), new OrderItem("Название 12", 2, 2_000L)), 5_000L),
                new Order(List.of(new OrderItem("Название 21", 5, 3_000L), new OrderItem("Название 22", 3, 5_000L)), 30_000L)
        );
        doReturn(orders).when(orderService).findAll();

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(view().name("orders"))
                .andExpect(model().attributeExists("orders"));
    }

    @Test
    @DisplayName("Получение списка заказов (заказов нет)")
    void testFindAll_NotFound() throws Exception {
        doReturn(new ArrayList<>()).when(orderService).findAll();

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(view().name("orders"))
                .andExpect(model().attributeExists("orders"));
    }

    @Test
    @DisplayName("Получение заказа (заказ есть)")
    void testFindById_Success() throws Exception {
        Long id = 1L;
        Order order = new Order(List.of(new OrderItem("Название 11", 1, 1_000L), new OrderItem("Название 12", 2, 2_000L)), 5_000L);
        order.setId(id);
        doReturn(Optional.of(order)).when(orderService).findById(id);

        mockMvc.perform(get("/orders/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("order"))
                .andExpect(model().attributeExists("newOrder", "order"));
    }

    @Test
    @DisplayName("Получение заказа (заказа нет)")
    void testFindById_NotFound() throws Exception {
        Long id = -1L;
        doReturn(Optional.empty()).when(orderService).findById(id);

        mockMvc.perform(get("/orders/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders"));
    }

 */
}
