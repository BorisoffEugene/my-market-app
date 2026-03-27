package ru.yandex.practicum.mymarket.service;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mymarket.domain.Item;
import ru.yandex.practicum.mymarket.dto.ItemDto;
import ru.yandex.practicum.mymarket.mapper.ItemMapper;
import ru.yandex.practicum.mymarket.repository.ItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public ItemService(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    public List<ItemDto> findAll(Sort sort) {
        return itemMapper.toDtoList(itemRepository.findAll(sort));
    }

    public void save(ItemDto itemDto) {
        Item item = itemMapper.toEntity(itemDto);
        itemRepository.save(item);
    }

    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

    public Optional<ItemDto> findById(Long id) {
        return Optional.of(itemMapper.toDto(itemRepository.findById(id).get()));
    }

    public Page<ItemDto> findByFiltr(String search, String sortType, int pageNumber, int pageSize) {
        Sort sort = switch (sortType) {
            case "ALPHA" -> Sort.by("title");
            case "PRICE" -> Sort.by("price");
            default -> Sort.unsorted();
        };

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Item> itemPage = itemRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, search, pageable);
        return itemPage.map(itemMapper::toDto);
    }

    public void changeCount(String action, Long id) {
        itemRepository.changeCount(action, id);
    }
}
