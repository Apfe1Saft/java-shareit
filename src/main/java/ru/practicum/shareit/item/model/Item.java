package ru.practicum.shareit.item.model;


import lombok.Data;
import ru.practicum.shareit.requests.ItemRequest;
import ru.practicum.shareit.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;

/**
 * // TODO .
 */
@Data
//@Entity
public class Item {
    @Min(0)
    //@Id
   // @Column(name="ID")
    private int id;
  //  @Column(name="name")
    private String name;
   // @Column(name="description")
    private String description;
   // @Column(name="available")
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
