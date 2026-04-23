package ru.yandex.practicum.mymarket.integration.web;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mymarket.config.RepositoryTestConfig;
import ru.yandex.practicum.mymarket.controller.AdminItemController;
import ru.yandex.practicum.mymarket.dto.ItemDto;
import ru.yandex.practicum.mymarket.service.ItemService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@WebFluxTest(AdminItemController.class)
@Import(RepositoryTestConfig.class)
@DisplayName("Интеграционное (WEB) тестирование админ-панели")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class AdminItemControllerTest {
  /*  @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private ItemService itemService;
 todo
    @Test
    @DisplayName("Получение списка товаров (товары есть)")
    void testFindAll_Success() {
        List<ItemDto> items = List.of(
                new ItemDto("Название 1", "Описание 1", "/images/1.jpg", 1_000L, 1),
                new ItemDto("Название 2", "Описание 2", "/images/2.jpg", 2_000L, 2)
        );
        when(itemService.findAll()).thenReturn(Flux.fromIterable(items));

        webTestClient.get()
                .uri("/admin-items")
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
    @DisplayName("Получение списка товаров (товаров нет)")
    void testFindAll_NotFound() {
        when(itemService.findAll()).thenReturn(Flux.fromIterable(new ArrayList<>()));

        webTestClient.get()
                .uri("/admin-items")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_HTML)
                .expectBody(String.class);
    }

    @Test
    @DisplayName("Добавление товара")
    void testAdd() {
        webTestClient.get()
                .uri("/admin-items/add")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_HTML)
                .expectBody(String.class)
                .value(html -> {
                    assert html.contains("Название");
                    assert html.contains("Описание");
                });
    }

    @Test
    @DisplayName("Удаление товара")
    void testDelete() {
        when(itemService.deleteById(1L)).thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/admin-items/delete/1")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/admin-items");
    }

    @Test
    @DisplayName("Редактирование товара (товар есть)")
    void testEdit_Success() {
        ItemDto item = new ItemDto("Название 1", "Описание 1", "/images/1.jpg", 1_000L, 1);
        when(itemService.findById(1L)).thenReturn(Mono.just(item));

        webTestClient.get()
                .uri("/admin-items/edit/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_HTML)
                .expectBody(String.class)
                .value(html -> {
                    assert html.contains("Название 1");
                    assert html.contains("Описание 1");
                });
    }

    @Test
    @DisplayName("Редактирование товара (товара нет)")
    void testEdit_NotFound() {
        when(itemService.findById(-1L)).thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/admin-items/edit/-1")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/admin-items");
    }

 */
}
