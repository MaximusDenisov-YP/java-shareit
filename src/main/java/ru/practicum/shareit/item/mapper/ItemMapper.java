package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.entity.Item;

public class ItemMapper {
    public static ItemDto entityItemToDto(Item item) {
        return ItemDto.builder()
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }

    public static Item dtoToEntityItem(ItemDto itemDto, long itemId, long ownerId) {
        return Item.builder()
                .id(itemId)
                .ownerId(ownerId)
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .build();
    }
}
