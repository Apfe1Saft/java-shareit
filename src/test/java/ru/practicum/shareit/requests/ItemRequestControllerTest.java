package ru.practicum.shareit.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.UserService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private ItemRequestController itemRequestController;

    private final ObjectMapper mapper = new ObjectMapper();

    private MockMvc mvc;

    private ItemRequest request;
    private User user;
    private Item item;
    private User booker;

    @BeforeEach
    void setUp() {
        mapper.registerModule(new JavaTimeModule());
        mvc = MockMvcBuilders.standaloneSetup(itemRequestController).build();
        user = new User(1, "Name", "qwerty@mail.ru");
        item = new Item(1, "itemName", "item description", true, user);
        booker = new User(2, "Name", "qwerty@mail.ru");
        request = new ItemRequest(1, "description", booker, LocalDateTime.now());
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
                    .andExpect(jsonPath("$.description", is("description")));
        }
    }

    @Test
    void getUserRequests() throws Exception {
        List<ItemRequest> itemRequestList = new LinkedList<>();
        itemRequestList.add(request);
        when(requestService.getUserRequests(anyLong())).thenReturn(itemRequestList);
        when(userService.getUser(anyLong())).thenReturn(Optional.ofNullable(user));
        try (MockedStatic<UserController> utilities = Mockito.mockStatic(UserController.class)) {
            utilities.when(UserController::getUserService).thenReturn(userService);
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Sharer-User-Id", "1");
            mvc.perform(get("/requests")
                    .headers(headers)
                    .content(mapper.writeValueAsString(request))
                    .characterEncoding(StandardCharsets.UTF_8)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id", is(1)))
                    .andExpect(jsonPath("$[0].description", is("description")));
        }
    }

    @Test
    void showRequests() throws Exception {
        List<ItemRequest> itemRequestList = new LinkedList<>();
        itemRequestList.add(request);
        when(requestService.showRequests(anyLong())).thenReturn(itemRequestList);
        when(userService.getUser(anyLong())).thenReturn(Optional.ofNullable(user));
        try (MockedStatic<UserController> utilities = Mockito.mockStatic(UserController.class)) {
            utilities.when(UserController::getUserService).thenReturn(userService);
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Sharer-User-Id", "1");
            mvc.perform(get("/requests/all")
                    .headers(headers)
                    .content(mapper.writeValueAsString(request))
                    .characterEncoding(StandardCharsets.UTF_8)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id", is(1)))
                    .andExpect(jsonPath("$[0].description", is("description")));
        }
    }

    @Test
    void showRequestsPageable() throws Exception {
        List<ItemRequest> itemRequestList = new LinkedList<>();
        itemRequestList.add(request);
        when(requestService.showRequests(anyLong())).thenReturn(itemRequestList);
        when(userService.getUser(anyLong())).thenReturn(Optional.ofNullable(user));
        try (MockedStatic<UserController> utilities = Mockito.mockStatic(UserController.class)) {
            utilities.when(UserController::getUserService).thenReturn(userService);
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Sharer-User-Id", "1");
            mvc.perform(get("/requests/all")
                    .headers(headers)
                    .param("from", "0")
                    .param("size", "1")
                    .content(mapper.writeValueAsString(request))
                    .characterEncoding(StandardCharsets.UTF_8)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }

    @Test
    void getRequest() throws Exception {
        when(requestService.getRequestById(anyLong())).thenReturn(request);
        when(userService.getUser(anyLong())).thenReturn(Optional.ofNullable(user));
        try (MockedStatic<UserController> utilities = Mockito.mockStatic(UserController.class)) {
            utilities.when(UserController::getUserService).thenReturn(userService);
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Sharer-User-Id", "1");
            mvc.perform(get("/requests/1")
                    .headers(headers)
                    .content(mapper.writeValueAsString(request))
                    .characterEncoding(StandardCharsets.UTF_8)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.description", is("description")));
        }
    }
}