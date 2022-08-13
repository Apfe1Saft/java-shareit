package ru.practicum.shareit.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingDto;

/**
 * // TODO .
 */
@Data
public class ItemDto {
    private long id;
    private String name;
    private String description;
    private Boolean available = null;
    @Getter
    @Setter
    private BookingDto lastBooking;
    @Getter
    @Setter
    private BookingDto nextBooking;

    public ItemDto(){

    }

    public ItemDto(long id, String name, String description, Boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
    }

    public Boolean isAvailable() {
        return available;
    }

}
