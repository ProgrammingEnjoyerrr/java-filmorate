package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

public interface GenreService {

    Genre addGenre(Genre genre);

    Collection<Genre> getAllGenres();

    Genre getGenreById(int id);
}
