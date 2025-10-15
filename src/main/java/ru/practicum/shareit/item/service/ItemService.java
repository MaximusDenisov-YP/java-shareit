package ru.practicum.shareit.item.service;

import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithDate;

import java.util.List;

public interface ItemService {
    List<ItemDto> getAllUsersItems(Long userId);

    ItemDtoWithDate getItemById(Long itemId, Long userId);

    List<ItemDto> getSearchingItemsByText(String text);

    ItemDto createItem(ItemDto itemDto, Long userId);

    CommentDto createComment(CommentDto commentDto, Long itemId, Long userid);

    ItemDto updateItem(ItemDto itemDto, Long itemId, Long userId);

    void deleteItemById(long itemId);
}
