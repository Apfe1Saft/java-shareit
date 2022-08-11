package ru.practicum.shareit.item;

import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.item.ItemDto;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public interface ItemService {
    Item addItem(Item item);

    Item updateItem(ItemDto itemDto, long ownerId, long itemId);

    ItemDto getItemDtoById(long itemId);

    List<ItemDto> searchItems(String text);

    Set<ItemDto> show(long id);
    Item getItemById(long itemId);
    CommentDto addComment(CommentDto commentDto,long itemId,long userId);

}
