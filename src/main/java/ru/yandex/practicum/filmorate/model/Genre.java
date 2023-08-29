package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@Builder
public class Genre {
    @Setter
    private Integer id;

    private final String name;
}
