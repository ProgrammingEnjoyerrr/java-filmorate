package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreServiceImpl implements GenreService {

    @Qualifier("genreDbStorage")
    private final GenreStorage genreStorage;

    @Override
    public Collection<Genre> getAllGenres() {
        log.info("searching all genres");
        return genreStorage.getAllGenres();
    }

    @Override
    public Genre getGenreById(int id) {
        log.info("searching genre with id {}", id);
        Optional<Genre> genreOpt = genreStorage.getGenreById(id);

        if (genreOpt.isEmpty()) {
            log.error("failed to find genre with id {}", id);
            throw new GenreNotFoundException("Попытка получить жанр с несуществующим id " + id);
        }

        final Genre genre = genreOpt.get();
        log.info("found genre {}", genre);
        return genre;
    }
}
