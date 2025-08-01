package ru.practicum.shareit.booking.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Booking {
    private Long id;
    private Date start;
    private Date end;
    private Long itemId;
    private Long bookerId; // userId?
    private BookingStatus status;
}
