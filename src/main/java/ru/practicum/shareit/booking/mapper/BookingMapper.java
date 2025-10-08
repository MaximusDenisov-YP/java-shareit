package ru.practicum.shareit.booking.mapper;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.entity.Booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class BookingMapper implements Function<Booking, BookingDto> {
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public BookingDto apply(Booking booking) {
        return BookingDto.builder()
                .start(booking.getStart().format(formatter))
                .end(booking.getEnd().format(formatter))
                .itemId(booking.getItemId())
                .build();
    }

    public static BookingDto toDto(Booking booking) {
        return new BookingMapper().apply(booking);
    }

    public static Booking toEntity(BookingDto dto) {
        return Booking.builder()
                .start(LocalDateTime.parse(dto.getStart()))
                .end(LocalDateTime.parse(dto.getEnd()))
                .itemId(dto.getItemId())
                .build();
    }
}