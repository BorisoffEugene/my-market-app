package ru.yandex.practicum.mymarket.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import ru.yandex.practicum.mymarket.domain.Item;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query(
            nativeQuery = true,
            value = """
                    select
                    	i.id, i.title, i.description, i.price, i.img_path, coalesce(ci.count, 0) count
                    from
                    	market.items i
                    	left join market.cart c on c.status = 'CURRENT'
                    	left join market.cart_items ci on ci.cart_id = c.id and ci.item_id = i.id
                    where
                    	i.title ilike '%'||:search||'%' or i.description ilike '%'||:search||'%'
                    """,
            countQuery = """
                    select
                    	count(i.id)
                    from
                    	market.items i
                    where
                    	i.title ilike '%'||:search||'%' or i.description ilike '%'||:search||'%'
                    """
    )
    Page<Item> findByFiltr(@Param("search") String search, Pageable pageable);

    @Query(
            nativeQuery = true,
            value = """
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
    Optional<Item> findById(@Param("id") Long id);
}
