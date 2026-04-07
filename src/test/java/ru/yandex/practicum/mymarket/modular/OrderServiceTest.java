package ru.yandex.practicum.mymarket.modular;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mymarket.domain.Order;
import ru.yandex.practicum.mymarket.repository.OrderRepository;
import ru.yandex.practicum.mymarket.service.OrderService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("Модульное тестирование заказов")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @MockitoBean
    private OrderRepository orderRepository;

    @Test
    @DisplayName("Получение списка заказов (заказы есть)")
    void testFindAll_Success() {
        Flux<Order> mockOrders = Flux.fromIterable(List.of(
                new Order(5_000L),
                new Order(30_000L)
        ));

        when(orderRepository.findAll()).thenReturn(mockOrders);
        Flux<Order> orders = orderRepository.findAll();

        assertNotNull(orders.collectList().block(), "Заказы должены существовать");
        assertEquals(2, orders.collectList().block().size(), "Количество заказов должно быть 2");
        assertEquals(5_000L, orders.collectList().block().getFirst().getTotalSum(), "Суммарная стоимость первого заказа должна быть 5000");
    }

    @Test
    @DisplayName("Получение списка заказов (заказов нет)")
    void testFindAll_NotFound() {
        Flux<Order> mockOrders = Flux.fromIterable(new ArrayList<>());

        when(orderRepository.findAll()).thenReturn(mockOrders);
        Flux<Order> orders = orderRepository.findAll();

        assertEquals(0, orders.collectList().block().size(), "Количество заказов должно быть 0");
    }

    @Test
    @DisplayName("Получение заказа (заказ есть)")
    void testFindById_Success() {
        Long id = 1L;
        Mono<Order> mockOrder = Mono.just(new Order(5_000L));

        when(orderRepository.findById(id)).thenReturn(mockOrder);
        Mono<Order> monoOrder = orderRepository.findById(id);
        assertTrue(monoOrder.hasElement().block(), "Заказ должен существовать");

        Order order = monoOrder.block();

        assertEquals(5_000L, order.getTotalSum(), String.format("Суммарная стоимость заказа должна быть: %d", 5_000L));
    }

    @Test
    @DisplayName("Получение заказа (заказа нет)")
    void testFindById_NotFound() {
        Long id = -1L;
        when(orderRepository.findById(id)).thenReturn(Mono.empty());
        Mono<Order> monoOrder = orderRepository.findById(id);
        assertFalse(monoOrder.hasElement().block(), "Заказа не должно быть");
    }

    @Test
    @DisplayName("Сохранение заказа")
    void testSave() {
        Order mockOrder = new Order(5_000L);

        when(orderRepository.save(mockOrder)).thenReturn(Mono.just(mockOrder));
        Mono<Order> order = orderRepository.save(mockOrder);

        assertNotNull(order.block(), "Заказ должен существовать");
        assertEquals(5_000L, order.block().getTotalSum(), String.format("Суммарная стоимость заказа должна быть: %d", 5_000L));
    }
}
