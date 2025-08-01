package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> getAllUsers();

    Optional<User> getUserById(long userId);

    User createUser(UserDto userDto);

    User updateUser(UserDto userDto, Long userId);

    void deleteUserById(Long userId);
}
