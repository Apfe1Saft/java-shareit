package ru.practicum.shareit.booking;

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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BookingControllerTest {
    @Mock
    private BookingService bookingService;
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

    private Booking booking;
    private BookingDto bookingDto;

    @BeforeEach
    void setUp() {
        mapper.registerModule(new JavaTimeModule());
        mvc = MockMvcBuilders.standaloneSetup(bookingController).build();
        User user = new User(1, "Name", "qwerty@mail.ru");
        Item item = new Item(1, "itemName", "item description", true, user);
        booking = new Booking(LocalDateTime.now(), LocalDateTime.now(), user, item, Status.APPROVED);
        bookingDto = new BookingDto(1, 1, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void create() throws Exception {
        when(bookingService.createBooking(booking)).thenReturn(booking);
        try (MockedStatic<BookingMapper> utilities = Mockito.mockStatic(BookingMapper.class)) {
            utilities.when(() -> BookingMapper.toBookingDto(any())).thenReturn(bookingDto);
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Sharer-User-Id", "1");
            mvc.perform(post("/bookings")
                    .headers(headers)
                    .content(mapper.writeValueAsString(bookingDto))
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
    void approval() throws Exception {
        doNothing().when(bookingService).approval(anyLong(), anyLong(), anyBoolean());
        when(bookingService.getBooking(anyLong())).thenReturn(booking);
        try (MockedStatic<BookingMapper> utilities = Mockito.mockStatic(BookingMapper.class)) {
            utilities.when(() -> BookingMapper.toBookingDto(any())).thenReturn(bookingDto);
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Sharer-User-Id", "1");
            mvc.perform(patch("/bookings/1?approved=true")
                    .headers(headers)
                    .content(mapper.writeValueAsString(bookingDto))
                    .characterEncoding(StandardCharsets.UTF_8)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(0)))
                    .andExpect(jsonPath("$.item.id", is(1)))
                    .andExpect(jsonPath("$.booker.id", is(1)));
        }
    }

    @Test
    void getBooking() throws Exception {
        when(bookingService.getBooking(anyLong())).thenReturn(booking);
        when(bookingService.isBookingExist(anyLong())).thenReturn(true);
        when(userService.getUser(anyLong())).thenReturn(Optional.of(booking.getBooker()));
        try (MockedStatic<UserController> utilities = Mockito.mockStatic(UserController.class)) {
            utilities.when(UserController::getUserService).thenReturn(userService);
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Sharer-User-Id", "1");
            mvc.perform(get("/bookings/1")
                    .headers(headers))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(0)))
                    .andExpect(jsonPath("$.item.id", is(1)))
                    .andExpect(jsonPath("$.booker.id", is(1)));
        }
    }

    @Test
    void getOwnerBookings() throws Exception {
        List<Booking> bookingList = new LinkedList<>();
        bookingList.add(booking);
        when(bookingService.showOwnerBookings(anyLong(), any())).thenReturn(bookingList);
        when(bookingService.isBookingExist(anyLong())).thenReturn(true);
        when(userService.getUser(anyLong())).thenReturn(Optional.of(booking.getBooker()));
        try (MockedStatic<UserController> utilities = Mockito.mockStatic(UserController.class)) {
            utilities.when(UserController::getUserService).thenReturn(userService);
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Sharer-User-Id", "1");
            mvc.perform(get("/bookings/owner")
                    .headers(headers)
                    .param("from", "0")
                    .param("size", "1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id", is(0)))
                    .andExpect(jsonPath("$[0].item.id", is(1)))
                    .andExpect(jsonPath("$[0].booker.id", is(1)));
        }
    }

    @Test
    void getOwnerBookingsPageable() throws Exception {
        List<Booking> bookingList = new LinkedList<>();
        bookingList.add(booking);
        when(bookingService.showOwnerBookings(anyLong(), any())).thenReturn(bookingList);
        when(bookingService.isBookingExist(anyLong())).thenReturn(true);
        when(userService.getUser(anyLong())).thenReturn(Optional.of(booking.getBooker()));
        try (MockedStatic<UserController> utilities = Mockito.mockStatic(UserController.class)) {
            utilities.when(UserController::getUserService).thenReturn(userService);
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Sharer-User-Id", "1");
            mvc.perform(get("/bookings/owner")
                    .headers(headers))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id", is(0)))
                    .andExpect(jsonPath("$[0].item.id", is(1)))
                    .andExpect(jsonPath("$[0].booker.id", is(1)));
        }
    }

    @Test
    void showAll() throws Exception {
        List<Booking> bookingList = new LinkedList<>();
        bookingList.add(booking);
        when(bookingService.showAll(any())).thenReturn(bookingList);
        when(userService.getUser(anyLong())).thenReturn(Optional.of(booking.getBooker()));
        try (MockedStatic<UserController> utilities = Mockito.mockStatic(UserController.class)) {
            utilities.when(UserController::getUserService).thenReturn(userService);
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Sharer-User-Id", "1");
            mvc.perform(get("/bookings")
                    .headers(headers)
                    .param("state", "ALL"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id", is(0)))
                    .andExpect(jsonPath("$[0].item.id", is(1)))
                    .andExpect(jsonPath("$[0].booker.id", is(1)));
        }
    }
}