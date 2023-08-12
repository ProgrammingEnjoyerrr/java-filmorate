package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FilmDbStorageTest {

    private final FilmDbStorage filmDbStorage;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final UserDbStorage userStorage;

    private static final int ANY_ID = 0;

    @Test
    @Order(1)
    void createFilm() {
        Film film = Film.builder()
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(LocalDate.of(1967, 3, 25))
                .duration(100)
                .build();
        film.setMpa(new Mpa(1, null));
        film.setGenres(List.of(new Genre(6, null)));

        Film created1 = filmDbStorage.createFilm(film);

        assertEquals(1, created1.getId());
        assertEquals(film.getName(), created1.getName());
        assertEquals(film.getDescription(), created1.getDescription());
        assertEquals(film.getReleaseDate(), created1.getReleaseDate());
        assertEquals(film.getDuration(), created1.getDuration());
        assertEquals(new Mpa(1, "G"), created1.getMpa());
        assertEquals(List.of(new Genre(6, "Боевик")), created1.getGenres());
        assertEquals(film.getGenres(), created1.getGenres());
        assertEquals(film.getUserLikes(), created1.getUserLikes());

        Film film2 = Film.builder()
                .name("New film")
                .releaseDate(LocalDate.of(1999, 4, 30))
                .description("New film about friends")
                .duration(120)
                .build();
        film2.setMpa(new Mpa(3, null));
        film2.setGenres(List.of(new Genre(1, null)));

        Film created2 = filmDbStorage.createFilm(film2);

        assertEquals(2, created2.getId());
        assertEquals(film2.getName(), created2.getName());
        assertEquals(film2.getDescription(), created2.getDescription());
        assertEquals(film2.getReleaseDate(), created2.getReleaseDate());
        assertEquals(film2.getDuration(), created2.getDuration());
        assertEquals(new Mpa(3, "PG-13"), created2.getMpa());
        assertEquals(List.of(new Genre(1, "Комедия")), created2.getGenres());
        assertEquals(film2.getUserLikes(), created2.getUserLikes());
    }

    @Test
    @Order(2)
    void updateFilm() {
        {
            // update film without genre
            Film film = Film.builder()
                    .id(1)
                    .name("Film Updated")
                    .description("New film update decription")
                    .releaseDate(LocalDate.of(1989, 4, 17))
                    .duration(190)
                    .build();
            film.setMpa(new Mpa(5, null));
            film.setGenres(new ArrayList<>());

            Film updated = filmDbStorage.updateFilm(film);
            assertEquals(1, updated.getId());
            assertEquals(film.getName(), updated.getName());
            assertEquals(film.getDescription(), updated.getDescription());
            assertEquals(film.getReleaseDate(), updated.getReleaseDate());
            assertEquals(film.getDuration(), updated.getDuration());
            assertEquals(new Mpa(5, "NC-17"), updated.getMpa());
            assertEquals(film.getGenres(), updated.getGenres());
            assertEquals(film.getUserLikes(), updated.getUserLikes());
        }

        {
            // update film with genre
            Film film = Film.builder()
                    .id(1)
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
            assertEquals(film.getGenres(), updated.getGenres());
            assertEquals(film.getUserLikes(), updated.getUserLikes());
        }
    }

    @Test
    @Order(3)
    void getFilms() {
        List<Film> films = new ArrayList<>(filmDbStorage.getFilms());
        assertNotNull(films);
        assertEquals(2, films.size());

        Film expected1 = Film.builder()
                .id(1)
                .name("Film Updated")
                .description("New film update decription")
                .releaseDate(LocalDate.of(1989, 4, 17))
                .duration(190)
                .build();
        expected1.setMpa(new Mpa(5, null));
        expected1.setGenres(List.of(new Genre(2, null)));

        Film resulted1 = films.get(0);
        assertEquals(1, resulted1.getId());
        assertEquals(expected1.getName(), resulted1.getName());
        assertEquals(expected1.getDescription(), resulted1.getDescription());
        assertEquals(expected1.getReleaseDate(), resulted1.getReleaseDate());
        assertEquals(expected1.getDuration(), resulted1.getDuration());
        assertEquals(new Mpa(5, "NC-17"), resulted1.getMpa());
        assertEquals(List.of(new Genre(2, "Драма")), resulted1.getGenres());
        assertEquals(expected1.getUserLikes(), resulted1.getUserLikes());

        Film expected2 = Film.builder()
                .name("New film")
                .releaseDate(LocalDate.of(1999, 4, 30))
                .description("New film about friends")
                .duration(120)
                .build();
        expected2.setMpa(new Mpa(3, null));
        expected2.setGenres(List.of(new Genre(1, null)));

        Film resulted2 = films.get(1);
        assertEquals(2, resulted2.getId());
        assertEquals(expected2.getName(), resulted2.getName());
        assertEquals(expected2.getDescription(), resulted2.getDescription());
        assertEquals(expected2.getReleaseDate(), resulted2.getReleaseDate());
        assertEquals(expected2.getDuration(), resulted2.getDuration());
        assertEquals(new Mpa(3, "PG-13"), resulted2.getMpa());
        assertEquals(List.of(new Genre(1, "Комедия")), resulted2.getGenres());
        assertEquals(expected2.getUserLikes(), resulted2.getUserLikes());
    }

    @Test
    @Order(4)
    void getFilmById() {
        Film expected = Film.builder()
                .id(2)
                .name("New film")
                .releaseDate(LocalDate.of(1999, 4, 30))
                .description("New film about friends")
                .duration(120)
                .build();
        expected.setMpa(new Mpa(3, "PG-13"));
        expected.setGenres(List.of(new Genre(1, "Комедия")));

        Film resulted = filmDbStorage.getFilmById(2);
        assertEquals(expected.getId(), resulted.getId());
        assertEquals(expected.getName(), resulted.getName());
        assertEquals(expected.getDescription(), resulted.getDescription());
        assertEquals(expected.getReleaseDate(), resulted.getReleaseDate());
        assertEquals(expected.getDuration(), resulted.getDuration());
        assertEquals(expected.getMpa(), resulted.getMpa());
        assertEquals(expected.getGenres(), resulted.getGenres());
        assertEquals(expected.getUserLikes(), resulted.getUserLikes());
    }

    @Test
    @Order(5)
    void getFilmByUnknownId() {
        FilmNotFoundException exception = assertThrows(FilmNotFoundException.class,
                () -> filmDbStorage.getFilmById(100500));
        assertEquals("Фильм с id 100500 не найден.", exception.getMessage());
    }

    @Test
    @Order(6)
    void addUserLike() {
        {
            final User user = new User(ANY_ID, "dolore ullamco", "some name", "yandex@mail.ru",
                    LocalDate.parse("1946-08-20", FORMATTER));
            final User created = userStorage.createUser(user);
            assertEquals(1, created.getId());
            filmDbStorage.addUserLike(2, created.getId());
        }

        {
            final User user = new User(ANY_ID, "dolore ullamco", "some name", "yandex@mail.ru",
                    LocalDate.parse("1946-08-20", FORMATTER));
            final User created = userStorage.createUser(user);
            assertEquals(2, created.getId());
            filmDbStorage.addUserLike(1, created.getId());
            filmDbStorage.addUserLike(2, created.getId());
        }

        List<Film> mostPopular = new ArrayList<>(filmDbStorage.getPopularFilms(1));
        assertNotNull(mostPopular);
        assertEquals(1, mostPopular.size());

        Film got = mostPopular.get(0);
        assertEquals(2, got.getId());
    }

    @Test
    @Order(7)
    void getPopularFilms() {
        List<Film> mostPopular = new ArrayList<>(filmDbStorage.getPopularFilms(10));
        assertNotNull(mostPopular);
        assertEquals(2, mostPopular.size());

        Film film1 = mostPopular.get(0);
        assertEquals(2, film1.getId());
        assertEquals(Set.of(1L, 2L), film1.getUserLikes());

        Film film2 = mostPopular.get(1);
        assertEquals(1, film2.getId());
        assertEquals(Set.of(2L), film2.getUserLikes());
    }

    @Test
    @Order(8)
    void deleteUserLike() {
        int filmId = 2;
        filmDbStorage.deleteUserLike(filmId, 1);
        filmDbStorage.deleteUserLike(filmId, 2);

        List<Film> mostPopular = new ArrayList<>(filmDbStorage.getPopularFilms(1));
        assertNotNull(mostPopular);
        assertEquals(1, mostPopular.size());

        Film got = mostPopular.get(0);
        assertEquals(1, got.getId());
    }
}