package ru.practicum.shareit.item.model;


import lombok.Data;
import ru.practicum.shareit.requests.ItemRequest;
import ru.practicum.shareit.user.User;

import javax.validation.constraints.Min;

/**
 * // TODO .
 */
@Data

public class Item {
    @Min(0)
    private int id;
    private String name;
    private String description;
    private Boolean available;
    private User owner;
    private ItemRequest request;

    public Item(int id, String name, String description, boolean available, User owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.owner = owner;
    }

    public Boolean isAvailable() {
        return available;
    }

}
