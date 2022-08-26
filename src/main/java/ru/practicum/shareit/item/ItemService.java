package ru.practicum.shareit.item;

import org.springframework.data.domain.Page;
import ru.practicum.shareit.comment.Comment;
import ru.practicum.shareit.comment.CommentDto;

import java.util.List;

public interface ItemService {
    Item addItem(Item item);

    Item updateItem(ItemDto itemDto, long ownerId, long itemId);

    ItemDto getItemDtoById(long itemId, long userId);

    List<ItemDto> searchItems(String text);

    List<ItemDto> searchItems(String text, int from, int size);

    List<ItemDto> show(long id);

    Page<Item> show(long id, int from, int size);

    Item getItemById(long itemId);

    CommentDto addComment(CommentDto commentDto, long itemId, long userId);

    Comment getComment(long itemId);

    void removeItem(long id);

}
