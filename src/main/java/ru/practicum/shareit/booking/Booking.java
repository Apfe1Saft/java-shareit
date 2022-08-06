package ru.practicum.shareit.booking;

import lombok.Data;
import ru.practicum.shareit.requests.ItemRequest;
import ru.practicum.shareit.user.User;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

/**
 * // TODO .
 */
@Data
public class Booking {
    private int id;
    private LocalDate start;
    private LocalDate end;
    private ItemRequest item;
    private User booker;
    @Enumerated(EnumType.STRING)
    private Status status;

}
