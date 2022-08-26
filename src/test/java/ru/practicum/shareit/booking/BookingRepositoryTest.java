package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.practicum.shareit.config.PersistenceConfig;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemController;
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
        ItemServiceImpl.class, UserController.class, ItemController.class})
class BookingRepositoryTest {
    private final UserService userService;
    private final ItemService itemService;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookingRepository repository;

    @Test
    public void contextLoads() {
        Assertions.assertNotNull(em);
    }

    @Test
    @Order(1)
    void findByBookerId() {
        User user = new User(1, "Name", "qwerty@mail.ru");
        userService.addUser(user);
        Item item = new Item(1, "itemName", "item description", true, user);
        itemService.addItem(item);
        Booking booking = new Booking(LocalDateTime.now(), LocalDateTime.now(), user, item, Status.APPROVED);
        booking.setId(1);
        Pageable uPage = PageRequest.of(0, 1);
        Assertions.assertEquals(repository.findByBookerId(1L, uPage).toString(), "[]");
        repository.save(booking);
        Assertions.assertNotNull(repository.findByBookerId(1L, uPage));
    }

    @Test
    @Order(2)
    void getOwnerBookings() {
        User user = new User(2, "Name", "qwerty@mail.ru");
        userService.addUser(user);
        Item item = new Item(2, "itemName", "item description", true, user);
        itemService.addItem(item);
        Booking booking = new Booking(LocalDateTime.now(), LocalDateTime.now(), user, item, Status.APPROVED);
        booking.setId(2);
        Pageable uPage = PageRequest.of(0, 1);
        Assertions.assertEquals(repository.getOwnerBookings(2L, uPage).toString(), "[]");
        repository.save(booking);
        Assertions.assertNotNull(repository.getOwnerBookings(2L, uPage));
    }
}