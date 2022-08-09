package ru.practicum.shareit.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Getter
public class UserServiceImpl implements UserService{
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
        for(User user : getUsers()){
            if (user.getEmail().equals(email)) return true;
        }
        return false;
    }

    @Override
    public void setUsers(Set<User> users) {
        for(User user : users){
            repository.save(user);
        }
    }

    @Override
    public void addUser(User user) {
        repository.save(user);
    }

    @Override
    public void deleteUser(long userId) {
        repository.delete(getUser(userId).get());
    }

    @Override
    public User update(User user) {
        repository.save(user);
        return user;
    }
}
