package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public List<Item> getAllUsersItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getAllUsersItems(userId);
    }

    @GetMapping("/{itemId}")
    public Item getItemById(@PathVariable Long itemId, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getItemById(itemId, userId);
    }

    @GetMapping("/search")
    public List<Item> getSearchingItemsByText(@RequestParam String text) {
        return itemService.getSearchingItemsByText(text);
    }

    @PostMapping
    public Item createNewItem(@Valid @RequestBody ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.createItem(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public Item updateItem(@RequestBody ItemDto itemDto, @Positive @PathVariable Long itemId,
                           @Positive @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.updateItem(itemDto, itemId, userId);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable Long itemId) {
        itemService.deleteItemById(itemId);
    }
}
