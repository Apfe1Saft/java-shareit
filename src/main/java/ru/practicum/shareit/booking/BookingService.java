package ru.practicum.shareit.booking;

import java.util.List;

public interface BookingService {
    Booking createBooking(Booking booking);
    Boolean approval(long bookingId,long userId,boolean approval);
    Booking getBooking(long bookingId);
    List<Booking> showAllOwnerBookings(long userId);
    List<Booking> showAllUserItemBookings(long userId);

}
