package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.utils.validation.constraints.NoSpaces;
import ru.yandex.practicum.filmorate.utils.validation.constraints.NotInFuture;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
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
}
