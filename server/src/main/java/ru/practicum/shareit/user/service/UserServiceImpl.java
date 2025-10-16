package ru.practicum.shareit.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public UserDto getUserById(long userId) {
        User userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID %d - не существует!".formatted(userId)));
        return UserMapper.toDto(userEntity);
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        boolean hasEmail = userRepository.existsUserByEmail(userDto.getEmail());
        if (hasEmail)
            throw new ConflictException(
                    "Пользователь с таким email %s - уже существует!".formatted(userDto.getEmail()));
        return UserMapper.toDto(userRepository.save(UserMapper.toEntity(userDto)));
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto userDto, long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь с таким ID %s - не существует!".formatted(userId)));
        String userDtoEmail = userDto.getEmail();
        if (userDtoEmail != null) {
            if (userRepository.existsUserByEmail(userDtoEmail)) {
                throw new ConflictException("Пользователь с таким email %s - уже существует!".formatted(userDtoEmail));
            }
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getName() != null) user.setName(userDto.getName());
        return UserMapper.toDto(userRepository.save(user));
    }

    @Override
    public void deleteUserById(long userId) {
        userRepository.deleteById(userId);
    }
}