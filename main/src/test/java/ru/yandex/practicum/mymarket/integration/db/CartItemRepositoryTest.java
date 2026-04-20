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
@DisplayName("Интеграционное (DB) тестирование товаров в корзине")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class CartItemRepositoryTest {
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
    @DisplayName("Получение товаров в корзине (товары есть)")
    void testFindCartItems_Success() {
        StepVerifier.create(cartItemRepository.findCartItems("user"))//todo
                .assertNext(findItem -> {
                    assertThat(findItem.getTitle().equals(item.getTitle()));
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Получение товаров в корзине (товаров нет)")
    void testFindCartItems_NotFound() {
        cartRepository.deleteAll().block();
        StepVerifier.create(cartItemRepository.findCartItems("user"))//todo
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    @DisplayName("Получение товара в корзине (товар есть)")
    void testFindByItemId_Success() {
        StepVerifier.create(cartItemRepository.findByItemId(item.getId()))
                .expectNextMatches(item -> item.getItemId().equals(item.getId()))
                .verifyComplete();
    }

    @Test
    @DisplayName("Получение товара в корзине (товара нет)")
    void testFindByItemId_NotFound() {
        StepVerifier.create(cartItemRepository.findByItemId(-1L))
                .expectNextCount(0)
                .verifyComplete();
    }

 */
}