package ru.yandex.practicum.mymarket.integration.web;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mymarket.config.RepositoryTestConfig;
import ru.yandex.practicum.mymarket.controller.ItemController;
import ru.yandex.practicum.mymarket.dto.ItemDto;
import ru.yandex.practicum.mymarket.service.CartService;
import ru.yandex.practicum.mymarket.service.ItemService;

import java.util.List;

import static org.mockito.Mockito.*;

@WebFluxTest(ItemController.class)
@Import(RepositoryTestConfig.class)
@DisplayName("Интеграционное (WEB) тестирование товаров")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class ItemControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private ItemService itemService;

    @MockitoBean
    private CartService cartService;
/*
    @Test
    @DisplayName("Получение списка товаров + поиск + пагинация (товары есть)")
    void testFindByFiltr_Success() {
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

        when(itemService.findByFiltr(search, sort, pageNumber, pageSize)).thenReturn(Mono.just(paging));

        webTestClient.get()
                .uri("/items")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_HTML)
                .expectBody(String.class)
                .value(html -> {
                    assert html.contains("Название 1");
                    assert html.contains("Описание 1");
                    assert html.contains("1000");
                });
    }

    @Test
    @DisplayName("Получение списка товаров + поиск + пагинация (товаров нет)")
    void testFindByFiltr_NotFound() {
        String search = "";
        String sort = "NO";
        int pageNumber = 0;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.unsorted());
        Page<ItemDto> paging = new PageImpl<>(List.of(), pageable, 0);

        when(itemService.findByFiltr(search, sort, pageNumber, pageSize)).thenReturn(Mono.just(paging));

        webTestClient.get()
                .uri("/items")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_HTML)
                .expectBody(String.class);
    }

    @Test
    @DisplayName("Получение товара (товар есть)")
    void testFindById_Success() {
        Long id = 1L;
        ItemDto item = new ItemDto("Название 1", "Описание 1", "/images/1.jpg", 1_000L, 1);
        item.setId(id);
        when(itemService.findById(id)).thenReturn(Mono.just(item));

        webTestClient.get()
                .uri("/items/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_HTML)
                .expectBody(String.class)
                .value(html -> {
                    assert html.contains("Название 1");
                    assert html.contains("Описание 1");
                    assert html.contains("1000");
                });
    }

    @Test
    @DisplayName("Получение товара (товара нет)")
    void testFindById_NotFound() {
        when(itemService.findById(-1L)).thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/items/-1")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/items");
    }

    @Test
    @DisplayName("Изменение количества товара на витрине")
    void testDoItemsAction() {
        when(cartService.changeCount("PLUS", 1L, "user")).thenReturn(Mono.empty()); //todo

        webTestClient.post()
                .uri("/items?id=1&action=PLUS")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/items?search=&sort=NO&pageNumber=0&pageSize=5");
    }

    @Test
    @DisplayName("Изменение количества товара в карточке товара")
    void testDoItemAction() {
        String action = "PLUS";
        Long id = 1L;

        when(cartService.changeCount("PLUS", 1L, "user")).thenReturn(Mono.empty()); //todo

        webTestClient.post()
                .uri("/items/1?action=PLUS")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/items/1");
    }

 */
}
