package ru.yandex.practicum.mymarket.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mymarket.domain.Item;

public interface ItemRepository extends ReactiveCrudRepository<Item, Long> {
    @Query("""
                    select
                    	i.id, i.title, i.description, i.price, i.img_path, coalesce(ci.count, 0) count
                    from
                    	market.items i
                    	left join market.cart c on c.status = 'CURRENT'
                    	left join market.cart_items ci on ci.cart_id = c.id and ci.item_id = i.id
                    where
                    	i.title ilike '%'||:search||'%' or i.description ilike '%'||:search||'%'
                    order by
                        case :#{#pageable.getSort().get().toList().getFirst().getProperty()}
                            when 'title' then i.title
                            when 'price' then to_char(i.price, '00000000000000000000')
                        end
                    limit :#{#pageable.pageSize} offset :#{#pageable.offset}
                    """
    )
    Flux<Item> findByFiltr(String search, Pageable pageable, int order);

    @Query("""
                    select
                    	count(i.id)
                    from
                    	market.items i
                    where
                    	i.title ilike '%'||:search||'%' or i.description ilike '%'||:search||'%'
                    """
    )
    Mono<Long> countByFiltr(String search);

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
