package ru.practicum.shareit.user;

import ru.practicum.shareit.exception.NullParamException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    List<User> getUsers();

    Optional<User> getUser(long userId);

    boolean isEmailExist(String email);

    void setUsers(Set<User> users);

    void addUser(User user);

    void deleteUser(long userId);

    User update(User user);
}
