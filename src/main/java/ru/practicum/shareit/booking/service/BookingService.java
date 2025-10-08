package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

public interface BookingService {
    public List<BookingDto> getAllBookingsByUserId(Long userId);

    BookingDto getBookingById(Long bookingId, Long userId);

    BookingDto createBooking(BookingDto bookingDto, Long userId);

    BookingDto updateBooking(Long bookingId, Boolean isApproved, Long userId);

    void deleteBooking(Long bookingId);
}
