package ru.practicum.shareit.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
//@Profile("test")
@RequiredArgsConstructor
@Getter
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public List<User> getUsers() {
        return repository.findAll();
    }

    @Override
    public Optional<User> getUser(long userId) {
        return repository.findById(userId);
    }

    @Override
    public boolean isEmailExist(String email) {
        for (User user : getUsers()) {
            if (user.getEmail().equals(email)) return true;
        }
        return false;
    }

    @Override
    public void setUsers(Set<User> users) {
        for (User user : users) {
            repository.save(user);
        }
    }

    @Override
    public User addUser(User user) {
        repository.save(user);
        return repository.getById(user.getId());
    }

    @Override
    public void deleteUser(long userId) {
        repository.delete(getUser(userId).get());
    }

    @Override
    public User update(UserDto user) {
        if (getUser(user.getId()).isPresent()) {
            User updatedUser = getUser(user.getId()).get();
            if (user.getName() != null) updatedUser.setName(user.getName());
            if (user.getEmail() != null) updatedUser.setEmail(user.getEmail());
            repository.save(updatedUser);
            return updatedUser;
        }
        repository.save(UserMapper.toUser(user));
        return UserMapper.toUser(user);
    }
}
