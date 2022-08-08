package ru.practicum.shareit.booking;

import lombok.Data;
import lombok.NonNull;
import ru.practicum.shareit.requests.ItemRequest;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * // TODO .
 */
@Data
public class Booking {
    @Column(name = "booking_id")
    private int id;
    @Column(name = "booking_start")
    private LocalDate start;
    @Column(name = "booking_end")
    private LocalDate end;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private ItemRequest request;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User booker;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

}
