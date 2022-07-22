package ru.practicum.shareit.requests;

import lombok.Data;
import ru.practicum.shareit.user.User;

import java.time.LocalDate;

/**
 * // TODO .
 */
@Data
public class ItemRequest {
    int id;
    String description;
    User requestor;
    LocalDate created;

}
