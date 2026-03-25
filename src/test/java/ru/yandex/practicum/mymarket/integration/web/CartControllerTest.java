package ru.yandex.practicum.mymarket.integration.web;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.mymarket.controller.CartController;
import ru.yandex.practicum.mymarket.domain.Item;
import ru.yandex.practicum.mymarket.service.CartService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
public class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CartService cartService;

    @Test
    @DisplayName("Получение списка товаров в корзине (товары есть)")
    void testItems_Success() throws Exception {
        List<Item> items = List.of(
                new Item("Название 1", "Описание 1", "/images/1.jpg", 1_000L, 1),
                new Item("Название 2", "Описание 2", "/images/2.jpg", 2_000L, 2)
        );

        doReturn(items).when(cartService).items();
        doReturn(5_000L).when(cartService).total();

        mockMvc.perform(get("/cart/items"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"))
                .andExpect(model().attributeExists("items", "total"));
    }

    @Test
    @DisplayName("Получение списка товаров в корзине (товаров нет)")
    void testItems_NotFound() throws Exception {
        doReturn(new ArrayList<>()).when(cartService).items();
        doReturn(0L).when(cartService).total();

        mockMvc.perform(get("/cart/items"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"))
                .andExpect(model().attributeExists("items", "total"));
    }

    @Test
    @DisplayName("Изменение количества товара в корзине")
    void testChangeCount() throws Exception {
        Long id = 1L;
        String action = "PLUS";
        doNothing().when(cartService).changeCount(action, id);
        mockMvc.perform(post("/cart/items?id={id}&action={action}", id, action))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart/items"));
    }
}
