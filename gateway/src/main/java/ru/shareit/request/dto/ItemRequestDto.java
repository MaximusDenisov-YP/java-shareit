package ru.shareit.request.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.shareit.item.dto.ItemShortDto;
import ru.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class ItemRequestDto {
    private Long id;
    private String description;
    private String title;
    private LocalDateTime created;
    private UserDto requester;
    private List<ItemShortDto> items;
}
