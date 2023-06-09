package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmServiceImpl implements FilmService {

    private final UserService userService;

    private final FilmStorage filmStorage;

    @Override
    public Film createFilm(Film film) {
        log.info("creating film {}.", film);
        return filmStorage.createFilm(film);
    }

    @Override
    public Film updateFilm(Film film) {
        log.info("updating film {}.", film);
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
}
