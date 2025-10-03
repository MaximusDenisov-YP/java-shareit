package ru.practicum.shareit.item.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.item.mapper.ItemMapper;
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
    public List<ItemDto> getAllUsersItems(Long userId) {
        return itemRepository.findAllByOwnerId(userId).stream()
                .map(ItemMapper::entityItemToDto)
                .toList();
    }

    @Override
    public ItemDto getItemById(Long itemId, Long userId) {
        Item itemEntity = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь с данным ID не найдена!"));
        return ItemMapper.entityItemToDto(itemEntity);
    }

    @Override
    public List<ItemDto> getSearchingItemsByText(String text) {
        if (text.isEmpty()) return new ArrayList<>();
        return itemRepository.findItemsByName(text).stream()
                .map(ItemMapper::entityItemToDto)
                .toList();
    }

    @Override
    @Transactional
    public ItemDto createItem(ItemDto itemDto, Long userId) {
        if (userRepository.existsById(userId)) {
            return ItemMapper.entityItemToDto(
                    itemRepository.save(ItemMapper.dtoToEntityItem(itemDto, itemDto.getId(), userId)));
        } else {
            throw new NotFoundException("Пользователя с ID %d - не существует!".formatted(userId));
        }
    }

    @Override
    @Transactional
    public ItemDto updateItem(ItemDto itemDto, Long itemId, Long userId) {
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new NotFoundException("Вещи с ID %d - не существует!".formatted(itemId)));
        if (itemDto.getName() != null) {
            item.setName(item.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(item.getDescription());
        }
        return ItemMapper.entityItemToDto(itemRepository.save(item));
    }

    @Override
    public void deleteItemById(long itemId) {
        itemRepository.deleteById(itemId);
    }
}