package ru.practicum.shareit.request.mapper;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.entity.ItemRequest;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.mapper.UserMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ItemRequestMapper {

    public static ItemRequestDto toDto(ItemRequest entity) {
        return ItemRequestDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .created(entity.getCreated())
                .items(entity.getItems().stream().map(ItemMapper::entityToShortDto).toList())
                .requester(UserMapper.toDto(entity.getRequester()))
                .build();
    }

    public static ItemRequestDto toDto(ItemRequest entity, List<ItemDto> items) {
        return ItemRequestDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .created(entity.getCreated())
                .requester(UserMapper.toDto(entity.getRequester()))
                .build();
    }

    public static ItemRequest toEntity(ItemRequestDto dto, User user) {
        return ItemRequest.builder()
                .title(dto.getTitle())
                .requester(user)
                .description(dto.getDescription())
                .created(LocalDateTime.now())
                .items(new ArrayList<>())
                .build();
    }
}
