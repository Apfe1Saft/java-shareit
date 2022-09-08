package ru.practicum.shareit.booking;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.client.BaseClient;

import java.util.Map;

@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${shareIt-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .build()
        );
    }

    public Object create(BookingDto booking, Integer userId) {
        return post("", userId, booking);
    }

    public Object approval(Integer bookingId, Boolean approved, Integer userId) {
        return patch("/" + bookingId + "?approved=" + approved, userId, approved);
    }

    public Object getBooking(Integer userId, Integer bookingId) {
        return get("/" + bookingId, userId);
    }

    public Object showAll(Integer userId, State status, String from, String size) {
        Map<String, Object> parameters = Map.of(
                "state", status,
                "from", from,
                "size", size
        );
        return get("" + "?state=" + status + "&from=" + from + "&size=" + size, userId, parameters);
    }

    public Object getOwnerBookings(Integer userId, State status, String from, String size) {
        Map<String, Object> parameters = Map.of(
                "state", status,
                "from", from,
                "size", size
        );
        return get("/owner?state=" + status + "&from=" + from + "&size=" + size, userId, parameters);
    }
}