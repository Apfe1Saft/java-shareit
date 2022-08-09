package ru.practicum.shareit.user;

import lombok.Data;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.Item;
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
    private long id;
    @Column(name = "user_name", nullable = false, length = 255)
    private String name;
    @Column(name = "user_email", nullable = false, length = 512)
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    private List<Item> items;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "booker")
    private List<Booking> bookings;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "requestor")
    private List<ItemRequest> requests;

    public User() {
    }

}
