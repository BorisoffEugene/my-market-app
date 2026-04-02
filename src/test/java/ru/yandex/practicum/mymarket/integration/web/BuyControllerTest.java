package ru.yandex.practicum.mymarket.integration.web;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.mymarket.domain.OrderItem;
import ru.yandex.practicum.mymarket.dto.OrderDto;
import ru.yandex.practicum.mymarket.service.*;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Интеграционное (WEB) тестирование покупок")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class BuyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;
    @MockitoBean
    private CartService cartService;
/*
    @Test
    @DisplayName("Покупка")
    void testBy() throws Exception {
        OrderDto order = new OrderDto(List.of(new OrderItem("Название 11", 1, 1_000L), new OrderItem("Название 12", 2, 2_000L)), 5_000L);
        order.setId(1L);
        doReturn(order).when(orderService).save(any(OrderDto.class));

        mockMvc.perform(post("/buy"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders/1?newOrder=true"));
    }

 */
}
