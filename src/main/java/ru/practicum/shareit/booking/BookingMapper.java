package ru.practicum.shareit.booking;

import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

public class BookingMapper {
    public static Booking toBooking(BookingDto bookingDto, User user, Item item) {
        return new Booking(
                bookingDto.getId(),
                bookingDto.getStart(),
                bookingDto.getEnd(),
                user,
                item,
                Status.WAITING
        );
    }

    public static BookingDto toBookingDto(Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getItem().getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getStatus()
        );
    }
}
