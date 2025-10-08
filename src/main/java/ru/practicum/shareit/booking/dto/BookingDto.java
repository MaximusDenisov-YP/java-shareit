package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.practicum.shareit.item.entity.Item;

import java.util.Date;

@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class BookingDto {
    // TODO: !!! СРОЧНО СДЕЛАТЬ ДВА DTO, один на вход с Long itemId, а второй отдающий на контроллер с Item item - где он object с полями!!!
    @NotNull
    private String start;
    @NotNull
    private String end;
    @NotNull
    private Item item;
}