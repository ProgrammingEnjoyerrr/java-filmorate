package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmService {
    Film createFilm(Film film);

    Film updateFilm(Film film);

    Film getFilm(int id);

    Collection<Film> getFilms();
}
