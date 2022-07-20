package ru.practicum.shareit.user;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * // TODO .
 */
@Data
public class User {
    @Min(0)
    private int id;// — уникальный идентификатор пользователя;
    private String name;// — имя или логин пользователя;
    private String email;// — адрес электронной почты (учтите, что два пользователя не могут
    //        иметь одинаковый адрес электронной почты).
    public User(){}

}
