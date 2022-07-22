package ru.practicum.shareit.item.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.Set;
import java.util.stream.Stream;

@Service
public interface ItemService {
    Item addItem(Item item);

    Item updateItem(Item item);

    ItemDto getItemDtoById(int itemId);

    Stream<ItemDto> searchItems(String text);

    Set<ItemDto> show(int id);

}
