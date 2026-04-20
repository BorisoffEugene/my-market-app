package ru.yandex.practicum.mymarket.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mymarket.domain.CartItem;
import ru.yandex.practicum.mymarket.domain.Item;

public interface CartItemRepository extends ReactiveCrudRepository<CartItem, Long> {
    Mono<CartItem> findByItemId(Long itemId);

    @Query("""
                    select
                        i.id, i.title, i.description, i.img_path, i.price, ci.count
                    from
                        market.cart c
                        join market.cart_items ci on ci.cart_id = c.id
                        join market.items i on i.id = ci.item_id
                    where
                        c.status = 'CURRENT' and c.username = :username
                    """
    )
    Flux<Item> findCartItems(String username);
}
