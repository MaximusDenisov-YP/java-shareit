package ru.practicum.shareit.item.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.booking.entity.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.entity.Comment;
import ru.practicum.shareit.comment.mapper.CommentMapper;
import ru.practicum.shareit.comment.repository.CommentRepository;
import ru.practicum.shareit.exception.NotAvailableException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithDate;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.entity.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;
    private final ItemRequestRepository requestRepository;

    @Override
    public List<ItemDto> getAllUsersItems(Long userId) {
        return itemRepository.findAllByOwnerId(userId).stream()
                .map(ItemMapper::entityItemToDto)
                .toList();
    }

    @Override
    public ItemDtoWithDate getItemById(Long itemId, Long userId) {
        Item itemEntity = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь с данным ID не найдена!"));
        Optional<Booking> lastBooking = bookingRepository.findFirstByItemIdAndStartBeforeAndStatusOrderByEndDesc(
                itemId, LocalDateTime.now(), BookingStatus.APPROVED);
        Optional<Booking> nextBooking = bookingRepository.findFirstByItemIdAndStartAfterAndStatusOrderByStartAsc(
                itemId, LocalDateTime.now(), BookingStatus.APPROVED);
        return ItemMapper.entityItemToDtoWithDate(
                itemEntity,
                lastBooking.map(Booking::getEnd).orElse(null),
                nextBooking.map(Booking::getStart).orElse(null)
        );
    }

    @Override
    public List<ItemDto> getSearchingItemsByText(String text) {
        if (text.isEmpty()) return new ArrayList<>();
        return itemRepository.findItemsByNameIgnoreCase(text).stream()
                .filter(Item::getAvailable)
                .map(ItemMapper::entityItemToDto)
                .toList();
    }

    @Override
    @Transactional
    public ItemDto createItem(ItemDto itemDto, Long userId) {
        ItemRequest itemRequest;
        Long requestId = itemDto.getRequestId();
        if (userRepository.existsById(userId)) {
            Item itemToPost = ItemMapper.dtoToEntityItem(itemDto, userId);
            if (requestId != null) {
                if (itemDto.getName() == null)
                    throw new NotAvailableException("Нельзя ответить вещью без названия на запрос");
                itemRequest = requestRepository.findById(requestId).orElseThrow(
                        () -> new NotFoundException("Запрос с ID = %d не найден!".formatted(requestId))
                );
                itemToPost.setRequest(itemRequest);
            }
            return ItemMapper.entityItemToDto(itemRepository.save(itemToPost));
        } else {
            throw new NotFoundException("Пользователя с ID %d - не существует!".formatted(userId));
        }
    }

    @Override
    @Transactional
    public CommentDto createComment(CommentDto commentDto, Long itemId, Long userId) {
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new NotFoundException(String.format("Вещи с ID = %d не существует!", itemId)));
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(String.format("Пользователя с ID = %d - не существует!", userId)));
        Booking booking = bookingRepository.findBookingByItemIdAndBookerId(itemId, userId).orElseThrow(
                () -> new NotFoundException(String.format(
                        "Бронирование с Item ID = %d и Booker Id = %d - не найдено!", itemId, userId)));
        if (booking.getEnd().isBefore(LocalDateTime.now())) {
            return CommentMapper.toDto(commentRepository.save(Comment.builder()
                    .withUser(user)
                    .withItem(item)
                    .withText(commentDto.getText())
                    .withCreated(LocalDateTime.now())
                    .build()
            ));
        } else {
            throw new NotAvailableException("Комментарий нельзя поставить, так как бронирование вещи не окончено!");
        }
    }

    @Override
    @Transactional
    public ItemDto updateItem(ItemDto itemDto, Long itemId, Long userId) {
        Item item = itemRepository.findItemByIdAndOwnerId(itemId, userId).orElseThrow(
                () -> new NotFoundException("Вещи с ID %d - не существует!".formatted(itemId)));
        log.info("Найден Item для update -> {}", item);

        String nameDto = itemDto.getName();
        if (nameDto != null) {
            item.setName(nameDto);
        }
        String descriptionDto = itemDto.getDescription();
        if (descriptionDto != null) {
            item.setDescription(descriptionDto);
        }
        Boolean available = itemDto.getAvailable();
        if (available != null) {
            item.setAvailable(available);
        }

        return ItemMapper.entityItemToDto(itemRepository.save(item));
    }

    @Override
    public void deleteItemById(long itemId) {
        itemRepository.deleteById(itemId);
    }
}