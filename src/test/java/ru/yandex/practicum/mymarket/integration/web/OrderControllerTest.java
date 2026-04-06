package ru.yandex.practicum.mymarket.integration.web;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mymarket.controller.OrderController;
import ru.yandex.practicum.mymarket.domain.OrderItem;
import ru.yandex.practicum.mymarket.dto.OrderDto;
import ru.yandex.practicum.mymarket.service.OrderService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@WebFluxTest(OrderController.class)
@DisplayName("Интеграционное (WEB) тестирование заказов")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class OrderControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private OrderService orderService;

    @Test
    @DisplayName("Получение списка заказов (заказы есть)")
    void testFindAll_Success() {
        Flux<OrderDto> orders = Flux.fromIterable(List.of(
                new OrderDto(List.of(new OrderItem("Название 11", 1, 1_000L), new OrderItem("Название 12", 2, 2_000L)), 5_000L),
                new OrderDto(List.of(new OrderItem("Название 21", 5, 3_000L), new OrderItem("Название 22", 3, 5_000L)), 30_000L)
        ));
        when(orderService.findAll()).thenReturn(orders);

        webTestClient.get()
                .uri("/orders")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_HTML)
                .expectBody(String.class)
                .value(html -> {
                    assert html.contains("Название 11");
                    assert html.contains("Название 12");
                    assert html.contains("30000");
                });
    }

    @Test
    @DisplayName("Получение списка заказов (заказов нет)")
    void testFindAll_NotFound() {
        when(orderService.findAll()).thenReturn(Flux.fromIterable(new ArrayList<>()));

        webTestClient.get()
                .uri("/orders")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_HTML)
                .expectBody(String.class);
    }

    @Test
    @DisplayName("Получение заказа (заказ есть)")
    void testFindById_Success() {
        Long id = 1L;
        OrderDto order = new OrderDto(List.of(new OrderItem("Название 11", 1, 1_000L), new OrderItem("Название 12", 2, 2_000L)), 5_000L);
        order.setId(id);
        when(orderService.findById(id)).thenReturn(Mono.just(order));

        webTestClient.get()
                .uri("/orders/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_HTML)
                .expectBody(String.class)
                .value(html -> {
                    assert html.contains("Название 11");
                    assert html.contains("Название 12");
                    assert html.contains("5000");
                });
    }

    @Test
    @DisplayName("Получение заказа (заказа нет)")
    void testFindById_NotFound() {
        when(orderService.findById(-1L)).thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/orders/-1")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/orders");
    }
}
