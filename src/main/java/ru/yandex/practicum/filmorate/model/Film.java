package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Film {
    @Setter
    private Integer id;
    private final String name;
    private final String description;
    private final LocalDate releaseDate;
    private final Integer duration;
}
