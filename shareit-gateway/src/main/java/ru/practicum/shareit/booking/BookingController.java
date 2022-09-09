package ru.practicum.shareit.booking;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.NullParamException;
import ru.practicum.shareit.exception.ValidationException;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingClient bookingClient;

    @PostMapping
    public Object create(@RequestBody BookingDto bookingDto,
                         @RequestHeader("X-Sharer-User-Id") Integer userId) {
        if (userId == null) {
            throw new NullParamException("");
        } else {
            return bookingClient.create(bookingDto, userId);
        }
    }

    @PatchMapping("/{bookingId}")
    public Object approval(@PathVariable Integer bookingId,
                           @RequestParam Boolean approved,
                           @RequestHeader("X-Sharer-User-Id") Integer userId) {
        if (userId == null) {
            throw new NullParamException("");
        } else {
            return bookingClient.approval(bookingId, approved, userId);
        }
    }

    @GetMapping("/{bookingId}")
    public Object getBooking(@RequestHeader("X-Sharer-User-Id") Integer userId,
                             @PathVariable Integer bookingId) throws NullPointerException {
        if (userId == null) {
            throw new NullParamException("");
        } else {
            return bookingClient.getBooking(userId, bookingId);
        }
    }

    @GetMapping("/owner")
    public Object getOwnerBookings(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                   @RequestParam(defaultValue = "ALL") String state,
                                   @RequestParam(required = false, defaultValue = "") String from,
                                   @RequestParam(required = false, defaultValue = "") String size)
            throws NullParamException {
        State newState;
        try {
            newState = State.valueOf(state);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("");
        }
        if (userId == null) {
            throw new NullParamException("");
        } else {
            return bookingClient.getOwnerBookings(userId, newState, from, size);
        }
    }

    @GetMapping
    public Object showAll(@RequestHeader("X-Sharer-User-Id") Integer userId,
                          @RequestParam(defaultValue = "ALL") String state,
                          @RequestParam(required = false, defaultValue = "") String from,
                          @RequestParam(required = false, defaultValue = "") String size) {
        State newState;
        try {
            newState = State.valueOf(state);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("");
        }
        if (userId == null) {
            throw new NullParamException("");
        } else {
            return bookingClient.showAll(userId, newState, from, size);
        }
    }
}