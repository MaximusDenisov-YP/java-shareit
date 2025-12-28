package ru.shareit.booking;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.shareit.booking.dto.BookingCreateDto;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingClient bookingClient;

    @GetMapping("/owner")
    public ResponseEntity<Object> getAllUserBookings(@RequestHeader(name = "X-Sharer-User-Id") @Positive Long userId) {
        return bookingClient.getBookingsByUser(userId);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBookingById(
            @PathVariable @Positive Long bookingId,
            @RequestHeader(name = "X-Sharer-User-Id") @Positive Long userId) {
        return bookingClient.getBookingById(userId, bookingId);
    }

    @GetMapping()
    public ResponseEntity<Object> getAllUBookings(@RequestHeader(name = "X-Sharer-User-Id") @Positive Long userId) {
        return bookingClient.getBookingsByUser(userId);
    }

    @PostMapping
    public ResponseEntity<Object> createNewBooking(
            @RequestBody @Valid BookingCreateDto bookingCreateDto,
            @RequestHeader("X-Sharer-User-Id") @Positive Long userId) {
        log.info("Gateway send request [createBooking]: userId={}, bookingSt = {}", userId, bookingCreateDto.getStart());
        return bookingClient.createBooking(userId, bookingCreateDto);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> updateBooking(
            @PathVariable @Positive Long bookingId,
            @RequestParam Boolean approved,
            @RequestHeader(name = "X-Sharer-User-Id") @Positive Long userId) {
        return bookingClient.updateBooking(userId, bookingId, approved);
    }
}
