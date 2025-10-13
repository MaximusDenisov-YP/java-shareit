package ru.practicum.shareit.item.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.practicum.shareit.comment.entity.Comment;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@Entity
@Table(name = "items")
public class Item {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<Comment> comments;

    public Item() {
    }
}