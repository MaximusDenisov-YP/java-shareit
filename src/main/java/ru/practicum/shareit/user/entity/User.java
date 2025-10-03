package ru.practicum.shareit.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Length(min = 1, max = 30)
    @NotNull
    private String name;
    @Email
    @NotNull
    private String email;

    public User() {
    }
}