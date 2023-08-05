package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class Mpa {
    @Setter
    private Integer id;

    String name;
}
