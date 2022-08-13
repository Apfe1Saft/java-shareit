package ru.practicum.shareit.user;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.NullParamException;
import ru.practicum.shareit.exception.WrongDataException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * // TODO .
 */
@Validated
@RestController
@RequestMapping(path = "/users")
@Slf4j
public class UserController {
    @Getter
    private static UserService userService;

    public UserController(UserService userService) {
        UserController.userService = userService;
    }

    @GetMapping
    public List<User> show() {
        return userService.getUsers();
    }

    @PostMapping
    public @Valid User create(@Valid @RequestBody final User user) {
        if (user.getEmail() == null) throw new WrongDataException("");
        userChecker(user);
        userService.addUser(user);
        return user;
    }

    @PutMapping
    public @Valid User put(@Valid @RequestBody final UserDto user) {
        return userService.update(user);
    }

    @PatchMapping("/{id}")
    public @Valid User update(@PathVariable("id") int id, @Valid @RequestBody final UserDto user) {
        user.setId(id);
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable("id") long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public Optional<User> getById(@PathVariable("id") long id) {
        if (userService.getUser(id).isPresent()) {
            return userService.getUser(id);
        } else throw new NotFoundException("User not exist");
    }

    public void userChecker(User user) {
        if (user.getName() == null) throw new NullParamException("");
        if (user.getEmail() != null) {
            if (!user.getEmail().contains("@")) throw new WrongDataException("");
        }
    }
}
