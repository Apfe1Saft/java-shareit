package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.WrongDataException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository repository;

    @Override//I&T
    public Booking createBooking(Booking booking) {
        if (booking.getBooker().getId() == booking.getItem().getOwner().getId())
            throw new NotFoundException("Owner and Booker are one User.");
        if (booking.getStart().isBefore(LocalDateTime.now()))
            throw new WrongDataException("Wrong start date.");
        if (booking.getItem().isAvailable()) {
            repository.save(booking);
            return repository.findById(booking.getId()).orElseThrow();
        } else throw new WrongDataException("available is false.");
    }

    @Override//I&T
    public void approval(long bookingId, long userId, boolean approval) {
        if (repository.existsById(bookingId) && repository.getById(bookingId).getStatus().equals(Status.WAITING)) {
            if (approval) {
                Booking booking = getBooking(bookingId);
                if (booking.getItem().getOwner().getId() == userId) {
                    booking.setStatus(Status.APPROVED);
                    repository.save(booking);
                } else throw new NotFoundException("Wrong User Id.");
            } else {
                Booking booking = getBooking(bookingId);
                if (booking.getItem().getOwner().getId() == userId) {
                    booking.setStatus(Status.REJECTED);
                    repository.save(booking);
                } else throw new WrongDataException("Wrong User Id.");
            }
        } else throw new WrongDataException("Wrong Booking Id.");
    }

    @Override//I&T
    public Booking getBooking(long bookingId) {
        return repository.getById(bookingId);
    }

    @Override//I&T
    public List<Booking> showAllUserBookings(long userId, State state) {
        return showAll(state).stream()
                .filter(x -> x.getBooker().getId() == userId)
                .sorted(new BookingComparator())
                .collect(Collectors.toList());
    }

    @Override//I&T
    public List<Booking> showOwnerBookings(long userId, State state) {
        List<Booking> listOfBooking = showAll(state).stream()
                .filter(x -> x.getItem().getOwner()
                        .getId() == userId).collect(Collectors.toList());
        Collections.reverse(listOfBooking);
        return listOfBooking;
    }
    @Override//I&T
    public List<Booking> showAll(State state) {
        switch (state) {
            case ALL:
                return repository.findAll().stream()
                        .sorted(Comparator.comparing(Booking::getStart))
                        .collect(Collectors.toList());
            case PAST:
                return repository.findAll().stream()
                        .filter(x -> x.getEnd().isBefore(LocalDateTime.now()))
                        .collect(Collectors.toList());
            case FUTURE:
                return repository.findAll().stream()
                        .filter(x -> x.getStart().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toList());
            case CURRENT:
                return repository.findAll().stream()
                        .filter(x -> x.getStart().isBefore(LocalDateTime.now()))
                        .filter(x -> x.getEnd().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toList());
            case WAITING:
                return repository.findAll().stream()
                        .filter(x -> x.getStatus().equals(Status.WAITING))
                        .collect(Collectors.toList());
            default:
                return repository.findAll().stream()
                        .filter(x -> x.getStatus().equals(Status.REJECTED))
                        .collect(Collectors.toList());
        }

    }

    @Override//I&T
    public List<Booking> showAll(State state,int firstPage,int size) {
        Pageable uPage = PageRequest.of(firstPage, size, Sort.by("start"));
        switch (state) {
            case ALL:
                return repository.findAll(uPage).stream()
                        .sorted(Comparator.comparing(Booking::getStart))
                        .collect(Collectors.toList());
            case PAST:
                return repository.findAll(uPage).stream()
                        .filter(x -> x.getEnd().isBefore(LocalDateTime.now()))
                        .collect(Collectors.toList());
            case FUTURE:
                return repository.findAll(uPage).stream()
                        .filter(x -> x.getStart().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toList());
            case CURRENT:
                return repository.findAll(uPage).stream()
                        .filter(x -> x.getStart().isBefore(LocalDateTime.now()))
                        .filter(x -> x.getEnd().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toList());
            case WAITING:
                return repository.findAll(uPage).stream()
                        .filter(x -> x.getStatus().equals(Status.WAITING))
                        .collect(Collectors.toList());
            default:
                return repository.findAll(uPage).stream()
                        .filter(x -> x.getStatus().equals(Status.REJECTED))
                        .collect(Collectors.toList());
        }

    }

    @Override//I&T
    public boolean isBookingExist(long bookingId) {
        return repository.existsById(bookingId);
    }

    @Override//I&T
    public List<Booking> getOwnerBookings(int firstPage, int size, long userId) {
        if (firstPage < 0 || size < 0) {
            throw new WrongDataException("");
        }
        if (size == 0) {
            throw new WrongDataException("");
        }
        Pageable uPage = PageRequest.of(firstPage, size, Sort.by("start"));
        return repository.getOwnerBookings(userId,uPage);
    }


}