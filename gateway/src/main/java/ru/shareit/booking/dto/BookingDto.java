package ru.shareit.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.shareit.item.dto.ItemDto;
import ru.shareit.user.dto.UserDto;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class BookingDto {
    @NotNull
    private Long id;
    @NotNull
    private String start;
    @NotNull
    private String end;
    @NotNull
    private ItemDto item;
    @NotNull
    private BookingStatus status;
    private UserDto booker;
}