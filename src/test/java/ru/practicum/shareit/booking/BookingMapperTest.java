package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringJUnitConfig({PersistenceConfig.class, UserServiceImpl.class,
        ItemServiceImpl.class, UserController.class, ItemController.class})
class BookingMapperTest {
    private final UserService userService;
    private final ItemService itemService;

    @Test
    void toBookingDto() {
        User user = new User(2, "Name", "qwerty@mail.ru");
        userService.addUser(user);
        Item item = new Item(1, "itemName", "item description", true, user);
        itemService.addItem(item);
        Booking booking = new Booking(LocalDateTime.now(), LocalDateTime.now(), user, item, Status.APPROVED);
        assertThat(BookingMapper.toBookingDto(booking).getId(),
                equalTo(0L));
        assertThat(BookingMapper.toBookingDto(booking).getBookerId(),
                equalTo(0L));
        assertThat(BookingMapper.toBookingDto(booking).getItemId(),
                equalTo(1L));
    }

}