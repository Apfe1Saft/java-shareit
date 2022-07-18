package ru.practicum.shareit.requests;

import lombok.Data;
import ru.practicum.shareit.user.User;

import java.time.LocalDate;

/**
 * // TODO .
 */
@Data
public class ItemRequest {
    int id;// — уникальный идентификатор запроса;
    String description;// — текст запроса, содержащий описание требуемой вещи;
    User requestor;// — пользователь, создавший запрос;
    LocalDate created;// — дата и время создания запроса.

}
