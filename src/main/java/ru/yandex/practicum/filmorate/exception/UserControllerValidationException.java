package ru.yandex.practicum.filmorate.exception;

public class UserControllerValidationException extends RuntimeException {
    public UserControllerValidationException(String message) {
        super(message);
    }
}
