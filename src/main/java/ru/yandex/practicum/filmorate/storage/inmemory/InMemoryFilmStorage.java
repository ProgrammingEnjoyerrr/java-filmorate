package ru.yandex.practicum.filmorate.storage.inmemory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> idToFilm = new HashMap<>();
    private int id = 0;

    @Override
    public Film createFilm(Film film) {
        ++id;
        film.setId(id);
        idToFilm.put(id, film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (!idToFilm.containsKey(film.getId())) {
            throw new FilmValidationException("Попытка обновить фильм " +
                    "с несуществующим id " + film.getId());
        }

        idToFilm.put(id, film);
        return film;
    }

    @Override
    public Film getFilm(int id) {
        return null;
    }

    @Override
    public Collection<Film> getFilms() {
        return new ArrayList<>(idToFilm.values());
    }
}
