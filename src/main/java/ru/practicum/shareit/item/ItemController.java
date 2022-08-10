package ru.practicum.shareit.item;

import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.WrongDataException;
import ru.practicum.shareit.user.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * // TODO .
 */
@RestController
@RequestMapping("/items")
public class ItemController {
    private ItemService itemService;

    public ItemController(ItemService service) {
        System.out.println("Item");
        this.itemService = service;
    }

    @PostMapping
    public @Valid ItemDto create(@RequestHeader("X-Sharer-User-Id") String ownerId,
                                 @Valid @RequestBody final ItemDto itemDto) {
        if (itemDto.getName() == null) throw new NotFoundException("");
        if (itemDto.getName().equals("") | itemDto.getDescription() == null) throw new WrongDataException("");
        return ItemMapper.toItemDto(itemService.addItem(ItemMapper.toItem(itemDto, Integer.parseInt(ownerId))));
    }

    @GetMapping
    public @Valid Set<ItemDto> show(@RequestHeader("X-Sharer-User-Id") String ownerId) {
        return itemService.show(Integer.parseInt(ownerId));
    }

    @PatchMapping("/{itemId}")
    public @Valid ItemDto update(@PathVariable("itemId") long itemId,
                                 @RequestHeader("X-Sharer-User-Id") String ownerId,
                                 @Valid @RequestBody final ItemDto itemDto) {
        return ItemMapper.toItemDto(itemService.updateItem(itemDto,Long.parseLong(ownerId),itemId));
    }

    @GetMapping("/{id}")
    public ItemDto getItemDtoById(@PathVariable("id") long id) {
        if(itemService.getItemDtoById(id)!=null) {
            return itemService.getItemDtoById(id);
        }
        throw new NotFoundException("Item not exist.");
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam("text") String text) {
        return itemService.searchItems(text);
    }

}
