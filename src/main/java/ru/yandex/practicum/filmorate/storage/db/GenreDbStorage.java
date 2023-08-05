package ru.yandex.practicum.filmorate.storage.db;

import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.Collection;
import java.util.Optional;

public class GenreDbStorage implements GenreStorage {
    @Override
    public Collection<Genre> getAllGenres() {
        return null;
    }

    @Override
    public Optional<Genre> getGenreById(int id) {
        return Optional.empty();
    }
}
