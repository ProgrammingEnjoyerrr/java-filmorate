package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> idToFilm = new HashMap<>();
    private int id = 0;

    @PostMapping
    @ResponseBody
    public Film create(@Valid @RequestBody Film film) {
        validate(film);

        ++id;
        film.setId(id);
        idToFilm.put(id, film);

        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        if (!idToFilm.containsKey(film.getId())) {
            throw new FilmValidationException("Попытка обновить фильм " +
                    "с несуществующим id " + film.getId());
        }

        validate(film);

        idToFilm.put(id, film);
        return film;
    }

    @GetMapping
    public Collection<Film> getAllFilms() {
        return idToFilm.values();
    }

    private void validate(final Film film) {
        if (film.getName().isBlank()) {
            throw new FilmValidationException("название не может быть пустым.");
        }

        if (film.getDescription().length() > 200) {
            throw new FilmValidationException("максимальная длина описания — 200 символов");
        }

        final LocalDate filmBirthDay = LocalDate.of(1895, 12, 28);
        if (film.getReleaseDate().isBefore(filmBirthDay)) {
            throw new FilmValidationException("дата релиза — не раньше 28 декабря 1895 года");
        }

        if (film.getDuration() <= 0) {
            throw new FilmValidationException("продолжительность фильма должна быть положительной");
        }
    }
}
