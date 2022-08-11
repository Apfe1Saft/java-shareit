package ru.practicum.shareit.comment;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CommentDto {
    private long id;
    private long itemId;
    private long userId;
    private String text;
    private LocalDate created;

    public CommentDto(long id, String text, LocalDate created,long itemId,long userId) {
        this.id = id;
        this.text = text;
        this.created = created;
    }
}
