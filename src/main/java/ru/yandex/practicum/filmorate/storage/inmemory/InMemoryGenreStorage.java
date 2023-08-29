package ru.yandex.practicum.filmorate.storage.inmemory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Qualifier("inMemoryGenreStorage")
public class InMemoryGenreStorage implements GenreStorage {
    private final Map<Integer, Genre> idToGenre = Map.of(
            1, new Genre(1, "Комедия"),
            2, new Genre(2, "Драма"),
            3, new Genre(3, "Мультфильм"),
            4, new Genre(4, "Триллер"),
            5, new Genre(5, "Документальный"),
            6, new Genre(6, "Боевик")
    );

    @Override
    public Collection<Genre> getAllGenres() {
        return idToGenre.values()
                .stream()
                .sorted(Comparator.comparingInt(Genre::getId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Genre> getGenreById(int id) {
        return Optional.ofNullable(idToGenre.get(id));
    }
}
