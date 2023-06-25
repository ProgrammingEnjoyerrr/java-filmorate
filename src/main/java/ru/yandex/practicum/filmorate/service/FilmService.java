package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmService {
    Film createFilm(Film film);

    Film updateFilm(Film film);

    Film getFilmById(String filmId);

    Collection<Film> getFilms();

    void addUserLike(String filmId, String userId);

    void deleteUserLike(String filmId, String userId);

    Collection<Film> getPopularFilms(String count);
}
