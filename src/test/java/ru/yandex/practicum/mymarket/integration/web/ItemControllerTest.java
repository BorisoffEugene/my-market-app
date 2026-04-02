package ru.yandex.practicum.mymarket.integration.web;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.mymarket.controller.ItemController;
import ru.yandex.practicum.mymarket.domain.Item;
import ru.yandex.practicum.mymarket.dto.ItemDto;
import ru.yandex.practicum.mymarket.service.CartService;
import ru.yandex.practicum.mymarket.service.ItemService;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DisplayName("Интеграционное (WEB) тестирование товаров")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemService itemService;

    @MockitoBean
    private CartService cartService;
/*
    @Test
    @DisplayName("Получение списка товаров + поиск + пагинация (товары есть)")
    void testFindByFiltr_Success() throws Exception {
        String search = "";
        String sort = "NO";
        int pageNumber = 0;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.unsorted());
        Page<ItemDto> paging = new PageImpl<>(
                List.of(
                        new ItemDto("Название 1", "Описание 1", "/images/1.jpg", 1_000L, 1),
                        new ItemDto("Название 2", "Описание 2", "/images/2.jpg", 2_000L, 2)
                ),
                pageable,
                2);

        doReturn(paging).when(itemService).findByFiltr(search, sort, pageNumber, pageSize);

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(view().name("items"))
                .andExpect(model().attributeExists("search", "sort", "paging", "items"));
    }

    @Test
    @DisplayName("Получение списка товаров + поиск + пагинация (товаров нет)")
    void testFindByFiltr_NotFound() throws Exception {
        String search = "";
        String sort = "NO";
        int pageNumber = 0;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.unsorted());
        Page<Item> paging = new PageImpl<>(List.of(), pageable, 0);

        doReturn(paging).when(itemService).findByFiltr(search, sort, pageNumber, pageSize);

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(view().name("items"))
                .andExpect(model().attributeExists("search", "sort", "paging", "items"));
    }

    @Test
    @DisplayName("Получение товара (товар есть)")
    void testFindById_Success() throws Exception {
        Long id = 1L;
        ItemDto item = new ItemDto("Название 1", "Описание 1", "/images/1.jpg", 1_000L, 1);
        item.setId(id);
        doReturn(Optional.of(item)).when(itemService).findById(id);

        mockMvc.perform(get("/items/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("item"))
                .andExpect(model().attributeExists("item"));
    }

    @Test
    @DisplayName("Получение товара (товара нет)")
    void testFindById_NotFound() throws Exception {
        Long id = -1L;
        doReturn(Optional.empty()).when(itemService).findById(id);

        mockMvc.perform(get("/items/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/items"));
    }

    @Test
    @DisplayName("Изменение количества товара на витрине")
    void testDoItemsAction() throws Exception {
        String search = "";
        String sort = "NO";
        int pageNumber = 0;
        int pageSize = 5;
        String action = "PLUS";
        Long id = 1L;

        doNothing().when(cartService).changeCount(action, id);

        mockMvc.perform(post("/items?id={id}&search={search}&sort={sort}&pageNumber={pageNumber}&pageSize={pageSize}&action={action}", id, search, sort, pageNumber, pageSize, action))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/items?*"));
    }

    @Test
    @DisplayName("Изменение количества товара в карточке товара")
    void testDoItemAction() throws Exception {
        String action = "PLUS";
        Long id = 1L;

        doNothing().when(cartService).changeCount(action, id);

        mockMvc.perform(post("/items/{id}?action={action}", id, action))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/items/1"));
    }

 */
}
