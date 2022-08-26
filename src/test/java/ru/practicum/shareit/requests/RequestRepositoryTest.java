package ru.practicum.shareit.requests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.practicum.shareit.config.PersistenceConfig;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.ItemServiceImpl;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.UserServiceImpl;

import java.time.LocalDateTime;

@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringJUnitConfig({PersistenceConfig.class, UserServiceImpl.class,
        ItemServiceImpl.class, UserController.class})
class RequestRepositoryTest {
    private final UserService userService;
    private final ItemService itemService;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private RequestRepository repository;

    @Test
    public void contextLoads() {
        Assertions.assertNotNull(em);
    }

    @Test
    void findNotUserRequests() {
        User user = new User(1, "Name", "a@mail.ru");
        userService.addUser(user);
        Item item = new Item(1, "itemName", "item description", true, user);
        itemService.addItem(item);
        User booker = new User(2, "Name", "b@mail.ru");
        ItemRequest request = new ItemRequest(1, "description", booker, LocalDateTime.now());
        userService.addUser(booker);
        Pageable uPage = PageRequest.of(0, 1);
        Assertions.assertEquals(repository.findNotUserRequests(1L, uPage).toString(), "[]");
        repository.save(request);
        Assertions.assertNotNull(repository.findNotUserRequests(1L, uPage));

    }
}