package ru.practicum.shareit.item.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.entity.Item;

import java.util.List;

public interface ItemService {
    List<ItemDto> getAllUsersItems(Long userId);

    ItemDto getItemById(Long itemId, Long userId);

    List<ItemDto> getSearchingItemsByText(String text);

    ItemDto createItem(ItemDto itemDto, Long userId);

    ItemDto updateItem(ItemDto itemDto, Long itemId, Long userId);

    void deleteItemById(long itemId);
}
