package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(long userId);

    User createUser(UserDto userDto);

    User updateUser(UserDto userDto, Long userId);

    void deleteUserById(long userId);
}
