package ru.practicum.shareit.item;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserStorage;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

/**
 * // TODO .
 */
@RestController
@RequestMapping("/items")
public class ItemController {
    @GetMapping
    public Set<Item> show() {
        return ItemStorage.getItems();
    }

    @PostMapping
    public @Valid Item create(@Valid @RequestBody final Item item, HttpServletResponse response, BindingResult result) {
        if (item.getId() == 0) item.setId(ItemStorage.getMaxId());
        ItemStorage.addItem(item);
        return item;
    }

    @PatchMapping("/{itemId}")
    public @Valid Item update(@Valid @RequestBody final Item item, HttpServletResponse response, BindingResult result) {
        ItemStorage.update(item);
        return item;
    }

    @GetMapping("/{id}")
    public Optional<Item> getItemDtoById(@PathVariable("id") int id, HttpServletResponse response) {
        return ItemStorage.getItem(id);
    }

    @GetMapping
    public Optional<Item> getItemsByOwnerById(@PathVariable("id") int id, HttpServletResponse response) {
        return ItemStorage.getItem(id);
    }

    @GetMapping("search?text={text}")
    public Optional<Item> search(@PathVariable("id") int id, HttpServletResponse response) {
        return ItemStorage.getItem(id);
    }

}