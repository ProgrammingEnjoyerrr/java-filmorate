package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.utils.validation.constraints.AfterDate;
import ru.yandex.practicum.filmorate.utils.validation.constraints.MaxByteLength;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Film {
    @Setter
    private Integer id;

    @NotBlank
    private final String name;

    @MaxByteLength(200)
    private final String description;

    @AfterDate("1895-12-28")
    private final LocalDate releaseDate;

    @Positive
    private final Integer duration;
}
