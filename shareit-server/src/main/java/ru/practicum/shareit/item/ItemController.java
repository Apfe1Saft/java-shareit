package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.WrongDataException;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public  ItemDto create(@RequestHeader("X-Sharer-User-Id") String ownerId,
                                  @RequestBody final ItemDto itemDto) {
        if (itemDto.getName() == null) throw new NotFoundException("");
        if (itemDto.getName().equals("") || itemDto.getDescription() == null) throw new WrongDataException("");
        return itemService.addItem(itemDto, Long.parseLong(ownerId));
    }

    @GetMapping
    public  List<?> show(@RequestHeader("X-Sharer-User-Id") String ownerId,
                               @RequestParam(name = "from", defaultValue = "") String from,
                               @RequestParam(name = "size", defaultValue = "") String size) {
        if (!size.equals("")) {
            if (itemService.show(Long.parseLong(ownerId), Integer.parseInt(from), Integer.parseInt(size)).getClass().getSimpleName().equals("PageImpl")) {
                return itemService.show(Long.parseLong(ownerId), Integer.parseInt(from), Integer.parseInt(size)).getContent();
            }
            return (List<?>) itemService.show(Long.parseLong(ownerId), Integer.parseInt(from), Integer.parseInt(size));
        } else {
            return itemService.show(Long.parseLong(ownerId));
        }
    }

    @PatchMapping("/{itemId}")
    public  ItemDto update(@PathVariable("itemId") long itemId,
                                 @RequestHeader("X-Sharer-User-Id") String ownerId,
                                  @RequestBody final ItemDto itemDto) {
        System.out.println("Hi");
        return itemService.updateItem(itemDto, Long.parseLong(ownerId), itemId);
    }

    @GetMapping("/{id}")
    public ItemDto getItemDtoById(@PathVariable("id") long id, @RequestHeader("X-Sharer-User-Id") String userId) {

        if (itemService.getItemDtoById(id, Long.parseLong(userId)) != null) {
            return itemService.getItemDtoById(id, Long.parseLong(userId));
        }
        throw new NotFoundException("Item not exist.");
    }

    @GetMapping("/search")
    public List<? extends Object> search(@RequestParam("text") String text,
                                         @RequestParam(name = "from", defaultValue = "") String from,
                                         @RequestParam(name = "size", defaultValue = "") String size) {
        if (!size.equals("")) {
            return itemService.searchItems(text, Integer.parseInt(from), Integer.parseInt(size));
        } else
            return itemService.searchItems(text);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@PathVariable("itemId") long itemId,  @RequestBody CommentDto commentDto,
                                 @RequestHeader("X-Sharer-User-Id") String userId) {
        if (commentDto.getText().equals("")) throw new WrongDataException("text is empty.");
        return itemService.addComment(commentDto, itemId, Long.parseLong(userId));
    }

}
