package ru.practicum.shareit.user;

import lombok.Data;

@Data
public class UserDto {
    int id;
    String name;
    String email;
    public UserDto(String name,String email){
        this.name = name;
        this.email = email;
    }
}
