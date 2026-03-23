package ru.yandex.practicum.mymarket.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.mymarket.domain.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "update market.items set count = case when (count = 0 and :action = 'MINUS') or (:action = 'DELETE') then 0 else count + case :action when 'PLUS' then 1 when 'MINUS' then -1 else 0 end end where id = :id", nativeQuery = true)
    void changeCount(@Param("action") String action, @Param("id") Long id);

    List<Item> findByCountGreaterThan(int minCount);

    @Query("select coalesce(sum(i.price * i.count), 0) from Item i where i.count > :minCount")
    int sumPriceByCountGreaterThan(@Param("minCount") int minCount);
}
