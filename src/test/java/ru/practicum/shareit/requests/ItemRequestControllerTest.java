package ru.practicum.shareit.requests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.practicum.shareit.booking.*;
import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.item.*;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.UserDto;
import ru.practicum.shareit.user.UserService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ItemRequestControllerTest {
    @Mock
    private RequestService requestService;
    @Mock
    private UserService userService;
    @Mock
    private BookingMapper bookingMapper;
    @Mock
    private UserController userController;
    @InjectMocks
    private BookingController bookingController;

    private final ObjectMapper mapper = new ObjectMapper();

    private MockMvc mvc;

    private ItemRequest request;
    private User user;
    private Item item;
    private User booker;

    @BeforeEach
    void setUp() {
        mapper.registerModule(new JavaTimeModule());
        mvc = MockMvcBuilders.standaloneSetup(bookingController).build();
        user = new User(1, "Name", "qwerty@mail.ru");
        item = new Item(1, "itemName", "item description", true, user);
        booker = new User(2, "Name", "qwerty@mail.ru");
        //Booking booking = new Booking(LocalDateTime.now(), LocalDateTime.now(), user, item, Status.APPROVED);
        request = new ItemRequest(1,"description",booker,LocalDateTime.now());
    }

    @Test
    void addRequest() throws Exception {
        when(requestService.addRequest(anyLong(), any())).thenReturn(request);
        when(userService.getUser(anyLong())).thenReturn(Optional.of(user));
        try (MockedStatic<UserController> utilities = Mockito.mockStatic(UserController.class)) {
            utilities.when(UserController::getUserService).thenReturn(userService);
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Sharer-User-Id", "1");
            mvc.perform(post("/requests")
                    .headers(headers)
                    .content(mapper.writeValueAsString(request))
                    .characterEncoding(StandardCharsets.UTF_8)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.itemId", is(1)))
                    .andExpect(jsonPath("$.bookerId", is(0)));
        }
    }

    @Test
    void getUserRequests() {
    }

    @Test
    void showRequests() {
    }

    @Test
    void getRequest() {
    }
}