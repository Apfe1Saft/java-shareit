package ru.practicum.shareit.item;

import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.WrongDataException;
import ru.practicum.shareit.requests.ItemRequestController;
import ru.practicum.shareit.user.UserController;

public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        if(item.getRequest()==null){
            return new ItemDto(
                    item.getId(),
                    item.getName(),
                    item.getDescription(),
                    item.isAvailable()
            );
        }
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.isAvailable(),
                item.getRequest().getId()
        );
    }

    public static Item toItem(ItemDto itemDto, long ownerId) {
        if (itemDto.isAvailable() == null)
            throw new WrongDataException("");
        if (UserController.getUserService().getUser(ownerId).isPresent()) {
            if (itemDto.getRequestId() != 0) {
                if (itemDto.getId() == 0) {
                    return new Item(
                            itemDto.getName(),
                            itemDto.getDescription(),
                            itemDto.isAvailable(),
                            UserController.getUserService().getUser(ownerId).get(),
                            ItemRequestController.getService().getRequestById(itemDto.getRequestId())
                    );
                } else return new Item(
                        itemDto.getId(),
                        itemDto.getName(),
                        itemDto.getDescription(),
                        itemDto.isAvailable(),
                        UserController.getUserService().getUser(ownerId).get(),
                        ItemRequestController.getService().getRequestById(itemDto.getRequestId())
                );
            }else {
                if (itemDto.getId() == 0) {
                    return new Item(
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
        }

        throw new NotFoundException("");
    }
}
