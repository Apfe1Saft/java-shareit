package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.practicum.shareit.booking.*;
import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.config.PersistenceConfig;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.UserServiceImpl;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringJUnitConfig({PersistenceConfig.class, UserServiceImpl.class, ItemServiceImpl.class
        , UserController.class, BookingServiceImpl.class, BookingController.class, ItemController.class})
class ItemServiceImplTest {
    private final EntityManager em;
    private final UserService userService;
    private final ItemService itemService;
    private final BookingService bookingService;
    //private final UserController controller;

    @Test
    @Order(1)
    void addItem() {
        User user = new User(1, "Name", "qwerty@mail.ru");
        userService.addUser(user);
        Item item = new Item(1, "itemName", "item description", true, user);
        assertThat(itemService.addItem(item), equalTo(item));
    }

    @Test
    @Order(2)
    void updateItem() {
        User user = new User(2, "Name", "qwerty@mail.ru");
        userService.addUser(user);
        Item item = new Item(2, "itemName", "item description", true, user);
        itemService.addItem(item);
        assertThat(itemService.updateItem(ItemMapper.toItemDto(item), item.getOwner().getId(), 2), equalTo(item));

    }

    @Test
    @Order(3)
    void getItemDtoById() {
        User user = new User(3, "Name", "qwerty@mail.ru");
        userService.addUser(user);
        Item item = new Item(3, "itemName", "item description", true, user);
        itemService.addItem(item);
        assertThat(itemService.getItemById(3), equalTo(item));
    }

    @Test
    @Order(4)
    void searchItems() {
        User user = new User(4, "Name", "qwerty@mail.ru");
        userService.addUser(user);
        Item item = new Item(4, "itemName", "item description", true, user);
        itemService.addItem(item);
        assertThat(itemService.searchItems("item").get(0).getId(), equalTo(item.getId()));
        userService.deleteUser(4);
        itemService.removeItem(4);
    }

    @Test
    @Order(5)
    void showPageable() {
        User user = new User(5, "Name", "qwerty@mail.ru");
        userService.addUser(user);
        Item item = new Item(5, "itemName", "item description", true, user);
        itemService.addItem(item);
        assertThat(itemService.show(5).toString(), equalTo("[ItemDto(id=5, name=itemName, description=item description, available=true, lastBooking=null, nextBooking=null, comments=[], requestId=0)]"));
        assertThat(itemService.show(5, 0, 2).getTotalElements(), equalTo(1L));
        Item item1 = new Item(6, "itemName", "item description", true, user);
        itemService.addItem(item1);
        assertThat(itemService.show(5, 0, 2).getTotalElements(), equalTo(2L));
    }

    @Test
    @Order(6)
    void show() {
        User user = new User(6, "Name", "qwerty@mail.ru");
        userService.addUser(user);
        Item item = new Item(7, "itemName", "item description", true, user);
        itemService.addItem(item);
        assertThat(itemService.show(6).toString(),
                equalTo("[ItemDto(id=7, name=itemName, description=item description, available=true, lastBooking=null, nextBooking=null, comments=[], requestId=0)]"));
    }

    @Test
    @Order(7)
    void searchItemsPageable() {
        User user = new User(7, "Name", "qwerty@mail.ru");
        userService.addUser(user);
        Item item = new Item(8, "itemName", "item description", true, user);
        itemService.addItem(item);
        assertThat(itemService.searchItems("item", 0, 2).get(0).getId(), equalTo(8L));

    }

    @Test
    @Order(8)
    void getItemById() {
        User user = new User(8, "Name", "qwerty@mail.ru");
        userService.addUser(user);
        Item item = new Item(9, "itemName", "item description", true, user);
        itemService.addItem(item);
        assertThat(itemService.getItemById(9), equalTo(item));
    }

    @Test
    @Order(9)
    void addComment() throws InterruptedException {
        User user = new User(9, "Name", "a@mail.ru");
        userService.addUser(user);
        Item item = new Item(10, "itemName", "item description", true, user);
        itemService.addItem(item);
        User userOne = new User(10, "Name", "b@mail.ru");
        userService.addUser(userOne);
        CommentDto comment = new CommentDto(1, "comment", LocalDate.now(), 10, "Mark");
        bookingService.createBooking(new Booking(LocalDateTime.now(), LocalDateTime.now(), userOne, item, Status.APPROVED));
        Thread.sleep(500);
        assertThat(itemService.addComment(comment, 10, 10).getId(),
                equalTo(1L));
    }

    @Test
    @Order(10)
    void getComment() throws InterruptedException {
        User user = new User(11, "Name", "a@mail.ru");
        userService.addUser(user);
        Item item = new Item(11, "itemName", "item description", true, user);
        itemService.addItem(item);
        User userOne = new User(12, "Name", "b@mail.ru");
        userService.addUser(userOne);
        CommentDto comment = new CommentDto(2, "comment", LocalDate.now(), 11, "Mark");
        bookingService.createBooking(new Booking(LocalDateTime.now(), LocalDateTime.now(), userOne, item, Status.APPROVED));
        Thread.sleep(500);
        itemService.addComment(comment, 11, 12);
        assertThat(itemService.getComment(11).getItem().getId(), equalTo(11L));
    }

}