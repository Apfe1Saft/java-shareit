package ru.practicum.shareit.item.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.practicum.shareit.requests.ItemRequest;
import ru.practicum.shareit.user.User;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * // TODO .
 */
@Data

public class Item {
    @Min(0)
    private int id;//— уникальный идентификатор вещи;
    private String name;// — краткое название;
    private String description;// — развёрнутое описание;
    //@JsonProperty(value = "available")
    //@JsonInclude(JsonInclude.Include.NON_NULL)

    private Boolean available;// — статус о том, доступна или нет вещь для аренды;
    private User owner;// — владелец вещи;
    private ItemRequest request;// — если вещь была создана по запросу другого пользователя, то в этом

    public Item( int id,String name, String description, boolean available, User owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.owner = owner;
    }

    public Boolean isAvailable() {
        return available;
    }

    //поле будет храниться ссылка на соответствующий запрос.
}
