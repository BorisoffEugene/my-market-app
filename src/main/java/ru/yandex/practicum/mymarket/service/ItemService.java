package ru.yandex.practicum.mymarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mymarket.domain.Item;
import ru.yandex.practicum.mymarket.repository.ItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public List<Item> findAll(Sort sort) {
        return itemRepository.findAll(sort);
    }

    public void save(Item item) {
        itemRepository.save(item);
    }

    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }
}
