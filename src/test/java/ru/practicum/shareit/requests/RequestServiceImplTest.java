package ru.practicum.shareit.requests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.BookingServiceImpl;
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
        RequestServiceImpl.class
})
class RequestServiceImplTest {
    private final EntityManager em;
    private final UserService userService;
    private final ItemService itemService;
    private final BookingService bookingService;
    private final RequestService requestService;

    @Test
    @Order(1)
    void addRequest() {
        User user = new User(1, "Name", "a@mail.ru");
        userService.addUser(user);
        User booker = new User(2, "Name", "b@mail.ru");
        userService.addUser(booker);
        Item item = new Item(1, "itemName", "item description", true, user);
        itemService.addItem(item);
        //Booking booking = new Booking(LocalDateTime.now(), LocalDateTime.now(), booker, item, Status.APPROVED);
        ItemRequest itemRequest = new ItemRequest(1,"d",booker,LocalDateTime.now());
        assertThat(requestService.addRequest(1,itemRequest).toString(),
                equalTo(itemRequest.toString()));
    }

    @Test
    @Order(2)
    void getUserRequests() {
        User user = new User(3, "Name", "a@mail.ru");
        userService.addUser(user);
        User booker = new User(4, "Name", "b@mail.ru");
        userService.addUser(booker);
        Item item = new Item(2, "itemName", "item description", true, user);
        itemService.addItem(item);
        ItemRequest itemRequest = new ItemRequest(2,"d",booker,LocalDateTime.now());
        requestService.addRequest(3,itemRequest);
        assertThat(requestService.getUserRequests(4).get(0).toString(),
                equalTo(itemRequest.toString()));
    }

    @Test
    @Order(3)
    void showRequests() {
        User user = new User(5, "Name", "a@mail.ru");
        userService.addUser(user);
        User booker = new User(6, "Name", "b@mail.ru");
        userService.addUser(booker);
        Item item = new Item(3, "itemName", "item description", true, user);
        itemService.addItem(item);
        ItemRequest itemRequest = new ItemRequest(3,"d",booker,LocalDateTime.now());
        requestService.addRequest(5,itemRequest);
        assertThat(requestService.showRequests(6).get(0).toString(),
                equalTo(itemRequest.toString()));
    }

    @Test
    @Order(4)
    void ShowRequestsPageable() {
        User user = new User(7, "Name", "a@mail.ru");
        userService.addUser(user);
        User booker = new User(8, "Name", "b@mail.ru");
        userService.addUser(booker);
        Item item = new Item(4, "itemName", "item description", true, user);
        itemService.addItem(item);
        ItemRequest itemRequest = new ItemRequest(4,"d",booker,LocalDateTime.now());
        requestService.addRequest(7,itemRequest);
        assertThat(requestService.showRequests(0,1,7).get(0).toString(),
                equalTo(itemRequest.toString()));
    }

    @Test
    @Order(5)
    void getRequestById() {
        User user = new User(9, "Name", "a@mail.ru");
        userService.addUser(user);
        User booker = new User(10, "Name", "b@mail.ru");
        userService.addUser(booker);
        Item item = new Item(5, "itemName", "item description", true, user);
        itemService.addItem(item);
        ItemRequest itemRequest = new ItemRequest(5,"d",booker,LocalDateTime.now());
        requestService.addRequest(9,itemRequest);
        assertThat(requestService.getRequestById(5).toString(),
                equalTo(itemRequest.toString()));
    }

    @Test
    @Order(6)
    void getAllRequests() {
        User user = new User(11, "Name", "a@mail.ru");
        userService.addUser(user);
        User booker = new User(12, "Name", "b@mail.ru");
        userService.addUser(booker);
        Item item = new Item(6, "itemName", "item description", true, user);
        itemService.addItem(item);
        ItemRequest itemRequest = new ItemRequest(6,"d",booker,LocalDateTime.now());
        requestService.addRequest(11,itemRequest);
        assertThat(requestService.getAllRequests(11).get(0).toString(),
                equalTo(itemRequest.toString()));
    }
}