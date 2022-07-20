package ru.practicum.shareit.item.impl;

import ru.practicum.shareit.item.ItemStorage;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.item.model.Item;

import java.util.Set;

public class ItemServiceImpl implements ItemService {

    @Override
    public void addItem(Item item) {
        ItemStorage.addItem(item);
    }

    @Override
    public void updateItem(Item item) {
        ItemStorage.update(item);
    }

    @Override
    public Item getItemDtoById(int itemId) {
        ItemStorage.getItem(itemId);
    }

    @Override
    public Item getItemByOwnerById(int itemId) {
        ItemStorage.getItem(itemId);
    }

    @Override
    public Set<Item> searchItems(String text) {
        // ???
    }
}
