package ru.practicum.shareit.booking;

import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.NullParamException;
import ru.practicum.shareit.exception.WrongDataException;
import ru.practicum.shareit.item.ItemDto;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * // TODO .
 */
//
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private BookingService bookingService;

    @GetMapping
    public List<User> show() {
    }

    @PostMapping
    public @Valid User create(@Valid @RequestBody final User user) {
    }

    @PutMapping
    public @Valid User put(@Valid @RequestBody final UserDto user) {
    }

    @PatchMapping("/{id}")
    public @Valid User update(@PathVariable("id") int id, @Valid @RequestBody final UserDto user) {
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable("id") long id) {
    }

    @GetMapping("/{id}")
    public Optional<User> getById(@PathVariable("id") long id) {
    }

    public void userChecker(User user) {
    }
}
