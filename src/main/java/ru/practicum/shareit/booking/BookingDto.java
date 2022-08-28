package ru.practicum.shareit.booking;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * // TODO .
 */
@Data
@Getter
@Setter
@NoArgsConstructor
public class BookingDto {
    private long id;
    private long itemId;
    private long bookerId;
    private LocalDateTime start;
    private LocalDateTime end;
    private Status status;

    public BookingDto(long id, long itemId, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.itemId = itemId;
        this.start = start;
        this.end = end;
    }
    public BookingDto(long id, long itemId, LocalDateTime start, LocalDateTime end,Status status) {
        this.id = id;
        this.itemId = itemId;
        this.start = start;
        this.end = end;
        this.status = status;
    }

    public BookingDto(long id, long itemId, long bookerId, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.itemId = itemId;
        this.start = start;
        this.end = end;
        this.bookerId = bookerId;
    }
    public BookingDto(long id, long itemId, long bookerId, LocalDateTime start, LocalDateTime end,Status status) {
        this.id = id;
        this.itemId = itemId;
        this.start = start;
        this.end = end;
        this.bookerId = bookerId;
        this.status = status;
    }
}
