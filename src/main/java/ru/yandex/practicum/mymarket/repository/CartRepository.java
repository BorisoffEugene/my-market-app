package ru.yandex.practicum.mymarket.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mymarket.domain.Cart;

public interface CartRepository extends ReactiveCrudRepository<Cart, Long> {
    Mono<Cart> findFirstByStatus(String status);

    @Query("""
                select
                    coalesce(sum(i.price * ci.count), 0)
                from
                    market.cart c
                    join market.cart_items ci on ci.cart_id = c.id
                    join market.items i on i.id = ci.item_id
                where
                    c.status = 'CURRENT'
                """
    )
    Mono<Long> cartTotal();

    @Modifying
    @Query("update market.cart set status = 'SOLD' where status = 'CURRENT'")
    Mono<Void> sold();
}
