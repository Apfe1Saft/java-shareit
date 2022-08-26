package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.practicum.shareit.config.PersistenceConfig;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.UserServiceImpl;

@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringJUnitConfig({PersistenceConfig.class, UserServiceImpl.class,
        ItemServiceImpl.class})
class ItemRepositoryTest {
    private final UserService userService;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private ItemRepository repository;

    @Test
    public void contextLoads() {
        Assertions.assertNotNull(em);
    }

    @Test
    @Order(1)
    void searchWithParams() {
        User user = new User(1, "Name", "qwerty@mail.ru");
        userService.addUser(user);
        Item item = new Item(1, "itemName", "item description", true, user);
        //Pageable uPage = PageRequest.of(0, 1);
        Assertions.assertEquals(repository.searchWithParams("item").toString(), "[]");
        repository.save(item);
        Assertions.assertNotNull(repository.searchWithParams("item"));
    }

    @Test
    @Order(2)
    void searchWithParamsPageable() {
        User user = new User(2, "Name", "qwerty@mail.ru");
        userService.addUser(user);
        Item item = new Item(2, "itemName", "item description", true, user);
        Pageable uPage = PageRequest.of(0, 1);
        Assertions.assertEquals(repository.searchWithParams("item", uPage).toString(), "[]");
        repository.save(item);
        Assertions.assertNotNull(repository.searchWithParams("item", uPage));
    }
}