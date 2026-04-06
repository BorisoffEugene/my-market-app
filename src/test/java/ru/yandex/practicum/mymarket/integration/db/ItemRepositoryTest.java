package ru.yandex.practicum.mymarket.integration.db;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.r2dbc.test.autoconfigure.DataR2dbcTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.yandex.practicum.mymarket.domain.Item;
import ru.yandex.practicum.mymarket.repository.ItemRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataR2dbcTest
@DisplayName("Интеграционное (DB) тестирование товаров")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;

    private Item item1;
    private Item item2;

    @BeforeEach
    void beforeEach() {
        itemRepository.deleteAll().block();
        item1 = itemRepository.save(new Item("Название 1", "Описание 1", "/images/1.jpg", 1_000L, 1)).block();
        item2 = itemRepository.save(new Item("Название 2", "Описание 2", "/images/2.jpg", 2_000L, 2)).block();
    }

    @Test
    @DisplayName("Получение списка товаров (товары есть)")
    void testFindAll_Success() {
        StepVerifier.create(itemRepository.findAll())
                .assertNext(item -> {
                    assertThat(item.getTitle().equals(item1.getTitle()));
                })
                .thenConsumeWhile(item -> true)
                .verifyComplete();
    }

    @Test
    @DisplayName("Получение списка товаров (товаров нет)")
    void testFindAll_NotFound() {
        itemRepository.deleteAll().block();

        StepVerifier.create(itemRepository.findAll())
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    @DisplayName("Сохранение товара")
    void testSave() {
        Mono<Item> item = itemRepository.save(new Item("Название 3", "Описание 3", "/images/3.jpg", 3_000L, 3));

        StepVerifier.create(item)
                .expectNextMatches(savedItem ->
                        savedItem.getId() != null && savedItem.getTitle().equals("Название 3")
                )
                .verifyComplete();

        StepVerifier.create(itemRepository.findAll())
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    @DisplayName("Удаление товара")
    void testDeleteById() {
        itemRepository.deleteById(item1.getId()).block();

        StepVerifier.create(itemRepository.findAll())
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("Получение товара (товар есть)")
    void testFindById_Success() {
        StepVerifier.create(itemRepository.findById(item1.getId()))
                .expectNextMatches(item -> item.getTitle().equals(item1.getTitle()))
                .verifyComplete();
    }

    @Test
    @DisplayName("Получение товара (товара нет)")
    void testFindById_NotFound() {
        StepVerifier.create(itemRepository.findById(-1L))
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    @DisplayName("Получение списка товаров + поиск + пагинация (товары есть)")
    void testFindByFiltr_Success() {
        String search = "Название";
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id"));

        StepVerifier.create(itemRepository.findByFiltr(search, pageable))
                .assertNext(item -> {
                    assertThat(item.getTitle().equals(item1.getTitle()));
                })
                .thenConsumeWhile(item -> true)
                .verifyComplete();
    }

    @Test
    @DisplayName("Получение списка товаров + поиск + пагинация (товаров нет)")
    void testFindByFiltr_NotFound() {
        String search = "Несуществующее название";
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id"));

        StepVerifier.create(itemRepository.findByFiltr(search, pageable))
                .expectNextCount(0)
                .verifyComplete();
    }
}