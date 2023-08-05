package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmServiceImpl implements FilmService {

    private final UserService userService;

    private final FilmStorage filmStorage;

    @Qualifier("inMemoryMpaStorage")
    private final MpaStorage mpaStorage;

    @Qualifier("inMemoryGenreStorage")
    private final GenreStorage genreStorage;

    @Override
    public Film createFilm(Film film) {
        log.info("creating film {}.", film);

        assignMpa(film);
        assignGenres(film);

        return filmStorage.createFilm(film);
    }

    @Override
    public Film updateFilm(Film film) {
        log.info("updating film {}.", film);

        assignMpa(film);
        assignGenres(film);

        return filmStorage.updateFilm(film);
    }

    @Override
    public Collection<Film> getFilms() {
        log.info("searching all films.");
        return filmStorage.getFilms();
    }

    @Override
    public Film getFilmById(String filmIdStr) {
        int filmId = Integer.parseInt(filmIdStr);

        log.info("searching film with id {}.", filmId);
        return filmStorage.getFilmById(filmId);
    }

    @Override
    public void addUserLike(String filmIdStr, String userIdStr) {
        int filmId = Integer.parseInt(filmIdStr);
        int userId = Integer.parseInt(userIdStr);

        log.info("adding user's (with id {}) like for film with id {}", userId, filmId);
        filmStorage.addUserLike(filmId, userId);
    }

    @Override
    public void deleteUserLike(String filmIdStr, String userIdStr) {
        // will throw if not exist
        userService.getUserById(userIdStr);

        int filmId = Integer.parseInt(filmIdStr);
        int userId = Integer.parseInt(userIdStr);

        log.info("removing user's (with id {}) like for film with id {}", userId, filmId);
        filmStorage.deleteUserLike(filmId, userId);
    }

    @Override
    public Collection<Film> getPopularFilms(String countStr) {
        int count = Integer.parseInt(countStr);

        log.info("searching top {} popular films", count);
        return filmStorage.getPopularFilms(count);
    }

    private void assignMpa(Film film) {
        Mpa mpa = film.getMpa();
        if (mpa == null) {
            return;
        }

        int mpaId = mpa.getId();
        Optional<Mpa> mpaOpt = mpaStorage.getMpaById(mpaId);
        if (mpaOpt.isEmpty()) {
            throw new MpaNotFoundException("Попытка получить MPA с несуществующим id " + mpaId);
        }

        film.setMpa(mpaOpt.get());
    }

    private void assignGenres(Film film) {
        List<Genre> genres = film.getGenres();
        if (genres == null || genres.isEmpty()) {
            return;
        }

        Set<Genre> newGenres = new HashSet<>();
        for (Genre genre : genres) {
            int id = genre.getId();
            Optional<Genre> genreOpt = genreStorage.getGenreById(id);
            if (genreOpt.isEmpty()) {
                throw new GenreNotFoundException("Попытка получить жанр с несуществующим id " + id);
            }
            newGenres.add(genreOpt.get());
        }

        // TODO assert genres.size() == newGenres.size() -> throw

        // Postman - тесты хотят от меня упорядоченности(
        film.setGenres(newGenres
                .stream()
                .sorted(Comparator.comparing(Genre::getId))
                .collect(Collectors.toList()));
    }
}
