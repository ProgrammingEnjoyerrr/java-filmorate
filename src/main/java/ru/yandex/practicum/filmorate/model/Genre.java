package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class Genre {
    @Setter
    private Integer id;

    String name;
}
