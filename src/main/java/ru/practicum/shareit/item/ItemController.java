package ru.practicum.shareit.item;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.WrongDataException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * // TODO .
 */
@RestController
@RequestMapping("/items")
public class ItemController {
    private ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @PostMapping
    public @Valid ItemDto create(@RequestHeader("X-Sharer-User-Id") String ownerId,
                                 @Valid @RequestBody final ItemDto itemDto, HttpServletResponse response, BindingResult result) {
        if (itemDto.getName() == null) throw new NotFoundException("");
        if (itemDto.getName().equals("") | itemDto.getDescription() == null) throw new WrongDataException("");
        return ItemMapper.toItemDto(service.addItem(ItemMapper.toItem(itemDto, Integer.parseInt(ownerId))));
    }

    @GetMapping
    public @Valid Set<ItemDto> show(@RequestHeader("X-Sharer-User-Id") String ownerId) {
        return service.show(Integer.parseInt(ownerId));
    }

    @PatchMapping("/{itemId}")
    public @Valid ItemDto update(@PathVariable("itemId") int itemId,
                                 @RequestHeader("X-Sharer-User-Id") String ownerId,
                                 @Valid @RequestBody final ItemDto itemDto, HttpServletResponse response, BindingResult result) {
        if (Integer.parseInt(ownerId) == Objects.requireNonNull(ItemStorage.getItem(itemId)).getOwner().getId()) {
            if (itemDto.isAvailable() == null) {
                itemDto.setAvailable(ItemStorage.getItem(itemId).getAvailable());
            }
            itemDto.setId(itemId);
            return ItemMapper.toItemDto(service.updateItem(ItemMapper.toItem(itemDto, Integer.parseInt(ownerId))));
        }
        throw new NotFoundException("");
    }

    @GetMapping("/{id}")
    public ItemDto getItemDtoById(@PathVariable("id") int id,
                                  HttpServletResponse response) {
        return service.getItemDtoById(id);
    }

    @GetMapping("/search")
    public Stream<ItemDto> search(@RequestParam("text") String text, HttpServletResponse response) {
        return service.searchItems(text);
    }

}
