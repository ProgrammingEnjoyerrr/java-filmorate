package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Film createFilm(Film film);

    Film updateFilm(Film film);

    Collection<Film> getFilms();

    Film getFilmById(int userId);

    void addUserLike(int filmId, int userId);

    void deleteUserLike(int filmId, int userId);

    Collection<Film> getPopularFilms(int count);
}
