package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;

@Component
public class ItemServiceImpl implements ItemService {

    @Override
    public Item addItem(Item item) {
        return ItemStorage.addItem(item);
    }

    @Override
    public Item updateItem(Item item) {
        return ItemStorage.update(item);
    }

    @Override
    public ItemDto getItemDtoById(int itemId) {
        if (ItemStorage.getItem(itemId) != null) {
            return ItemMapper.toItemDto(ItemStorage.getItem(itemId));
        }
        return null;
    }

    @Override
    public Stream<ItemDto> searchItems(String text) {

        Set<Item> answerItems = new HashSet<>();

        for (Item item : ItemStorage.getItems()) {
            if (item.getName().toUpperCase(Locale.ROOT).contains(text.toUpperCase(Locale.ROOT)) ||
                    item.getDescription().toUpperCase(Locale.ROOT).contains(text.toUpperCase(Locale.ROOT))) {
                answerItems.add(item);
            }
        }
        Set<ItemDto> answer = new HashSet<>();
        if (text.equals(""))
            return answer.stream();
        for (Item item : answerItems) {
            answer.add(ItemMapper.toItemDto(item));
        }

        return answer.stream().sorted(Comparator.comparing(ItemDto::getId)).filter(ItemDto::isAvailable);
    }

    @Override
    public Set<ItemDto> show(int id) {
        Set<ItemDto> answer = new HashSet<>();
        for (Item item : ItemStorage.getItems()) {
            if (item.getOwner().getId() == id) {
                answer.add(ItemMapper.toItemDto(item));
            }
        }
        return answer;
    }
}
