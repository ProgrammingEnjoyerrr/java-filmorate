package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/films")
@Validated
@Slf4j
@RequiredArgsConstructor
public class FilmController {

    private final FilmService service;

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.debug("got request POST /films");
        log.debug("request body: {}", film);

        Film created = service.createFilm(film);

        log.debug("film created");
        return created;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.debug("got request PUT /films");
        log.debug("request body: {}", film);

        Film updated = service.updateFilm(film);

        log.debug("film updated");
        return updated;
    }

    @GetMapping
    public Collection<Film> getAllFilms() {
        return service.getFilms();
    }
}
