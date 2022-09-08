package ru.practicum.shareit.user;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.NullParamException;
import ru.practicum.shareit.exception.WrongDataException;
import ru.practicum.shareit.user.dto.UserDto;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/users")
@Slf4j
public class UserController {

    private final UserClient userClient;

    @GetMapping
    public Object show() {
        return userClient.show();
    }

    @PostMapping
    public Object create(@RequestBody UserDto userDto) {
        if (userDto.getEmail() == null) throw new WrongDataException("");
        userChecker(userDto);
        return userClient.create(userDto);
    }

    @PutMapping
    public Object put(@RequestBody UserDto userDto) {
        return userClient.put(userDto);
    }

    @PatchMapping("/{id}")
    public Object update(@PathVariable int id, @RequestBody UserDto userDto) {
        return userClient.update(id, userDto);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable int id) {
        userClient.remove(id);
    }

    @GetMapping("/{userId}")
    public Object getById(@PathVariable int userId) {
        return userClient.getById(userId);
    }

    public void userChecker(UserDto user) {
        if (user.getName() == null) throw new NullParamException("NullParamException");
        if (user.getEmail() != null) {
            if (!user.getEmail().contains("@")) throw new WrongDataException("WrongDataException");
            return;
        }
        throw new NullParamException("NullParamException");
    }

}