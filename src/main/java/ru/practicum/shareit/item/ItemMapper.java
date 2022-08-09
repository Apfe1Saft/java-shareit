package ru.practicum.shareit.item;

import ru.practicum.shareit.user.UserStorage;

import ru.practicum.shareit.exception.*;

public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.isAvailable()
        );
    }

    public static Item toItem(ItemDto itemDto, int ownerId) {
        if (itemDto.isAvailable() == null)
            throw new WrongDataException("");
        if (UserStorage.getUser(ownerId).isPresent()) {
            if (itemDto.getId() == 0) {
                return new Item(
                        ItemStorage.getMaxId(),
                        itemDto.getName(),
                        itemDto.getDescription(),
                        itemDto.isAvailable(),
                        UserStorage.getUser(ownerId).get()
                );
            } else return new Item(
                    itemDto.getId(),
                    itemDto.getName(),
                    itemDto.getDescription(),
                    itemDto.isAvailable(),
                    UserStorage.getUser(ownerId).get()
            );
        }
        throw new NotFoundException("");
    }
}
