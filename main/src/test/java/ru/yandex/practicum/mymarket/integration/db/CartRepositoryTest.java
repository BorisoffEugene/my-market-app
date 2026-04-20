package ru.yandex.practicum.mymarket.integration.db;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.r2dbc.test.autoconfigure.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import reactor.test.StepVerifier;
import ru.yandex.practicum.mymarket.config.RepositoryTestConfig;
import ru.yandex.practicum.mymarket.domain.Cart;
import ru.yandex.practicum.mymarket.domain.CartItem;
import ru.yandex.practicum.mymarket.domain.Item;
import ru.yandex.practicum.mymarket.repository.CartItemRepository;
import ru.yandex.practicum.mymarket.repository.CartRepository;
import ru.yandex.practicum.mymarket.repository.ItemRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataR2dbcTest
@Import(RepositoryTestConfig.class)
@DisplayName("Интеграционное (DB) тестирование корзины")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class CartRepositoryTest {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ItemRepository itemRepository;

    private Item item;
/*
    @BeforeEach
    void beforeEach() {
        itemRepository.deleteAll().block();
        item = itemRepository.save(new Item("Название 1", "Описание 1", "/images/1.jpg", 1_000L, 1)).block();
        cartRepository.deleteAll().block();
        Cart cart = cartRepository.save(new Cart("user")).block(); //todo
        CartItem cartItem = cartItemRepository.save(new CartItem(cart.getId(), item.getId())).block();
        cartItem.incCount();
        cartItemRepository.save(cartItem).block();
        cart.setTotal(1_000L);
        cartRepository.save(cart).block();
    }

    @Test
    @DisplayName("Суммарная цена товаров в корзине")
    void testTotal() {
        StepVerifier.create(cartRepository.cartTotal("user"))//todo
                .assertNext(total -> {
                    assertThat(total.equals(1_000L));
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Получение текущей корзины (корзина есть)")
    void testFindFirstByStatus_Success() {
        StepVerifier.create(cartRepository.findFirstByStatusAndUsername("CURRENT", "user"))//todo
                .assertNext(cart -> {
                    assertThat(cart.getTotal().equals(1_000L));
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Получение текущей корзины (корзины нет)")
    void testFindFirstByStatus_NotFound() {
        StepVerifier.create(cartRepository.findFirstByStatusAndUsername("NOT_FOUND", "user"))//todo
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    @DisplayName("Сохранение корзины")
    void testSave() {
        Cart savedCart = cartRepository.findFirstByStatusAndUsername("CURRENT", "user").block(); //todo
        StepVerifier.create(cartRepository.save(savedCart))
                .assertNext(cart -> {
                    assertThat(cart.getTotal().equals(1_000L));
                })
                .verifyComplete();
    }

 */
}