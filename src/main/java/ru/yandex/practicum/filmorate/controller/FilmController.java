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

    private final FilmService filmService;

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.debug("got request POST /films");
        log.debug("request body: {}", film);

        Film created = filmService.createFilm(film);

        log.debug("film created");
        return created;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.debug("got request PUT /films");
        log.debug("request body: {}", film);

        Film updated = filmService.updateFilm(film);

        log.debug("film updated");
        return updated;
    }

    @GetMapping
    public Collection<Film> getAllFilms() {
        log.debug("got request GET /films");

        return filmService.getFilms();
    }

    @GetMapping(value = "/{id}")
    public Film getFilmById(@PathVariable("id") String filmIdStr) {
        log.debug("got request GET /films/{}", filmIdStr);

        return filmService.getFilmById(filmIdStr);
    }


    @PutMapping(value = "/{id}/like/{userId}")
    public void addUserLike(@PathVariable("id") String filmIdStr,
                            @PathVariable("userId") String userIdStr) {
        log.debug("got request PUT /films/{}/like/{}", filmIdStr, userIdStr);

        filmService.addUserLike(filmIdStr, userIdStr);
    }

    @DeleteMapping(value = "/{id}/like/{userId}")
    public void deleteUserLike(@PathVariable("id") String filmIdStr,
                               @PathVariable("userId") String userIdStr) {
        log.debug("got request DELETE /films/{}/like/{}", filmIdStr, userIdStr);

        filmService.deleteUserLike(filmIdStr, userIdStr);
    }

    @GetMapping(value = "/popular")
    public Collection<Film> getPopularFilms(@RequestParam(defaultValue = "10") String count) {
        log.debug("got request GET /films/popular");
        log.debug("query params: count={}", count);

        return filmService.getPopularFilms(count);
    }
}
