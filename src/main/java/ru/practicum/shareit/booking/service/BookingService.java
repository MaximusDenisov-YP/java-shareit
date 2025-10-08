package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

public interface BookingService {
    List<BookingDto> getAllBooking();

    BookingDto getBookingById(Long bookingId);

    BookingDto createBooking(BookingDto bookingDto, Long userId);

    BookingDto updateBooking(Long bookingId, Boolean isApproved, Long userId);

    void deleteBooking(Long bookingId);
}
