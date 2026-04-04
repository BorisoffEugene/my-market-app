package ru.yandex.practicum.mymarket.integration.db;

import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.r2dbc.test.autoconfigure.DataR2dbcTest;
import ru.yandex.practicum.mymarket.repository.CartItemRepository;
import ru.yandex.practicum.mymarket.repository.CartRepository;
import ru.yandex.practicum.mymarket.repository.ItemRepository;

@DataR2dbcTest
@DisplayName("Интеграционное (DB) тестирование товаров в корзине")
public class CartItemRepositoryTest {
    @Autowired
    private CartItemRepository cartItemRepository;
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
    @DisplayName("Получение товаров в корзине (товары есть)")
    void testFindCartItems_Success() {
        List<Item> items = cartItemRepository.findCartItems();
        assertNotNull(items, "Товары в корзине должны быть");
        assertEquals(1, items.size(), String.format("Товаров в корзине должно быть: %d", 1));
        assertEquals(item.getTitle(), items.getFirst().getTitle(), String.format("Наименование товара в корзине: %s", item.getTitle()));
    }

    @Test
    @DisplayName("Получение товаров в корзине (товаров нет)")
    void testFindCartItems_NotFound() {
        cartRepository.deleteAll();
        List<Item> items = cartItemRepository.findCartItems();
        assertEquals(0, items.size(), String.format("Товаров в корзине должно быть: %d", 0));
    }

    @Test
    @DisplayName("Получение товара в корзине (товар есть)")
    void testFindByItemId_Success() {
        Optional<CartItem> cartItem = cartItemRepository.findByItemId(item.getId());
        assertTrue(cartItem.isPresent(), "Товары в корзине должны быть");
        assertEquals(item.getId(), cartItem.get().getItemId(), String.format("ID товара в корзине должен быть: %s", item.getId()));
    }

    @Test
    @DisplayName("Получение товара в корзине (товара нет)")
    void testFindByItemId_NotFound() {
        Optional<CartItem> cartItem = cartItemRepository.findByItemId(-1L);
        assertFalse(cartItem.isPresent(), "Товара в корзине не должны быть");
    }

 */
}