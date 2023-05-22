package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class User {
    @Setter
    private Integer id;
    private final String login;
    private final String name;
    private final String email;
    private final LocalDate birthday;

}
