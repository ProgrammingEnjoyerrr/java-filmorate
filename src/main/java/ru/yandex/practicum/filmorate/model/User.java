package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import ru.yandex.practicum.filmorate.utils.validation.constraints.NoSpaces;
import ru.yandex.practicum.filmorate.utils.validation.constraints.NotInFuture;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class User {
    @Setter
    private Integer id;

    @NotEmpty
    @NoSpaces
    private final String login;

    @Setter
    private String name;

    @Email
    private final String email;

    @NotInFuture
    private final LocalDate birthday;

    public User(String login, String name, String email, LocalDate birthday) {
        this.login = login;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
    }
}
