package ru.yandex.practicum.mymarket.integration.db;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.r2dbc.test.autoconfigure.DataR2dbcTest;
import ru.yandex.practicum.mymarket.repository.CartRepository;
import ru.yandex.practicum.mymarket.repository.ItemRepository;

@DataR2dbcTest
@DisplayName("Интеграционное (DB) тестирование корзины")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class CartRepositoryTest {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ItemRepository itemRepository;
/*

    private Item item;

    @BeforeEach
    void beforeEach() {
        itemRepository.deleteAll();
        item = itemRepository.save(new Item("Название 1", "Описание 1", "/images/1.jpg", 1_000L, 1));
        cartRepository.deleteAll();
        cartService.changeCount("PLUS", item.getId());
    }

    @Test
    @DisplayName("Суммарная цена товаров в корзине")
    void testTotal() {
        Long total = cartRepository.cartTotal();
        assertEquals(item.getCount() * item.getPrice(), total, String.format("Суммарная цена тваров в корзине должна быть %d", item.getCount() * item.getPrice()));
    }

    @Test
    @DisplayName("Получение текущей корзины (корзина есть)")
    void testFindFirstByStatus_Success() {
        Optional<Cart> cart = cartRepository.findFirstByStatus("CURRENT");
        assertTrue(cart.isPresent(), "Корзина должна быть");
        assertEquals(item.getCount() * item.getPrice(), cart.get().getTotal(), String.format("Суммарная цена тваров в корзине должна быть %d", item.getCount() * item.getPrice()));
    }

    @Test
    @DisplayName("Получение текущей корзины (корзины нет)")
    void testFindFirstByStatus_NotFound() {
        Optional<Cart> cart = cartRepository.findFirstByStatus("NOT_FOUND");
        assertFalse(cart.isPresent(), "Корзины не должно быть");
    }

    @Test
    @DisplayName("Сохранение корзины")
    void testSave() {
        Optional<Cart> optionalCart = cartRepository.findFirstByStatus("CURRENT");
        Cart cart = cartRepository.save(optionalCart.get());

        assertNotNull(cart, "Корзина должна быть");
        assertEquals(item.getCount() * item.getPrice(), cart.getTotal(), String.format("Суммарная цена тваров в корзине должна быть %d", item.getCount() * item.getPrice()));
    }

 */
}