package ru.yandex.practicum.mymarket.integration.db;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.mymarket.domain.Item;
import ru.yandex.practicum.mymarket.repository.ItemRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DisplayName("Интеграционное (DB) тестирование товаров")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;

    private Item item1;
    private Item item2;

    @BeforeEach
    void beforeEach() {
        itemRepository.deleteAll();
        item1 = itemRepository.save(new Item("Название 1", "Описание 1", "/images/1.jpg", 1_000L, 1));
        item2 = itemRepository.save(new Item("Название 2", "Описание 2", "/images/2.jpg", 2_000L, 2));
    }

    @Test
    @DisplayName("Получение списка товаров (товары есть)")
    void testFindAll_Success() {
        List<Item> items = itemRepository.findAll(Sort.by("id"));

        assertNotNull(items, "Товары должены существовать");
        assertEquals(2, items.size(), "Количество товаров должно быть 2");
        assertEquals(item1.getTitle(), items.getFirst().getTitle(), String.format("Название должно быть: %s", item1.getTitle()));
        assertEquals(item1.getDescription(), items.getFirst().getDescription(), String.format("Описание должно быть: %s", item1.getDescription()));
        assertEquals(item1.getImgPath(), items.getFirst().getImgPath(), String.format("Путь к изображению должен быть: %s", item1.getImgPath()));
        assertEquals(item1.getPrice(), items.getFirst().getPrice(), String.format("Цена должна быть: %d", item1.getPrice()));
        assertEquals(item1.getCount(), items.getFirst().getCount(), String.format("Количество должно быть: %d", item1.getCount()));
    }

    @Test
    @DisplayName("Получение списка товаров (товаров нет)")
    void testFindAll_NotFound() {
        itemRepository.deleteAll();
        List<Item> items = itemRepository.findAll(Sort.by("id"));
        assertEquals(0, items.size(), "Количество товаров должно быть 0");
    }

    @Test
    @DisplayName("Сохранение товара")
    void testSave() {
        Item item = itemRepository.save(new Item("Название 3", "Описание 3", "/images/3.jpg", 3_000L, 3));

        assertNotNull(item, "Товар должен существовать");
        assertEquals("Название 3", item.getTitle(), String.format("Название должно быть: %s", "Название 3"));
        assertEquals("Описание 3", item.getDescription(), String.format("Описание должно быть: %s", "Описание 3"));
        assertEquals("/images/3.jpg", item.getImgPath(), String.format("Путь к изображению должен быть: %s", "/images/3.jpg"));
        assertEquals(3_000L, item.getPrice(), String.format("Цена должна быть: %d", 3_000L));
        assertEquals(3, item.getCount(), String.format("Количество должно быть: %d", 3));

        List<Item> items = itemRepository.findAll();
        assertEquals(3, items.size(), String.format("Количество товаров должно быть: %d", 3));
    }

    @Test
    @DisplayName("Удаление товара")
    void testDeleteById() {
        itemRepository.deleteById(item1.getId());
        List<Item> items = itemRepository.findAll();
        assertEquals(1, items.size(), "Количество товаров должно быть 1");
        assertTrue(items.stream().noneMatch(c -> c.getId().equals(item1.getId())), String.format("Не должно быть товара с ID: %d", item1.getId()));
    }

    @Test
    @DisplayName("Получение товара (товар есть)")
    void testFindById_Success() {
        Optional<Item> optionalItem = itemRepository.findById(item1.getId());
        assertTrue(optionalItem.isPresent(), "Товар должен существовать");

        Item item = optionalItem.get();

        assertEquals(item1.getTitle(), item.getTitle(), String.format("Название должно быть: %s", item1.getTitle()));
        assertEquals(item1.getDescription(), item.getDescription(), String.format("Описание должно быть: %s", item1.getDescription()));
        assertEquals(item1.getImgPath(), item.getImgPath(), String.format("Путь к изображению должен быть: %s", item1.getImgPath()));
        assertEquals(item1.getPrice(), item.getPrice(), String.format("Цена должна быть: %d", item1.getPrice()));
        assertEquals(item1.getCount(), item.getCount(), String.format("Количество должно быть: %d", item1.getCount()));
    }

    @Test
    @DisplayName("Получение товара (товара нет)")
    void testFindById_NotFound() {
        Optional<Item> optionalItem = itemRepository.findById(-1L);
        assertFalse(optionalItem.isPresent(), "Товара не должно быть");
    }

    @Test
    @DisplayName("Изменение количества товара")
    void testChangeCount() {
        itemRepository.changeCount("PLUS", item1.getId());
        Optional<Item> optionalItem = itemRepository.findById(item1.getId());
        assertTrue(optionalItem.isPresent(), "Товар должен существовать");
        Item item = optionalItem.get();
        assertEquals(1, item.getCount(), "Количество товара в корзине должно быть: 1");
    }

    @Test
    @DisplayName("Получение списка товаров + поиск + пагинация (товары есть)")
    void testFindByFiltr_Success() {
        String search = "Название";
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id"));

        Page<Item> item = itemRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, search, pageable);

        assertEquals(0, item.getNumber(), "Номер текущей страницы должен быть 0");
        assertEquals(5, item.getSize(), "Количество товаров на странице должно быть 5");
        assertFalse(item.hasNext(), "Следующей страницы не должно быть");
        assertFalse(item.hasPrevious(), "Предыдущей страницы не должно быть");
        assertTrue(item.hasContent(), "Товары должены существовать");
        assertEquals(2, item.getContent().size(), "Количество товаров должно быть 2");
        assertEquals(item1.getTitle(), item.getContent().getFirst().getTitle(), String.format("Название должно быть: %s", item1.getTitle()));
        assertEquals(item1.getDescription(), item.getContent().getFirst().getDescription(), String.format("Описание должно быть: %s", item1.getDescription()));
        assertEquals(item1.getImgPath(), item.getContent().getFirst().getImgPath(), String.format("Путь к изображению должен быть: %s", item1.getImgPath()));
        assertEquals(item1.getPrice(), item.getContent().getFirst().getPrice(), String.format("Цена должна быть: %d", item1.getPrice()));
        assertEquals(item1.getCount(), item.getContent().getFirst().getCount(), String.format("Количество должно быть: %d", item1.getCount()));
    }

    @Test
    @DisplayName("Получение списка товаров + поиск + пагинация (товаров нет)")
    void testFindByFiltr_NotFound() {
        String search = "Несуществующее название";
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id"));

        Page<Item> item = itemRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, search, pageable);

        assertEquals(0, item.getNumber(), "Номер текущей страницы должен быть 0");
        assertEquals(5, item.getSize(), "Количество товаров на странице должно быть 5");
        assertFalse(item.hasNext(), "Следующей страницы не должно быть");
        assertFalse(item.hasPrevious(), "Предыдущей страницы не должно быть");
        assertFalse(item.hasContent(), "Товаров не должно быть");
    }
}