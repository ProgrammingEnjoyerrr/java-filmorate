package ru.yandex.practicum.filmorate.storage.inmemory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Qualifier("inMemoryFilmStorage")
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
            throw new FilmNotFoundException("Попытка обновить фильм " +
                    "с несуществующим id " + film.getId());
        }

        idToFilm.put(film.getId(), film);
        return film;
    }

    @Override
    public Film getFilmById(int filmId) {
        final Film film = idToFilm.get(filmId);
        if (film == null) {
            throw new FilmNotFoundException("Фильм с id " + filmId + " не найден.");
        }

        return film;
    }

    @Override
    public Collection<Film> getFilms() {
        return new ArrayList<>(idToFilm.values());
    }

    @Override
    public void addUserLike(int filmId, int userId) {
        final Film film = getFilmById(filmId);

        film.addUserLike((long) userId);
    }

    @Override
    public void deleteUserLike(int filmId, int userId) {
        final Film film = getFilmById(filmId);

        film.removeUserLike((long) userId);
    }

    @Override
    public Collection<Film> getPopularFilms(int count) {
        return idToFilm.values().stream()
                .sorted((o1, o2) -> o2.getUserLikes().size() - o1.getUserLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }
}
