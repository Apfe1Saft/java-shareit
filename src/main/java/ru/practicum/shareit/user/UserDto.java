package ru.practicum.shareit.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserDto {
    long id;
    String name;
    String email;

    public UserDto() {

    }

    public UserDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
