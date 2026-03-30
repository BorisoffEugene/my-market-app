package ru.yandex.practicum.mymarket.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.mymarket.domain.Item;
import ru.yandex.practicum.mymarket.dto.ItemDto;

import java.util.List;

@Component
public class ItemMapper {
    public ItemMapper() {
    }

    public ItemDto toDto(Item item) {
        return new ItemDto(item.getId(), item.getTitle(), item.getDescription(), item.getImgPath(), item.getPrice(), item.getCount());
    }

    public Item toEntity(ItemDto itemDto) {
        return new Item(itemDto.getId(), itemDto.getTitle(), itemDto.getDescription(), itemDto.getImgPath(), itemDto.getPrice(), itemDto.getCount());
    }

    public List<ItemDto> toDtoList(List<Item> items) {
        return items.stream()
                .map(this::toDto)
                .toList();
    }

    public List<Item> toEntityList(List<ItemDto> itemsDto) {
        return itemsDto.stream()
                .map(this::toEntity)
                .toList();
    }
}
