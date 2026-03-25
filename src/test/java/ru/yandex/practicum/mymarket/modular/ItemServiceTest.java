package ru.yandex.practicum.mymarket.modular;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.yandex.practicum.mymarket.domain.Item;
import ru.yandex.practicum.mymarket.repository.ItemRepository;
import ru.yandex.practicum.mymarket.service.ItemService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("Модульное тестирование товаров")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class ItemServiceTest {
    @Autowired
    private ItemService itemService;

    @MockitoBean
    private ItemRepository itemRepository;

    @Test
    @DisplayName("Получение списка товаров (товары есть)")
    void testFindAll_Success() {
        List<Item> mockItems = List.of(
                new Item(1L, "Название 1", "Описание 1", "/images/1.jpg", 1_000L, 1),
                new Item(2L, "Название 2", "Описание 2", "/images/2.jpg", 2_000L, 2)
        );

        when(itemRepository.findAll(Sort.by("id"))).thenReturn(mockItems);
        List<Item> items = itemRepository.findAll(Sort.by("id"));

        assertNotNull(items, "Товары должены существовать");
        assertEquals(2, items.size(), "Количество товаров должно быть 2");

        assertEquals(1L, items.getFirst().getId(), String.format("ID должен быть: %d", 1L));
        assertEquals("Название 1", items.getFirst().getTitle(), String.format("Название должно быть: %s", "Название 1"));
        assertEquals("Описание 1", items.getFirst().getDescription(), String.format("Описание должно быть: %s", "Описание 1"));
        assertEquals("/images/1.jpg", items.getFirst().getImgPath(), String.format("Путь к изображению должен быть: %s", "/images/1.jpg"));
        assertEquals(1_000L, items.getFirst().getPrice(), String.format("Цена должна быть: %d", 1_000L));
        assertEquals(1, items.getFirst().getCount(), String.format("Количество должно быть: %d", 1));
    }

    @Test
    @DisplayName("Получение списка товаров (товаров нет)")
    void testFindAll_NotFound() {
        List<Item> mockItems = new ArrayList<>();

        when(itemRepository.findAll(Sort.by("id"))).thenReturn(mockItems);
        List<Item> items = itemRepository.findAll(Sort.by("id"));

        assertEquals(0, items.size(), "Количество товаров должно быть 0");
    }

    @Test
    @DisplayName("Сохранение товара")
    void testSave() {
        Item mockItem = new Item(1L, "Название 1", "Описание 1", "/images/1.jpg", 1_000L, 1);
        when(itemRepository.save(mockItem)).thenReturn(mockItem);
        Item item = itemRepository.save(mockItem);

        assertNotNull(item, "Товар должен существовать");
        assertEquals(1L, item.getId(), String.format("ID должен быть: %d", 1L));
        assertEquals("Название 1", item.getTitle(), String.format("Название должно быть: %s", "Название 1"));
        assertEquals("Описание 1", item.getDescription(), String.format("Описание должно быть: %s", "Описание 1"));
        assertEquals("/images/1.jpg", item.getImgPath(), String.format("Путь к изображению должен быть: %s", "/images/1.jpg"));
        assertEquals(1_000L, item.getPrice(), String.format("Цена должна быть: %d", 1_000L));
        assertEquals(1, item.getCount(), String.format("Количество должно быть: %d", 1));
    }

    @Test
    @DisplayName("Удаление товара")
    void testDeleteById() {
        Long id = 1L;
        doNothing().when(itemRepository).deleteById(id);
        itemRepository.deleteById(id);
        verify(itemRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Получение товара (товар есть)")
    void testFindById_Success() {
        Long id = 1L;
        Item mockItem = new Item(1L, "Название 1", "Описание 1", "/images/1.jpg", 1_000L, 1);

        when(itemRepository.findById(id)).thenReturn(Optional.of(mockItem));
        Optional<Item> optionalItem = itemRepository.findById(id);
        assertTrue(optionalItem.isPresent(), "Товар должен существовать");

        Item item = optionalItem.get();

        assertEquals(1L, item.getId(), String.format("ID должен быть: %d", 1L));
        assertEquals("Название 1", item.getTitle(), String.format("Название должно быть: %s", "Название 1"));
        assertEquals("Описание 1", item.getDescription(), String.format("Описание должно быть: %s", "Описание 1"));
        assertEquals("/images/1.jpg", item.getImgPath(), String.format("Путь к изображению должен быть: %s", "/images/1.jpg"));
        assertEquals(1_000L, item.getPrice(), String.format("Цена должна быть: %d", 1_000L));
        assertEquals(1, item.getCount(), String.format("Количество должно быть: %d", 1));
    }

    @Test
    @DisplayName("Получение товара (товара нет)")
    void testFindById_NotFound() {
        Long id = -1L;
        when(itemRepository.findById(id)).thenReturn(Optional.empty());
        Optional<Item> optionalItem = itemRepository.findById(id);
        assertFalse(optionalItem.isPresent(), "Товара не должно быть");
    }

    @Test
    @DisplayName("Изменение количества товара")
    void testChangeCount() {
        doNothing().when(itemRepository).changeCount("PLUS", 1L);
        itemRepository.changeCount("PLUS", 1L);
        verify(itemRepository, times(1)).changeCount("PLUS", 1L);
    }

    @Test
    @DisplayName("Получение списка товаров + поиск + пагинация (товары есть)")
    void testFindByFiltr_Success() {
        String search = "Название";
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id"));
        Page<Item> mockItem = new PageImpl<>(
                List.of(
                        new Item(1L, "Название 1", "Описание 1", "/images/1.jpg", 1_000L, 1),
                        new Item(2L, "Название 2", "Описание 2", "/images/2.jpg", 2_000L, 2)
                ),
                pageable,
                2);

        when(itemRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, search, pageable)).thenReturn(mockItem);
        Page<Item> item = itemRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, search, pageable);

        assertEquals(0, item.getNumber(), "Номер текущей страницы должен быть 0");
        assertEquals(5, item.getSize(), "Количество товаров на странице должно быть 5");
        assertFalse(item.hasNext(), "Следующей страницы не должно быть");
        assertFalse(item.hasPrevious(), "Предыдущей страницы не должно быть");
        assertTrue(item.hasContent(), "Товары должены существовать");
        assertEquals(2, item.getContent().size(), "Количество товаров должно быть 2");
        assertEquals(1L, item.getContent().getFirst().getId(), String.format("ID должен быть: %d", 1L));
        assertEquals("Название 1", item.getContent().getFirst().getTitle(), String.format("Название должно быть: %s", "Название 1"));
        assertEquals("Описание 1", item.getContent().getFirst().getDescription(), String.format("Описание должно быть: %s", "Описание 1"));
        assertEquals("/images/1.jpg", item.getContent().getFirst().getImgPath(), String.format("Путь к изображению должен быть: %s", "/images/1.jpg"));
        assertEquals(1_000L, item.getContent().getFirst().getPrice(), String.format("Цена должна быть: %d", 1_000L));
        assertEquals(1, item.getContent().getFirst().getCount(), String.format("Количество должно быть: %d", 1));
    }

    @Test
    @DisplayName("Получение списка товаров + поиск + пагинация (товаров нет)")
    void testFindByFiltr_NotFound() {
        String search = "Несуществующее название";
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id"));
        Page<Item> mockItem = new PageImpl<>(List.of(), pageable, 2);

        when(itemRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, search, pageable)).thenReturn(mockItem);
        Page<Item> item = itemRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, search, pageable);

        assertEquals(0, item.getNumber(), "Номер текущей страницы должен быть 0");
        assertEquals(5, item.getSize(), "Количество товаров на странице должно быть 5");
        assertFalse(item.hasNext(), "Следующей страницы не должно быть");
        assertFalse(item.hasPrevious(), "Предыдущей страницы не должно быть");
        assertFalse(item.hasContent(), "Товаров не должно быть");
    }
}
