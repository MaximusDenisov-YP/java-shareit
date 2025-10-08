package ru.practicum.shareit.booking.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class BookingDto {
    private String start;
    private String end;
    private Long itemId;
}