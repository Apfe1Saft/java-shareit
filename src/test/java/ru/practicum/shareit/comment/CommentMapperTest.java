package ru.practicum.shareit.comment;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
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
import java.time.LocalDate;


@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringJUnitConfig({PersistenceConfig.class, UserServiceImpl.class,
        ItemServiceImpl.class, UserController.class, ItemController.class})
class CommentMapperTest {
    private final UserService userService;
    private final ItemService itemService;

    @Test
    @Order(1)
    void toComment() {
        User user = new User(1, "Name", "qwerty@mail.ru");
        userService.addUser(user);
        Item item = new Item(1, "itemName", "item description", true, user);
        itemService.addItem(item);
        CommentDto commentDto = new CommentDto(1, "text", LocalDate.now(), 1, "Karl");
        Assertions.assertEquals(CommentMapper.toComment(commentDto, 1, 1).getId(),
                1);
    }

    @Test
    @Order(2)
    void toCommentDto() {
        User user = new User(2, "Name", "qwerty@mail.ru");
        userService.addUser(user);
        Item item = new Item(2, "itemName", "item description", true, user);
        itemService.addItem(item);
        Comment comment = new Comment(2, "text", item, user, LocalDate.now());
        Assertions.assertEquals(CommentMapper.toCommentDto(comment).getId(),
                2);
    }
}