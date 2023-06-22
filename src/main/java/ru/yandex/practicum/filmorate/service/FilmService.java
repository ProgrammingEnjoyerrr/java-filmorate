package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmService {
    Film createFilm(Film film);

    Film updateFilm(Film film);

    Film getFilmById(int id);

    Collection<Film> getFilms();

    void addUserLike(int filmId, int userId);

    void deleteUserLike(int filmId, int userId);

    Collection<Film> getPopularFilms(int count);
}
