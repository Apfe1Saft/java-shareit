package ru.practicum.shareit.user;

import ru.practicum.shareit.exception.NullParamException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserStorage {
    private static Set<User> users = new HashSet<>();
    private static int maxId = 0;

    public static int getMaxId() {
        maxId++;
        return maxId;
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

    public static User update(User user) {
        if (getUser((int) user.getId()).isPresent()) {
            if (isEmailExist(user.getEmail()) & !getUser((int) user.getId()).get().getEmail().equals(user.getEmail())) {
                throw new NullParamException("");
            }
        }
        if (getUser((int) user.getId()).isPresent()) {
            User updatedUser = getUser((int) user.getId()).get();
            if (user.getName() != null) updatedUser.setName(user.getName());
            if (user.getEmail() != null) updatedUser.setEmail(user.getEmail());
            user = updatedUser;

        }
        deleteUser((int) user.getId());
        addUser(user);
        return user;
    }
}
