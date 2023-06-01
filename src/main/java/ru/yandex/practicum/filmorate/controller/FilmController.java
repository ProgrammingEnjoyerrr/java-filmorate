package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;

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
        ++id;
        film.setId(id);
        idToFilm.put(id, film);

        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        if (!idToFilm.containsKey(film.getId())) {
            throw new FilmValidationException("Попытка обновить фильм " +
                    "с несуществующим id " + film.getId());
        }

        idToFilm.put(id, film);
        return film;
    }

    @GetMapping
    public Collection<Film> getAllFilms() {
        return idToFilm.values();
    }
}
