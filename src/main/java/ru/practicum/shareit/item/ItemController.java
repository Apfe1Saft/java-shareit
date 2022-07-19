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
        if(item.getId() == 0) item.setId(ItemStorage.getMaxId());
        ItemStorage.addItem(item);
        return item;
    }

    @PutMapping
    public @Valid void  put(@Valid @RequestBody final Item item, HttpServletResponse response, BindingResult result) {
        if(ItemStorage.getItem(item.getId()).isPresent()){
            update(item,response,result);
        }
        else create(item,response,result);
    }

    @PatchMapping
    public @Valid Item update(@Valid @RequestBody final Item item, HttpServletResponse response, BindingResult result) {
        ItemStorage.update(item);
        return item;
    }

    @DeleteMapping("/{id}")
    public Optional<Item> remove(@PathVariable("id") int id, HttpServletResponse response) {
        ItemStorage.deleteItem(id);
        return ItemStorage.getItem(id);
    }

    @GetMapping("/{id}")
    public Optional<Item> getById(@PathVariable("id") int id, HttpServletResponse response) {
        return ItemStorage.getItem(id);
    }

}