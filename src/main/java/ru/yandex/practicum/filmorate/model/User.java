package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class User {
    @Setter
    private Integer id;

    @NotBlank
    @NotEmpty
    private final String login;

    @Setter
    private String name;

    @Email
    private final String email;

    private final LocalDate birthday;

}
