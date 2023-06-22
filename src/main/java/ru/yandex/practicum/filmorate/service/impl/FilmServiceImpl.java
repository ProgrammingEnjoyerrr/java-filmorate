package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final UserService userService;

    private final FilmStorage storage;

    @Override
    public Film createFilm(Film film) {
        return storage.createFilm(film);
    }

    @Override
    public Film updateFilm(Film film) {
        return storage.updateFilm(film);
    }

    @Override
    public Collection<Film> getFilms() {
        return storage.getFilms();
    }

    @Override
    public Film getFilmById(int id) {
        return storage.getFilmById(id);
    }

    @Override
    public void addUserLike(int filmId, int userId) {
        storage.addUserLike(filmId, userId);
    }

    @Override
    public void deleteUserLike(int filmId, int userId) {
        // will throw if not exist
        userService.getUserById(userId);
        storage.deleteUserLike(filmId, userId);
    }

    @Override
    public Collection<Film> getPopularFilms(int count) {
        return storage.getPopularFilms(count);
    }
}
