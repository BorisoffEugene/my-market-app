package ru.yandex.practicum.mymarket.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mymarket.domain.OrderItem;

public interface OrderItemRepository extends ReactiveCrudRepository<OrderItem, Long> {
    Flux<OrderItem> findAllByOrderId(Long orderId);

    @Modifying
    @Query("""
        insert into market.order_items(order_id, title, count, price)
            select
                o.id order_id, i.title, ci.count, i.price
            from
                market.orders o
                join market.cart c on c.status = 'CURRENT' and c.username = o.username
                join market.cart_items ci on ci.cart_id = c.id
                join market.items i on i.id = ci.item_id
            where
                o.id = :orderId
        """)
    Mono<Void> sold(Long orderId);
}
