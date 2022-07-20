package ru.practicum.shareit.exception;

public class EmailException extends RuntimeException {
    public EmailException(final String message) {
        super(message);
    }
}
