package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @GetMapping
    public List<BookingDto> getAllUserBookings(@RequestHeader(name = "X-Sharer-User-Id") @Positive Long userId) {
        return bookingService.getAllBookingsByUserId(userId);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBookingById(
            @PathVariable @Positive Long bookingId,
            @RequestHeader(name = "X-Sharer-User-Id") @Positive Long userId) {
        return bookingService.getBookingById(bookingId, userId);
    }

    @PostMapping
    public BookingDto createNewBooking(
            @RequestBody @Valid BookingCreateDto bookingDto,
            @RequestHeader("X-Sharer-User-Id") @Positive Long userId) {
        return bookingService.createBooking(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto updateBooking(
            @PathVariable @Positive Long bookingId,
            @RequestParam Boolean approved,
            @RequestHeader(name = "X-Sharer-User-Id") @Positive Long userId) {
        return bookingService.updateBooking(bookingId, approved, userId);
    }

    @DeleteMapping("/{bookingId}")
    public void deleteBooking(@PathVariable @Positive Long bookingId) {
        bookingService.deleteBooking(bookingId);
    }
}
