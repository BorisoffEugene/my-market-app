package ru.yandex.practicum.mymarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.mymarket.domain.Cart;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findFirstByStatus(String status);

    @Query(
            nativeQuery = true,
            value = """
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
    Long cartTotal();

    @Modifying
    @Transactional
    @Query("update Cart c set c.status = 'SOLD' where c.status = 'CURRENT'")
    void sold();
}
