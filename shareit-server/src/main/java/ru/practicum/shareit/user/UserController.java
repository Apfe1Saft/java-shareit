package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.NullParamException;
import ru.practicum.shareit.exception.WrongDataException;

import java.util.List;



@RestController
@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> show() {
        return userService.getUsers();
    }

    @PostMapping
    public  User create( @RequestBody final User user) {
        if (user.getEmail() == null) throw new WrongDataException("");
        userChecker(user);
        userService.addUser(user);
        return user;
    }

    @PutMapping
    public  User put( @RequestBody final UserDto user) {
        return userService.update(user);
    }

    @PatchMapping("/{id}")
    public  User update(@PathVariable("id") int id,  @RequestBody final UserDto user) {
        user.setId(id);
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable("id") long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable("id") long id) {
        if (userService.getUser(id).isPresent()) {
            return userService.getUser(id).get();
        } else throw new NotFoundException("User not exist");
    }

    public void userChecker(User user) {
        if (user.getName() == null) throw new NullParamException("NullParamException");
        if (user.getEmail() != null) {
            if (!user.getEmail().contains("@")) throw new WrongDataException("WrongDataException");
            return;
        }
        throw new NullParamException("NullParamException");
    }
}
