package ru.yandex.practicum.mymarket.integration.web;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mymarket.config.RepositoryTestConfig;
import ru.yandex.practicum.mymarket.controller.BuyController;
import ru.yandex.practicum.mymarket.domain.OrderItem;
import ru.yandex.practicum.mymarket.dto.OrderDto;
import ru.yandex.practicum.mymarket.service.*;

import java.util.List;

import static org.mockito.Mockito.*;

@WebFluxTest(BuyController.class)
@Import(RepositoryTestConfig.class)
@DisplayName("Интеграционное (WEB) тестирование покупок")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class BuyControllerTest {
 /*   @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private OrderService orderService;
    @MockitoBean
    private PaymentService paymentService;
    @MockitoBean
    private CartService cartService;
 todo
    @Test
    @DisplayName("Покупка")
    void testBy() {
        OrderDto order = new OrderDto(List.of(new OrderItem("Название 11", 1, 500L), new OrderItem("Название 12", 2, 500L)), 1_500L);
        order.setId(1L);
        when(paymentService.debit(cartService.total("user"))).thenReturn(Mono.just("OK")); //todo
        when(orderService.sold("user")).thenReturn(Mono.just(order)); //todo

        webTestClient.post()
                .uri("/buy")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/orders/1?newOrder=true");
    }

 */
}
