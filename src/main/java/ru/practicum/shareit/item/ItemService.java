package ru.practicum.shareit.item;

import ru.practicum.shareit.item.ItemDto;
import ru.practicum.shareit.item.Item;

import java.util.Set;
import java.util.stream.Stream;

public interface ItemService {
    Item addItem(Item item);

    Item updateItem(Item item);

    ItemDto getItemDtoById(int itemId);

    Stream<ItemDto> searchItems(String text);

    Set<ItemDto> show(int id);

}
