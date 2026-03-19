package ru.yandex.practicum.mymarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mymarket.repository.ItemRepository;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;


}
