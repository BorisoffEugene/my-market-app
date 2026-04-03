package ru.yandex.practicum.mymarket.modular;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.yandex.practicum.mymarket.domain.Item;
import ru.yandex.practicum.mymarket.repository.CartItemRepository;
import ru.yandex.practicum.mymarket.repository.CartRepository;
import ru.yandex.practicum.mymarket.service.CartService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("Модульное тестирование корзины")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class CartServiceTest {
    @Autowired
    private CartService cartService;

    @MockitoBean
    private CartItemRepository cartItemRepository;

    @MockitoBean
    private CartRepository cartRepository;

    @Test
    @DisplayName("Получение списка товаров в корзине (товары есть)")
    void testItems_Success() {
        Flux<Item> mockItems = Flux.fromIterable(List.of(
                new Item("Название 1", "Описание 1", "/images/1.jpg", 1_000L, 1),
                new Item("Название 2", "Описание 2", "/images/2.jpg", 2_000L, 2)
        ));

        when(cartItemRepository.findCartItems()).thenReturn(mockItems);
        Flux<Item> items = cartItemRepository.findCartItems();

        assertNotNull(items, "Товары должены существовать");
        assertEquals(2, items.collectList().block().size(), "Количество товаров должно быть 2");
        assertEquals("Название 1", items.collectList().block().getFirst().getTitle(), String.format("Название должно быть: %s", "Название 1"));
        assertEquals("Описание 1", items.collectList().block().getFirst().getDescription(), String.format("Описание должно быть: %s", "Описание 1"));
        assertEquals("/images/1.jpg", items.collectList().block().getFirst().getImgPath(), String.format("Путь к изображению должен быть: %s", "/images/1.jpg"));
        assertEquals(1_000L, items.collectList().block().getFirst().getPrice(), String.format("Цена должна быть: %d", 1_000L));
        assertEquals(1, items.collectList().block().getFirst().getCount(), String.format("Количество должно быть: %d", 1));
    }

    @Test
    @DisplayName("Получение списка товаров в корзине (товаров нет)")
    void testItems_NotFound() {
        Flux<Item> mockItems = Flux.fromIterable(new ArrayList<>());

        when(cartItemRepository.findCartItems()).thenReturn(mockItems);
        Flux<Item> items = cartItemRepository.findCartItems();

        assertEquals(0, items.collectList().block().size(), "Количество товаров должно быть 0");
    }

    @Test
    @DisplayName("Получение сумарной цены товаров в корзине (товары есть)")
    void testTotal_Success() {
        Mono<Long> mockTotal = Mono.just(1_500L);
        when(cartRepository.cartTotal()).thenReturn(mockTotal);
        Mono<Long> total = cartRepository.cartTotal();

        assertEquals(mockTotal, total, String.format("Суммарная цена должна быть %d", mockTotal.block()));
    }

    @Test
    @DisplayName("Получение сумарной цены товаров в корзине (товаров нет)")
    void testTotal_NotFound() {
        Mono<Long> mockTotal = Mono.just(0L);
        when(cartRepository.cartTotal()).thenReturn(mockTotal);
        Mono<Long> total = cartRepository.cartTotal();

        assertEquals(mockTotal, total, String.format("Суммарная цена должна быть %d", mockTotal.block()));
    }

    @Test
    @DisplayName("Продано")
    void testSold() {
        when(cartRepository.sold()).thenReturn(Mono.empty());
        Mono<Void> result = cartRepository.sold();
        StepVerifier.create(result).verifyComplete();
    }
}
