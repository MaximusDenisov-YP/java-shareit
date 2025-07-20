package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User getUserById(long userId) {
        return userRepository.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID %d - не существует!".formatted(userId)));
    }

    @Override
    public User createUser(UserDto userDto) {
        if (userDto.getEmail() == null)
            throw new ValidationException("Email не должен быть пуст!");
        emailValidate(userDto);
        return userRepository.createUser(userDto);
    }

    @Override
    public User updateUser(UserDto userDto, Long userId) {
        emailValidate(userDto);
        return userRepository.updateUser(userDto, userId);
    }

    @Override
    public void deleteUserById(long userId) {
        userRepository.deleteUserById(userId);
    }

    private void emailValidate(UserDto userDto) {
        if (userDto.getEmail() != null) {
            boolean emailAlreadyUse = userRepository.getAllUsers()
                    .stream()
                    .map(User::getEmail)
                    .anyMatch(userDto.getEmail()::equals);
            if (emailAlreadyUse)
                throw new ConflictException("Пользователь с таким email %s - уже существует!".formatted(userDto.getEmail()));
        }
    }
}
