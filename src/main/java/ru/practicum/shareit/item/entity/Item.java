package ru.practicum.shareit.item.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class Item {
    @Positive
    @NotNull
    private Long id;
    @Positive
    @NotNull
    private Long ownerId;
    @Length(min = 5, max = 30)
    @NotNull
    private String name;
    @Length(min = 1, max = 500)
    @NotNull
    private String description;
    @NotNull
    private Boolean available;
}