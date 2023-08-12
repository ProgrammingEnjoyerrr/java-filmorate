package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class GenreDbStorageTest {

    private final GenreDbStorage genreDbStorage;

    private final Map<Integer, String> expectedResults = Map.of(
            1, "Комедия",
            2, "Драма",
            3, "Мультфильм",
            4, "Триллер",
            5, "Документальный",
            6, "Боевик"
    );

    @Test
    void getGenreByKnownIdShouldBeOk() {
        for (final var entry : expectedResults.entrySet()) {
            final Integer expectedId = entry.getKey();
            final String expectedName = entry.getValue();

            Optional<Genre> genreOpt = genreDbStorage.getGenreById(expectedId);
            assertTrue(genreOpt.isPresent());

            Genre genre = genreOpt.get();
            assertEquals(expectedId, genre.getId());
            assertEquals(expectedName, genre.getName());
        }
    }

    @Test
    void getGenreByUnknownIdShouldReturnEmptyOptional() {
        final int unknownId = 100500;
        Optional<Genre> genreOpt = genreDbStorage.getGenreById(unknownId);
        assertTrue(genreOpt.isEmpty());
    }

    @Test
    void getAllGenresShouldBeOk() {
        List<Genre> genres = new ArrayList<>(genreDbStorage.getAllGenres());
        assertNotNull(genres);
        assertFalse(genres.isEmpty());
        for (final Genre genre : genres) {
            final Integer expectedId = genre.getId();
            final String expectedName = expectedResults.get(expectedId);

            assertEquals(expectedId, genre.getId());
            assertEquals(expectedName, genre.getName());
        }

        final List<Genre> sorted = genreDbStorage.getAllGenres().stream()
                .sorted(Comparator.comparingInt(Genre::getId))
                .collect(Collectors.toList());
        assertEquals(sorted, genres);
    }
}