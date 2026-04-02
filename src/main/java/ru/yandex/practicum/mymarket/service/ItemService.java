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

    public Mono<ItemDto> save(ItemDto itemDto) {
        return itemRepository.save(itemMapper.toEntity(itemDto)).map(itemMapper::toDto);
    }

    public Mono<Void> deleteById(Long id) {
        return itemRepository.deleteById(id);
    }

    public Mono<ItemDto> findById(Long id) {
        return itemRepository.findById(id).map(itemMapper::toDto);
    }

    public Mono<Page<ItemDto>> findByFiltr(String search, String sortType, int pageNumber, int pageSize) {
        Sort sort = switch (sortType) {
            case "ALPHA" -> Sort.by("title");
            case "PRICE" -> Sort.by("price");
            default ->  Sort.by("unsorted");
        };

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        return itemRepository.findByFiltr(search, pageable, 2)
                .map(itemMapper::toDto)
                .collectList()
                .zipWith(itemRepository.countByFiltr(search))
                .map(tuple -> new PageImpl<>(tuple.getT1(), pageable, tuple.getT2()));
    }
}
