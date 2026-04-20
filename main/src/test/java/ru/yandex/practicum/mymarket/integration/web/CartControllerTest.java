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
import ru.yandex.practicum.mymarket.controller.CartController;
import ru.yandex.practicum.mymarket.dto.ItemDto;
import ru.yandex.practicum.mymarket.service.CartService;
import ru.yandex.practicum.mymarket.service.PaymentService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@WebFluxTest(CartController.class)
@Import(RepositoryTestConfig.class)
@DisplayName("Интеграционное (WEB) тестирование корзины")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class CartControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private CartService cartService;
    @MockitoBean
    private PaymentService paymentService;
/*
    @Test
    @DisplayName("Получение списка товаров в корзине (товары есть)")
    void testItems_Success() {
        Flux<ItemDto> items = Flux.fromIterable(List.of(
                new ItemDto("Название 1", "Описание 1", "/images/1.jpg", 1_000L, 1),
                new ItemDto("Название 2", "Описание 2", "/images/2.jpg", 2_000L, 2)
        ));

        when(cartService.items("user")).thenReturn(items); //todo
        when(cartService.total("user")).thenReturn(Mono.just(5_000L)); //todo

        webTestClient.get()
                .uri("/cart/items")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_HTML)
                .expectBody(String.class)
                .value(html -> {
                    assert html.contains("Название 1");
                    assert html.contains("Описание 1");
                    assert html.contains("5000");
                });
    }

    @Test
    @DisplayName("Получение списка товаров в корзине (товаров нет)")
    void testItems_NotFound() {
        when(cartService.items("user")).thenReturn(Flux.fromIterable(new ArrayList<>())); //todo
        when(cartService.total("user")).thenReturn(Mono.just(0L)); //todo

        webTestClient.get()
                .uri("/cart/items")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_HTML)
                .expectBody(String.class);
    }

    @Test
    @DisplayName("Изменение количества товара в корзине")
    void testChangeCount() {
        when(cartService.changeCount("PLUS", 1L, "user")).thenReturn(Mono.empty()); //todo

        webTestClient.post()
                .uri("/cart/items?id=1&action=PLUS")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/cart/items");
    }

 */
}
