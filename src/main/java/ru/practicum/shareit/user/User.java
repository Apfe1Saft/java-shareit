package ru.practicum.shareit.user;

import lombok.Data;

import javax.validation.constraints.Min;

/**
 * // TODO .
 */
@Data
public class User {
    @Min(0)
    int id;// — уникальный идентификатор пользователя;
    String name;// — имя или логин пользователя;
    String email;// — адрес электронной почты (учтите, что два пользователя не могут
    //        иметь одинаковый адрес электронной почты).

}
