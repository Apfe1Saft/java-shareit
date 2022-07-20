package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ItemStorage {
    private static Set<Item> items = new HashSet<>();
    private static int maxId = 0;

    public static int getMaxId() {
        maxId++;
        return maxId;
    }

    public static Set<Item> getItems() {
        return items;
    }

    public static Optional<Item> getItem(int itemId){
        return getItems().stream().filter(item -> item.getId() == itemId).findFirst();
    }

    private static void setItems(Set<Item> items) {
        ItemStorage.items = items;
    }

    public static void addItem(Item item) {
        getItems().add(item);
    }
    public static void deleteItem(int itemId){
        getItems().removeIf(item -> item.getId() == itemId);
    }

    public static void update(Item item) {
        deleteItem(item.getId());
        addItem(item);
    }
}
