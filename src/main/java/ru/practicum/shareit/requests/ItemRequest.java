package ru.practicum.shareit.requests;

import lombok.Data;
import ru.practicum.shareit.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * // TODO .
 */
@Data
//@Entity
//@Table(name = "item_request")
public class ItemRequest {
    //@Id
    private int id;
    //@Column(name = "description")
    private String description;
    private User requestor;
    private LocalDate created;

}
