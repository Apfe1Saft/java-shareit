package ru.practicum.shareit.user;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.ItemStorage;
import ru.practicum.shareit.item.model.Item;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

/**
 * // TODO .
 */
@RestController
@RequestMapping(path = "/users")
public class UserController {
    @GetMapping
    public Set<User> show() {
        return UserStorage.getUsers();
    }

    @PostMapping
    public @Valid User create(@Valid @RequestBody final User user, HttpServletResponse response, BindingResult result) {
        if(user.getId() == 0) user.setId(UserStorage.getMaxId());
        if(UserStorage.isEmailExist(user.getEmail())) throw
        UserStorage.addUser(user);
        return user;
    }

    @PutMapping
    public @Valid void  put(@Valid @RequestBody final User user, HttpServletResponse response, BindingResult result) {
        if(UserStorage.getUser(user.getId()).isPresent()){
            update(user,response,result);
        }
        else create(user,response,result);
    }

    @PatchMapping
    public @Valid User update(@Valid @RequestBody final User user, HttpServletResponse response, BindingResult result) {
        UserStorage.update(user);
        return user;
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
}
