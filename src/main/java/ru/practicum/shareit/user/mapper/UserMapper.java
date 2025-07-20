package ru.practicum.shareit.user.mapper;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.entity.User;

public class UserMapper {

    public static UserDto entityUserToDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User dtoToEntityItem(UserDto userDto, long userId) {
        return User.builder()
                .id(userId)
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }
}
