package ru.yandex.practicum.mymarket.integration.web;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mymarket.config.RepositoryTestConfig;
import ru.yandex.practicum.mymarket.domain.OrderItem;
import ru.yandex.practicum.mymarket.dto.OrderDto;
import ru.yandex.practicum.mymarket.service.*;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Import(RepositoryTestConfig.class)
@DisplayName("Интеграционное (WEB) тестирование покупок")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class BuyControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private OrderService orderService;
    @MockitoBean
    private PaymentService paymentService;
    @MockitoBean
    private CartService cartService;

    @Test
    @DisplayName("Проверка неавторизованного пользователя")
    void shouldReturn3xx_WhenUserNotFound() {
        OrderDto order = new OrderDto(List.of(new OrderItem("Название 11", 1, 500L), new OrderItem("Название 12", 2, 500L)), 1_500L);
        order.setId(1L);
        when(paymentService.debit(cartService.total("user"))).thenReturn(Mono.just("OK"));
        when(orderService.sold("user")).thenReturn(Mono.just(order));

        webTestClient.post()
                .uri("/buy")
                .exchange()
                .expectStatus().is3xxRedirection();
    }

    @Test
    @WithMockUser(username = "user")
    @DisplayName("Покупка")
    void testBy() {
        OrderDto order = new OrderDto(List.of(new OrderItem("Название 11", 1, 500L), new OrderItem("Название 12", 2, 500L)), 1_500L);
        order.setId(1L);
        when(paymentService.debit(cartService.total("user"))).thenReturn(Mono.just("OK"));
        when(orderService.sold("user")).thenReturn(Mono.just(order));

        webTestClient.post()
                .uri("/buy")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/orders/1?newOrder=true");
    }
}
