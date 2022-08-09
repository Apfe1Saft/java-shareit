package ru.practicum.shareit.requests;

import lombok.Data;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * // TODO .
 */
@Data
@Entity
@Table(name = "requests")
public class ItemRequest {
    @Id
    @Column(name = "request_id")
    private long id;
    @Column(name = "request_description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User requestor;

    @Column(name = "request_created")
    private LocalDate created;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "request")
    private List<Booking> bookings;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "request")
    private List<Item> items;

}
