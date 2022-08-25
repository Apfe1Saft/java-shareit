package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.practicum.shareit.config.PersistenceConfig;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.ItemServiceImpl;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.UserServiceImpl;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringJUnitConfig({PersistenceConfig.class,
        UserServiceImpl.class,
        ItemServiceImpl.class,
        BookingServiceImpl.class,
})
class BookingServiceImplTest {
    private final EntityManager em;
    private final UserService userService;
    private final ItemService itemService;
    private final BookingService bookingService;

    @Test
    @Order(1)
    void createBooking() throws InterruptedException {
        User user = new User(1, "Name", "a@mail.ru");
        userService.addUser(user);
        User booker = new User(2, "Name", "b@mail.ru");
        userService.addUser(booker);
        Item item = new Item(1, "itemName", "item description", true, user);
        itemService.addItem(item);
        Booking booking = new Booking(LocalDateTime.now(), LocalDateTime.now(), booker, item, Status.APPROVED);
        assertThat(bookingService.createBooking(booking),equalTo(booking));
    }

    @Test
    @Order(2)
    void approval() {
        User user = new User(3, "Name", "a@mail.ru");
        userService.addUser(user);
        User booker = new User(4, "Name", "b@mail.ru");
        userService.addUser(booker);
        Item item = new Item(2, "itemName", "item description", true, user);
        itemService.addItem(item);
        Booking booking = new Booking(LocalDateTime.now(), LocalDateTime.now(), booker, item, Status.APPROVED);
        bookingService.createBooking(booking);

    }

    @Test
    @Order(3)
    void getBooking() {
        User user = new User(5, "Name", "a@mail.ru");
        userService.addUser(user);
        User booker = new User(6, "Name", "b@mail.ru");
        userService.addUser(booker);
        Item item = new Item(3, "itemName", "item description", true, user);
        itemService.addItem(item);
        Booking booking = new Booking(LocalDateTime.now(), LocalDateTime.now(), booker, item, Status.APPROVED);
        bookingService.createBooking(booking);
        assertThat(bookingService.getBooking(3),equalTo(booking));
    }

    @Test
    @Order(4)
    void showAllUserBookings() {
        User user = new User(7, "Name", "a@mail.ru");
        userService.addUser(user);
        User booker = new User(8, "Name", "b@mail.ru");
        userService.addUser(booker);
        Item item = new Item(4, "itemName", "item description", true, user);
        itemService.addItem(item);
        Booking booking = new Booking(LocalDateTime.now(), LocalDateTime.now(), booker, item, Status.APPROVED);
        bookingService.createBooking(booking);
        assertThat(bookingService.showAllUserBookings(8,State.ALL).get(0),equalTo(booking));
    }

    @Test
    @Order(5)
    void showOwnerBookings() {
        User user = new User(9, "Name", "a@mail.ru");
        userService.addUser(user);
        User booker = new User(10, "Name", "b@mail.ru");
        userService.addUser(booker);
        Item item = new Item(5, "itemName", "item description", true, user);
        itemService.addItem(item);
        Booking booking = new Booking(LocalDateTime.now(), LocalDateTime.now(), booker, item, Status.APPROVED);
        bookingService.createBooking(booking);
        assertThat(bookingService.showOwnerBookings(9,State.ALL).get(0),equalTo(booking));
    }

    @Test
    @Order(6)
    void showAll() {
        User user = new User(11, "Name", "a@mail.ru");
        userService.addUser(user);
        User booker = new User(12, "Name", "b@mail.ru");
        userService.addUser(booker);
        Item item = new Item(6, "itemName", "item description", true, user);
        itemService.addItem(item);
        Booking booking = new Booking(LocalDateTime.now(), LocalDateTime.now(), booker, item, Status.APPROVED);
        bookingService.createBooking(booking);
        assertThat(bookingService.showAll(State.ALL).get(0),equalTo(booking));
    }

    @Test
    @Order(7)
    void ShowAllPageable() {
        User user = new User(13, "Name", "a@mail.ru");
        userService.addUser(user);
        User booker = new User(14, "Name", "b@mail.ru");
        userService.addUser(booker);
        Item item = new Item(7, "itemName", "item description", true, user);
        itemService.addItem(item);
        Booking booking = new Booking(LocalDateTime.now(), LocalDateTime.now(), booker, item, Status.APPROVED);
        bookingService.createBooking(booking);
        assertThat(bookingService.showAll(State.ALL,0,1).get(0),equalTo(booking));
    }

    @Test
    @Order(8)
    void isBookingExist() {
        User user = new User(15, "Name", "a@mail.ru");
        userService.addUser(user);
        User booker = new User(16, "Name", "b@mail.ru");
        userService.addUser(booker);
        Item item = new Item(8, "itemName", "item description", true, user);
        itemService.addItem(item);
        Booking booking = new Booking(LocalDateTime.now(), LocalDateTime.now(), booker, item, Status.APPROVED);
        bookingService.createBooking(booking);
        assertThat(bookingService.isBookingExist(7),equalTo(false));
        assertThat(bookingService.isBookingExist(8),equalTo(true));
    }

    @Test
    @Order(9)
    void getOwnerBookings() {
        User user = new User(17, "Name", "a@mail.ru");
        userService.addUser(user);
        User booker = new User(18, "Name", "b@mail.ru");
        userService.addUser(booker);
        Item item = new Item(9, "itemName", "item description", true, user);
        itemService.addItem(item);
        Booking booking = new Booking(LocalDateTime.now(), LocalDateTime.now(), booker, item, Status.APPROVED);
        bookingService.createBooking(booking);
        assertThat(bookingService.getOwnerBookings(0,1,17).get(0),equalTo(booking));
    }
}