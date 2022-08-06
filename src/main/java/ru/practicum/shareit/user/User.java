package ru.practicum.shareit.user;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;

/**
 * // TODO .
 */
@Data
//@Entity
public class User {
    @Min(0)
    //@Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "ID")
    private int id;
    //@Column(name = "name", nullable = false, length = 255)
    private String name;
    //@Column(name = "email", nullable = false, length = 512)
    private String email;

    public User() {
    }

}
