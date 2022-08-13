package ru.practicum.shareit.booking;

import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.user.UserController;

public class BookingMapper {
    public static Booking toBooking(BookingDto bookingDto,long userId){
        return new Booking(
                bookingDto.getStart(),
                bookingDto.getEnd(),
                //user
                UserController.getUserService().getUser(userId).get(),
                //item
                ItemController.getItemService().getItemById(bookingDto.getItemId()),
                //status
                Status.WAITING
        );
    }
    public static BookingDto toBookingDto(Booking booking){
        return new BookingDto(
                booking.getId(),
                booking.getItem().getId(),
                booking.getStart(),
                booking.getEnd()
        );
    }
}
