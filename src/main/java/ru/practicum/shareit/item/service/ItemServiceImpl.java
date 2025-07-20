package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public List<Item> getAllUsersItems(Long userId) {
        return itemRepository.getAllUsersItems(userId);
    }

    @Override
    public Item getItemById(Long itemId, Long userId) {
        return itemRepository.getItemById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь с данным ID не найдена!"));
    }

    @Override
    public List<Item> getSearchingItemsByText(String text) {
        if (text.isEmpty()) return new ArrayList<>();
        return itemRepository.getSearchingItemsByText(text);
    }

    @Override
    public Item createItem(ItemDto itemDto, Long userId) {
        hasUser(userId);
        return itemRepository.createItem(itemDto, userId);
    }

    @Override
    public Item updateItem(ItemDto itemDto, Long itemId, Long userId) {
        hasUser(userId);
        return itemRepository.updateItem(itemDto, itemId, userId);
    }

    @Override
    public void deleteItemById(long itemId) {
        itemRepository.deleteItemById(itemId);
    }

    private void hasUser(long userId) {
        userRepository.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователя с ID %d - не существует!".formatted(userId)));
    }
}
