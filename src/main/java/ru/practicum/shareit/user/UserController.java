package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public User createUser(@Valid @RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PatchMapping("/{id}")
    public User updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Long id) {
        return userService.updateUser(userDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable long id) {
        userService.deleteUserById(id);
    }
}
