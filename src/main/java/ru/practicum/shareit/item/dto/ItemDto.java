package ru.practicum.shareit.item.dto;

import lombok.Data;

/**
 * // TODO .
 */
@Data
public class ItemDto {
    private int id;
    private String name;
    private String description;
    private Boolean available = null;

    public ItemDto(int id, String name, String description, Boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
    }

    public Boolean isAvailable() {
        return available;
    }

}
