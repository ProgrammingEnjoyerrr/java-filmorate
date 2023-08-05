package ru.yandex.practicum.filmorate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.filmorate.utils.validation.constraints.AfterDate;
import ru.yandex.practicum.filmorate.utils.validation.constraints.MaxByteLength;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    private final Set<Long> userLikes;

    private final Set<Genre> genres;

    public Film(Integer id, String name, String description, LocalDate releaseDate, Integer duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.userLikes = new HashSet<>();
        this.genres = new HashSet<>();
    }

    public boolean addUserLike(Long userId) {
        return userLikes.add(userId);
    }

    public void removeUserLike(Long userId) {
        userLikes.remove(userId);
    }
}
