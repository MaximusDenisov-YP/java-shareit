package ru.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.shareit.request.dto.ItemRequestDto;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {

    private final ItemRequestClient itemClient;

    @GetMapping
    public ResponseEntity<Object> getCurrentRequests(
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return itemClient.getItemRequests(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequests(
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return itemClient.getAllItemRequests(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequestById(
            @PathVariable Long requestId
    ) {
        return itemClient.getItemRequestById(requestId);
    }

    @PostMapping
    public ResponseEntity<Object> createRequest(
            @RequestBody ItemRequestDto itemRequestDto,
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return itemClient.createItemRequest(itemRequestDto, userId);
    }
}