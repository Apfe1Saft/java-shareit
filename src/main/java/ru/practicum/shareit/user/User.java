package ru.practicum.shareit.user;

import lombok.Data;

/**
 * // TODO .
 */
@Data
public class User {
    int id;// — уникальный идентификатор пользователя;
    String name;// — имя или логин пользователя;
    String email;// — адрес электронной почты (учтите, что два пользователя не могут
    //        иметь одинаковый адрес электронной почты).

}
