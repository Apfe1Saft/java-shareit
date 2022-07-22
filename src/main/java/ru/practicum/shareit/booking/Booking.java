package ru.practicum.shareit.booking;

import lombok.Data;
import ru.practicum.shareit.requests.ItemRequest;
import ru.practicum.shareit.user.User;

import java.time.LocalDate;

/**
 * // TODO .
 */
@Data
public class Booking {
    int id;
    LocalDate start;
    LocalDate end;
    ItemRequest item;
    User booker;
    Status status;

}
