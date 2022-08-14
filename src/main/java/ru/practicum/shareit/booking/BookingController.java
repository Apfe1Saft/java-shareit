package ru.practicum.shareit.booking;

import lombok.Getter;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.UserController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * // TODO .
 */
//
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    @Getter
    private static BookingService bookingService;

    public BookingController(BookingService bookingService) {
        BookingController.bookingService = bookingService;
    }

    @PostMapping
    public @Valid BookingDto create(@Valid @RequestBody final BookingDto bookingDto,
                                    @RequestHeader("X-Sharer-User-Id") String userId) {
        return BookingMapper.toBookingDto(
                bookingService.createBooking(BookingMapper.toBooking(bookingDto, Long.parseLong(userId)))
        );
    }

    @PatchMapping("/{bookingId}?")
    public Booking approval(@RequestHeader("X-Sharer-User-Id") String userId,
                            @RequestParam("approved") boolean approval, @PathVariable("bookingId") String bookingId, HttpServletRequest request) {
        bookingId = new AntPathMatcher().extractPathWithinPattern(request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString(), request.getRequestURI());
        bookingService.approval(Long.parseLong(bookingId),
                Long.parseLong(userId), approval);
        return getBookingService().getBooking(Long.parseLong(bookingId));
    }

    @GetMapping(value = "/{bookingId:[0-9]+}")
    public @Valid Booking getBooking(@RequestHeader("X-Sharer-User-Id") String userId, @PathVariable("bookingId") long bookingId) {
        System.out.println("getBooking");
        if (!bookingService.isBookingExist(bookingId)) {
            throw new NotFoundException("Booking is not exist.");
        }
        isUserExist(Long.parseLong(userId));
        if (bookingService.getBooking(bookingId).getBooker().getId() == Long.parseLong(userId) ||
                bookingService.getBooking(bookingId).getItem().getOwner().getId() == Long.parseLong(userId)) {
            System.out.println("WORK");
            return bookingService.getBooking(bookingId);
        }
        throw new NotFoundException("User is not the creator of the booking or owner of the item.");
    }

    @GetMapping(value = "/owner")
    public @Valid List<Booking> getOwnerBookings(@RequestHeader("X-Sharer-User-Id") String userId,
                                                 @RequestParam(name = "state", defaultValue = "ALL", required = false) String state) {
        System.out.println("/owner");
        isUserExist(Long.parseLong(userId));
        State newState;
        try {
            newState = State.valueOf(state);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Unknown state: UNSUPPORTED_STATUS");
        }
        return bookingService.showOwnerBookings(Long.parseLong(userId), newState);
    }


    @GetMapping("?state={state}")
    public List<Booking> showAllUserBookings(@RequestHeader("X-Sharer-User-Id") String userId,
                                             @RequestParam(name = "state", defaultValue = "ALL") String state) {
        return bookingService.showAllUserBookings(Long.parseLong(userId), State.valueOf(state));
    }

    @GetMapping("")
    public List<Booking> showAll(@RequestHeader("X-Sharer-User-Id") String userId,
                                 @RequestParam(name = "state", defaultValue = "ALL") String state) {
        State newState;
        try {
            newState = State.valueOf(state);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Unknown state: UNSUPPORTED_STATUS");
        }
        if (UserController.getUserService().getUser(Long.parseLong(userId)).isEmpty()) {
            throw new ValidationException("Wrong");
        }
        List<Booking> answer;

        answer = bookingService.showAll(newState).stream().filter(x -> x.getBooker()
                .getId() == Long.parseLong(userId)).collect(Collectors.toList());


        Collections.reverse(answer);
        return answer;
    }

    public void isUserExist(long userId) {
        if (UserController.getUserService().getUser(userId).isEmpty()) {
            throw new ValidationException("Wrong");
        }
    }

}
