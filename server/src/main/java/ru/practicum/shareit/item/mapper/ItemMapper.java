package ru.practicum.shareit.item.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.comment.mapper.CommentMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithDate;
import ru.practicum.shareit.item.dto.ItemShortDto;
import ru.practicum.shareit.item.entity.Item;

import java.time.LocalDateTime;

@UtilityClass
public class ItemMapper {
    public static ItemDto entityItemToDto(Item item) {
        ItemDto result = ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
        if (item.getComments() != null) {
            result.setComments(item.getComments().stream().map(CommentMapper::toDto).toList());
            return result;
        }
        return result;
    }

    public static ItemDtoWithDate entityItemToDtoWithDate(Item item, LocalDateTime last, LocalDateTime next) {
        ItemDtoWithDate result = ItemDtoWithDate.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
        if (item.getComments() != null) {
            result.setComments(item.getComments().stream().map(CommentMapper::toDto).toList());
        }
        if (last != null && next != null) {
            result.setLastBooking(last);
            result.setNextBooking(next);
        }
        return result;
    }

    public static Item dtoToEntityItem(ItemDto itemDto, long ownerId) {
        return Item.builder()
                .id(itemDto.getId())
                .ownerId(ownerId)
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .build();
    }

    public static ItemShortDto entityToShortDto(Item item) {
        return ItemShortDto.builder()
                .id(item.getId())
                .name(item.getName())
                .ownerId(item.getOwnerId())
                .build();
    }
}
