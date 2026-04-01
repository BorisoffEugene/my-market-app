package ru.yandex.practicum.mymarket.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mymarket.domain.Item;

public interface ItemRepository extends R2dbcRepository<Item, Long> {
    @Query("""
                    select
                    	i.id, i.title, i.description, i.price, i.img_path, coalesce(ci.count, 0) count
                    from
                    	market.items i
                    	left join market.cart c on c.status = 'CURRENT'
                    	left join market.cart_items ci on ci.cart_id = c.id and ci.item_id = i.id
                    where
                    	i.title ilike '%'||:search||'%' or i.description ilike '%'||:search||'%'
                    """
    )
    Flux<Item> findByFiltr(String search, Pageable pageable);

    @Query("""
                    select
                    	i.id, i.title, i.description, i.price, i.img_path, coalesce(ci.count, 0) count
                    from
                    	market.items i
                    	left join market.cart c on c.status = 'CURRENT'
                    	left join market.cart_items ci on ci.cart_id = c.id and ci.item_id = i.id
                    where
                    	i.id = :id
                    """
    )
    Mono<Item> findById(@Param("id") Long id);
}
