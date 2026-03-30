package ru.yandex.practicum.mymarket.modular;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.yandex.practicum.mymarket.domain.Order;
import ru.yandex.practicum.mymarket.domain.OrderItem;
import ru.yandex.practicum.mymarket.repository.OrderRepository;
import ru.yandex.practicum.mymarket.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<Order> mockOrders = List.of(
                new Order(List.of(new OrderItem("Название 11", 1, 1_000L), new OrderItem("Название 12", 2, 2_000L)), 5_000L),
                new Order(List.of(new OrderItem("Название 21", 5, 3_000L), new OrderItem("Название 22", 3, 5_000L)), 30_000L)
        );

        when(orderRepository.findAll()).thenReturn(mockOrders);
        List<Order> orders = orderRepository.findAll();

        assertNotNull(orders, "Заказы должены существовать");
        assertEquals(2, orders.size(), "Количество заказов должно быть 2");
        assertEquals(2, orders.getFirst().getItems().size(), "Количество тваров в первом заказе должно быть 2");
        assertEquals(5_000L, orders.getFirst().getTotalSum(), String.format("Суммарная стоимость первого заказа должна быть: %d", 5_000L));
        assertEquals("Название 11", orders.getFirst().getItems().getFirst().getTitle(), String.format("Название первого товара в первом заказе должно быть: %s", "Название 11"));
        assertEquals(1, orders.getFirst().getItems().getFirst().getCount(), String.format("Количество первого товара в первом заказе должно быть: %d", 1));
        assertEquals(1_000L, orders.getFirst().getItems().getFirst().getPrice(), String.format("Стоимость первого товара в первом заказе должно быть: %d", 1_000L));
    }

    @Test
    @DisplayName("Получение списка заказов (заказов нет)")
    void testFindAll_NotFound() {
        List<Order> mockOrders = new ArrayList<>();

        when(orderRepository.findAll()).thenReturn(mockOrders);
        List<Order> orders = orderRepository.findAll();

        assertEquals(0, orders.size(), "Количество заказов должно быть 0");
    }

    @Test
    @DisplayName("Получение заказа (заказ есть)")
    void testFindById_Success() {
        Long id = 1L;
        Order mockOrder = new Order(List.of(new OrderItem("Название 11", 1, 1_000L), new OrderItem("Название 12", 2, 2_000L)), 5_000L);

        when(orderRepository.findById(id)).thenReturn(Optional.of(mockOrder));
        Optional<Order> optionalOrder = orderRepository.findById(id);
        assertTrue(optionalOrder.isPresent(), "Заказ должен существовать");

        Order order = optionalOrder.get();

        assertEquals(2, order.getItems().size(), "Количество товаров в заказе должно быть 2");
        assertEquals(5_000L, order.getTotalSum(), String.format("Суммарная стоимость заказа должна быть: %d", 5_000L));
        assertEquals("Название 11", order.getItems().getFirst().getTitle(), String.format("Название первого товара в заказе должно быть: %s", "Название 11"));
        assertEquals(1, order.getItems().getFirst().getCount(), String.format("Количество первого товара в заказе должно быть: %d", 1));
        assertEquals(1_000L, order.getItems().getFirst().getPrice(), String.format("Стоимость первого товара в заказе должно быть: %d", 1_000L));
    }

    @Test
    @DisplayName("Получение заказа (заказа нет)")
    void testFindById_NotFound() {
        Long id = -1L;
        when(orderRepository.findById(id)).thenReturn(Optional.empty());
        Optional<Order> optionalOrder = orderRepository.findById(id);
        assertFalse(optionalOrder.isPresent(), "Заказа не должно быть");
    }

    @Test
    @DisplayName("Сохранение заказа")
    void testSave() {
        Order mockOrder = new Order(List.of(new OrderItem("Название 11", 1, 1_000L), new OrderItem("Название 12", 2, 2_000L)), 5_000L);

        when(orderRepository.save(mockOrder)).thenReturn(mockOrder);
        Order order = orderRepository.save(mockOrder);

        assertNotNull(order, "Заказ должен существовать");
        assertEquals(2, order.getItems().size(), "Количество тваров в заказе должно быть 2");
        assertEquals(5_000L, order.getTotalSum(), String.format("Суммарная стоимость заказа должна быть: %d", 5_000L));
        assertEquals("Название 11", order.getItems().getFirst().getTitle(), String.format("Название первого товара в заказе должно быть: %s", "Название 11"));
        assertEquals(1, order.getItems().getFirst().getCount(), String.format("Количество первого товара в заказе должно быть: %d", 1));
        assertEquals(1_000L, order.getItems().getFirst().getPrice(), String.format("Стоимость первого товара в заказе должно быть: %d", 1_000L));
    }
}
