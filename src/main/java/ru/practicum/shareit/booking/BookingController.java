package ru.practicum.shareit.booking;

import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.WrongDataException;

import javax.validation.Valid;
import java.util.List;


/**
 * // TODO .
 */
//
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public @Valid BookingDto create(@Valid @RequestBody final BookingDto bookingDto,
                                 @RequestHeader("X-Sharer-User-Id") String userId) {
        System.out.println(bookingDto);
        return BookingMapper.toBookingDto(
                bookingService.createBooking(BookingMapper.toBooking(bookingDto,Long.parseLong(userId)))
        );
    }

    @GetMapping("/{bookingId}")
    public @Valid Booking getBooking(@RequestHeader("X-Sharer-User-Id") String userId,@PathVariable("bookingId") long bookingId) {
        System.out.println(bookingId);
        if(bookingService.getBooking(bookingId).getBooker().getId()==Long.parseLong(userId)){
            return bookingService.getBooking(bookingId);
        }
        throw new WrongDataException("User is not the creator of the booking or the owner of the item.");
    }

    @PatchMapping("/{bookingId}?")
    public void approval(@RequestHeader("X-Sharer-User-Id") String userId,
                         @RequestParam("approved") boolean approval,@PathVariable("bookingId") int bookingId) {
        System.out.println("UserId:"+userId);
        System.out.println("BookingId:"+bookingId);
        System.out.println("Approval:"+approval);
        //bookingService.approval(Long.parseLong(bookingId),Long.parseLong(userId),approval);
    }

    @GetMapping("/bookings?state={state}")
    public List<Booking> showAllOwnerBookings(@RequestHeader("X-Sharer-User-Id") String userId,
                                              @PathVariable("state") String state) {
        return bookingService.showAllOwnerBookings(Long.parseLong(userId));
    }

    @GetMapping("/owner?state={state}")
    public List<Booking> showAllUserItemBookings(@RequestHeader("X-Sharer-User-Id") String userId,
                                                @PathVariable("state") String state) {
        return bookingService.showAllUserItemBookings(Long.parseLong(userId));
    }

}
