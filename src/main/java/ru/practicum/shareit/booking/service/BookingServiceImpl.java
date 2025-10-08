package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.booking.entity.BookingStatus;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotAvailableException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public List<BookingDto> getAllBookingsByUserId(Long userId) {
        return bookingRepository.findBookingsByBookerId(userId).stream()
                .map(BookingMapper::toDto)
                .toList();
    }

    @Override
    public BookingDto getBookingById(Long bookingId, Long userId) {
        //TODO: Отдавать Booking вместе с object item and user {user.id}
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(
                () -> new NotFoundException(String.format("Бронирование с ID = %d, не найдено!", bookingId)));
        if (userId.equals(booking.getBookerId()) || userId.equals(booking.getItem().getOwnerId())) {
            return BookingMapper.toDto(booking);
        } else {
            throw new NotAvailableException(
                    "Информация о конкретном бронировании доступна только заявителю или владельцу вещи");
        }
    }

    @Override
    public BookingDto createBooking(BookingDto bookingDto, Long userId) {
        // TODO: Дописать логику, где бизнес-ошибка с start, end.
        if (userRepository.existsById(userId)) {
            Item item = itemRepository.findById(bookingDto.getItem().getId()).orElseThrow(
                    () -> new NotFoundException(String.format("Предмет с ID = %d, не найден!", bookingDto.getItem().getId())));
            if (item.getAvailable()) {
                Booking booking = BookingMapper.toEntity(bookingDto);
                booking.setBookerId(userId);
                booking.setStatus(BookingStatus.WAITING);
                return BookingMapper.toDto(bookingRepository.save(booking));
            } else {
                throw new NotAvailableException("Предмет недоступен для бронирования!");
            }
        } else {
            throw new NotFoundException(String.format("Пользователя с таким ID = %d, не существует!", userId));
        }
    }

    @Override
    public BookingDto updateBooking(Long bookingId, Boolean isApproved, Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format("Пользователь с ID = %d, не существует!", userId));
        }
        Booking booking = bookingRepository.findBookingByIdAndBookerId(bookingId, userId)
                .orElseThrow(() -> new NotFoundException(String.format("Бронирование с ID = %d, не найдено!", bookingId)));
        if (isApproved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }
        return BookingMapper.toDto(bookingRepository.save(booking));
    }

    @Override
    public void deleteBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }
}
