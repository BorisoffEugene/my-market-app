package ru.yandex.practicum.mymarket.integration.db;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.r2dbc.test.autoconfigure.DataR2dbcTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.yandex.practicum.mymarket.domain.Order;
import ru.yandex.practicum.mymarket.repository.OrderRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataR2dbcTest
@DisplayName("Интеграционное (DB) тестирование заказов")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    private Order order1;
    private Order order2;

    @BeforeEach
    void beforeEach() {
        orderRepository.deleteAll().block();
        order1 = orderRepository.save(new Order(5_000L)).block();
        order2 = orderRepository.save(new Order(30_000L)).block();
    }

    @Test
    @DisplayName("Получение списка заказов (заказы есть)")
    void testFindAll_Success() {
        StepVerifier.create(orderRepository.findAll())
                .assertNext(order -> {
                    assertThat(order.getTotalSum().equals(order1.getTotalSum()));
                })
                .thenConsumeWhile(order -> true)
                .verifyComplete();
    }

    @Test
    @DisplayName("Получение списка заказов (заказов нет)")
    void testFindAll_NotFound() {
        orderRepository.deleteAll().block();

        StepVerifier.create(orderRepository.findAll())
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    @DisplayName("Получение заказа (заказ есть)")
    void testFindById_Success() {
        StepVerifier.create(orderRepository.findById(order1.getId()))
                .assertNext(order -> {
                    assertThat(order.getTotalSum().equals(order1.getTotalSum()));
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Получение заказа (заказа нет)")
    void testFindById_NotFound() {
        StepVerifier.create(orderRepository.findById(-1L))
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    @DisplayName("Сохранение заказа")
    void testSave() {
        Mono<Order> order = orderRepository.save(new Order(15_000L));

        StepVerifier.create(order)
                .expectNextMatches(savedOrder ->
                        savedOrder.getId() != null && savedOrder.getTotalSum().equals(15_000L)
                )
                .verifyComplete();

        StepVerifier.create(orderRepository.findAll())
                .expectNextCount(3)
                .verifyComplete();
    }
}