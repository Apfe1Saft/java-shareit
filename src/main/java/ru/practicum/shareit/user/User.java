package ru.practicum.shareit.user;

import lombok.Data;

import javax.validation.constraints.Min;

/**
 * // TODO .
 */
@Data
public class User {
    @Min(0)
    private int id;
    private String name;
    private String email;

    public User() {
    }

}
