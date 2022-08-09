package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import ru.practicum.shareit.exception.*;

/**
 * // TODO .
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
@Slf4j
public class UserController {
    private final UserService userService;
    @GetMapping
    public List<User> show() {
        return userService.getUsers();
    }

    @PostMapping
    public @Valid User create(@Valid @RequestBody final User user, HttpServletResponse response, BindingResult result) {
        if (user.getEmail() == null) throw new WrongDataException("");
        userChecker(user);
        UserStorage.addUser(user);
        return user;
    }

    @PutMapping
    public @Valid User put(@Valid @RequestBody final User user, HttpServletResponse response, BindingResult result) {
        if (UserStorage.getUser((int) user.getId()).isPresent()) {
            return update((int) user.getId(), user, response, result);
        } else return create(user, response, result);
    }

    @PatchMapping("/{id}")
    public @Valid User update(@PathVariable("id") int id, @Valid @RequestBody final User user, HttpServletResponse response, BindingResult result) {
        user.setId(id);
        return UserStorage.update(user);
    }

    @DeleteMapping("/{id}")
    public Optional<User> remove(@PathVariable("id") int id, HttpServletResponse response) {
        UserStorage.deleteUser(id);
        return UserStorage.getUser(id);
    }

    @GetMapping("/{id}")
    public Optional<User> getById(@PathVariable("id") int id, HttpServletResponse response) {
        return UserStorage.getUser(id);
    }

    public void userChecker(User user) {
        if (user.getName() == null) throw new NullParamException("");
        if (UserStorage.isEmailExist(user.getEmail())) throw new ValidationException("");
        if (user.getEmail() != null) {
            if (!user.getEmail().contains("@")) throw new WrongDataException("");
        }
        if (user.getId() == 0) user.setId(UserStorage.getMaxId());
    }
}
