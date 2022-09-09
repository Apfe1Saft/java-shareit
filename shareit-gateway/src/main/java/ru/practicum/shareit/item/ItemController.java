package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.NullParamException;
import ru.practicum.shareit.exception.WrongDataException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.constraints.Min;

@RestController
@AllArgsConstructor
@RequestMapping("/items")
@Slf4j
public class ItemController {

    private final ItemClient itemClient;

    @PostMapping
    public Object create(@RequestBody ItemDto itemDto,
                         @RequestHeader("X-Sharer-User-Id") Integer userId) {
        if (itemDto.getName() == null) throw new NotFoundException("");
        if (itemDto.getName().equals("") || itemDto.getDescription() == null) throw new WrongDataException("");
        if (itemDto.getAvailable() == null) throw new WrongDataException("");

        return itemClient.create(itemDto, userId);
    }

    @GetMapping
    public Object show(@RequestHeader("X-Sharer-User-Id") Integer userId,
                       @RequestParam(required = false, defaultValue = "")  String from,
                       @RequestParam(required = false, defaultValue = "")  String size)
            throws NullParamException {
            return itemClient.show(userId, from, size);
    }

    @PatchMapping("/{itemId}")
    public Object update(@RequestBody ItemDto itemDto,
                             @RequestHeader("X-Sharer-User-Id") Integer userId,
                             @PathVariable int itemId) {
        if (userId == null) {
            throw new NullParamException("");
        } else {
            return itemClient.update(itemDto, userId, itemId);
        }
    }

    @GetMapping("/{itemId}")
    public Object getItemDtoById(@PathVariable int itemId,
                          @RequestHeader("X-Sharer-User-Id") Integer userId) {
        if (userId == null) {
            throw new NullParamException("");
        } else {
            return itemClient.getItemDtoById(userId, itemId);
        }
    }


    @GetMapping("/search")
    public Object search(@RequestHeader("X-Sharer-User-Id") Integer userId,
                               @RequestParam String text,
                               @RequestParam(required = false, defaultValue = "0") @Min(0) Integer from,
                               @RequestParam(required = false, defaultValue = "10") @Min(1) Integer size)
            throws NullParamException {
        if (userId == null) {
            throw new NullParamException("");
        } else {
            return itemClient.search(userId, text, from, size);
        }
    }

    @PostMapping("/{itemId}/comment")
    public Object addComment(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                @PathVariable Integer itemId, @RequestBody CommentDto comment)
            throws NullParamException {
        if (comment.getText().equals("")) throw new WrongDataException("");
        if (userId == null) {
            throw new NullParamException("");
        } else {
            return itemClient.addComment(userId, itemId, comment);
        }
    }
}