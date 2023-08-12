package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FilmDbStorageTest {

    private final FilmDbStorage filmDbStorage;

    private static final int ANY_ID = 0;

    @Test
    void createFilm() {
        Film film = Film.builder()
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(LocalDate.of(1967, 3, 25))
                .duration(100)
                .build();
        film.setMpa(new Mpa(1, null));
        film.setGenres(List.of(new Genre(6, null)));

        Film created = filmDbStorage.createFilm(film);

        assertEquals(1, created.getId());
        assertEquals(film.getName(), created.getName());
        assertEquals(film.getDescription(), created.getDescription());
        assertEquals(film.getReleaseDate(), created.getReleaseDate());
        assertEquals(film.getDuration(), created.getDuration());
        assertEquals(new Mpa(1, "G"), created.getMpa());
        assertEquals(List.of(new Genre(6, "Боевик")), created.getGenres());
        assertEquals(film.getGenres(), created.getGenres());
        assertEquals(film.getUserLikes(), created.getUserLikes());

        film = Film.builder()
                .id(film.getId())
                .name("Film Updated")
                .description("New film update decription")
                .releaseDate(LocalDate.of(1989, 4, 17))
                .duration(190)
                .build();
        film.setMpa(new Mpa(5, null));
        film.setGenres(List.of(new Genre(2, null)));

        Film updated = filmDbStorage.updateFilm(film);
        assertEquals(1, updated.getId());
        assertEquals(film.getName(), updated.getName());
        assertEquals(film.getDescription(), updated.getDescription());
        assertEquals(film.getReleaseDate(), updated.getReleaseDate());
        assertEquals(film.getDuration(), updated.getDuration());
        assertEquals(new Mpa(5, "NC-17"), updated.getMpa());
        assertEquals(List.of(new Genre(2, "Драма")), updated.getGenres());
        assertEquals(film.getUserLikes(), updated.getUserLikes());
    }

    @Test
    void postManScenario() {
        Film film = Film.builder()
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(LocalDate.of(1967, 3, 25))
                .duration(100)
                .build();
        film.setMpa(new Mpa(1, null));

        Film created = filmDbStorage.createFilm(film);

        assertEquals(1, created.getId());
        assertEquals(film.getName(), created.getName());
        assertEquals(film.getDescription(), created.getDescription());
        assertEquals(film.getReleaseDate(), created.getReleaseDate());
        assertEquals(film.getDuration(), created.getDuration());
        assertEquals(new Mpa(1, "G"), created.getMpa());
        //assertEquals(List.of(new Genre(6, "Боевик")), created.getGenres());
        assertEquals(film.getGenres(), created.getGenres());
        assertEquals(film.getGenres(), created.getGenres());
        assertEquals(film.getUserLikes(), created.getUserLikes());

        film = Film.builder()
                .id(film.getId())
                .name("Film Updated")
                .description("New film update decription")
                .releaseDate(LocalDate.of(1989, 4, 17))
                .duration(190)
                .build();
        film.setMpa(new Mpa(4, null));
        //film.setGenres(List.of(new Genre(2, null)));

        Film updated = filmDbStorage.updateFilm(film);
        assertEquals(1, updated.getId());
        assertEquals(film.getName(), updated.getName());
        assertEquals(film.getDescription(), updated.getDescription());
        assertEquals(film.getReleaseDate(), updated.getReleaseDate());
        assertEquals(film.getDuration(), updated.getDuration());
        assertEquals(new Mpa(4, "R"), updated.getMpa());
        //assertEquals(List.of(new Genre(2, "Драма")), updated.getGenres());
        assertEquals(film.getGenres(), updated.getGenres());
        assertEquals(film.getUserLikes(), updated.getUserLikes());

        film = Film.builder()
                .id(film.getId())
                .name("Film Updated")
                .description("New film update decription")
                .releaseDate(LocalDate.of(1989, 4, 17))
                .duration(190)
                .build();
        film.setMpa(new Mpa(5, null));
        film.setGenres(List.of(new Genre(2, null)));

        updated = filmDbStorage.updateFilm(film);
        assertEquals(1, updated.getId());
        assertEquals(film.getName(), updated.getName());
        assertEquals(film.getDescription(), updated.getDescription());
        assertEquals(film.getReleaseDate(), updated.getReleaseDate());
        assertEquals(film.getDuration(), updated.getDuration());
        assertEquals(new Mpa(5, "NC-17"), updated.getMpa());
        assertEquals(List.of(new Genre(2, "Драма")), updated.getGenres());
        //assertEquals(film.getGenres(), updated.getGenres());
        assertEquals(film.getUserLikes(), updated.getUserLikes());

        // Film id=1 update remove genre
        film = Film.builder()
                .id(film.getId())
                .name("Film Updated")
                .releaseDate(LocalDate.of(1989, 4, 17))
                .description("New film update decription")
                .duration(190)
                .build();
        film.setMpa(new Mpa(5, null));
        film.setGenres(new ArrayList<>());

        updated = filmDbStorage.updateFilm(film);
        assertEquals(1, updated.getId());
        assertEquals(film.getName(), updated.getName());
        assertEquals(film.getDescription(), updated.getDescription());
        assertEquals(film.getReleaseDate(), updated.getReleaseDate());
        assertEquals(film.getDuration(), updated.getDuration());
        assertEquals(new Mpa(5, "NC-17"), updated.getMpa());
        //assertEquals(List.of(new Genre(2, "Драма")), updated.getGenres());
        assertEquals(film.getGenres(), updated.getGenres());
        assertEquals(film.getUserLikes(), updated.getUserLikes());
        // end of "Film id=1 update remove genre"
    }

    @Test
    void updateFilm() {
    }

    @Test
    void getFilms() {
    }

    @Test
    void getFilmById() {
    }

    @Test
    void addUserLike() {
    }

    @Test
    void deleteUserLike() {
    }

    @Test
    void getPopularFilms() {
    }
}