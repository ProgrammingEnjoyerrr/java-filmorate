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
        log.debug("got request GET /films");
        return service.getFilms();
    }

    @GetMapping(value = "/{id}")
    public Film getFilmById(@PathVariable("id") String filmIdStr) {
        log.debug("got request GET /films/{}", filmIdStr);
        int filmId = Integer.parseInt(filmIdStr);

        return service.getFilmById(filmId);
    }


    @PutMapping(value = "/{id}/like/{userId}")
    public void addUserLike(@PathVariable("id") String filmIdStr,
                            @PathVariable("userId") String userIdStr) {
        log.debug("got request PUT /films/{}/like/{}", filmIdStr, userIdStr);
        int filmId = Integer.parseInt(filmIdStr);
        int userId = Integer.parseInt(userIdStr);

        service.addUserLike(filmId, userId);
    }

    @DeleteMapping(value = "/{id}/like/{userId}")
    public void deleteUserLike(@PathVariable("id") String filmIdStr,
                               @PathVariable("userId") String userIdStr) {
        log.debug("got request DELETE /films/{}/like/{}", filmIdStr, userIdStr);
        int filmId = Integer.parseInt(filmIdStr);
        int userId = Integer.parseInt(userIdStr);

        service.deleteUserLike(filmId, userId);
    }

    @GetMapping(value = "/popular")
    public Collection<Film> getPopularFilms(@RequestParam(defaultValue = "10") Integer count) {
        log.debug("got request GET /films/popular");
        log.debug("query params: count={}", count);
        return service.getPopularFilms(count);
    }
}
