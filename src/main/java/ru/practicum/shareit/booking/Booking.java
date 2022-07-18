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
    int id;// — уникальный идентификатор бронирования;
    LocalDate start;// — дата начала бронирования;
    LocalDate end;// — дата конца бронирования;
    ItemRequest item;// — вещь, которую пользователь бронирует;
    User booker;// — пользователь, который осуществляет бронирование;
    Status status;// — статус бронирования. Может принимать одно из следующих
    //значений: WAITING — новое бронирование, ожидает одобрения, APPROVED
    //бронирование подтверждено владельцем, REJECTED — бронирование
    //отклонено владельцем, CANCELED — бронирование отменено создателем.

}
