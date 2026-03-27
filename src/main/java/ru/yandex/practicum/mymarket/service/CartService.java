package ru.yandex.practicum.mymarket.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.mymarket.dto.ItemDto;
import ru.yandex.practicum.mymarket.mapper.ItemMapper;
import ru.yandex.practicum.mymarket.repository.ItemRepository;

import java.util.List;

@Service
public class CartService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public CartService(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    public List<ItemDto> items() {
        return itemMapper.toDtoList(itemRepository.findByCountGreaterThan(0));
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
