package ru.shareit.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.shareit.client.BaseClient;
import ru.shareit.user.dto.UserDto;

@Service
public class UserClient extends BaseClient {

    private static final String ENDPOINT = "/users";

    public UserClient(@Value("${shareit-server.url}") String host, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(host + ENDPOINT)).build());
    }

    public ResponseEntity<Object> getUserById(long userId) {
        return get("/" + userId);
    }

    public ResponseEntity<Object> getAllUsers() {
        return get("");
    }

    public ResponseEntity<Object> createUser(UserDto user) {
        return post("", user);
    }

    public ResponseEntity<Object> updateUser(long userId, UserDto user) {
        return patch("/" + userId, user);
    }

    public void deleteById(long userId) {
        delete("/" + userId);
    }
}