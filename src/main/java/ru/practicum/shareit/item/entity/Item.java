package ru.practicum.shareit.item.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "items")
public class Item {
    @Positive
    @Id
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

    public Item() {
    }
}