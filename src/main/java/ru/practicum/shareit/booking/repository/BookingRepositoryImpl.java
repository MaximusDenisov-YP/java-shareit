package ru.practicum.shareit.booking.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

@Repository
public class BookingRepositoryImpl implements BookingRepository {
    @Override
    public List<BookingDto> getAllBooking() {
        return null;
    }

    @Override
    public BookingDto getBookingById() {
        return null;
    }

    @Override
    public BookingDto createBooking() {
        return null;
    }

    @Override
    public BookingDto updateBooking() {
        return null;
    }

    @Override
    public void deleteBooking() {

    }
}
