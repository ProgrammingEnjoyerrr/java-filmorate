package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final Map<Integer, Film> idToFilm = new HashMap<>();
    private int id = 0;

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.debug("got request POST /films");
        log.debug("request body: {}", film);

        ++id;
        film.setId(id);
        idToFilm.put(id, film);

        log.debug("film created");
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.debug("got request PUT /films");
        log.debug("request body: {}", film);

        if (!idToFilm.containsKey(film.getId())) {
            log.error("attempt to update film with unknown id: {}", film.getId());
            throw new FilmValidationException("Попытка обновить фильм " +
                    "с несуществующим id " + film.getId());
        }

        idToFilm.put(id, film);
        log.debug("film updated");
        return film;
    }

    @GetMapping
    public Collection<Film> getAllFilms() {
        return new ArrayList<>(idToFilm.values());
    }
}
