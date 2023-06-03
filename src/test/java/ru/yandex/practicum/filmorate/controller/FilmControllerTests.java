package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.*;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FilmControllerTests {

    private static FilmController filmController;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final int ANY_ID = 0;

    @BeforeAll
    private static void init() {
        filmController = new FilmController();
    }

    @Test
    @Order(1)
    void createFilmShouldBeOk() {
        final Film film = new Film(ANY_ID, "nisi eiusmod", "adipisicing", LocalDate.parse("1967-03-25", FORMATTER), 100);

        final Film created = filmController.create(film);

        assertEquals(1, created.getId());
        assertEquals(created.getName(), film.getName());
        assertEquals(created.getDescription(), film.getDescription());
        assertEquals(created.getReleaseDate(), film.getReleaseDate());
        assertEquals(created.getDuration(), film.getDuration());
    }

    @Test
    @Order(2)
    void updateFilmShouldBeOk() {
        final Film film = new Film(1, "Film Updated", "New film update description", LocalDate.parse("1989-04-17", FORMATTER), 190);

        final Film updated = filmController.update(film);

        assertEquals(1, updated.getId());
        assertEquals(updated.getName(), film.getName());
        assertEquals(updated.getDescription(), film.getDescription());
        assertEquals(updated.getReleaseDate(), film.getReleaseDate());
        assertEquals(updated.getDuration(), film.getDuration());
    }

    @Test
    @Order(3)
    void updateUnknownFilmShouldThrow() {
        final Film film = new Film(9999, "Film Updated", "New film update description", LocalDate.parse("1989-04-17", FORMATTER), 190);

        final FilmValidationException exception = assertThrows(FilmValidationException.class,
                () -> filmController.update(film));
        assertEquals("Попытка обновить фильм с несуществующим id 9999", exception.getMessage());
    }

    @Test
    @Order(4)
    void getAllFilmsShouldBeOk() {
        final Collection<Film> users = filmController.getAllFilms();
        assertEquals(1, users.size());

        final Film film = users.stream().limit(1).collect(Collectors.toList()).get(0);

        assertEquals(1, film.getId());
        assertEquals("Film Updated", film.getName());
        assertEquals("New film update description", film.getDescription());
        assertEquals(LocalDate.parse("1989-04-17", FORMATTER), film.getReleaseDate());
        assertEquals(190, film.getDuration());
    }
}
