package ru.yandex.practicum.mymarket.modular;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.yandex.practicum.mymarket.domain.Item;
import ru.yandex.practicum.mymarket.repository.ItemRepository;
import ru.yandex.practicum.mymarket.service.CartService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("Модульное тестирование корзины")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class CartServiceTest {
    @Autowired
    private CartService cartService;

    @MockitoBean
    private ItemRepository itemRepository;

    @Test
    @DisplayName("Получение списка товаров в корзине (товары есть)")
    void testItems_Success() {
        List<Item> mockItems = List.of(
                new Item("Название 1", "Описание 1", "/images/1.jpg", 1_000L, 1),
                new Item("Название 2", "Описание 2", "/images/2.jpg", 2_000L, 2)
        );

        when(itemRepository.findByCountGreaterThan(0)).thenReturn(mockItems);
        List<Item> items = itemRepository.findByCountGreaterThan(0);

        assertNotNull(items, "Товары должены существовать");
        assertEquals(2, items.size(), "Количество товаров должно быть 2");
        assertEquals("Название 1", items.getFirst().getTitle(), String.format("Название должно быть: %s", "Название 1"));
        assertEquals("Описание 1", items.getFirst().getDescription(), String.format("Описание должно быть: %s", "Описание 1"));
        assertEquals("/images/1.jpg", items.getFirst().getImgPath(), String.format("Путь к изображению должен быть: %s", "/images/1.jpg"));
        assertEquals(1_000L, items.getFirst().getPrice(), String.format("Цена должна быть: %d", 1_000L));
        assertEquals(1, items.getFirst().getCount(), String.format("Количество должно быть: %d", 1));
    }

    @Test
    @DisplayName("Получение списка товаров в корзине (товаров нет)")
    void testItems_NotFound() {
        List<Item> mockItems = new ArrayList<>();

        when(itemRepository.findByCountGreaterThan(0)).thenReturn(mockItems);
        List<Item> items = itemRepository.findByCountGreaterThan(0);

        assertEquals(0, items.size(), "Количество товаров должно быть 0");
    }

    @Test
    @DisplayName("Получение сумарной цены товаров в корзине (товары есть)")
    void testTotal_Success() {
        Long mockTotal = 1500L;
        when(itemRepository.sumPriceByCountGreaterThan(0)).thenReturn(mockTotal);
        Long total = itemRepository.sumPriceByCountGreaterThan(0);

        assertEquals(mockTotal, total, String.format("Суммарная цена должна быть %d", mockTotal));
    }

    @Test
    @DisplayName("Получение сумарной цены товаров в корзине (товаров нет)")
    void testTotal_NotFound() {
        Long mockTotal = 0L;
        when(itemRepository.sumPriceByCountGreaterThan(0)).thenReturn(mockTotal);
        Long total = itemRepository.sumPriceByCountGreaterThan(0);

        assertEquals(mockTotal, total, String.format("Суммарная цена должна быть %d", mockTotal));
    }

    @Test
    @DisplayName("Очистка корзины")
    void testClearCount() {
        doNothing().when(itemRepository).clearCount();
        itemRepository.clearCount();
        verify(itemRepository, times(1)).clearCount();
    }

    @Test
    @DisplayName("Изменение количества товара в корзине")
    void testChangeCount() {
        doNothing().when(itemRepository).changeCount("PLUS", 1L);
        itemRepository.changeCount("PLUS", 1L);
        verify(itemRepository, times(1)).changeCount("PLUS", 1L);
    }
}
