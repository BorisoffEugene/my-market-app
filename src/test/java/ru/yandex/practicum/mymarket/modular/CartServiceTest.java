package ru.yandex.practicum.mymarket.modular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.mymarket.repository.ItemRepository;
import ru.yandex.practicum.mymarket.service.CartService;

@SpringBootTest
public class CartServiceTest {
    @Autowired
    private CartService cartService;

    @Autowired
    private ItemRepository itemRepository;

}
