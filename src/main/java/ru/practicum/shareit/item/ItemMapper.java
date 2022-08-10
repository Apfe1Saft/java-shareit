package ru.practicum.shareit.item;

import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.UserService;
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

    public static Item toItem(ItemDto itemDto, long ownerId) {
        if (itemDto.isAvailable() == null)
            throw new WrongDataException("");
        if (UserController.getUserService().getUser(ownerId).isPresent()) {
            if (itemDto.getId() == 0) {
                return new Item(
                        ItemStorage.getMaxId(),
                        itemDto.getName(),
                        itemDto.getDescription(),
                        itemDto.isAvailable(),
                        UserController.getUserService().getUser(ownerId).get()
                );
            } else return new Item(
                    itemDto.getId(),
                    itemDto.getName(),
                    itemDto.getDescription(),
                    itemDto.isAvailable(),
                    UserController.getUserService().getUser(ownerId).get()
            );
        }
        throw new NotFoundException("");
    }
}
