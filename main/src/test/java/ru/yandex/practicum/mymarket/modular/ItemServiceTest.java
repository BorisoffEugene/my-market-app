package ru.yandex.practicum.mymarket.modular;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.yandex.practicum.mymarket.domain.Item;
import ru.yandex.practicum.mymarket.repository.ItemRepository;
import ru.yandex.practicum.mymarket.service.ItemService;

import java.util.ArrayList;
import java.util.List;

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
/* todo
    @Test
    @DisplayName("Получение списка товаров (товары есть)")
    void testFindAll_Success() {
        Flux<Item> mockItems = Flux.fromIterable(List.of(
                new Item("Название 1", "Описание 1", "/images/1.jpg", 1_000L, 1),
                new Item("Название 2", "Описание 2", "/images/2.jpg", 2_000L, 2)
        ));

        when(itemRepository.findAll()).thenReturn(mockItems);
        Flux<Item> items = itemRepository.findAll();

        assertNotNull(items, "Товары должены существовать");
        assertEquals(2, items.collectList().block().size(), "Количество товаров должно быть 2");
        assertEquals("Название 1", items.collectList().block().getFirst().getTitle(), String.format("Название должно быть: %s", "Название 1"));
        assertEquals("Описание 1", items.collectList().block().getFirst().getDescription(), String.format("Описание должно быть: %s", "Описание 1"));
        assertEquals("/images/1.jpg", items.collectList().block().getFirst().getImgPath(), String.format("Путь к изображению должен быть: %s", "/images/1.jpg"));
        assertEquals(1_000L, items.collectList().block().getFirst().getPrice(), String.format("Цена должна быть: %d", 1_000L));
        assertEquals(1, items.collectList().block().getFirst().getCount(), String.format("Количество должно быть: %d", 1));
    }

    @Test
    @DisplayName("Получение списка товаров (товаров нет)")
    void testFindAll_NotFound() {
        Flux<Item> mockItems = Flux.fromIterable(new ArrayList<>());

        when(itemRepository.findAll()).thenReturn(mockItems);
        Flux<Item> items = itemRepository.findAll();

        assertEquals(0, items.collectList().block().size(), "Количество товаров должно быть 0");
    }

    @Test
    @DisplayName("Сохранение товара")
    void testSave() {
        Item mockItem = new Item("Название 1", "Описание 1", "/images/1.jpg", 1_000L, 1);
        when(itemRepository.save(mockItem)).thenReturn(Mono.just(mockItem));
        Mono<Item> item = itemRepository.save(mockItem);

        assertNotNull(item, "Товар должен существовать");
        assertEquals("Название 1", item.block().getTitle(), String.format("Название должно быть: %s", "Название 1"));
        assertEquals("Описание 1", item.block().getDescription(), String.format("Описание должно быть: %s", "Описание 1"));
        assertEquals("/images/1.jpg", item.block().getImgPath(), String.format("Путь к изображению должен быть: %s", "/images/1.jpg"));
        assertEquals(1_000L, item.block().getPrice(), String.format("Цена должна быть: %d", 1_000L));
        assertEquals(1, item.block().getCount(), String.format("Количество должно быть: %d", 1));
    }

    @Test
    @DisplayName("Удаление товара")
    void testDeleteById() {
        Long id = 1L;
        when(itemRepository.deleteById(id)).thenReturn(Mono.empty());
        Mono<Void> result = itemRepository.deleteById(id);
        StepVerifier.create(result).verifyComplete();
    }

    @Test
    @DisplayName("Получение товара (товар есть)")
    void testFindById_Success() {
        Long id = 1L;
        Item mockItem = new Item("Название 1", "Описание 1", "/images/1.jpg", 1_000L, 1);

        when(itemRepository.findById(id)).thenReturn(Mono.just(mockItem));
        Mono<Item> monoItem = itemRepository.findById(id);
        assertTrue(monoItem.hasElement().block(), "Товар должен существовать");

        Item item = monoItem.block();

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
        when(itemRepository.findById(id)).thenReturn(Mono.empty());
        Mono<Item> monoItem = itemRepository.findById(id);
        assertFalse(monoItem.hasElement().block(), "Товара не должно быть");
    }

    @Test
    @DisplayName("Получение списка товаров + поиск + пагинация (товары есть)")
    void testFindByFiltr_Success() {
        String search = "Название";
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id"));
        Flux<Item> mockItems = Flux.fromIterable(
                List.of(
                        new Item("Название 1", "Описание 1", "/images/1.jpg", 1_000L, 1),
                        new Item("Название 2", "Описание 2", "/images/2.jpg", 2_000L, 2)
                ));

        when(itemRepository.findByFiltr(search, pageable)).thenReturn(mockItems);
        Flux<Item> items = itemRepository.findByFiltr(search, pageable);

        assertEquals(2, items.collectList().block().size(), "Количество товаров должно быть 2");
        assertEquals("Название 1", items.collectList().block().getFirst().getTitle(), String.format("Название должно быть: %s", "Название 1"));
        assertEquals("Описание 1", items.collectList().block().getFirst().getDescription(), String.format("Описание должно быть: %s", "Описание 1"));
        assertEquals("/images/1.jpg", items.collectList().block().getFirst().getImgPath(), String.format("Путь к изображению должен быть: %s", "/images/1.jpg"));
        assertEquals(1_000L, items.collectList().block().getFirst().getPrice(), String.format("Цена должна быть: %d", 1_000L));
        assertEquals(1, items.collectList().block().getFirst().getCount(), String.format("Количество должно быть: %d", 1));
    }

    @Test
    @DisplayName("Получение списка товаров + поиск + пагинация (товаров нет)")
    void testFindByFiltr_NotFound() {
        String search = "Несуществующее название";
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id"));
        Flux<Item> mockItems = Flux.fromIterable(new ArrayList<>());

        when(itemRepository.findByFiltr(search, pageable)).thenReturn(mockItems);
        Flux<Item> items = itemRepository.findByFiltr(search, pageable);

        assertEquals(0, items.collectList().block().size(), "Количество товаров должно быть 0");
    }

 */
}
