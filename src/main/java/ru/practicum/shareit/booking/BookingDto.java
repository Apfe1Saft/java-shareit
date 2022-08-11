package ru.practicum.shareit.booking;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    private LocalDate start;
    private LocalDate end;

    public BookingDto(long id,long itemId, LocalDate start, LocalDate end) {
        this.id = id;
        this.itemId = itemId;
        this.start =start;
        this.end = end;
    }
}
