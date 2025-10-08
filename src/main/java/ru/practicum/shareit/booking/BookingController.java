package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @GetMapping
    public List<BookingDto> getAllBooking() {
        return bookingService.getAllBooking();
    }

    @PostMapping
    public BookingDto createNewBooking(
            @Valid @RequestBody BookingDto bookingDto,
            @Valid @Positive @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingService.createBooking(bookingDto, userId);
    }

    @PutMapping("/{bookingId}")
    public BookingDto updateBooking(
            @PathVariable Long bookingId,
            @RequestParam Boolean isApproved,
            @RequestHeader(name = "X-Sharer-User-Id") Long userId) {
        return bookingService.updateBooking(bookingId, isApproved, userId);
    }

    @DeleteMapping("/{bookingId}")
    public void deleteBooking(@PathVariable Long bookingId) {
        bookingService.deleteBooking(bookingId);
    }
}
