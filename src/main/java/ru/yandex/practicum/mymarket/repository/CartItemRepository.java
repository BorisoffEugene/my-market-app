package ru.yandex.practicum.mymarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.mymarket.domain.CartItem;
import ru.yandex.practicum.mymarket.domain.Item;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByItemId(Long itemId);
    @Query(
            nativeQuery = true,
            value = """
                select
                    i.id, i.title, i.description, i.img_path, i.price, ci.count 
                from 
                    market.cart c
                    join market.cart_items ci on ci.cart_id = c.id
                    join market.items i on i.id = ci.item_id
                where
                    c.status = 'CURRENT'
                """
    )
    List<Item> findCartItems();
}
