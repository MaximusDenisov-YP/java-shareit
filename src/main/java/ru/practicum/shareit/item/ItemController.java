package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithDate;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> getAllUsersItems(@RequestHeader("X-Sharer-User-Id") @Positive Long userId) {
        return itemService.getAllUsersItems(userId);
    }

    @GetMapping("/{itemId}")
    public ItemDtoWithDate getItemById(@PathVariable @Positive Long itemId,
                                       @RequestHeader("X-Sharer-User-Id") @Positive Long userId) {
        return itemService.getItemById(itemId, userId);
    }

    @GetMapping("/search")
    public List<ItemDto> getSearchingItemsByText(@RequestParam @Size(max = 300) String text) {
        return itemService.getSearchingItemsByText(text);
    }

    @PostMapping
    public ItemDto createNewItem(@RequestBody @Valid ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") @Positive Long userId) {
        return itemService.createItem(itemDto, userId);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto createNewComment(@RequestBody @Valid CommentDto commentDto,
                                       @PathVariable @Positive Long itemId,
                                       @RequestHeader("X-Sharer-User-Id") @Positive Long userId) {
        return itemService.createComment(commentDto, itemId, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestBody @Valid ItemDto itemDto, @PathVariable @Positive Long itemId,
                              @RequestHeader("X-Sharer-User-Id") @Positive Long userId) {
        return itemService.updateItem(itemDto, itemId, userId);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable @Positive Long itemId) {
        itemService.deleteItemById(itemId);
    }
}
