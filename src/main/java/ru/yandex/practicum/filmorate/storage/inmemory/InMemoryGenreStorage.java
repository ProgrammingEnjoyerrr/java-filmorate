package ru.yandex.practicum.filmorate.storage.inmemory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.*;

@Component
public class InMemoryGenreStorage implements GenreStorage {
    private final Set<Genre> genres = new HashSet<>();
    private final Map<Integer, Genre> idToGenre = new HashMap<>();
    private int id = 0;

    @Override
    public Optional<Genre> addGenre(Genre genre) {
        if (genres.contains(genre)) {
            return Optional.empty();
        }

        ++id;
        genre.setId(id);
        genres.add(genre);
        idToGenre.put(id, genre);

        return Optional.of(genre);
    }

    @Override
    public Collection<Genre> getAllGenres() {
        return new ArrayList<>(idToGenre.values());
    }

    @Override
    public Optional<Genre> getGenreById(int id) {
        return Optional.ofNullable(idToGenre.get(id));
    }
}
