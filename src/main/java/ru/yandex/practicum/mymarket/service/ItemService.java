package ru.yandex.practicum.mymarket.service;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.mymarket.dto.ItemDto;
import ru.yandex.practicum.mymarket.mapper.ItemMapper;
import ru.yandex.practicum.mymarket.repository.ItemRepository;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public ItemService(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    public Flux<ItemDto> findAll() {
        return itemRepository.findAll().map(itemMapper::toDto);
    }

    public void save(ItemDto itemDto) {
        itemRepository.save(itemMapper.toEntity(itemDto));
    }

    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

    public Mono<ItemDto> findById(Long id) {
        return itemRepository.findById(id).map(itemMapper::toDto);
    }

    public Flux<ItemDto> findByFiltr(String search, String sortType, int pageNumber, int pageSize) {
        Sort sort = switch (sortType) {
            case "ALPHA" -> Sort.by("title");
            case "PRICE" -> Sort.by("price");
            default -> Sort.unsorted();
        };

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        return itemRepository.findByFiltr(search, pageable).map(itemMapper::toDto);
    }
}
