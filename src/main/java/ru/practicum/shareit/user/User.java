package ru.practicum.shareit.user;

import lombok.Data;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.requests.ItemRequest;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * // TODO .
 */
@Data
@Entity
@Table(name = "users")
public class User {
    @Min(0)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;
    @Column(name = "user_name", nullable = false, length = 255)
    private String name;
    @Column(name = "user_email", nullable = false, length = 512)
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "items")
    private List<Item> items;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bookings")
    private List<Booking> bookings;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "requests")
    private List<ItemRequest> requests;

    public User() {
    }

}
