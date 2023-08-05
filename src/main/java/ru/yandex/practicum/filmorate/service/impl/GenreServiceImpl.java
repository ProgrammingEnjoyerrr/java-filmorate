package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreServiceImpl implements GenreService {

    private final GenreStorage genreStorage;

    @Override
    public Genre addGenre(Genre genre) {
        Optional<Genre> genreOpt = genreStorage.addGenre(genre);
        if (genreOpt.isEmpty()) {
            // throw
        }

        return genreOpt.get();
    }

    @Override
    public Collection<Genre> getAllGenres() {
        return null;
    }

    @Override
    public Genre getGenreById(int id) {
        return null;
    }
}
