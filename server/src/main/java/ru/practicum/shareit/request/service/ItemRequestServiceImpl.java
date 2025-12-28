package ru.practicum.shareit.request.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.entity.ItemRequest;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;

    @Override
    public ItemRequestDto getRequestById(Long requestId) {
        return ItemRequestMapper.toDto(
                itemRequestRepository.findById(requestId).orElseThrow(
                        () -> new NotFoundException(String.format("Запрос на предмет с ID = %d не найден!", requestId)))
        );
    }

    @Transactional
    @Override
    public ItemRequestDto createRequest(ItemRequestDto dto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(String.format("Пользователь с ID = %d не найден!", userId)));
        ItemRequest request = ItemRequestMapper.toEntity(dto, user);
        return ItemRequestMapper.toDto(itemRequestRepository.save(request));
    }

    @Override
    public List<ItemRequestDto> getCurrentRequests(Long userId) {
        userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(String.format("Пользователь с ID = %d не найден!", userId)));
        return itemRequestRepository.findAllByRequesterIdOrderByCreatedDesc(userId).stream()
                .map(ItemRequestMapper::toDto)
                .toList();
    }

    @Override
    public List<ItemRequestDto> getAllRequests(Long userId) {
        userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(String.format("Пользователь с ID = %d не найден!", userId)));
        return itemRequestRepository.findAllByRequesterIdNotOrderByCreatedDesc(userId).stream()
                .map(ItemRequestMapper::toDto)
                .toList();
    }
}