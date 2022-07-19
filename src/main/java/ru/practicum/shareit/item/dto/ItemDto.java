package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.requests.ItemRequest;
import ru.practicum.shareit.user.User;

/**
 * // TODO .
 */
@Data
public class ItemDto {
    int id;
    String name;
    String description;
    boolean available;
    User owner;
    int requestId;

    public ItemDto(String name, String description, boolean available, int requestId) {
        this.name = name;
        this.description = description;
        this.available = available;
        this.requestId = requestId;
    }
}
