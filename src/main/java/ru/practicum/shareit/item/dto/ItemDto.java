package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class ItemDto {
    private Long id;
    @Length(min = 5, max = 30)
    private String name;
    @Length(min = 1, max = 500)
    private String description;
    private Boolean available;
}
