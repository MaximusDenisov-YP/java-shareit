package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.entity.Item;

import java.util.List;

public interface ItemService {
    List<Item> getAllUsersItems(Long userId);

    Item getItemById(Long itemId, Long userId);

    List<Item> getSearchingItemsByText(String text);

    Item createItem(ItemDto itemDto, Long userId);

    Item updateItem(ItemDto itemDto, Long itemId, Long userId);

    void deleteItemById(long itemId);
}
