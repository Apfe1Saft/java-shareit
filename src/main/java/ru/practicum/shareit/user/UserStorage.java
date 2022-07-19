package ru.practicum.shareit.user;

import ru.practicum.shareit.item.model.Item;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserStorage {
    private static Set<User> users = new HashSet<>();

    public static int getMaxId() {
        int max = 0;
        for (User user : getUsers()) {
            if (user.getId() > max) max = user.getId();
        }
        return max + 1;
    }

    public static boolean isEmailExist(String email) {
        for (User user : getUsers()) {
            if (user.getEmail().equals(email)) return true;
        }
        return false;
    }

    public static Set<User> getUsers() {
        return users;
    }

    public static Optional<User> getUser(int userId) {
        return getUsers().stream().filter(user -> user.getId() == userId).findFirst();
    }

    private static void setUsers(Set<User> users) {
        UserStorage.users = users;
    }

    public static void addUser(User user) {
        getUsers().add(user);

    }

    public static void deleteUser(int userId) {
        getUsers().removeIf(user -> user.getId() == userId);
    }

    public static void update(User user) {
        deleteUser(user.getId());
        addUser(user);
    }
}
