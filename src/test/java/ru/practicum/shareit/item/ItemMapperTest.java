package ru.practicum.shareit.item;

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
import ru.practicum.shareit.requests.ItemRequest;
import ru.practicum.shareit.requests.RequestService;
import ru.practicum.shareit.requests.RequestServiceImpl;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.UserServiceImpl;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringJUnitConfig({PersistenceConfig.class, UserServiceImpl.class, ItemServiceImpl.class, BookingServiceImpl.class, RequestServiceImpl.class,
        UserController.class,//  BookingController.class,
        //       ItemController.class,  ItemRequestController.class
})
class ItemMapperTest {
    private final UserService userService;
    private final ItemService itemService;
    private final BookingService bookingService;
    private final RequestService requestService;

    @Test
    @Order(1)
    void toItemDto() {
        User user = new User(1, "Name", "qwerty@mail.ru");
        Item item = new Item(1, "itemName", "item description", true, user);
        assertThat(ItemMapper.toItemDto(item).toString(), equalTo("ItemDto(id=1, name=itemName, description=item description, available=true, lastBooking=null, nextBooking=null, comments=[], requestId=0)"));
        ItemRequest itemRequest = new ItemRequest(1, "descr",
                new User(2, "Name", "qwerty@mail.ru"), LocalDateTime.now());
        Item item1 = new Item(2, "itemName", "item description", true, user, itemRequest);
        assertThat(ItemMapper.toItemDto(item1).toString(), equalTo("ItemDto(id=2, name=itemName, description=item description, available=true, lastBooking=null, nextBooking=null, comments=[], requestId=1)"));

    }

    @Test
    @Order(2)
    void toItem() {
        User user = new User(1, "Name", "qwerty@mail.ru");
        userService.addUser(user);
        ItemRequest request = new ItemRequest(1, "descr",
                new User(1, "Name", "qwerty@mail.ru"), LocalDateTime.now());
        requestService.addRequest(1, request);
        ItemDto itemDto = new ItemDto(1, "item", "descr", true, 1);
        assertThat(ItemMapper.toItem(itemDto, 1).getId(), equalTo(1L));
        assertThat(ItemMapper.toItem(itemDto, 1).getName(), equalTo("item"));
        assertThat(ItemMapper.toItem(itemDto, 1).getDescription(), equalTo("descr"));
    }
}