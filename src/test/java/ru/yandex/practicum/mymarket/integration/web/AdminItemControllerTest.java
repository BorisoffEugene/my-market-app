package ru.yandex.practicum.mymarket.integration.web;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.mymarket.controller.AdminItemController;
import ru.yandex.practicum.mymarket.domain.Item;
import ru.yandex.practicum.mymarket.service.ItemService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminItemController.class)
@DisplayName("Интеграционное (WEB) тестирование админ-панели")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class AdminItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemService itemService;

    @Test
    @DisplayName("Получение списка товаров (товары есть)")
    void testFindAll_Success() throws Exception {
        List<Item> items = List.of(
                new Item("Название 1", "Описание 1", "/images/1.jpg", 1_000L, 1),
                new Item("Название 2", "Описание 2", "/images/2.jpg", 2_000L, 2)
        );
        doReturn(items).when(itemService).findAll(Sort.by("id"));

        mockMvc.perform(get("/admin-items"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-items"))
                .andExpect(model().attributeExists("items"));
    }

    @Test
    @DisplayName("Получение списка товаров (товаров нет)")
    void testFindAll_NotFound() throws Exception {
        doReturn(new ArrayList<>()).when(itemService).findAll(Sort.by("id"));

        mockMvc.perform(get("/admin-items"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-items"))
                .andExpect(model().attributeExists("items"));
    }

    @Test
    @DisplayName("Добавление товара")
    void testAdd() throws Exception {
        mockMvc.perform(get("/admin-items/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-item"))
                .andExpect(model().attributeExists("item"));
    }

    @Test
    @DisplayName("Сохранение товара")
    void testSave() throws Exception {
        doNothing().when(itemService).save(any(Item.class));

        mockMvc.perform(multipart("/admin-items/save")
                        .file(new MockMultipartFile("imageFile", "1.jpg", "image/*", "content".getBytes())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin-items"));
    }

    @Test
    @DisplayName("Удаление товара")
    void testDelete() throws Exception {
        Long id = 1L;
        doNothing().when(itemService).deleteById(id);

        mockMvc.perform(get("/admin-items/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin-items"));
    }

    @Test
    @DisplayName("Редактирование товара (товар есть)")
    void testEdit_Success() throws Exception {
        Long id = 1L;
        Item item = new Item("Название 1", "Описание 1", "/images/1.jpg", 1_000L, 1);
        doReturn(Optional.of(item)).when(itemService).findById(id);

        mockMvc.perform(get("/admin-items/edit/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-item"))
                .andExpect(model().attributeExists("item"));
    }

    @Test
    @DisplayName("Редактирование товара (товара нет)")
    void testEdit_NotFound() throws Exception {
        Long id = -1L;
        doReturn(Optional.empty()).when(itemService).findById(id);

        mockMvc.perform(get("/admin-items/edit/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin-items"));
    }
}
