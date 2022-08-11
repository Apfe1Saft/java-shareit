package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.WrongDataException;
import ru.practicum.shareit.item.Item;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository repository;

    @Override
    public Booking createBooking(Booking booking) {
        if(booking.getStart().isBefore(LocalDate.now()))
            throw new WrongDataException("Wrong start date.");
        if(booking.getItem().isAvailable()) {
            repository.save(booking);
            return repository.getById(booking.getId());
        }
        else throw new WrongDataException("available is false.");
    }

    @Override
    public Boolean approval(long bookingId, long userId,boolean approval) {
        if(approval) {
            Booking booking = getBooking(bookingId);
            if (booking.getBooker().getId() == userId) {
                booking.setStatus(Status.APPROVED);
                return true;
            }
            else throw new WrongDataException("Wrong User Id.");
        }
        else {
            Booking booking = getBooking(bookingId);
            if (booking.getBooker().getId() == userId) {
                booking.setStatus(Status.REJECTED);
                return true;
            }
            else throw new WrongDataException("Wrong User Id.");
        }
    }

    @Override
    public Booking getBooking(long bookingId) {
        return repository.getById(bookingId);
    }

    @Override
    public List<Booking> showAllOwnerBookings(long userId) {
        return repository.findAll().stream().filter(x -> x.getBooker().getId() == userId)
                .sorted(new BookingComparator()).
                        collect(Collectors.toList());
    }

    @Override
    public List<Booking> showAllUserItemBookings(long userId) {
        return repository.findAll().stream().filter(x -> x.getItem().getOwner().getId() == userId).collect(Collectors.toList());
    }
}

class BookingComparator implements Comparator<Booking> {

    @Override
    public int compare(Booking a, Booking b) {
        return a.getStart().compareTo(b.getStart());
    }
}
