package ru.practicum.shareit.item;

import lombok.Data;

/**
 * // TODO .
 */
@Data
public class ItemDto {
    private long id;
    private String name;
    private String description;
    private Boolean available = null;

    public ItemDto(long id, String name, String description, Boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
    }

    public Boolean isAvailable() {
        return available;
    }

}
