package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.entity.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    List<Item> getAllUsersItems(long userId);

    Optional<Item> getItemById(long itemId);

    List<Item> getSearchingItemsByText(String text);

    Item createItem(ItemDto itemDto, long userId);

    Item updateItem(ItemDto itemDto, long itemId, long userId);

    void deleteItemById(long itemId);
}
