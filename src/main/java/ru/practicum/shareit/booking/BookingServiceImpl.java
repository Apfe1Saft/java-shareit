package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.WrongDataException;
import ru.practicum.shareit.item.Item;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository repository;

    @Override
    public Booking createBooking(Booking booking) {
        if(booking.getBooker().getId() == booking.getItem().getOwner().getId())
            throw new NotFoundException("Owner and Booker are one User.");
        if (booking.getStart().isBefore(LocalDateTime.now()))
            throw new WrongDataException("Wrong start date.");
        if (booking.getItem().isAvailable()) {
            repository.save(booking);
            System.out.println(booking);
            return repository.getById(booking.getId());
        } else throw new WrongDataException("available is false.");
    }

    @Override
    public void approval(long bookingId, long userId, boolean approval) {
        if (repository.existsById(bookingId)&& repository.getById(bookingId).getStatus().equals(Status.WAITING)) {
            if (approval ) {
                Booking booking = getBooking(bookingId);
                System.out.println(booking.getBooker().getId() + "!=" + bookingId);
                if (booking.getItem().getOwner().getId() == userId) {//!!!!!!!!!!!
                    booking.setStatus(Status.APPROVED);
                    repository.save(booking);
                } else throw new NotFoundException("Wrong User Id.");
            } else {
                //System.out.println("SO");
                Booking booking = getBooking(bookingId);
                if (booking.getItem().getOwner().getId() == userId) {
                    booking.setStatus(Status.REJECTED);
                    repository.save(booking);
                } else throw new WrongDataException("Wrong User Id.");
            }
        } else throw new WrongDataException("Wrong Booking Id.");
    }

    @Override
    public Booking getBooking(long bookingId) {
        return repository.getById(bookingId);
    }

    @Override
    public List<Booking> showAllUserBookings(long userId,State state) {
        return showAll(state).stream().filter(x -> x.getBooker().getId() == userId)
                .sorted(new BookingComparator()).
                        collect(Collectors.toList());
    }

    @Override
    public List<Booking> showOwnerBookings(long userId, State state) {
        //System.out.println(showAll(state).stream().filter(x->x.getItem().getOwner().getId()==userId).collect(Collectors.toList()));
        List<Booking> listOfBooking = showAll(state).stream().filter(x -> x.getItem().getOwner().
                getId() == userId).collect(Collectors.toList());
        Collections.reverse(listOfBooking);
        return listOfBooking;
    }

    @Override
    public List<Booking> showAll(State state) {
        switch (state) {
            case ALL:
                return repository.findAll().stream().sorted(Comparator.comparing(Booking::getStart)).
                        collect(Collectors.toList());
            case PAST:
                return repository.findAll().stream().filter(x -> x.getEnd().isBefore(LocalDateTime.now())).
                        collect(Collectors.toList());
            case FUTURE:
                return repository.findAll().stream().filter(x -> x.getStart().isAfter(LocalDateTime.now())).
                        collect(Collectors.toList());
            case CURRENT:
                return repository.findAll().stream().filter(x -> x.getStart().isBefore(LocalDateTime.now())).
                        filter(x -> x.getEnd().isAfter(LocalDateTime.now())).
                        collect(Collectors.toList());
            case WAITING:
                return repository.findAll().stream().filter(x -> x.getStatus().equals(Status.WAITING)).
                        collect(Collectors.toList());
            default://REJECTED
                return repository.findAll().stream().filter(x -> x.getStatus().equals(Status.REJECTED)).
                        collect(Collectors.toList());
        }

    }

    @Override
    public boolean isBookingExist(long bookingId) {
        return repository.existsById(bookingId);
    }
}

class BookingComparator implements Comparator<Booking> {

    @Override
    public int compare(Booking a, Booking b) {
        return a.getStart().compareTo(b.getStart());
    }
}
