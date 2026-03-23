package ru.yandex.practicum.mymarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mymarket.domain.Item;
import ru.yandex.practicum.mymarket.repository.ItemRepository;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private ItemRepository itemRepository;

    public List<Item> items() {
        return itemRepository.findByCountGreaterThan(0);
    }

    public Long total() {
        return itemRepository.sumPriceByCountGreaterThan(0);
    }

    public void changeCount(String action, Long id) {
        itemRepository.changeCount(action, id);
    }

    public void clearCount() {
        itemRepository.clearCount();
    }
}
