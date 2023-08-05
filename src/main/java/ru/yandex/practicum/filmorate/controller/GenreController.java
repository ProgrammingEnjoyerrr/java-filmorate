package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.Collection;

@RestController
@RequestMapping("/genres")
@Slf4j
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genresService;

    @GetMapping
    public Collection<Genre> getAllGenres() {
        log.debug("got request GET /genres");

        return genresService.getAllGenres();
    }

    @GetMapping(value = "/{id}")
    public Genre getGenreById(@PathVariable("id") int genreId) {
        log.debug("got request GET /genres/{}", genreId);

        return genresService.getGenreById(genreId);
    }

}
