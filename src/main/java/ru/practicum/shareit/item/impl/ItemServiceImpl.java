package ru.practicum.shareit.item.impl;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemStorage;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.item.model.Item;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
        if(ItemStorage.getItem(itemId)!=null) {
            return ItemMapper.toItemDto(ItemStorage.getItem(itemId));
        }
        return null;
    }

    @Override
    public Set<ItemDto> searchItems(String text) {
        Set<Item> answerItems = new HashSet<>();
        for(Item item: ItemStorage.getItems()){
            if(item.getName().contains(text) || item.getDescription().contains(text)){
                answerItems.add(item);
            }
        }
        Set<ItemDto> answer = new HashSet<>();
        for(Item item : answerItems){
            answer.add(ItemMapper.toItemDto(item));
        }

        return answer;
    }

    @Override
    public Set<ItemDto> show(int id) {
        Set<ItemDto> answer = new HashSet<>();
        for(Item item : ItemStorage.getItems()){
            if(item.getOwner().getId() == id) {
                answer.add(ItemMapper.toItemDto(item));
            }
        }
        return answer;
    }
}
