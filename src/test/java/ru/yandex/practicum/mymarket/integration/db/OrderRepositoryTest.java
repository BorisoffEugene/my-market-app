package ru.yandex.practicum.mymarket.integration.db;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.mymarket.domain.Order;
import ru.yandex.practicum.mymarket.domain.OrderItem;
import ru.yandex.practicum.mymarket.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DisplayName("Интеграционное (DB) тестирование заказов")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    private Order order1;
    private Order order2;


    @BeforeEach
    void beforeEach() {
        orderRepository.deleteAll();
        order1 = orderRepository.save(new Order(List.of(new OrderItem("Название 11", 1, 1_000L), new OrderItem("Название 12", 2, 2_000L)), 5_000L));
        order2 = orderRepository.save(new Order(List.of(new OrderItem("Название 21", 5, 3_000L), new OrderItem("Название 22", 3, 5_000L)), 30_000L));
    }

    @Test
    @DisplayName("Получение списка заказов (заказы есть)")
    void testFindAll_Success() {
        List<Order> orders = orderRepository.findAll(Sort.by("id"));

        assertNotNull(orders, "Заказы должены существовать");
        assertEquals(2, orders.size(), "Количество заказов должно быть 2");
        assertEquals(order1.getItems().size(), orders.getFirst().getItems().size(), String.format("Количество тваров в первом заказе должно быть %d", order1.getItems().size()));
        assertEquals(order1.getTotalSum(), orders.getFirst().getTotalSum(), String.format("Суммарная стоимость первого заказа должна быть: %d", order1.getTotalSum()));
        assertEquals(order1.getItems().getFirst().getTitle(), orders.getFirst().getItems().getFirst().getTitle(), String.format("Название первого товара в первом заказе должно быть: %s", order1.getItems().getFirst().getTitle()));
        assertEquals(order1.getItems().getFirst().getCount(), orders.getFirst().getItems().getFirst().getCount(), String.format("Количество первого товара в первом заказе должно быть: %d", order1.getItems().getFirst().getCount()));
        assertEquals(order1.getItems().getFirst().getPrice(), orders.getFirst().getItems().getFirst().getPrice(), String.format("Стоимость первого товара в первом заказе должно быть: %d", order1.getItems().getFirst().getPrice()));
    }

    @Test
    @DisplayName("Получение списка заказов (заказов нет)")
    void testFindAll_NotFound() {
        orderRepository.deleteAll();
        List<Order> orders = orderRepository.findAll();

        assertEquals(0, orders.size(), "Количество заказов должно быть 0");
    }

    @Test
    @DisplayName("Получение заказа (заказ есть)")
    void testFindById_Success() {
        Optional<Order> optionalOrder = orderRepository.findById(order1.getId());
        assertTrue(optionalOrder.isPresent(), "Заказ должен существовать");

        Order order = optionalOrder.get();

        assertEquals(order1.getItems().size(), order.getItems().size(), String.format("Количество товаров в заказе должно быть %d", order1.getItems().size()));
        assertEquals(order1.getTotalSum(), order.getTotalSum(), String.format("Суммарная стоимость заказа должна быть: %d", order1.getTotalSum()));
        assertEquals(order1.getItems().getFirst().getTitle(), order.getItems().getFirst().getTitle(), String.format("Название первого товара в заказе должно быть: %s", order1.getItems().getFirst().getTitle()));
        assertEquals(order1.getItems().getFirst().getCount(), order.getItems().getFirst().getCount(), String.format("Количество первого товара в заказе должно быть: %d", order1.getItems().getFirst().getCount()));
        assertEquals(order1.getItems().getFirst().getPrice(), order.getItems().getFirst().getPrice(), String.format("Стоимость первого товара в заказе должно быть: %d", order1.getItems().getFirst().getPrice()));
    }

    @Test
    @DisplayName("Получение заказа (заказа нет)")
    void testFindById_NotFound() {
        Optional<Order> optionalOrder = orderRepository.findById(-1L);
        assertFalse(optionalOrder.isPresent(), "Заказа не должно быть");
    }

    @Test
    @DisplayName("Сохранение заказа")
    void testSave() {
        Order order = orderRepository.save(new Order(List.of(new OrderItem("Название 31", 1, 1_000L), new OrderItem("Название 32", 2, 2_000L)), 5_000L));

        assertNotNull(order, "Заказ должен существовать");
        assertEquals(2, order.getItems().size(), String.format("Количество тваров в заказе должно быть %d", 2));
        assertEquals(5_000L, order.getTotalSum(), String.format("Суммарная стоимость заказа должна быть: %d", 5_000L));
        assertEquals("Название 31", order.getItems().getFirst().getTitle(), String.format("Название первого товара в заказе должно быть: %s", "Название 31"));
        assertEquals(1, order.getItems().getFirst().getCount(), String.format("Количество первого товара в заказе должно быть: %d", 1));
        assertEquals(1_000L, order.getItems().getFirst().getPrice(), String.format("Стоимость первого товара в заказе должно быть: %d", 1_000L));

        List<Order> orders = orderRepository.findAll();
        assertEquals(3, orders.size(), String.format("Количество заказов должно быть: %d", 3));
    }
}
