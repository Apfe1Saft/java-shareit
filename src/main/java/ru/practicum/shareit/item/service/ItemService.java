package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.Item;

import java.util.Set;

public interface ItemService {
    void addItem(Item item);
    void updateItem(Item item);
    Item getItemDtoById(int itemId);
    Item getItemByOwnerById(int itemId);
    Set<Item> searchItems(String text);

}
