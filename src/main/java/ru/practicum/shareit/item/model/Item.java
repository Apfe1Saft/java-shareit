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
    int id;//— уникальный идентификатор вещи;
    String name;// — краткое название;
    String description;// — развёрнутое описание;
    boolean available;// — статус о том, доступна или нет вещь для аренды;
    User owner;// — владелец вещи;
    ItemRequest request;// — если вещь была создана по запросу другого пользователя, то в этом
    //поле будет храниться ссылка на соответствующий запрос.
}
